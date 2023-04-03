package com.project.roadsign.controller;

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

import com.project.roadsign.entity.Category;
import com.project.roadsign.entity.Roadsign;
import com.project.roadsign.service.CategoryService;
import com.project.roadsign.service.RoadsignService;

import jakarta.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("/roadsign")
public class RoadsignController {
    
    private final CategoryService categoryService;
    private final RoadsignService roadsignService;

    @Autowired
    public RoadsignController(CategoryService categoryService, RoadsignService roadsignService) {
        this.categoryService = categoryService;
        this.roadsignService = roadsignService;
    }

    // @GetMapping
    // public String getRoadsigns(Model model) {
    //     List<Category> categories = categoryService.getCategories();
    //     List<Roadsign> roadsigns = roadsignService.getRoadsigns();
    //     model.addAttribute("categories", categories);
    //     model.addAttribute("roadsigns", roadsigns);
    //     return "roadsign";
    // }


    @GetMapping
    public String getRoadsigns(Model model, HttpServletRequest request) {
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

        return getRoadsignPageable(pageNo, column, order, model);
    }


    @PostMapping(value="/create")
    public String addRoadsign(@RequestParam String text, @RequestParam String filename, @RequestParam String image, @RequestParam Long categoryId, RedirectAttributes redirectAttributes) {
        Category category = categoryService.getCategory(categoryId);
        Roadsign newRoadsign  = new Roadsign(null, text, filename, image, category);       
        roadsignService.saveRoadsign(newRoadsign);
        redirectAttributes.addFlashAttribute("message", "Road sign created successfully!");
        return "redirect:/roadsign/page/1?column=id&order=desc";
    }
    
    @RequestMapping(value = "/mode")
    public String modeRoadSign(@RequestParam String type, @RequestParam Long id, @RequestParam String text, @RequestParam String filename, @RequestParam String image, @RequestParam Long categoryId, RedirectAttributes redirectAttributes ) {
        Category category = categoryService.getCategory(categoryId);
        Roadsign newRoadsign  = new Roadsign(id, text, filename, image, category);
        if (type.equals("edit")) {
            roadsignService.saveRoadsign(newRoadsign);
            redirectAttributes.addFlashAttribute("message", "Road sign updated successfully!");
            return "redirect:/roadsign";
        } else if (type.equals("delete")) {
            roadsignService.deleteRoadsign(id);
            redirectAttributes.addFlashAttribute("message", "Road sign deleted successfully!");
            return "redirect:/roadsign";
        } else {
            redirectAttributes.addFlashAttribute("message", "Invalid method type!");
            return "redirect:/roadsign";
        }
    }

    @GetMapping("/page/{pageNo}")
    public String getRoadsignPageable(@PathVariable(value = "pageNo") int pageNo, @RequestParam("column") String column, @RequestParam("order") String order, Model model) {
        int pageSize = 25;
        Page<Roadsign> page = roadsignService.getRoadsignPageable(pageNo, pageSize, column, order);
        if (pageNo > page.getTotalPages()) {
            return "redirect:/roadsign/page/1?column=" + column + "&order=" + order;
        }
        List<Roadsign> roadsigns = page.getContent();
        List<Category> categories = categoryService.getCategories();
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("column", column);
        model.addAttribute("order", order);
        model.addAttribute("reverseOrder", order.equals("asc") ? "desc" : "asc");        
        model.addAttribute("categories", categories);
        model.addAttribute("roadsigns", roadsigns);
        return "roadsign";
    }
}

