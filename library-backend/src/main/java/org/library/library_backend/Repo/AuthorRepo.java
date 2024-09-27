package org.library.library_backend.Repo;

import org.library.library_backend.Model.Author;
import org.library.library_backend.Model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepo extends JpaRepository<Author,Integer> {
}
