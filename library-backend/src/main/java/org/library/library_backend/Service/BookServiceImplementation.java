package org.library.library_backend.Service;

import org.library.library_backend.Model.Book;
import org.library.library_backend.Repo.BookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImplementation implements BookService {
    @Autowired
    BookRepo bookRepo;
    @Override
    public ResponseEntity<Book> addBook(Book book) {
        Book bookObj = bookRepo.save(book);

        return new ResponseEntity<>(bookObj,HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Book> updateBookById(Long id, Book newBookData) {
        Optional<Book> oldbookData = bookRepo.findById(id);
        if(oldbookData.isPresent()){
            Book updatedBookData = oldbookData.get();
            updatedBookData.setTitle(newBookData.getTitle());
            updatedBookData.setAuthor(newBookData.getAuthor());

            Book bookObj = bookRepo.save(updatedBookData);
            return new ResponseEntity<>(bookObj,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<HttpStatus> deleteBookById(Long id) {
            bookRepo.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Book>> getAllBooks(){
            try{
                List<Book> bookList = new ArrayList<>();
                bookRepo.findAll().forEach(bookList::add);

                if (bookList.isEmpty()){
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                }
                return new ResponseEntity<>(bookList,HttpStatus.OK);

            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
    }
    @Override
    public ResponseEntity<Book> getBookById(Long id) {
            Optional<Book> bookData = bookRepo.findById(id);
            if(bookData.isPresent()){
                return new ResponseEntity<>(bookData.get(),HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


