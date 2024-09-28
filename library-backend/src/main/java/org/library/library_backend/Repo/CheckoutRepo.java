package org.library.library_backend.Repo;

import org.library.library_backend.Model.Checkout;
import org.library.library_backend.Model.CheckoutStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CheckoutRepo extends JpaRepository<Checkout,Long>{

    List<Checkout> findAllByStatus(CheckoutStatus borrowed);

    @Query("SELECT c FROM Checkout c WHERE c.dueDate < :currentDate AND c.status = 'BORROWED'")
    List<Checkout> findOverdueCheckouts(@Param("currentDate") LocalDate currentDate);

}
