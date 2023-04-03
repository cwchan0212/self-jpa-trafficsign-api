package com.project.roadsign.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.roadsign.entity.Roadsign;
import com.project.roadsign.service.RoadsignService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/roadsign")

public class RoadsignRestController {
    
    @Autowired
    RoadsignService roadsignService;

    // @Autowired
    // public RoadsignRestController(RoadsignServiceImp roadsignService) {
    //     this.roadsignService = roadsignService;
    // }

    @GetMapping
    public ResponseEntity<List<Roadsign>> getRoadsigns() {
        return new ResponseEntity<>(roadsignService.getRoadsigns(), HttpStatus.OK);
    }

    @GetMapping(value="/{roadsignId}")
    public ResponseEntity<Roadsign> getRoadsign(@PathVariable Long roadsignId) {
        return new ResponseEntity<>(roadsignService.getRoadsign(roadsignId), HttpStatus.OK);
    }
    // public ResponseEntity<Object> findRoadsignById(@PathVariable Long roadsignId) {
    //     List<Roadsign> roadsignList = roadsignService.findAllRoadsigns();        
    //     Optional<Roadsign> roadsign = roadsignList.stream()
    //             .filter(c -> c.getId().equals(roadsignId))
    //             .findFirst();        
    //             if (roadsign.isPresent()) {
    //                 return ResponseEntity.status(HttpStatus.OK).body(roadsign.get());
    //             } else {
    //                 Map<String, Object> map = new HashMap<>();
    //                 map.put("message", "RoadsignId not found.");
    //                 return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
    //             }
    // }
    
    @PostMapping
    public ResponseEntity<Roadsign> saveRoadsign(@RequestBody Roadsign roadsign) {
        return new ResponseEntity<>(roadsignService.saveRoadsign(roadsign), HttpStatus.CREATED);
    }
    // public ResponseEntity<Optional<Roadsign>> createRoadsign(@RequestBody Roadsign roadsign) {
    //     Long roadsignId = roadsignService.addRoadsign(roadsign.getCategoryId(), roadsign.getText(), roadsign.getFilename(), roadsign.getImage());
    //     Optional<Roadsign> roadsignOptional = roadsignService.findOneRoadsign(roadsignId);
    //     return ResponseEntity.status(HttpStatus.CREATED).body(roadsignOptional);
    // }
    
    // @PutMapping(value="/{roadsignId}")
    // public ResponseEntity<Object> updateRoadsign(@PathVariable Long roadsignId, @RequestBody Roadsign roadsign) {
    //     roadsignService.saveRoadsign(roadsignId, roadsign.getText(), roadsign.getFilename(), roadsign.getImage(), roadsign.getCategoryId());
    //     Optional<Roadsign> roadsignOptional = roadsignService.findOneRoadsign(roadsignId);
    //     if (roadsignOptional.isPresent()) {
    //         return ResponseEntity.status(HttpStatus.OK).body(roadsignOptional);
    //     } else {
    //         Map<String, Object> map = new HashMap<>();
    //         map.put("message", "RoadsignId not found.");
    //         return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
    //     }
    // }
    @PutMapping(value ="/{roadsignId}")
    public ResponseEntity<Roadsign> updateRoadsign(@PathVariable Long roadsignId, @RequestBody Roadsign roadsign) {
        Roadsign updateRoadSign = new Roadsign(roadsignId, roadsign.getText(), roadsign.getFilename(), roadsign.getImage(), roadsign.getCategory());
        return new ResponseEntity<Roadsign>(roadsignService.saveRoadsign(updateRoadSign), HttpStatus.CREATED);

    }

    @DeleteMapping(value="/{roadsignId}")
    public ResponseEntity<HttpStatus> deleteRoadsign(@PathVariable Long roadsignId) {
        roadsignService.deleteRoadsign(roadsignId);
        return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);

    }
    // public ResponseEntity<Object> updateRoadsign(@PathVariable Long roadsignId) {
    //     Optional<Roadsign> roadsignOptional = roadsignService.findOneRoadsign(roadsignId);
    //     if (roadsignOptional.isPresent()) {
    //         roadsignService.deleteRoadsign(roadsignId);
    //         return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    //     } else {
    //         Map<String, Object> map = new HashMap<>();
    //         map.put("message", "RoadsignId not found.");
    //         return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
    //     }
    // }

    // @PostMapping
    // public ResponseEntity<Category> createCategory(@RequestBody Category category) {
    //     categoryService.addCategory(category);
    //     return ResponseEntity.status(HttpStatus.CREATED).body(category);

    // }

}
