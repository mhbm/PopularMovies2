package com.example.android.popularmovies.data;

import java.io.Serializable;

/**
 * Created by Mateus Macedo on 22/09/17.
 */

public class ReviewModel implements Serializable {
    private int id;
    private String author;
    private String content;
    private String url;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
