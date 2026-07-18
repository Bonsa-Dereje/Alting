package org.alting;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class TelegramFetcher {
    public static void fetchTelegramPosts() throws Exception {
        Document doc = Jsoup.connect("https://t.me/s/opportunity_alerts")
                .userAgent("Mozilla/5.0")
                .get();
        
        Elements posts = doc.select("div.tgme_widget_message_wrap");
    if (posts.isEmpty()) {
        System.out.println("No posts found.");
    } else {
        for(Element post : posts) {
            String dataPost = post.attr("data_post");
            String messageId = dataPost.substring(dataPost.indexOf('/') + 1);

            Element textEl = post.selectFirst("div.tgme_widget_message_text");
            String text = (textEl != null ? textEl.text() : "no text");
            
            System.out.println("—— #" + messageId + " ——");
            System.out.println(text);
        }}
    }
    public static void main(String[] args) throws Exception {
        fetchTelegramPosts();
    }
};
