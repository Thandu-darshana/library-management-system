package org.library.library_backend.Service;

import org.library.library_backend.Model.Author;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AuthorService {
    public ResponseEntity<Author> addAuthor(Author author);
    public ResponseEntity<Author> updateAuthorById(Long id, Author newAuthorData);
    public ResponseEntity<HttpStatus> deleteAuthorById(Long author_id);
    public ResponseEntity<List<Author>> getAllAuthors();
    public ResponseEntity<Author> getAuthorById(Long author_id);

}
