package org.library.library_backend.Service;

import org.library.library_backend.Model.Checkout;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public interface CheckoutService {
    public ResponseEntity<Checkout> checkoutBook(Long bookId, Long memberId, LocalDate dueDate);

    public ResponseEntity<Checkout> returnBook(Long checkoutId);

    public ResponseEntity<List<Checkout>> getActiveCheckouts();

    public ResponseEntity<List<Checkout>>getOverdueCheckouts();

    public ResponseEntity<Checkout> getCheckoutById(Long checkoutId);
}
