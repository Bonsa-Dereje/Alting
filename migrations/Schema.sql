CREATE TABLE IF NOT EXISTS channels(
    channel_id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    channel_name TEXT NOT NULL,
    channel_username TEXT NOT NULL UNIQUE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS raw_posts(
  id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  channel_id BIGINT NOT NULL REFERENCES channels(channel_id) ON DELETE CASCADE,
  channel_name TEXT NOT NULL,
  message_id BIGINT NOT NULL,
  message_text TEXT NOT NULL,
  image_urls JSONB NOT NULL DEFAULT '[]'::jsonb,
  processed BOOLEAN NOT NULL DEFAULT FALSE,
  posted_at TIMESTAMPTZ,
  created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
  UNIQUE(channel_id, message_id)
);

CREATE TABLE IF NOT EXISTS spotlight_opportunities(
    opportunity_id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    raw_post_id BIGINT NOT NULL UNIQUE REFERENCES raw_posts(id) ON DELETE CASCADE,
    title TEXT NOT NULL,
    summary TEXT NOT NULL,
    category TEXT NOT NULL,
    tags JSONB NOT NULL DEFAULT '[]'::jsonb,
    deadline TIMESTAMPTZ,
    location TEXT,
    target_audience TEXT,
    importance_level TEXT NOT NULL,
    image_url TEXT,
    source_channel_name TEXT NOT NULL,
    original_post TEXT NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);