package com.example.android.bgdb.model;

/**
 * Stores board game data.
 */
public class BoardGame {

    private String id;
    private String name;
    private String year;
    private String thumbnailUrl;
    private byte[] thumbnailBlob;
    private String rank;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public byte[] getThumbnailBlob() {
        return thumbnailBlob;
    }

    public void setThumbnailBlob(byte[] thumbnailBlob) {
        this.thumbnailBlob = thumbnailBlob;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }
}
