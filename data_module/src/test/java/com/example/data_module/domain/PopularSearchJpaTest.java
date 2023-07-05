package com.example.data_module.domain;

import com.example.data_module.repository.SearchRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@ExtendWith(SpringExtension.class)
public class PopularSearchJpaTest {
    @Autowired
    private SearchRepository searchRepository;

    @Test
    void test_save() {
        PopularSearch popularSearch = new PopularSearch("TEST", 1);
        searchRepository.save(popularSearch);

        PopularSearch findPopularSearch = searchRepository.findByKeyword("TEST").get();

        assertEquals(findPopularSearch.getKeyword(), "TEST");
        assertEquals(findPopularSearch.getScore(), 1);
    }

    @Test
    void test_add_score() {
        PopularSearch popularSearch = new PopularSearch("TEST2", 1);
        searchRepository.save(popularSearch);

        PopularSearch findPopularSearch = searchRepository.findByKeyword("TEST2").get();

        assertEquals(findPopularSearch.getScore(), 1);

        findPopularSearch.addScore();

        assertEquals(findPopularSearch.getScore(), 2);
    }

}
