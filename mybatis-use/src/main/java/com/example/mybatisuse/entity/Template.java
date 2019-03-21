package com.example.mybatisuse.entity;

import java.time.LocalDateTime;
import java.util.Objects;

public class Template {

    private String id;

    private Content content;

    private LocalDateTime createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Template)) return false;
        Template template = (Template) o;
        return Objects.equals(id, template.id) &&
                content.equals( template.content) &&
                createTime.equals(template.createTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, content, createTime);
    }
}
