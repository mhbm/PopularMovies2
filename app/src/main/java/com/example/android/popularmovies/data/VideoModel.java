package com.example.android.popularmovies.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Mateus Macedo on 29/09/17.
 */

public class VideoModel implements Parcelable {
    private int id;
    private String key;
    private String type;
    private String size;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.key);
        dest.writeString(this.type);
        dest.writeString(this.size);
    }

    public VideoModel() {
    }

    protected VideoModel(Parcel in) {
        this.key = in.readString();
        this.type = in.readString();
        this.size = in.readString();
    }

    public static final Parcelable.Creator<VideoModel> CREATOR = new Parcelable.Creator<VideoModel>() {
        @Override
        public VideoModel createFromParcel(Parcel source) {
            return new VideoModel(source);
        }

        @Override
        public VideoModel[] newArray(int size) {
            return new VideoModel[size];
        }
    };
}
