package com.example.mybatisuse.entity;

import java.util.Objects;

public class Content {
    private String title;
    private String content;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Content)) return false;
        Content content1 = (Content) o;
        return Objects.equals(title, content1.title) &&
                Objects.equals(content, content1.content);
    }

    @Override
    public int hashCode() {

        return Objects.hash(title, content);
    }
}
