package org.library.library_backend.Controller;

import org.library.library_backend.Model.Checkout;
import org.library.library_backend.Service.CheckoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/checkouts")
public class CheckoutController {
    @Autowired
    private CheckoutService checkoutService;

    @PostMapping("/checkout")
    public ResponseEntity<Checkout> checkoutBook(
            @RequestParam Long bookId,
            @RequestParam Long memberId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dueDate) {
        return checkoutService.checkoutBook(bookId, memberId, dueDate);
    }


    @PostMapping("/return/{checkoutId}")
    public ResponseEntity<Checkout> returnBook(@PathVariable Long checkoutId) {
        return checkoutService.returnBook(checkoutId);
    }


    @GetMapping("/active")
    public ResponseEntity<List<Checkout>> getActiveCheckouts() {
        return checkoutService.getActiveCheckouts();
    }


    @GetMapping("/overdue")
    public ResponseEntity<List<Checkout>> getOverdueCheckouts() {
        return checkoutService.getOverdueCheckouts();
    }

    @GetMapping("/{checkoutId}")
    public ResponseEntity<Checkout> getCheckoutById(@PathVariable Long checkoutId) {
        return checkoutService.getCheckoutById(checkoutId);
    }
}
