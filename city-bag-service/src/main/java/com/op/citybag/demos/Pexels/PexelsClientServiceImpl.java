package com.op.citybag.demos.Pexels;

import com.op.citybag.demos.exception.AppException;
import com.op.citybag.demos.model.Entity.PexelsPhoto;
import com.op.citybag.demos.model.Entity.PexelsSearchResponse;
import com.op.citybag.demos.model.common.GlobalServiceStatusCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: 原神
 * @Description:
 * @Date: 2025/3/25 19:43
 * @Version: 1.0
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class PexelsClientServiceImpl implements PexelsClientService{

    private final PexelsConfigProperties config;
    private final RestTemplate restTemplate;

    @Override
    public PexelsSearchResponse searchPhotos(String query, int page, int perPage) {

        String url = String.format("%s/search?query=%s&page=%d&per_page=%d",
                config.getBaseUrl(), URLEncoder.encode(query, StandardCharsets.UTF_8), page, perPage);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", config.getApiKey());

        try {
            ResponseEntity<PexelsSearchResponse> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    new HttpEntity<>(headers),
                    PexelsSearchResponse.class);

            return response.getBody();
        } catch (HttpClientErrorException e) {
            log.info("Pexels API请求失败: {}", e.getStatusCode());
            throw new AppException(String.valueOf(GlobalServiceStatusCode.PEXELS_API_ERROR.getCode()), "图片搜索服务暂时不可用");
        }
    }

    @Override
    public String searchOnePhoto(String query){

        PexelsSearchResponse pexelsSearchResponse = searchPhotos(query, 0, 1);
        if (pexelsSearchResponse.getPhotos().isEmpty()) {
            log.info("Pexels API请求失败");
            return null;
        }

        PexelsPhoto pexelsPhoto = pexelsSearchResponse.getPhotos().get(0);
        return pexelsPhoto.getSrc().get("tiny");
    }


    @Override
    public List<String> searchFivePhotos(String query) {
        return searchPhotos(query,5);
    }

    @Override
    public List<String> searchPhotos(String query,int num){
        PexelsSearchResponse pexelsSearchResponse = searchPhotos(query, 0, 5);
        if (pexelsSearchResponse.getPhotos().isEmpty()) {
            log.info("Pexels API请求失败");
            return null;
        }

        List<String> result = new ArrayList<>();
        for (PexelsPhoto pexelsPhoto : pexelsSearchResponse.getPhotos()) {
            String tinyUrl = pexelsPhoto.getSrc().get("tiny");
            if (tinyUrl == null){
                break;
            }
            result.add(tinyUrl);
            if (result.size() == 5) {
                break;
            }
        }
        return result;
    }

    @Override
    public Map<String, String> searchPhotosByQueries(List<String> queries) {
        Map<String, String> result = new HashMap<>();
        for (String query : queries) {
            String photoUrl = searchOnePhoto(query);
            if (photoUrl != null) {
                result.put(query, photoUrl);
            }
        }
        return result;
    }
}
