package org.library.library_backend.Repo;

import org.library.library_backend.Model.Checkout;
import org.library.library_backend.Model.Fine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FineRepository extends JpaRepository<Fine, Long> {

    List<Fine> findByCheckout_Member_MemberId(String memberId);

    List<Fine> findByCheckout_Member_MemberIdAndIsPaidFalse(String memberId);

    List<Fine> findByCheckout(Checkout checkout);
}
