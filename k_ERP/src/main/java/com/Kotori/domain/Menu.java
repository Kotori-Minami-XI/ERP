package com.Kotori.domain;

public class Menu {
    private Long id;

    private String text;

    private String url;

    private Menu parent;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text == null ? null : text.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public Menu getParent() {
        return parent;
    }

    public void setParent(Menu parent) {
        this.parent = parent;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", url='" + url + '\'' +
                ", parent=" + parent +
                '}';
    }
}