package com.example.tutorial_author.author;


import com.example.tutorial_author.author.model.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

/**
 * @author ccsw
 *
 */
public interface AuthorRepository extends CrudRepository<Author, Long> {

    Page<Author> findAll(Pageable pageable);
}