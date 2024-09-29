package org.library.library_backend.Service;

import org.library.library_backend.Model.Checkout;
import org.library.library_backend.Model.Fine;
import org.library.library_backend.Repo.CheckoutRepo;
import org.library.library_backend.Repo.FineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class FineService {

    @Autowired
    private FineRepository fineRepository;

    @Autowired
    private CheckoutRepo checkoutRepository;

    private static final double DAILY_FINE_RATE = 1.5; // Example daily fine rate

    public List<Fine> getAllFinesForMember(String memberId) {
        return fineRepository.findByCheckout_Member_MemberId(memberId);
    }

    public List<Fine> getUnpaidFinesForMember(String memberId) {
        return fineRepository.findByCheckout_Member_MemberIdAndIsPaidFalse(memberId);
    }

    public Fine markFineAsPaid(Long fineId) throws Exception {
        Optional<Fine> fineOptional = fineRepository.findById(fineId);
        if (fineOptional.isPresent()) {
            Fine fine = fineOptional.get();
            fine.setPaid(true);
            return fineRepository.save(fine);
        } else {
            throw new Exception("Fine not found");
        }
    }

    public Fine createFine(Fine fine) {
        return fineRepository.save(fine);
    }

    public Fine calculateFineForCheckout(Long checkoutId) throws Exception {
        Optional<Checkout> checkoutOptional = checkoutRepository.findById(checkoutId);
        if (checkoutOptional.isPresent()) {
            Checkout checkout = checkoutOptional.get();
            LocalDate dueDate = checkout.getDueDate();
            LocalDate returnedOn = checkout.getReturnedOn() != null ? checkout.getReturnedOn() : LocalDate.now();

            long daysLate = ChronoUnit.DAYS.between(dueDate, returnedOn);

            if (daysLate > 0) {
                double fineAmount = daysLate * DAILY_FINE_RATE;
                Fine fine = new Fine();
                fine.setAmount(fineAmount);
                fine.setIssueDate(LocalDate.now());
                fine.setPaid(false);
                fine.setCheckout(checkout);
                return fineRepository.save(fine);
            } else {
                throw new Exception("No fine applicable as the book was returned on time");
            }
        } else {
            throw new Exception("Checkout not found");
        }
    }

    public List<Fine> getFinesByCheckout(Checkout checkout) {
        return fineRepository.findByCheckout(checkout);
    }
}
