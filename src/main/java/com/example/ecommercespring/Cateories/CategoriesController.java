package com.example.ecommercespring.Cateories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping ("/api")
public class CategoriesController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/allCategories")
    List<CategoryEntity> findAll() {
        return categoryService.findAllCategories();
    }
}
