package org.library.library_backend.Service;

import org.library.library_backend.Model.Category;
import org.library.library_backend.Repo.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImplementation implements CategoryService {
    @Autowired
    CategoryRepo categoryRepo;

    @Override
    public ResponseEntity<Category> addCategory(Category category) {
        Category categoryObj = categoryRepo.save(category);
        return new ResponseEntity<>(categoryObj, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Category> updateCategoryById(Long id, Category newCategoryData) {
        Optional<Category> oldCategoryData = categoryRepo.findById(id);
        if (oldCategoryData.isPresent()) {
            Category updatedCategoryData = oldCategoryData.get();
            updatedCategoryData.setName(newCategoryData.getName());

            Category categoryObj = categoryRepo.save(updatedCategoryData);
            return new ResponseEntity<>(categoryObj, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<HttpStatus> deleteCategoryById(Long id) {
        Optional<Category> category = categoryRepo.findById(id);
        if (category.isPresent()) {
            categoryRepo.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<List<Category>> getAllCategories() {
        try {
            List<Category> categoryList = new ArrayList<>();
            categoryRepo.findAll().forEach(categoryList::add);
            if (categoryList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(categoryList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Category> getCategoryById(Long id) {
        Optional<Category> categoryData = categoryRepo.findById(id);
        if (categoryData.isPresent()) {
            return new ResponseEntity<>(categoryData.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
