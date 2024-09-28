package org.library.library_backend.Model;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Books")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long book_id;
    @Column(name = "title" ,nullable = false)
    private String title;
    @ManyToOne
    @JoinColumn (name = "author_id" )
    private Author author;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(unique = true , nullable = false)
    private String isbn;

    @Column
    private LocalDate added_on;

    @Column(nullable = false)
    private Boolean available;

    @Column(nullable = false)
    private int copies;

    @OneToMany(mappedBy = "book")
    private List<Fine> fines;


    public void setId(Long bookId) {
    }
}
