package org.library.library_backend.Service;

import org.library.library_backend.Model.Category;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {
    public ResponseEntity<Category> addCategory(Category category);
    public ResponseEntity<Category> updateCategoryById(Long category_id, Category newCategoryData);
    public ResponseEntity<HttpStatus> deleteCategoryById(Long category_id);
    public ResponseEntity<List<Category>> getAllCategories();
    public ResponseEntity<Category> getCategoryById(Long category_id);

}
