package com.google.sps.data;

public final class Comment  {

  private final long id;
  private final String words;
  private final String  mood;
  private final long timestamp;

  public Comment(long id, String words, String mood, long timestamp) {
    this.id = id;
    this.words = words;
    this.mood = mood;
    this.timestamp = timestamp;
  }
}