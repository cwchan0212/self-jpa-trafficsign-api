package com.project.roadsign.controller;

import java.util.List;

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

import com.project.roadsign.entity.Category;
import com.project.roadsign.service.CategoryService;

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
@RequestMapping("/api/category")
@Tag(name = "Category", description = "APIs for managing road sign categories")
@OpenAPIDefinition(info = @Info(title = "Category API", version = "1.0", description = "API for managing categories"))

public class CategoryRestController {

    CategoryService categoryService;

    // Get all road signs
    @Operation(summary = "Get all categories")
    @GetMapping(value = "", produces = { "application/json" })
    public ResponseEntity<List<Category>> getCategories() {
        return new ResponseEntity<>(categoryService.getCategories(), HttpStatus.OK);
    }

    // Get a category by ID
    @Operation(summary = "Get a category by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category retrieved successfully", content = @Content(mediaType = "application/json", examples = {
                    @ExampleObject(name = "Category example", value = "{\n"
                            + "    \"id\": 2,\n"
                            + "    \"name\": \"Warning signs\",\n"
                            + "    \"description\": null,\n"
                            + "    \"roadsigns\": [\n"
                            + "        {\n"
                            + "            \"id\": 49,\n"
                            + "            \"text\": \"Distance to \\\"STOP\\\" line ahead\",\n"
                            + "            \"filename\": \"warning-sign-distance-to-stop-line-ahead-100-yards.jpg\",\n"
                            + "            \"image\": \"https://assets.digital.cabinet-office.gov.uk/media/55b75e26ed915d07e2000007/warning-sign-distance-to-stop-line-ahead-100-yards.jpg\"\n"
                            + "        },\n"
                            + "        {\n"
                            + "            \"id\": 50,\n"
                            + "            \"text\": \"Dual carriageway ends\",\n"
                            + "            \"filename\": \"warning-sign-dual-carriageway-ends.jpg\",\n"
                            + "            \"image\": \"https://assets.digital.cabinet-office.gov.uk/media/55b75e47e5274a215200000c/warning-sign-dual-carriageway-ends.jpg\"\n"
                            + "        }\n"
                            + "    ]\n"
                            + "}")
            }))
    })
    @GetMapping(value = "/{categoryId}", produces = { "application/json" })
    public ResponseEntity<Category> getCategory(@PathVariable Long categoryId) {
        return new ResponseEntity<Category>(categoryService.getCategory(categoryId), HttpStatus.OK);
    }

    // Create a category
    @Operation(summary = "Create a category by ID")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201", 
            description = "Category created successfully", 
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(
                    example = "{\n"
                            + "  \"name\": \"This is a category name\",\n"
                            + "  \"description\": \"This is a category description\",\n"
                            + "}"
                )
            )
        )        
    })
    @PostMapping
    public ResponseEntity<Category> saveCategory(@RequestBody Category category) {
        return new ResponseEntity<Category>(categoryService.saveCategory(category), HttpStatus.CREATED);
    }

    // Update a category
    @Operation(summary = "Update a category")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201", 
            description = "Category updated successfully", 
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(
                    example = "{\n"
                            + "  \"id\" : 1,\n"
                            + "  \"name\": \"Updated name\",\n"
                            + "  \"description\": \"Updated description\",\n"
                            + "}"
                )
            )
        )        
    })   
    @PutMapping("/{categoryId}")
    public ResponseEntity<Category> saveCategory(@PathVariable Long categoryId, @RequestBody Category category) {
        Category updateCategory = new Category(categoryId, category.getName(), category.getDescription(),
                category.getRoadsigns());
        return new ResponseEntity<Category>(categoryService.saveCategory(updateCategory), HttpStatus.CREATED);
    }

    // Delete a category by ID
    @Operation(summary = "Delete a category by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Ccategory deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Ccategory not found")
    })
    @DeleteMapping(value = "/{categoryId}")
    public ResponseEntity<HttpStatus> deleteCategory(@PathVariable Long categoryId) {
        categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
