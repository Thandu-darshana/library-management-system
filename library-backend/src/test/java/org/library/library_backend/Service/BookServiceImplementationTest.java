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




@ExtendWith(MockitoExtension.class)
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
        author = new Author();
        author.setName("Author Name");

        category = new Category();
        category.setName("Category Name");

        book = new Book();
        book.setBook_id(1L);
        book.setTitle("Test Book");
        book.setAuthor(author);
        book.setCategory(category);
        book.setIsbn("1234567890");
        book.setAdded_on(LocalDate.now());
        book.setAvailable(true);

    }

    @Test
    public void addBookTest() {
        when(authorRepo.findByName(author.getName())).thenReturn(Optional.empty());
        when(authorRepo.save(any(Author.class))).thenReturn(author);
        when(categoryRepo.findByName(category.getName())).thenReturn(Optional.empty());
        when(categoryRepo.save(any(Category.class))).thenReturn(category);
        when(bookRepo.save(any(Book.class))).thenReturn(book);

        ResponseEntity<Book> response = bookService.addBook(book);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Test Book", response.getBody().getTitle());
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
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());

    }

    @Test
    public void GetAllBooksNoContentTest(){
        when(bookRepo.findAll()).thenReturn(new ArrayList<>());
        ResponseEntity<List<Book>> response = bookService.getAllBooks();
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

    }
    @Test
    public void GetBookByIdTest() {
        when(bookRepo.findById(anyLong())).thenReturn(Optional.of(book));

        ResponseEntity<Book> response = bookService.getBookById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Test Book", response.getBody().getTitle());
    }

    @Test
    public void GetBookById_NotFoundTest() {
        when(bookRepo.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Book> response = bookService.getBookById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void UpdateBookByIdTest() {
        when(bookRepo.findById(1L)).thenReturn(Optional.of(book));
        when(authorRepo.findByName(author.getName())).thenReturn(Optional.of(author));
        when(categoryRepo.findByName(category.getName())).thenReturn(Optional.of(category));
        when(bookRepo.save(any(Book.class))).thenReturn(book);

        Book updatedBook = new Book();
        updatedBook.setTitle("Updated Title");
        updatedBook.setAuthor(author);
        updatedBook.setCategory(category);
        updatedBook.setIsbn("0987654321");
        updatedBook.setAdded_on(LocalDate.now());
        updatedBook.setAvailable(true);

        ResponseEntity<Book> response = bookService.updateBookById(1L, updatedBook);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Updated Title", response.getBody().getTitle());
        verify(bookRepo, times(1)).save(any(Book.class));

    }
    @Test
    public void UpdateBookById_NotFoundTest(){
        when(bookRepo.findById(1L)).thenReturn(Optional.empty());

        Book updatedBook = new Book();
        updatedBook.setTitle("Updated Title");

        ResponseEntity<Book> response = bookService.updateBookById(1L, updatedBook);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(bookRepo, times(0)).save(any(Book.class));
    }
    @Test
    public void testDeleteBookById_Success() {
        doNothing().when(bookRepo).deleteById(anyLong());

        ResponseEntity<HttpStatus> response = bookService.deleteBookById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(bookRepo, times(1)).deleteById(1L);
    }


}
