package com.example.search_module.service;

import com.example.data_module.service.DataService;
import com.example.search_module.connector.KAKAOConnector;
import com.example.search_module.connector.NAVERConnector;
import com.example.search_module.dto.ResponseSearchBlogDTO;
import com.example.search_module.dto.kakao.KAKAORequestSearchBlogDTO;
import com.example.search_module.dto.kakao.KAKAOResponseSearchBlogDTO;
import com.example.search_module.dto.naver.NAVERRequestSearchBlogDTO;
import com.example.search_module.dto.naver.NAVERResponseSearchBlogDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@Service
@ComponentScan(basePackages = {"com.example.data_module"})
@Log4j2
public class SearchService {

    private final DataService dataService;

    public SearchService(DataService dataService) {
        this.dataService = dataService;
    }

    public ResponseEntity<? extends ResponseSearchBlogDTO> searchBlog(KAKAORequestSearchBlogDTO kakaoRequestSearchBlogDTO)throws Exception {
        ResponseEntity<ResponseSearchBlogDTO> responseEntity;
        try {
            responseEntity = new ResponseEntity<>(searchBlogUseKAKAO(kakaoRequestSearchBlogDTO), HttpStatus.OK);
        }
        catch (Exception exception) {
            log.error(exception.getMessage());
            responseEntity = new ResponseEntity<>(searchBlogUseNAVER(kakaoRequestSearchBlogDTO), HttpStatus.OK);
        }
        String keyword = kakaoRequestSearchBlogDTO.getKeyword();
        runDataServiceAsync(keyword);
        return responseEntity;
    }

    private void runDataServiceAsync(String keyword) {
        try {
            CompletableFuture.runAsync(() -> dataService.addScoreToKeyword(keyword));
        }
        catch (Exception exception) {
            log.error(exception.getMessage());
        }
    }

    private KAKAOResponseSearchBlogDTO searchBlogUseKAKAO(KAKAORequestSearchBlogDTO kakaoRequestSearchBlogDTO) throws IOException {
        KAKAOConnector kakaoConnector = new KAKAOConnector(kakaoRequestSearchBlogDTO.getQueryParameter());
        String responseString = kakaoConnector.connect();
        KAKAOResponseSearchBlogDTO kakaoResponseSearchBlogDTO = new KAKAOResponseSearchBlogDTO(responseString);
        kakaoResponseSearchBlogDTO.parseDTO();
        return kakaoResponseSearchBlogDTO;
    }

    private NAVERResponseSearchBlogDTO searchBlogUseNAVER(KAKAORequestSearchBlogDTO kakaoRequestSearchBlogDTO) throws IOException {
        NAVERRequestSearchBlogDTO naverRequestSearchBlogDTO = new NAVERRequestSearchBlogDTO();
        naverRequestSearchBlogDTO.convertKAKAORequestDtoToNAVERRequestDTO(kakaoRequestSearchBlogDTO);
        NAVERConnector naverConnector = new NAVERConnector(naverRequestSearchBlogDTO.getQueryParameter());

        String responseString = naverConnector.connect();
        NAVERResponseSearchBlogDTO naverResponseSearchBlogDTO = new NAVERResponseSearchBlogDTO(responseString);
        naverResponseSearchBlogDTO.parseDTO();
        return naverResponseSearchBlogDTO;
    }
}
