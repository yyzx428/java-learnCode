package com.example.mybatisuse.entity;

public class Subject {
    private int score;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "score=" + score +
                ", name='" + name + '\'' +
                '}';
    }
}
