package com.project.roadsign.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
@Table(name = "category")
public class Category {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "description")
    private String description; 

    // @JsonIgnore    
    @JsonManagedReference
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)  
    // @JoinColumn(name = "category_id")    
    private List<Roadsign> roadsigns;

    // public List<Roadsign> getRoadsignList() {
    //     return roadsigns;
    // }

    // public void setRoadsignList(List<Roadsign> roadsigns) {
    //     this.roadsigns = roadsigns;
    // }


    // public Category(String name, String description, List<Roadsign> roadsigns) {
    //     this.name = name;
    //     this.description = description;
    //     this.roadsigns = roadsigns;
    // }

    // public Category() { // add this constructor
    // }

    // public Long getId() {
    //     return id;
    // }

    // public void setId(Long id) {
    //     this.id = id;
    // }

    // public String getName() {
    //     return name;
    // }

    // public void setName(String name) {
    //     this.name = name;
    // }

    // public String getDescription() {
    //     return description;
    // }

    // public void setDescription(String description) {
    //     this.description = description;
    // }

    // public List<Roadsign> getRoadsigns() {
    //     return roadsigns;
    // }

    // public void setRoadsigns(List<Roadsign> roadsigns) {
    //     this.roadsigns = roadsigns;
    // }  
      

    
}
