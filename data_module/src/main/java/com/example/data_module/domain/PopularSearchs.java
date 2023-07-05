package com.example.data_module.domain;

import org.springframework.data.redis.core.ZSetOperations;

import java.util.*;
public class PopularSearchs {
    private List<PopularSearch> popularSearches;
    public PopularSearchs() {
        popularSearches = new ArrayList<>(10);
    }


    public PopularSearchs(List<PopularSearch> popularSearches) {
        this.popularSearches = popularSearches;
    }
    public void tupleToPopluarSearchs(Set<ZSetOperations.TypedTuple<String>> typedTuples) {
        for (ZSetOperations.TypedTuple<String> tuple : typedTuples) {
            PopularSearch popularSearch = new PopularSearch(tuple.getValue(), Math.round(tuple.getScore()));
            add(popularSearch);
        }
    }


    private void add(PopularSearch popularSearch) {
        popularSearches.add(popularSearch);
    }

    public List<PopularSearch> getPopularSearches() {
        return this.popularSearches;
    }
}
