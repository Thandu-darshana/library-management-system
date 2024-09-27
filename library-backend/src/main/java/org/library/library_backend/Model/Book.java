package org.library.library_backend.Model;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Books")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long book_id;
    @Column(name = "title" ,nullable = false)
    private String title;
    @ManyToOne
    @JoinColumn (name = "author_id" )
    private Author author;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(nullable = false)
    private String isbn;

    @Column(nullable = false)
    private LocalDateTime added_on;

    @Column(nullable = false)
    private Boolean available;





}
