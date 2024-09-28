package org.library.library_backend.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "Checkout")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Checkout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long checkoutId;
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "borrowed_on", nullable = false)
    private LocalDate borrowedOn;


    private LocalDate dueDate;

    @Column(name = "returned_on")
    private LocalDate returnedOn;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private CheckoutStatus status;


}
