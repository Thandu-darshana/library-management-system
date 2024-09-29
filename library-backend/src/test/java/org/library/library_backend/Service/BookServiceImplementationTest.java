package org.library.library_backend.Service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.library.library_backend.Model.Author;
import org.library.library_backend.Model.Book;
import org.library.library_backend.Model.Category;
import org.library.library_backend.Repo.AuthorRepo;
import org.library.library_backend.Repo.BookRepo;
import org.library.library_backend.Repo.CategoryRepo;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;




public class BookServiceImplementationTest {

    @Mock
    private BookRepo bookRepo;

    @Mock
    private AuthorRepo authorRepo;

    @Mock
    private CategoryRepo categoryRepo;

    @InjectMocks
    private BookServiceImplementation bookService;


    private Book book;
    private Author author;
    private Category category;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Create sample data
        author = new Author(1L, "J.K. Rowling");
        category = new Category(1L, "Fantasy");
        book = new Book(1L, "Harry Potter", author, category, "1234567890", LocalDate.now(), true, 5, new ArrayList<>());
    }

    @Test
    public void addBookTest() {
        // Mocking the repository methods for the test
        when(authorRepo.findByName(anyString())).thenReturn(Optional.empty());
        when(authorRepo.save(any(Author.class))).thenReturn(author);
        when(categoryRepo.findByName(anyString())).thenReturn(Optional.empty());
        when(categoryRepo.save(any(Category.class))).thenReturn(category);
        when(bookRepo.save(any(Book.class))).thenReturn(book);

        // Call the addBook method
        ResponseEntity<Book> response = bookService.addBook(book);

        // Verify the response and interactions
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(book, response.getBody());
        verify(authorRepo, times(1)).save(any(Author.class));
        verify(categoryRepo, times(1)).save(any(Category.class));
        verify(bookRepo, times(1)).save(any(Book.class));
    }
    @Test
    public void GetAllBooksTest() {
        List<Book> books = new ArrayList<>();
        books.add(book);

        when(bookRepo.findAll()).thenReturn(books);

        ResponseEntity<List<Book>> response = bookService.getAllBooks();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(book, response.getBody().get(0));
    }

    @Test
    public void GetAllBooksNoContentTest(){
        when(bookRepo.findAll()).thenReturn(new ArrayList<>());
        ResponseEntity<List<Book>> response = bookService.getAllBooks();
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

    }
    @Test
    public void testGetBookById_Success() {
        when(bookRepo.findById(anyLong())).thenReturn(Optional.of(book));

        ResponseEntity<Book> response = bookService.getBookById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(book, response.getBody());
    }

    @Test
    public void testGetBookById_NotFound() {
        when(bookRepo.findById(anyLong())).thenReturn(Optional.empty());

        ResponseEntity<Book> response = bookService.getBookById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testUpdateBookById() {
        // Updated book information
        Book updatedBook = new Book(1L, "Harry Potter and the Chamber of Secrets", author, category, "0987654321", LocalDate.now(), true, 5, new ArrayList<>());

        // Mock the repository calls
        when(bookRepo.findById(anyLong())).thenReturn(Optional.of(book)); // Existing book
        when(authorRepo.findByName(anyString())).thenReturn(Optional.of(author)); // Author exists
        when(categoryRepo.findByName(anyString())).thenReturn(Optional.of(category)); // Category exists
        when(bookRepo.save(any(Book.class))).thenReturn(updatedBook); // Mock save

        // Call the service method
        ResponseEntity<Book> response = bookService.updateBookById(1L, updatedBook);

        // Assert the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedBook, response.getBody());
    }
    @Test
    public void testDeleteBookById_Success() {
        doNothing().when(bookRepo).deleteById(anyLong());

        ResponseEntity<HttpStatus> response = bookService.deleteBookById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(bookRepo, times(1)).deleteById(1L);
    }


}
