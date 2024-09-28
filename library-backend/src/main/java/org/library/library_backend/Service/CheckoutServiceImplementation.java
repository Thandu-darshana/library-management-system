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
        checkout.setDueDate(LocalDate.now().plusWeeks(2));
        checkout.setStatus(CheckoutStatus.BORROWED);

        // Decrease available copies
        book.setCopies(book.getCopies() - 1);
        bookRepo.save(book);

        Checkout savedCheckout = checkoutRepo.save(checkout);
        return new ResponseEntity<>(savedCheckout, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Checkout> returnBook(Long checkoutId) {
        Checkout checkout = checkoutRepo.findById(checkoutId).orElse(null);
        if (checkout == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Checkout record not found
        }

        if (checkout.getStatus() != CheckoutStatus.BORROWED) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Book is not currently borrowed
        }

        checkout.setReturnedOn(LocalDate.now());
        checkout.setStatus(CheckoutStatus.RETURNED);

        // Increment available copies
        Book book = checkout.getBook();
        book.setCopies(book.getCopies() + 1);
        bookRepo.save(book);

        Checkout savedCheckout = checkoutRepo.save(checkout);
        return new ResponseEntity<>(savedCheckout, HttpStatus.OK);
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

