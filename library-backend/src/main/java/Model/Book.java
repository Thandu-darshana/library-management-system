package Model;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Books")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String title;

    private String Author;


}
