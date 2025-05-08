package com.example.tutorial_author.author;


import com.example.tutorial_author.author.model.Author;
import com.example.tutorial_author.author.model.AuthorDto;
import com.example.tutorial_author.author.model.AuthorSearchDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AuthorService {


    Author get(Long id);

    Page<Author> findPage(AuthorSearchDto dto);


    void save(Long id, AuthorDto dto);

    void delete(Long id) throws Exception;

    List<Author> findAll();
}
