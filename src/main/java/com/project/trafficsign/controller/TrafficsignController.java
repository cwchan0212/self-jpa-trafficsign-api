package com.project.trafficsign.controller;

import java.net.URI;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.trafficsign.entity.Category;
import com.project.trafficsign.entity.Trafficsign;
import com.project.trafficsign.service.CategoryService;
import com.project.trafficsign.service.TrafficsignService;

import jakarta.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("/trafficsign")
public class TrafficsignController {
    
    private final CategoryService categoryService;
    private final TrafficsignService trafficsignService;

    @Autowired
    public TrafficsignController(CategoryService categoryService, TrafficsignService trafficsignService) {
        this.categoryService = categoryService;
        this.trafficsignService = trafficsignService;
    }


    @GetMapping
    public String getTrafficsigns(Model model, HttpServletRequest request) {
        String column = "id";
        String order = "desc";
        int pageNo = 1;        
        String referrer = request.getHeader("Referer");
        
        if (referrer != null) {       
            URI referrerUri = URI.create(referrer);
            String path = referrerUri.getPath();    
            Pattern pattern = Pattern.compile("/page/(\\d+)");
            Matcher matcher = pattern.matcher(path);
            if (matcher.find()) {
                String pageStr = matcher.group(1);
                pageNo = Integer.parseInt(pageStr);
            }  
    
            String query = referrerUri.getRawQuery();
            if (query != null && !query.isEmpty()) {
                String[] pairs = query.split("&");
                for (String pair : pairs) {
                    String[] parts = pair.split("=");
                    String name = parts[0];
                    String value = parts.length > 1 ? parts[1] : "";
                    column = (name == "column") ? value : "id";
                    order = (name == "order") ? value : "desc";
                }
            }    
        }

        return getTrafficsignPageable(pageNo, column, order, model);
    }


    @PostMapping(value="/create")
    public String addTrafficsign(@RequestParam String text, @RequestParam String filename, @RequestParam String image, @RequestParam Long categoryId, RedirectAttributes redirectAttributes) {
        Category category = categoryService.getCategory(categoryId);
        Trafficsign newTrafficsign  = new Trafficsign(null, text, filename, image, category);       
        trafficsignService.saveTrafficsign(newTrafficsign);
        redirectAttributes.addFlashAttribute("message", "Traffic sign created successfully!");
        return "redirect:/trafficsign/page/1?column=id&order=desc";
    }
    
    @RequestMapping(value = "/mode")
    public String modeTrafficSign(@RequestParam String type, @RequestParam Long id, @RequestParam String text, @RequestParam String filename, @RequestParam String image, @RequestParam Long categoryId, RedirectAttributes redirectAttributes ) {
        Category category = categoryService.getCategory(categoryId);
        Trafficsign newTrafficsign  = new Trafficsign(id, text, filename, image, category);
        if (type.equals("edit")) {
            trafficsignService.saveTrafficsign(newTrafficsign);
            redirectAttributes.addFlashAttribute("message", "Traffic sign updated successfully!");
            return "redirect:/trafficsign";
        } else if (type.equals("delete")) {
            trafficsignService.deleteTrafficsign(id);
            redirectAttributes.addFlashAttribute("message", "Traffic sign deleted successfully!");
            return "redirect:/trafficsign";
        } else {
            redirectAttributes.addFlashAttribute("message", "Invalid method type!");
            return "redirect:/trafficsign";
        }
    }

    @GetMapping("/page/{pageNo}")
    public String getTrafficsignPageable(@PathVariable(value = "pageNo") int pageNo, @RequestParam("column") String column, @RequestParam("order") String order, Model model) {
        int pageSize = 25;
        Page<Trafficsign> page = trafficsignService.getTrafficsignPageable(pageNo, pageSize, column, order);
        if (pageNo > page.getTotalPages()) {
            return "redirect:/trafficsign/page/1?column=" + column + "&order=" + order;
        }
        List<Trafficsign> trafficsigns = page.getContent();
        List<Category> categories = categoryService.getCategories();
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("column", column);
        model.addAttribute("order", order);
        model.addAttribute("reverseOrder", order.equals("asc") ? "desc" : "asc");        
        model.addAttribute("categories", categories);
        model.addAttribute("trafficsigns", trafficsigns);
        return "trafficsign";
    }
}

