package com.example.jtademo.dto;

public class ParamTranslationDTO {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ParamTranslationDTO{" +
                "name='" + name + '\'' +
                '}';
    }
}
