package org.library.library_backend.Controller;

import org.library.library_backend.Model.Author;
import org.library.library_backend.Service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Authors")
public class AuthorController {

    @Autowired
    AuthorService authorService;

    @PostMapping("/addAuthor")
    public ResponseEntity<Author> addAuthor(@RequestBody Author author){
        return authorService.addAuthor(author);
    }
    @PostMapping("/UpdateAuthor/{id}")
    public ResponseEntity<Author> updateAuthorById(@PathVariable int id,@RequestBody Author newAuthorData){
        return authorService.updateAuthorById(id,newAuthorData);
    }
    @DeleteMapping("/deleteAuthorById/{id}")
    public ResponseEntity<HttpStatus> deleteAuthorById(@PathVariable int id){
        return authorService.deleteAuthorById(id);
    }
    @GetMapping("/getAllAuthors")
    public ResponseEntity<List<Author>> getAllAuthors(){
        return authorService.getAllAuthors();

    }
    @GetMapping("/getAuthorById/{id}")
    public ResponseEntity<Author> getAuthorById(@PathVariable int id){
        return authorService.getAuthorById(id);
    }
}
