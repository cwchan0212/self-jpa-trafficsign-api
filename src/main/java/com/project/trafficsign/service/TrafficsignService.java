package com.project.trafficsign.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.project.trafficsign.entity.Trafficsign;

public interface TrafficsignService {
    List<Trafficsign> getTrafficsigns();    
    Trafficsign getTrafficsign(Long TrafficsignId);
    Trafficsign saveTrafficsign(Trafficsign Trafficsign);
    void deleteTrafficsign(Long TrafficsignId);
    Page<Trafficsign> getTrafficsignPageable(int pageNo, int pageSize, String column, String order);
}
