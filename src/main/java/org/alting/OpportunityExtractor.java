package org.alting;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import io.github.cdimascio.dotenv.Dotenv;


public class OpportunityExtractor {
    record RawPost(long id, String channelName, String messageText, String imageUrls) {}

    private static final Dotenv dotenv = Dotenv.load();
    private static final String apiKey = dotenv.get("OPENROUTER_API_KEY");
    private static final String URL = "https://openrouter.ai/api/v1/chat/completions";   
    
    private static final String SYSTEM_PROMPT = """
        You convert a Telegram post from an opportunities channel into structured JSON.

Return ONLY data that matches the provided schema. Do not add commentary.

Rules:
- is_opportunity: true ONLY if the post describes a concrete opportunity a
  student can apply to or attend — a scholarship, fellowship, internship,
  competition, workshop, conference, hackathon, training, or job. Set it
  false for greetings, promotions, "follow us" messages, results
  announcements, or anything with nothing to apply to.
- title: a short, specific name for the opportunity (max ~10 words). No emojis,
  no hashtags.
- summary: 1-2 plain sentences describing what it is and what the applicant
  gets. No emojis.
- category: choose the single best fit. Use "fellowship" for funded
  fellowships/programs, "scholarship" for study funding, "job" for paid
  employment, "training"/"workshop" for skills programs, "volunteer" for
  unpaid roles. Use "other" only if nothing fits.
- tags: 2-5 short lowercase keywords (e.g. "fully funded", "remote", "women").
- deadline: the application deadline as an ISO date (YYYY-MM-DD). If no
  deadline is stated, use null. If only a month/year is given, use null.
- location: the city/country, or "Online" if remote. null if not stated.
- target_audience: who is eligible, in one short phrase. null if not stated.
- importance_level: "high" for fully funded, prestigious, or soon-closing
  opportunities; "medium" for normal ones; "low" for minor or local ones.
- Ignore hashtags, emojis, "share this", contact numbers, and the
  "follow us @channel" footer — they are never part of the data.
""";

    private static JSONObject buildResponseFormat() {
        JSONObject props = new JSONObject();
        props.put("is_opportunity", new JSONObject().put("type", "boolean"));
        props.put("title",   new JSONObject().put("type", "string"));
        props.put("summary", new JSONObject().put("type", "string"));
        props.put("category", new JSONObject()
                .put("type", "string")
                .put("enum", new JSONArray()
                        .put("scholarship").put("internship").put("competition").put("fellowship").put("volunteer").put("job")
                        .put("workshop").put("conference").put("hackathon").put("other")));
        props.put("tags", new JSONObject()
                .put("type", "array")
                .put("items", new JSONObject().put("type", "string")));
        props.put("deadline",        nullableString());
        props.put("location",        nullableString());
        props.put("target_audience", nullableString());
        props.put("importance_level", new JSONObject()
                .put("type", "string")
                .put("enum", new JSONArray().put("high").put("medium").put("low")));

        JSONObject schema = new JSONObject()
                .put("type", "object")
                .put("additionalProperties", false)
                .put("required", new JSONArray()
                        .put("is_opportunity").put("title").put("summary")
                        .put("category").put("tags").put("deadline")
                        .put("location").put("target_audience").put("importance_level"))
                .put("properties", props);

        JSONObject jsonSchema = new JSONObject()
                .put("name", "opportunity")
                .put("strict", true)
                .put("schema", schema);

        return new JSONObject().put("type", "json_schema").put("json_schema", jsonSchema);
    }

