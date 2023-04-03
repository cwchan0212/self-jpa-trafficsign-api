package com.project.roadsign.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.project.roadsign.entity.Roadsign;
import com.project.roadsign.exception.RoadsignNotFoundException;
import com.project.roadsign.repository.RoadsignRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class RoadsignServiceImp implements RoadsignService {

    public RoadsignRepository roadsignRepository;


    // @Autowired
    // public RoadsignServiceImp(RoadsignRepository roadsignRepository) {
    //     this.roadsignRepository = roadsignRepository;
    // }

    @Override
    public List<Roadsign> getRoadsigns() {
        return (List<Roadsign>) roadsignRepository.findAll();
    }

    @Override
    public Roadsign getRoadsign(Long roadsignId) {
        Optional<Roadsign> roadsign = roadsignRepository.findById(roadsignId);
        return unwrapRoadsign(roadsign, roadsignId);
    }

    @Override
    public Roadsign saveRoadsign(Roadsign roadsign) {
        return roadsignRepository.save(roadsign);
    }

    @Override
    public void deleteRoadsign(Long roadsignId) {
        roadsignRepository.deleteById(roadsignId);
    }

    static Roadsign unwrapRoadsign(Optional<Roadsign> roadsign, Long roadsignId) {
        if (roadsign.isPresent()) return roadsign.get();
        else throw new RoadsignNotFoundException(roadsignId);
    }

    @Override
    public Page<Roadsign> getRoadsignPageable(int pageNo, int pageSize, String column, String order) {
        Sort sort = order.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(column).ascending() : Sort.by(column).descending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return roadsignRepository.findAll(pageable);
    }




    // public Page<Roadsign> getRoadsignsPageable(int pageNumber, int pageSize) {
    //     Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
    //     return roadsignRepository.findAll(pageable);
    // }


//     public Optional<Roadsign> findOneRoadsign(Long roadsignId) {
//         try {
//             Roadsign roadsign = roadsignRepository.findById(roadsignId);
//             return Optional.of(roadsign);
//         } catch (RoadsignNotFoundException e) {
//             return Optional.empty();
//         }
//     }

//     public Long addRoadsign(Long categoryId, String text, String filename, String image) {
//         Long roadsignId = roadsignRepository.findMaxId() + 1L;
//         Roadsign roadsign = new Roadsign(roadsignId, text, filename, image, categoryId);
//         roadsignRepository.add(roadsign);
//         return roadsignId;
//     }

//     public void saveRoadsign(Long roadsignId, String text, String filename, String image, Long categoryId) {
//         Roadsign roadsign = new Roadsign(roadsignId, text, filename, image, categoryId);
//         roadsignRepository.save(roadsignId, roadsign);
//     }
    
//     public void deleteRoadsign(Long roadsignId) {
//         roadsignRepository.delete(roadsignId);
//     }
}
