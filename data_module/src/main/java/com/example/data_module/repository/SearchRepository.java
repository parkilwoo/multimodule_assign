package com.example.data_module.repository;

import com.example.data_module.domain.PopularSearch;
import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface SearchRepository extends JpaRepository<PopularSearch, String> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints({@QueryHint(name = "jakarta.persistence.lock.timeout", value = "3000")})
    Optional<PopularSearch> findByKeyword(String keyword);

    @Query(nativeQuery = true, value = "SELECT keyword,score FROM PopularSearch ORDER BY score LIMIT 10")
    List<PopularSearch> getPopularSearch();
}
