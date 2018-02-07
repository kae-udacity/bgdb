package com.example.android.bgdb.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Stores board game data.
 */
public class BoardGame implements Parcelable {

    private String id;
    private String name;
    private String description;
    private String year;
    private String rank;
    private String ranks;
    private String rating;
    private String thumbnailUrl;
    private byte[] thumbnailBlob;
    private String bannerUrl;
    private byte[] bannerBlob;

    public BoardGame() {}

    protected BoardGame(Parcel in) {
        id = in.readString();
        name = in.readString();
        description = in.readString();
        year = in.readString();
        rank = in.readString();
        ranks = in.readString();
        rating = in.readString();
        thumbnailUrl = in.readString();
        thumbnailBlob = in.createByteArray();
        bannerUrl = in.readString();
        bannerBlob = in.createByteArray();
    }

    public static final Creator<BoardGame> CREATOR = new Creator<BoardGame>() {
        @Override
        public BoardGame createFromParcel(Parcel in) {
            return new BoardGame(in);
        }

        @Override
        public BoardGame[] newArray(int size) {
            return new BoardGame[size];
        }
    };

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getRanks() {
        return ranks;
    }

    public void setRanks(String ranks) {
        this.ranks = ranks;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
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

    public String getBannerUrl() {
        return bannerUrl;
    }

    public void setBannerUrl(String bannerUrl) {
        this.bannerUrl = bannerUrl;
    }

    public byte[] getBannerBlob() {
        return bannerBlob;
    }

    public void setBannerBlob(byte[] bannerBlob) {
        this.bannerBlob = bannerBlob;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeString(description);
        parcel.writeString(year);
        parcel.writeString(rank);
        parcel.writeString(ranks);
        parcel.writeString(rating);
        parcel.writeString(thumbnailUrl);
        parcel.writeByteArray(thumbnailBlob);
        parcel.writeString(bannerUrl);
        parcel.writeByteArray(bannerBlob);
    }
}
