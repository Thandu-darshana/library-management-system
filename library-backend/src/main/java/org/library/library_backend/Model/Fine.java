package org.library.library_backend.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "fines")
public class Fine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "amount", nullable = false)
    private Double amount;

    @Column(name = "issue_date", nullable = false)
    private LocalDate issueDate;

    @Column(name = "is_paid", nullable = false)
    private boolean isPaid;


    @ManyToOne
    @JoinColumn(name = "checkout_id", nullable = false)
    private Checkout checkout;
}
