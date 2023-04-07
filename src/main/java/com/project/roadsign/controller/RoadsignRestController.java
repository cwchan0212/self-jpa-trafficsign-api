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

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/roadsign")
@Tag(name = "Roadsign", description = "APIs for managing road signs")
@OpenAPIDefinition(info = @Info(title = "Roadsign API", version = "1.0", description = "API for managing categories"))

public class RoadsignRestController {
    
    @Autowired
    RoadsignService roadsignService;

    // Get all road signs
    @Operation(summary = "Get all road signs")
    @GetMapping(value = "", produces = { "application/json" })
    public ResponseEntity<List<Roadsign>> getRoadsigns() {
        return new ResponseEntity<>(roadsignService.getRoadsigns(), HttpStatus.OK);
    }

    // Get a road sign by ID
    @Operation(summary = "Get a road sign by ID")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Road sign retrieved successfully",
            content = @Content(mediaType = "application/json",
                examples = {
                    @ExampleObject(
                        name = "Road sign example",
                        value = "{\"id\": 21, \"text\": \"Give priority to vehicles from opposite direction\", \"filename\": \"sign-giving-order-give-priority-vehicles.jpg\", \"image\": \"https://assets.digital.cabinet-office.gov.uk/media/55b9f637e5274a151e000026/sign-giving-order-give-priority-vehicles.jpg\"}")
                }))
    })
    @GetMapping(value = "/{roadsignId}", produces = { "application/json" })
    public ResponseEntity<Roadsign> getRoadsign(@PathVariable Long roadsignId) {
        return new ResponseEntity<>(roadsignService.getRoadsign(roadsignId), HttpStatus.OK);
    }

    // Create a road sign
    @Operation(summary = "Create a road sign by ID")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201", 
            description = "Road sign created successfully", 
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(
                    example = "{\n"
                            + "  \"text\": \"Stop\",\n"
                            + "  \"filename\": \"stop.jpg\",\n"
                            + "  \"image\": \"base64-encoded-image\",\n"
                            + "  \"category\": {\n"
                            + "    \"id\": 1\n"
                            + "  }\n"
                            + "}"
                )
            )
        )        
    })
    @PostMapping
    public ResponseEntity<Roadsign> saveRoadsign(@RequestBody Roadsign roadsign) {
        return new ResponseEntity<>(roadsignService.saveRoadsign(roadsign), HttpStatus.CREATED);
    }

    // Update a road sign
    @Operation(summary = "Update a road sign")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201", 
            description = "Road sign updated successfully", 
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(
                    example = "{\n"
                            + "  \"id\" : 1,\n"
                            + "  \"text\": \"Updated sign\",\n"
                            + "  \"filename\": \"sign-giving-order-entry-20-zone.jpg\",\n"
                            + "  \"image\": \"https://assets.digital.cabinet-office.gov.uk/media/55b9f29740f0b6151f000013/sign-giving-order-entry-20-zone.jpg\",\n"
                            + "  \"category\": {\n"
                            + "    \"id\": 2\n"
                            + "  }\n"
                            + "}"
                )
            )
        )        
    })    
    @PutMapping(value ="/{roadsignId}")
    public ResponseEntity<Roadsign> updateRoadsign(@PathVariable Long roadsignId, @RequestBody Roadsign roadsign) {
        Roadsign updateRoadSign = new Roadsign(roadsignId, roadsign.getText(), roadsign.getFilename(), roadsign.getImage(), roadsign.getCategory());
        return new ResponseEntity<Roadsign>(roadsignService.saveRoadsign(updateRoadSign), HttpStatus.CREATED);
    }

    // Delete a road sign by ID
    @Operation(summary = "Delete a road sign by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Road sign deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Road sign not found")
    })
    @DeleteMapping(value="/{roadsignId}")
    public ResponseEntity<Void> deleteRoadsign(@PathVariable Long roadsignId) {
        roadsignService.deleteRoadsign(roadsignId);
        return ResponseEntity.noContent().build();
    }  
}
