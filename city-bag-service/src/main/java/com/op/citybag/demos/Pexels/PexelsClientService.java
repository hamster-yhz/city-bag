package com.op.citybag.demos.Pexels;

import com.op.citybag.demos.model.Entity.PexelsSearchResponse;

import java.util.List;
import java.util.Map;

public interface PexelsClientService {

    PexelsSearchResponse searchPhotos(String query, int page, int perPage);

    String searchOnePhoto(String query);

    List<String> searchFivePhotos(String query);

    List<String> searchPhotos(String query,int num);

    Map<String,String> searchPhotosByQueries(List<String> query);

}
