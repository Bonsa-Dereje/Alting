package org.alting;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.json.JSONArray;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TelegramFetcher {

    record Channel(long id, String name, String username) {}

    public static void fetchAllChannels() throws Exception {
        List<Channel> channels = new ArrayList<>();

        try (Connection conn = PostgresDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                 "SELECT channel_id, channel_name, channel_username FROM channels WHERE active ORDER BY channel_id");
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                channels.add(new Channel(
                    rs.getLong("channel_id"),
                    rs.getString("channel_name"),
                    rs.getString("channel_username")));
            }
        }

        System.out.println("Found " + channels.size() + " active channels.");

        int total = 0;
        for (Channel ch : channels) {
            try {
                int n = fetchOneChannel(ch);
                System.out.println(ch.username() + ": " + n + " new");
                total += n;
                Thread.sleep(2000);
            } catch (Exception e) {
                System.out.println("FAILED " + ch.username() + ": " + e.getMessage());
            }
        }
        System.out.println("Done. " + total + " new posts total.");
    }

    static int fetchOneChannel(Channel ch) throws Exception {
        String sql = "INSERT INTO raw_posts (channel_id, channel_name, message_id, message_text, image_urls, posted_at) "
                   + "VALUES (?, ?, ?, ?, ?::jsonb, ?) "
                   + "ON CONFLICT (channel_id, message_id) DO NOTHING";

        int inserted = 0;

        Document doc = Jsoup.connect("https://t.me/s/" + ch.username())
                .userAgent("Mozilla/5.0")
                .get();

        Elements posts = doc.select("div.tgme_widget_message[data-post]");

        try (Connection conn = PostgresDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            for (Element post : posts) {
                List<String> urls = new ArrayList<>();
                for (Element photo : post.select("a.tgme_widget_message_photo_wrap")) {
                    String style = photo.attr("style");
                    int start = style.indexOf("url('");
                    int end = style.indexOf("')", start);
                    if (start >= 0 && end > start) {
                        urls.add(style.substring(start + 5, end));
                    }
                }
                String imageJson = new JSONArray(urls).toString();

                String dataPost = post.attr("data-post");
                String messageId = dataPost.substring(dataPost.indexOf('/') + 1);

                Element textEl = post.selectFirst("div.tgme_widget_message_text");
                String text = (textEl != null ? textEl.text() : "no text");

                Element timeEl = post.selectFirst("time[datetime]");
                OffsetDateTime postedAt = (timeEl != null)
                        ? OffsetDateTime.parse(timeEl.attr("datetime"))
                        : null;

                ps.setLong(1, ch.id());
                ps.setString(2, ch.name());
                ps.setLong(3, Long.parseLong(messageId));
                ps.setString(4, text);
                ps.setString(5, imageJson);
                ps.setObject(6, postedAt);

                inserted += ps.executeUpdate();
            }
        }

        return inserted;
    }

    public static void main(String[] args) throws Exception {
        fetchAllChannels();
    }
}
