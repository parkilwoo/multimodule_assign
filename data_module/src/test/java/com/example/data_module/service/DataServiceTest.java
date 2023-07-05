package com.example.data_module.service;

import com.example.data_module.domain.PopularSearch;
import com.example.data_module.domain.PopularSearchs;
import com.example.data_module.repository.SearchRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.PlatformTransactionManager;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class DataServiceTest {
    @InjectMocks
    private DataService dataService;

    @Mock
    SearchRepository searchRepository;

    @Mock
    RedisTemplate<String, String> redisTemplate;

    @Mock
    PlatformTransactionManager platformTransactionManager;

    @Test
    void 인기목록을_조회한다() {
        PopularSearch popularSearch1 = new PopularSearch("TEST", 1);
        PopularSearch popularSearch2 = new PopularSearch("TEST2", 2);

        List<PopularSearch> fakeList = new ArrayList<>();
        fakeList.add(popularSearch1);
        fakeList.add(popularSearch2);

        PopularSearchs fakeSearchs = new PopularSearchs(fakeList);
        when(dataService.getPopularSearch()).thenReturn(fakeSearchs);
        PopularSearchs popularSearchs = dataService.getPopularSearch();

        assertEquals(popularSearchs, new PopularSearchs(fakeList));

    }
}
