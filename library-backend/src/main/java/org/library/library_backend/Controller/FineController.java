package org.library.library_backend.Controller;

import org.library.library_backend.Model.Fine;
import org.library.library_backend.Service.FineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fines")
public class FineController {

    @Autowired
    private FineService fineService;

    @GetMapping("/{memberId}")
    public ResponseEntity<List<Fine>> getAllFines(@PathVariable String memberId) {
        return ResponseEntity.ok(fineService.getAllFinesForMember(memberId));
    }

    @GetMapping("/{memberId}/unpaid")
    public ResponseEntity<List<Fine>> getUnpaidFines(@PathVariable String memberId) {
        return ResponseEntity.ok(fineService.getUnpaidFinesForMember(memberId));
    }

    @PostMapping("/{fineId}/pay")
    public ResponseEntity<Fine> payFine(@PathVariable Long fineId) {
        try {
            Fine updatedFine = fineService.markFineAsPaid(fineId);
            return ResponseEntity.ok(updatedFine);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }


    @PostMapping("/checkout/{checkoutId}/calculate")
    public ResponseEntity<Fine> calculateFineForCheckout(@PathVariable Long checkoutId) {
        try {
            Fine fine = fineService.calculateFineForCheckout(checkoutId);
            return ResponseEntity.ok(fine);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Fine> addFine(@RequestBody Fine fine) {
        Fine createdFine = fineService.createFine(fine);
        return ResponseEntity.ok(createdFine);
    }
}
