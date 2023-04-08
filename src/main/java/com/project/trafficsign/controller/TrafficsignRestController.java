package com.project.trafficsign.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
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

import com.project.trafficsign.entity.Trafficsign;
import com.project.trafficsign.service.TrafficsignService;

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
@RequestMapping(value = "/api/trafficsign")
@Tag(name = "Traffic Sign", description = "APIs for managing traffic signs")
@OpenAPIDefinition(info = @Info(title = "Traffic Sign API", version = "1.0", description = "API for managing categories"))

public class TrafficsignRestController {
    
    @Autowired
    TrafficsignService trafficsignService;

    // Get all traffic signs
    @Operation(summary = "Get all traffic signs")
    @GetMapping(value = "", produces = { "application/json" })
    public ResponseEntity<List<Trafficsign>> getTrafficsigns() {
        return new ResponseEntity<>(trafficsignService.getTrafficsigns(), HttpStatus.OK);
    }

    // Get a traffic sign by ID
    @Operation(summary = "Get a traffic sign by ID")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Traffic sign retrieved successfully",
            content = @Content(mediaType = "application/json",
                examples = {
                    @ExampleObject(
                        name = "Traffic sign example",
                        value = "{\"id\": 21, \"text\": \"Give priority to vehicles from opposite direction\", \"filename\": \"sign-giving-order-give-priority-vehicles.jpg\", \"image\": \"https://assets.digital.cabinet-office.gov.uk/media/55b9f637e5274a151e000026/sign-giving-order-give-priority-vehicles.jpg\"}")
                }))
    })
    @GetMapping(value = "/{trafficsignId}", produces = { "application/json" })
    public ResponseEntity<Trafficsign> getTrafficsign(@PathVariable Long trafficsignId) {
        return new ResponseEntity<>(trafficsignService.getTrafficsign(trafficsignId), HttpStatus.OK);
    }

    // Create a traffic sign
    @Operation(summary = "Create a traffic sign by ID")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201", 
            description = "Traffic sign created successfully", 
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
    public ResponseEntity<Trafficsign> saveTrafficsign(@RequestBody Trafficsign trafficsign) {
        return new ResponseEntity<>(trafficsignService.saveTrafficsign(trafficsign), HttpStatus.CREATED);
    }

    // Update a traffic sign
    @Operation(summary = "Update a traffic sign")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201", 
            description = "Traffic sign updated successfully", 
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
    @PutMapping(value ="/{trafficsignId}")
    public ResponseEntity<Trafficsign> updateTrafficsign(@PathVariable Long trafficsignId, @RequestBody Trafficsign trafficsign) {
        Trafficsign updateTrafficsign = new Trafficsign(trafficsignId, trafficsign.getText(), trafficsign.getFilename(), trafficsign.getImage(), trafficsign.getCategory());
        return new ResponseEntity<Trafficsign>(trafficsignService.saveTrafficsign(updateTrafficsign), HttpStatus.CREATED);
    }

    // Delete a traffic sign by ID
    @Operation(summary = "Delete a traffic sign by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Traffic sign deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Traffic sign not found")
    })
    @DeleteMapping(value="/{trafficsignId}")
    public ResponseEntity<Void> deleteTrafficsign(@PathVariable Long trafficsignId) {
        trafficsignService.deleteTrafficsign(trafficsignId);
        return ResponseEntity.noContent().build();
    }  
}
