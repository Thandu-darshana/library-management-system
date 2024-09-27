package org.library.library_backend.Service;

import org.library.library_backend.Model.Author;
import org.library.library_backend.Repo.AuthorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AuthorServiceImplementation implements AuthorService{

    @Autowired
    AuthorRepo authorRepo;
    @Override
    public ResponseEntity<Author> addAuthor(Author author) {
        Optional<Author> existingAuthor = authorRepo.findByName(author.getName());
        if (existingAuthor.isPresent()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);  // Return conflict if author exists
        }
        Author authorObj = authorRepo.save(author);

        return new ResponseEntity<>(authorObj,HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Author> updateAuthorById(Long author_id, Author newAuthorData) {
        Optional<Author> oldAuthorData = authorRepo.findById(author_id);
        if(oldAuthorData.isPresent()){
            Author updatedAuthorData = oldAuthorData.get();
            updatedAuthorData.setName(newAuthorData.getName());

            Author authorObj = authorRepo.save(updatedAuthorData);
            return new ResponseEntity<>(authorObj,HttpStatus.OK);

        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<HttpStatus> deleteAuthorById(Long author_id) {
        Optional<Author> author = authorRepo.findById(author_id);
        if (author.isPresent()) {
            authorRepo.deleteById(author_id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    };

    @Override
    public ResponseEntity<List<Author>> getAllAuthors() {
        try {
            List<Author> authorList = new ArrayList<>();
            authorRepo.findAll().forEach(authorList::add);
            if(authorList.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(authorList,HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Author> getAuthorById(Long author_id) {
        Optional <Author> authorData = authorRepo.findById(author_id);
        if(authorData.isPresent()){
            return new ResponseEntity<>(authorData.get(),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
