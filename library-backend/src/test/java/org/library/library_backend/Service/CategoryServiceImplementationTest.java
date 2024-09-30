package org.library.library_backend.Service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.library.library_backend.Model.Category;
import org.library.library_backend.Repo.CategoryRepo;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceImplementationTest {

    @Mock
    private CategoryRepo categoryRepo;

    @InjectMocks
    private CategoryServiceImplementation categoryService;

    private Category category;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        category = new Category(1L, "Fiction");
    }

    @Test
    public void addCategoryTest() {
        when(categoryRepo.save(any(Category.class))).thenReturn(category);

        ResponseEntity<Category> response = categoryService.addCategory(category);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(category, response.getBody());
        verify(categoryRepo, times(1)).save(any(Category.class));
    }

    @Test
    public void updateCategoryByIdTest() {
        Category updatedCategory = new Category(1L, "Non-Fiction");

        when(categoryRepo.findById(anyLong())).thenReturn(Optional.of(category));
        when(categoryRepo.save(any(Category.class))).thenReturn(updatedCategory);

        ResponseEntity<Category> response = categoryService.updateCategoryById(1L, updatedCategory);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedCategory, response.getBody());
        verify(categoryRepo, times(1)).save(any(Category.class));
    }

    @Test
    public void updateCategoryByIdNotFoundTest() {
        when(categoryRepo.findById(anyLong())).thenReturn(Optional.empty());

        ResponseEntity<Category> response = categoryService.updateCategoryById(1L, category);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(categoryRepo, never()).save(any(Category.class));
    }

    @Test
    public void deleteCategoryByIdTest() {
        when(categoryRepo.findById(anyLong())).thenReturn(Optional.of(category));
        doNothing().when(categoryRepo).deleteById(anyLong());

        ResponseEntity<HttpStatus> response = categoryService.deleteCategoryById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(categoryRepo, times(1)).deleteById(anyLong());
    }

    @Test
    public void deleteCategoryByIdNotFoundTest() {
        when(categoryRepo.findById(anyLong())).thenReturn(Optional.empty());

        ResponseEntity<HttpStatus> response = categoryService.deleteCategoryById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(categoryRepo, never()).deleteById(anyLong());
    }

    @Test
    public void getAllCategoriesTest() {
        List<Category> categories = new ArrayList<>();
        categories.add(category);

        when(categoryRepo.findAll()).thenReturn(categories);

        ResponseEntity<List<Category>> response = categoryService.getAllCategories();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(category, response.getBody().get(0));
    }

    @Test
    public void getAllCategoriesNoContentTest() {
        when(categoryRepo.findAll()).thenReturn(new ArrayList<>());

        ResponseEntity<List<Category>> response = categoryService.getAllCategories();

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void getCategoryByIdTest() {
        when(categoryRepo.findById(anyLong())).thenReturn(Optional.of(category));

        ResponseEntity<Category> response = categoryService.getCategoryById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(category, response.getBody());
    }

    @Test
    public void getCategoryByIdNotFoundTest() {
        when(categoryRepo.findById(anyLong())).thenReturn(Optional.empty());

        ResponseEntity<Category> response = categoryService.getCategoryById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}