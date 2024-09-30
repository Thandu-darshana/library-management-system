package org.library.library_backend.Service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.library.library_backend.Model.Author;
import org.library.library_backend.Repo.AuthorRepo;
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
public class AuthorServiceImplementationTest {

    @Mock
    private AuthorRepo authorRepo;

    @InjectMocks
    private AuthorServiceImplementation authorService;

    private Author author;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        author = new Author(1L, "J.K. Rowling");
    }

    @Test
    public void addAuthorTest() {
        when(authorRepo.findByName(anyString())).thenReturn(Optional.empty());
        when(authorRepo.save(any(Author.class))).thenReturn(author);

        ResponseEntity<Author> response = authorService.addAuthor(author);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(author, response.getBody());
        verify(authorRepo, times(1)).save(any(Author.class));
    }

    @Test
    public void addAuthorConflictTest() {
        when(authorRepo.findByName(anyString())).thenReturn(Optional.of(author));

        ResponseEntity<Author> response = authorService.addAuthor(author);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        verify(authorRepo, never()).save(any(Author.class));
    }

    @Test
    public void updateAuthorByIdTest() {
        Author updatedAuthor = new Author(1L, "George R. R. Martin");

        when(authorRepo.findById(anyLong())).thenReturn(Optional.of(author));
        when(authorRepo.save(any(Author.class))).thenReturn(updatedAuthor);

        ResponseEntity<Author> response = authorService.updateAuthorById(1L, updatedAuthor);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedAuthor, response.getBody());
        verify(authorRepo, times(1)).save(any(Author.class));
    }

    @Test
    public void updateAuthorByIdNotFoundTest() {
        when(authorRepo.findById(anyLong())).thenReturn(Optional.empty());

        ResponseEntity<Author> response = authorService.updateAuthorById(1L, author);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(authorRepo, never()).save(any(Author.class));
    }

    @Test
    public void deleteAuthorByIdTest() {
        when(authorRepo.findById(anyLong())).thenReturn(Optional.of(author));
        doNothing().when(authorRepo).deleteById(anyLong());

        ResponseEntity<HttpStatus> response = authorService.deleteAuthorById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(authorRepo, times(1)).deleteById(anyLong());
    }

    @Test
    public void deleteAuthorByIdNotFoundTest() {
        when(authorRepo.findById(anyLong())).thenReturn(Optional.empty());

        ResponseEntity<HttpStatus> response = authorService.deleteAuthorById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(authorRepo, never()).deleteById(anyLong());
    }

    @Test
    public void getAllAuthorsTest() {
        List<Author> authors = new ArrayList<>();
        authors.add(author);

        when(authorRepo.findAll()).thenReturn(authors);

        ResponseEntity<List<Author>> response = authorService.getAllAuthors();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(author, response.getBody().get(0));
    }

    @Test
    public void getAllAuthorsNoContentTest() {
        when(authorRepo.findAll()).thenReturn(new ArrayList<>());

        ResponseEntity<List<Author>> response = authorService.getAllAuthors();

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void getAuthorByIdTest() {
        when(authorRepo.findById(anyLong())).thenReturn(Optional.of(author));

        ResponseEntity<Author> response = authorService.getAuthorById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(author, response.getBody());
    }

    @Test
    public void getAuthorByIdNotFoundTest() {
        when(authorRepo.findById(anyLong())).thenReturn(Optional.empty());

        ResponseEntity<Author> response = authorService.getAuthorById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
