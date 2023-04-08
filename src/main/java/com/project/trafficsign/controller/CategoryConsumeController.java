package com.project.trafficsign.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.reactive.function.client.WebClient;

import com.project.trafficsign.entity.Category;

@Controller
public class CategoryConsumeController {

    @RequestMapping("/")
    public String index(Model model) {
        String endpoint = "http://localhost:8080/api/category";
        WebClient client = WebClient.create(endpoint);
        List<Category> categories = client.get()
                                    .uri(endpoint)
                                    .retrieve()
                                    .bodyToFlux(Category.class)
                                    .collectList()
                                    .block();
        
                                    System.out.println("\n" + categories.size() + "\n");

        model.addAttribute("categories", categories);
        return "index";
    }
}
