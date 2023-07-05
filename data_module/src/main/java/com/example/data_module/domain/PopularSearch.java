package com.example.data_module.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class PopularSearch {

    @Id
    private String keyword;

    private long score;

    public PopularSearch() {

    }

    public PopularSearch(String keyword, long score) {
        this.keyword = keyword;
        this.score = score;
    }

    public void addScore() {
        this.score += 1;
    }
}
