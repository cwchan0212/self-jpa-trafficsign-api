package com.project.trafficsign.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.project.trafficsign.entity.Trafficsign;
import com.project.trafficsign.exception.TrafficsignNotFoundException;
import com.project.trafficsign.repository.TrafficsignRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class TrafficsignServiceImp implements TrafficsignService {

    public TrafficsignRepository trafficsignRepository;


    @Override
    public List<Trafficsign> getTrafficsigns() {
        return (List<Trafficsign>) trafficsignRepository.findAll();
    }

    @Override
    public Trafficsign getTrafficsign(Long trafficsignId) {
        Optional<Trafficsign> trafficsign = trafficsignRepository.findById(trafficsignId);
        return unwrapTrafficsign(trafficsign, trafficsignId);
    }

    @Override
    public Trafficsign saveTrafficsign(Trafficsign trafficsign) {
        return trafficsignRepository.save(trafficsign);
    }

    @Override
    public void deleteTrafficsign(Long trafficsignId) {
        trafficsignRepository.deleteById(trafficsignId);
    }

    static Trafficsign unwrapTrafficsign(Optional<Trafficsign> trafficsign, Long trafficsignId) {
        if (trafficsign.isPresent()) return trafficsign.get();
        else throw new TrafficsignNotFoundException(trafficsignId);
    }

    @Override
    public Page<Trafficsign> getTrafficsignPageable(int pageNo, int pageSize, String column, String order) {
        Sort sort = order.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(column).ascending() : Sort.by(column).descending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return trafficsignRepository.findAll(pageable);
    }

}
