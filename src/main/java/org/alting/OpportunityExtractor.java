package org.alting;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import io.github.cdimascio.dotenv.Dotenv;


public class OpportunityExtractor {
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
- category: choose the single best fit from the allowed values.
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
}
