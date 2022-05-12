package com.myproject.projectbeta2;

import java.util.List;

public class Mode {
    public String id;
    public Long number;
    public List<String> words;
    public String name;

    public Mode() {
    }

    public Mode(String id, String name, Long number, List<String> words) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.words = words;
    }
}
