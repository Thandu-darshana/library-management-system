package org.library.library_backend.Repo;

import org.library.library_backend.Model.Book;
import org.library.library_backend.Model.Category;
import org.library.library_backend.Model.Fine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FineRepository extends JpaRepository<Fine, Long> {

    List<Fine> findByUserIdAndIsPaidFalse(String memberId);

    List<Fine> findByUserId(String userId);

    List<Fine> findByBook(Book book);

    List<Fine> findByCategory(Category category);

}
