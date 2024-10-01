package org.library.library_backend.Controller;

import org.library.library_backend.Model.Author;
import org.library.library_backend.Service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    @Autowired
    AuthorService authorService;

    @PostMapping("/addAuthor")
    public ResponseEntity<Author> addAuthor(@RequestBody Author author){
        return authorService.addAuthor(author);
    }
    @PostMapping("/UpdateAuthor/{author_id}")
    public ResponseEntity<Author> updateAuthorById(@PathVariable Long author_id,@RequestBody Author newAuthorData){
        return authorService.updateAuthorById(author_id,newAuthorData);
    }
    @DeleteMapping("/deleteAuthorById/{author_id}")
    public ResponseEntity<HttpStatus> deleteAuthorById(@PathVariable Long author_id){
        return authorService.deleteAuthorById(author_id);
    }
    @GetMapping("/getAllAuthors")
    public ResponseEntity<List<Author>> getAllAuthors(){
        return authorService.getAllAuthors();

    }
    @GetMapping("/getAuthorById/{author_id}")
    public ResponseEntity<Author> getAuthorById(@PathVariable Long author_id){
        return authorService.getAuthorById(author_id);
    }
}
