package com.example.omar.fragfloat.Model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Omar on 12/18/2018.
 */

public class ResultsBean implements Serializable {

    private String id;
    private String title;
    private String type;
    private String imdb_score;
    private String imdb_poster_url;
    private String source_poster_url;
    private String source_cover_url;
    private String description;
//    private List<String> country;
//    private String language;
//    private String director;
    private List<String> tags;
    private List<String> actors;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImdb_score() {
        return imdb_score;
    }

    public void setImdb_score(String imdb_score) {
        this.imdb_score = imdb_score;
    }

    public String getImdb_poster_url() {
        return imdb_poster_url;
    }

    public void setImdb_poster_url(String imdb_poster_url) {
        this.imdb_poster_url = imdb_poster_url;
    }

    public String getSource_poster_url() {
        return source_poster_url;
    }

    public void setSource_poster_url(String source_poster_url) {
        this.source_poster_url = source_poster_url;
    }

    public String getSource_cover_url() {
        return source_cover_url;
    }

    public void setSource_cover_url(String source_cover_url) {
        this.source_cover_url = source_cover_url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }




//    public String getLanguage() {
//        return language;
//    }
//
//    public void setLanguage(String language) {
//        this.language = language;
//    }

//    public String getDirector() {
//        return director;
//    }
//
//    public void setDirector(String director) {
//        this.director = director;
//    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<String> getActors() {
        return actors;
    }

    public void setActors(List<String> actors) {
        this.actors = actors;
    }
}
