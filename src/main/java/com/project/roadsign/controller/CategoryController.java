package com.project.roadsign.controller;

import java.net.URI;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.roadsign.entity.Category;
import com.project.roadsign.service.CategoryService;

import jakarta.servlet.http.HttpServletRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping(value = "/create")
    public String addCategory(@RequestParam String name, @RequestParam String description, RedirectAttributes redirectAttributes) {
        Category newCategory = new Category(null, name, description, null);
        categoryService.saveCategory(newCategory);
        redirectAttributes.addFlashAttribute("message", "Category added successfully!");
        return "redirect:/category/page/1?column=id&order=desc";
    }

    @PostMapping(value = "/mode")
    public String modeCategory(HttpServletRequest request, @RequestParam String type, @RequestParam Long id, @RequestParam String name, @RequestParam String description,
            RedirectAttributes redirectAttributes) {
        if (type.equals("edit")) {
            Category updateCategory = new Category(id, name, description, null);
            categoryService.saveCategory(updateCategory);
            redirectAttributes.addFlashAttribute("message", "Category updated successfully!");
            return "redirect:/category";
        } else if (type.equals("delete")) {
            categoryService.deleteCategory(id);
            redirectAttributes.addFlashAttribute("message", "Category deleted successfully!");
            return "redirect:/category";
        } else {
            redirectAttributes.addFlashAttribute("message", "Invalid method type!");
            return "redirect:/category";
        }
    }

    @GetMapping
    public String getCategories(Model model, HttpServletRequest request) {

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
        return getCategoriesPagable(pageNo, column, order, model);
    }

    @GetMapping("/page/{pageNo}")
    public String getCategoriesPagable(@PathVariable(value = "pageNo") int pageNo, @RequestParam("column") String column, @RequestParam("order") String order, Model model) {
        int pageSize = 25;
        Page<Category> page = categoryService.getCategoriesPageable(pageNo, pageSize, column, order);
        if (pageNo > page.getTotalPages()) {
            return "redirect:/category/page/1?column=" + column + "&order=" + order;
        }
        List<Category> categories = page.getContent();
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("column", column);
        model.addAttribute("order", order);
        model.addAttribute("reverseOrder", order.equals("asc") ? "desc" : "asc");
        model.addAttribute("categories", categories);
        return "category";
    }
}
