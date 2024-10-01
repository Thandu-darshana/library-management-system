package org.library.library_backend.Controller;

import org.library.library_backend.Model.Book;
import org.library.library_backend.Service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;


@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/books")
public class BookController {

    @Autowired
    BookService bookService;

    @PostMapping("/addBook")
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        return bookService.addBook(book);
    }

    @PostMapping("/updateBookById/{book_id}")
    public ResponseEntity<Book> updateBookById(@PathVariable Long book_id, @RequestBody Book newBookData) {
        return bookService.updateBookById(book_id, newBookData);

    }

    @DeleteMapping("/deleteBookById/{book_id}")
    public ResponseEntity<HttpStatus> deleteBookById(@PathVariable Long book_id) {
        return bookService.deleteBookById(book_id);
    }

    @GetMapping("/getAllBooks")
    public ResponseEntity<List<Book>> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/getBookById/{book_id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long book_id) {
        return bookService.getBookById(book_id);
    }

}