    // Helper: a field that may be a string OR null — {"type": ["string","null"]}
    private static JSONObject nullableString() {
        return new JSONObject().put("type", new JSONArray().put("string").put("null"));
    }
    public static JSONObject structureOnePost(String messageText) throws Exception {
        JSONObject body = new JSONObject();
        body.put("models", new JSONArray()
                .put("nvidia/nemotron-3-super-120b-a12b:free")
                .put("openai/gpt-oss-20b:free")
                .put("google/gemma-4-26b-a4b-it:free"));
        body.put("messages", new JSONArray()
                .put(new JSONObject().put("role", "system").put("content", SYSTEM_PROMPT))
                .put(new JSONObject().put("role", "user").put("content", messageText)));
        body.put("response_format", buildResponseFormat());

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(URI.create(URL))
            .header("Authorization", "Bearer " + apiKey)
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(body.toString()))
            .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            System.out.println("HTTP " + response.statusCode() + ": " + response.body());
            return null;
        }
        JSONObject root = new JSONObject(response.body());
        String servedBy = root.getString("model");
        String content = root.getJSONArray("choices")
            .getJSONObject(0)
            .getJSONObject("message")
            .getString("content");
        // Free models sometimes wrap the JSON in ```fences``` or stray text.
        // Slice from the first '{' to the last '}' so we parse only the object.
        int start = content.indexOf('{');
        int end = content.lastIndexOf('}');
        if (start < 0 || end < 0) {
            System.out.println("No JSON found in response:\n" + content);
            return null;
        }
        content = content.substring(start, end + 1);

        System.out.println("served by: " + servedBy);
        return new JSONObject(content);
    }

    public static void processUnprocessed() throws Exception {
    List<RawPost> posts = new ArrayList<>();

    String selectSql =
        "SELECT id, channel_name, message_text, image_urls FROM raw_posts " +
        "WHERE processed = false AND message_text <> 'no text' " +
        "ORDER BY id LIMIT 45";

    try (Connection conn = PostgresDB.getConnection();
         PreparedStatement ps = conn.prepareStatement(selectSql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            posts.add(new RawPost(
                rs.getLong("id"),
                rs.getString("channel_name"),
                rs.getString("message_text"),
                rs.getString("image_urls")));
        }
    }

    System.out.println("Read " + posts.size() + " posts:");
    int opportunities = 0, junk = 0, failed = 0, consecutiveFails = 0;

        String insertSql =
        "INSERT INTO spotlight_opportunities " +
        "(raw_post_id, title, summary, category, tags, deadline, location, " +
        " target_audience, importance_level, image_url, source_channel_name, original_post) " +
        "VALUES (?, ?, ?, ?, ?::jsonb, ?::timestamptz, ?, ?, ?, ?, ?, ?) " +
        "ON CONFLICT (raw_post_id) DO NOTHING";
    String markSql = "UPDATE raw_posts SET processed = true WHERE id = ?";

    try (Connection conn = PostgresDB.getConnection();
         PreparedStatement insert = conn.prepareStatement(insertSql);
         PreparedStatement mark = conn.prepareStatement(markSql)) {

        for (RawPost post : posts) {
            JSONObject data = structureOnePost(post.messageText());
            Thread.sleep(3500);   // pace: stay under 20 requests/minute

            if (data == null) {                        // AI failed — leave for next run
                failed++;
                consecutiveFails++;
                if(consecutiveFails > 3) {
                    System.out.println(" More than 3 failures in a row, Likely Rate-Limited. Stopping.");
                    break;
                }
                continue;
            }
            consecutiveFails = 0;
            if (!data.getBoolean("is_opportunity")) {  // junk — mark done, insert nothing
                junk++;
                mark.setLong(1, post.id());
                mark.executeUpdate();
                System.out.println("#" + post.id() + "  not an opportunity");
                continue;
            }

            // A model occasionally returns is_opportunity=true with a blank title.
            // Treat that like junk: mark done, insert nothing (no empty cards).
            if (data.getString("title").isBlank()) {
                junk++;
                mark.setLong(1, post.id());
                mark.executeUpdate();
                System.out.println("#" + post.id() + "  blank title, skipped");
                continue;
            }

            opportunities++;
            JSONArray imgs = new JSONArray(post.imageUrls());
            String imageUrl = imgs.isEmpty() ? null : imgs.getString(0);

            insert.setLong(1, post.id());
            insert.setString(2, data.getString("title"));
            insert.setString(3, data.getString("summary"));
            insert.setString(4, data.getString("category"));
            insert.setString(5, data.getJSONArray("tags").toString());
            insert.setString(6, data.isNull("deadline") ? null : data.getString("deadline"));
            insert.setString(7, data.isNull("location") ? null : data.getString("location"));
            insert.setString(8, data.isNull("target_audience") ? null : data.getString("target_audience"));
            insert.setString(9, data.getString("importance_level"));
            insert.setString(10, imageUrl);
            insert.setString(11, post.channelName());
            insert.setString(12, post.messageText());
            insert.executeUpdate();

            mark.setLong(1, post.id());
            mark.executeUpdate();
            System.out.println("#" + post.id() + "  -> " + data.getString("title"));
        }
    }
    System.out.println("opportunity=" + opportunities + " junk=" + junk + " failed=" + failed);
}


    public static void main(String[] args) throws Exception {
        // String post2258 = """
        //     #Opportunity_Alerts📣 🚀Fully Funded Africa CDC Fellowship 2026 for Public Health Professionals🚀 ✨Are you public health professional ready to strengthen disease prevention & outbreak response across Africa? Apply for Africa CDC African Epidemic Services – Epidemiology Track Fellowship 2026. What You'll Gain: 🔹3 months of training in Addis Ababa, Ethiopia 🔹21 months of field placement in an African Union Member State 🔹Monthly stipend, travel, health insurance, learning materials & other Who Can Apply? 🔸Citizens of an AU Member State 🔸Under 35 years old 🔸Bachelor's or Master's degree in a health field 🔸At least 3 years of public health experience 🔸Proficient in at least one AU official language 📅Duration: 2 Years 🗓Program Starts: October 2026 📍Location: Addis Ababa, Ethiopia + Field Placement in an AU Member State 🔗Apply: https://ow.ly/H8O650Zob1b 📝Deadline: August 26, 2026 "If this isn't for you, please share it with others who might be interested."🙏 Follow us👇for more opportunities @opportunity_alerts
        //     """;
        // JSONObject data = structureOnePost(post2258);
        // System.out.println(data.toString(2));
        TelegramFetcher.fetchAllChannels();
        processUnprocessed();
    }
}
