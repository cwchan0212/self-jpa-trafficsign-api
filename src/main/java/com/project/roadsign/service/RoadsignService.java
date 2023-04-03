package com.project.roadsign.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.project.roadsign.entity.Roadsign;

public interface RoadsignService {
    List<Roadsign> getRoadsigns();    
    Roadsign getRoadsign(Long roadsignId);
    Roadsign saveRoadsign(Roadsign roadsign);
    void deleteRoadsign(Long roadsignId);
    Page<Roadsign> getRoadsignPageable(int pageNo, int pageSize, String column, String order);
}
