package com.example.popular_module.service;

import com.example.data_module.domain.PopularSearchs;
import com.example.data_module.service.DataService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@ComponentScan(basePackages = {"com.example.data_module"})
public class PopularService {
    private final DataService dataService;

    public PopularService(DataService dataService) {
        this.dataService = dataService;
    }

    public ResponseEntity<PopularSearchs> getListPopularSearch() {
        PopularSearchs popularSearchs = dataService.getPopularSearch();
        return new ResponseEntity<>(popularSearchs, HttpStatus.OK);
    }
}
