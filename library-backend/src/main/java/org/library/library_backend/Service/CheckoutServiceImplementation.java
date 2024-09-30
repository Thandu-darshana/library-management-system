package org.library.library_backend.Service;

import org.library.library_backend.Model.Book;
import org.library.library_backend.Model.Checkout;
import org.library.library_backend.Model.CheckoutStatus;
import org.library.library_backend.Model.Member;
import org.library.library_backend.Repo.BookRepo;
import org.library.library_backend.Repo.CheckoutRepo;
import org.library.library_backend.Repo.MemberRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CheckoutServiceImplementation implements CheckoutService{
    @Autowired
    private CheckoutRepo checkoutRepo;

    @Autowired
    private BookRepo bookRepo;

    @Autowired
    private MemberRepo memberRepo;
    @Override
    public ResponseEntity<Checkout> checkoutBook(Long bookId, Long memberId, LocalDate dueDate) {
        Book book = bookRepo.findById(bookId).orElse(null);
        if (book == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Book not found
        }

        Member member = memberRepo.findById(memberId).orElse(null);
        if (member == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Member not found
        }

        if (book.getCopies() <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // No copies available for this book
        }

        Checkout checkout = new Checkout();
        checkout.setBook(book);
        checkout.setMember(member);
        checkout.setBorrowedOn(LocalDate.now());
        // Set due date as 2 weeks from now
        checkout.setDueDate(dueDate != null ? dueDate:LocalDate.now().plusWeeks(2));
        checkout.setStatus(CheckoutStatus.BORROWED);

        // Decrease available copies
        book.setCopies(book.getCopies() - 1);
        bookRepo.save(book);

        Checkout savedCheckout = checkoutRepo.save(checkout);
        return new ResponseEntity<>(savedCheckout, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Checkout> returnBook(Long checkoutId) {
        Optional<Checkout> checkoutOptional = checkoutRepo.findById(checkoutId);
        if (!checkoutOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Checkout checkout = checkoutOptional.get();
        if (checkout.getStatus() == CheckoutStatus.RETURNED) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // Mark the book as returned
        checkout.setStatus(CheckoutStatus.RETURNED);

        // Increment the number of available copies for the book
        Book book = checkout.getBook();
        book.setCopies(book.getCopies() + 1);

        // Save the updated checkout and book
        checkoutRepo.save(checkout);
        bookRepo.save(book);

        return new ResponseEntity<>(checkout, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Checkout>> getActiveCheckouts() {
        List<Checkout> activeCheckouts = checkoutRepo.findAllByStatus(CheckoutStatus.BORROWED);
        if (activeCheckouts.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(activeCheckouts, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Checkout>>getOverdueCheckouts() {
        List<Checkout> overdueCheckouts = checkoutRepo.findOverdueCheckouts(LocalDate.now());
        if (overdueCheckouts.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(overdueCheckouts, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Checkout> getCheckoutById(Long checkoutId) {
        Checkout checkout = checkoutRepo.findById(checkoutId).orElse(null);
        if (checkout == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Checkout record not found
        }
        return new ResponseEntity<>(checkout, HttpStatus.OK);
    }
}

