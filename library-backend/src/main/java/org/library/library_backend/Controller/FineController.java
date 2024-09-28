package org.library.library_backend.Controller;

import org.library.library_backend.Model.Author;
import org.library.library_backend.Model.Book;
import org.library.library_backend.Model.Category;
import org.library.library_backend.Model.Fine;
import org.library.library_backend.Service.FineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fines")
public class FineController {

    @Autowired
    private FineService fineService;

    @GetMapping("/{userId}")
    public ResponseEntity<List<Fine>> getAllFines(@PathVariable String memberId) {
        return ResponseEntity.ok(fineService.getAllFinesForUser(memberId));
    }

    @GetMapping("/{userId}/unpaid")
    public ResponseEntity<List<Fine>> getUnpaidFines(@PathVariable String memberId) {
        return ResponseEntity.ok(fineService.getUnpaidFinesForUser(memberId));
    }

    @PostMapping("/{fineId}/pay")
    public ResponseEntity<Fine> payFine(@PathVariable Long fineId) {
        try {
            Fine updatedFine = fineService.markFineAsPaid(fineId);
            return ResponseEntity.ok(updatedFine);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Fine> addFine(@RequestBody Fine fine) {
        Fine createdFine = fineService.createFine(fine);
        return ResponseEntity.ok(createdFine);
    }

    @GetMapping("/book/{bookId}")
    public ResponseEntity<List<Fine>> getFinesByBook(@PathVariable Long bookId) {
        Book book = new Book();  // Assuming you fetch the book by ID
        book.setId(bookId);
        return ResponseEntity.ok(fineService.getFinesByBook(book));
    }

    // Retrieve fines by category ID
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Fine>> getFinesByCategory(@PathVariable Long categoryId) {
        Category category = new Category();  // Assuming you fetch the category by ID
        category.setId(categoryId);
        return ResponseEntity.ok(fineService.getFinesByCategory(category));
    }


}

