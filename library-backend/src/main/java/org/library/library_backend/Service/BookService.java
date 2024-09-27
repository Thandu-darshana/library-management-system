package org.library.library_backend.Service;

import org.library.library_backend.Model.Book;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface BookService {
    public ResponseEntity<Book> addBook(Book book);
    public ResponseEntity<Book> updateBookById(Long book_id, Book newBookData);
    public ResponseEntity<HttpStatus> deleteBookById(Long book_id);
    public ResponseEntity<List<Book>> getAllBooks();
    public ResponseEntity<Book> getBookById(Long book_id);


}
