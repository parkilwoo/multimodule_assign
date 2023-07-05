package com.example.data_module.service;

import com.example.data_module.domain.PopularSearchs;
import com.example.data_module.domain.PopularSearch;
import com.example.data_module.exception.RedisException;
import com.example.data_module.repository.SearchRepository;
import lombok.extern.log4j.Log4j2;
import org.hibernate.PessimisticLockException;
import org.hibernate.exception.LockTimeoutException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.List;
import java.util.Set;

@Log4j2
@Service
public class DataService {
    private final RedisTemplate<String, String> redisTemplate;
    private final SearchRepository searchRepository;
    private final PlatformTransactionManager transactionManager;

    private final String REDIS_SORTED_KEY = "keyword";

    public DataService(RedisTemplate<String, String> redisTemplate, SearchRepository searchRepository, PlatformTransactionManager transactionManager) {
        this.redisTemplate = redisTemplate;
        this.searchRepository = searchRepository;
        this.transactionManager = transactionManager;
    }

    public void addScoreToKeyword(String keyword) {
        try {
            addScoreToKeywordInRedis(keyword);
            addScoreToKeywordInRDB(keyword);
        }
        catch (RedisException redisException) {
            // Redis 업데이트 실패하면 데이터 정합성을 위해 DB 업데이트 시도
            addScoreToKeywordInRDB(keyword);
        }
    }

    private void addScoreToKeywordInRedis(String keyword) throws RedisException {
        try {
            redisTemplate.opsForZSet().incrementScore(REDIS_SORTED_KEY, keyword, 1);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RedisException(keyword);
        }
    }

    private void addScoreToKeywordInRDB(String keyword) {
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            PopularSearch popularSearch = searchRepository.findByKeyword(keyword).orElse(new PopularSearch(keyword, 0));
            popularSearch.addScore();
            this.transactionManager.commit(status);
        }
        catch (LockTimeoutException | PessimisticLockException lockException) {
            log.error("Database에 {} 조회수를 업데이트 실패", keyword);
            this.transactionManager.rollback(status);
        }
        catch (Exception e){
            e.printStackTrace();
            this.transactionManager.rollback(status);
        }
    }

    public PopularSearchs getPopularSearch() {
        PopularSearchs popularSearchs;
        try {
            popularSearchs = getPopularSearchUseRedis();
        }
        catch (Exception e) {
            popularSearchs = getPopularSearchUseRDB();
        }
        return popularSearchs;
    }

    private PopularSearchs getPopularSearchUseRedis() {
        ZSetOperations<String, String> zSetOps = redisTemplate.opsForZSet();
        Set<ZSetOperations.TypedTuple<String>> typedTuples = zSetOps.reverseRangeWithScores(REDIS_SORTED_KEY, 0, 9);
        PopularSearchs popularSearchs = new PopularSearchs();
        if(typedTuples != null) {
            popularSearchs.tupleToPopluarSearchs(typedTuples);
        }

        return popularSearchs;
    }

    private PopularSearchs getPopularSearchUseRDB() {
        List<PopularSearch> popularSearches = searchRepository.getPopularSearch();
        PopularSearchs popularSearchs = new PopularSearchs(popularSearches);
        return popularSearchs;
    }
}
