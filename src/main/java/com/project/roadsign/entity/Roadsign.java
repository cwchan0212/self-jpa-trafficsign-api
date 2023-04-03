package com.project.roadsign.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "roadsign")
public class Roadsign {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name="text", unique = true, nullable = false)
    private String text;

    @Column(name="filename")
    private String filename;

    @Column(name="image")
    private String image;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", referencedColumnName = "id", nullable = false)
    @JsonBackReference
    private Category category;

    @JsonProperty("category")
    public Category getCategory() {
        return category;
    }

    // public Roadsign(String text, String filename, String image, Category category) {
    //     this.text = text;
    //     this.filename = filename;
    //     this.image = image;
    //     this.category = category;
    // }

    // public Roadsign(String text, String filename, String image ) {
    //     this.text = text;
    //     this.filename = filename;
    //     this.image = image;
    // }

    // public Roadsign() {
    // }

    // public Long getId() {
    //     return id;
    // }

    // public void setId(Long id) {
    //     this.id = id;
    // }

    // public String getText() {
    //     return text;
    // }

    // public void setText(String text) {
    //     this.text = text;
    // }

    // public String getFilename() {
    //     return filename;
    // }

    // public void setFilename(String filename) {
    //     this.filename = filename;
    // }

    // public String getImage() {
    //     return image;
    // }

    // public void setImage(String image) {
    //     this.image = image;
    // }

    // public Category getCategory() {
    //     return category;
    // }
    
    // public void setCategory(Category category) {
    //     this.category = category;
    // }
}
