package org.library.library_backend.Model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "Category")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long category_id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "category")
    private List<Fine> fines;

    public void setId(Long categoryId) {
    }
}
