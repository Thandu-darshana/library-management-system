package org.library.library_backend.Service;

import org.library.library_backend.Model.Book;
import org.library.library_backend.Model.Category;
import org.library.library_backend.Model.Fine;
import org.library.library_backend.Repo.FineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FineService {

    @Autowired
    private FineRepository fineRepository;

    public List<Fine> getAllFinesForUser(String memberId) {
        return fineRepository.findByUserId(memberId);
    }

    public List<Fine> getUnpaidFinesForUser(String memberId) {
        return fineRepository.findByUserIdAndIsPaidFalse(memberId);
    }

    public Fine markFineAsPaid(Long fineId) throws Exception {
        Optional<Fine> fine = fineRepository.findById(fineId);
        if (fine.isPresent()) {
            Fine fineToUpdate = fine.get();
            fineToUpdate.setPaid(true);
            return fineRepository.save(fineToUpdate);
        } else {
            throw new Exception("Fine not found");
        }
    }

    public Fine createFine(Fine fine) {
        fine.setPaid(false);  // Default to unpaid
        return fineRepository.save(fine);
    }

    public List<Fine> getFinesByBook(Book book) {
        return fineRepository.findByBook(book);
    }

    public List<Fine> getFinesByCategory(Category category) {
        return fineRepository.findByCategory(category);
    }

}

