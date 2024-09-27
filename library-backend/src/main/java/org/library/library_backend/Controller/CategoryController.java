package org.library.library_backend.Controller;

import org.library.library_backend.Model.Category;
import org.library.library_backend.Service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @PostMapping("/addCategory")
    public ResponseEntity<Category> addCategory(@RequestBody Category category) {
        return categoryService.addCategory(category);
    }

    @PostMapping("/updateCategory/{category_id}")
    public ResponseEntity<Category> updateCategoryById(@PathVariable Long category_id, @RequestBody Category newCategoryData) {
        return categoryService.updateCategoryById(category_id, newCategoryData);
    }

    @DeleteMapping("/deleteCategoryById/{category_id}")
    public ResponseEntity<HttpStatus> deleteCategoryById(@PathVariable Long category_id) {
        return categoryService.deleteCategoryById(category_id);
    }

    @GetMapping("/getAllCategories")
    public ResponseEntity<List<Category>> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/getCategoryById/{category_id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long category_id) {
        return categoryService.getCategoryById(category_id);
    }

}
