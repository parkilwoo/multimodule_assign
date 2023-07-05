package com.example.search_module.cotroller;

import com.example.search_module.dto.kakao.KAKAORequestSearchBlogDTO;
import com.example.search_module.service.SearchService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Validated
public class SearchController {

    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/api/v1/blog")
    public ResponseEntity<?> searchBlog(@Valid KAKAORequestSearchBlogDTO kakaoRequestSearchBlogDTO) {
        try {
            return searchService.searchBlog(kakaoRequestSearchBlogDTO);
        }
        catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
