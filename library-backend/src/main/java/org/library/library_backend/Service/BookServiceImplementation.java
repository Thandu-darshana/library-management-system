package org.library.library_backend.Service;

import org.library.library_backend.Model.Author;
import org.library.library_backend.Model.Book;
import org.library.library_backend.Model.Category;
import org.library.library_backend.Repo.AuthorRepo;
import org.library.library_backend.Repo.BookRepo;
import org.library.library_backend.Repo.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class BookServiceImplementation implements BookService {
    @Autowired
    BookRepo bookRepo;
    @Autowired
    AuthorRepo authorRepo;

    @Autowired
    CategoryRepo categoryRepo;
    @Override
    public ResponseEntity<Book> addBook(Book book) {
        Author author = book.getAuthor();
        Optional<Author> existingAuthor = authorRepo.findByName(author.getName());

        if(!existingAuthor.isPresent()){
            author = authorRepo.save(author);
        }else{
            author = existingAuthor.get();
        }
        book.setAuthor(author);

        // Handle Category
        Category category = book.getCategory();
        Optional<Category> existingCategory = categoryRepo.findByName(category.getName());
        if (!existingCategory.isPresent()) {
            category = categoryRepo.save(category); // Save new category if not exists
        } else {
            category = existingCategory.get(); // Use existing category
        }
        book.setCategory(category);

        // Save the book
        book.setAdded_on(LocalDate.now());
        Book bookObj = bookRepo.save(book);
        return new ResponseEntity<>(bookObj, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Book> updateBookById(Long book_id, Book newBookData) {
        Optional<Book> oldbookData = bookRepo.findById(book_id);
        if(oldbookData.isPresent()){
            Book updatedBookData = oldbookData.get();
            updatedBookData.setTitle(newBookData.getTitle());

            Author newAuthor = newBookData.getAuthor();
            Optional<Author> existingAuthor = authorRepo.findByName(newAuthor.getName());

            if(!existingAuthor.isPresent()){
                newAuthor = authorRepo.save(newAuthor);
            }else{
                newAuthor = existingAuthor.get();
            }
            updatedBookData.setAuthor(newAuthor);

            // Update Category
            Category newCategory = newBookData.getCategory();
            Optional<Category> existingCategory = categoryRepo.findByName(newCategory.getName());
            if (!existingCategory.isPresent()) {
                newCategory = categoryRepo.save(newCategory); // Save new category if not exists
            } else {
                newCategory = existingCategory.get(); // Use existing category
            }
            updatedBookData.setCategory(newCategory);

            // Update other fields
            updatedBookData.setIsbn(newBookData.getIsbn());
            updatedBookData.setAdded_on(newBookData.getAdded_on());
            updatedBookData.setAvailable(newBookData.getAvailable());

            // Save the updated book
            Book bookObj = bookRepo.save(updatedBookData);
            return new ResponseEntity<>(bookObj, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @Override
    public ResponseEntity<HttpStatus> deleteBookById(Long book_id) {
            bookRepo.deleteById(book_id);
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
    public ResponseEntity<Book> getBookById(Long book_id) {
            Optional<Book> bookData = bookRepo.findById(book_id);
            if(bookData.isPresent()){
                return new ResponseEntity<>(bookData.get(),HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


