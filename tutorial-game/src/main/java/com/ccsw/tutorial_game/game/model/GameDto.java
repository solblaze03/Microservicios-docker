package com.ccsw.tutorial_game.game.model;


import com.ccsw.tutorial_game.Author.model.AuthorDto;
import com.ccsw.tutorial_game.Category.model.CategoryDto;

public class GameDto {

    private Long id;

    private String title;

    private String age;

    private CategoryDto Category;

    private AuthorDto Author;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public CategoryDto getCategory() {
        return Category;
    }

    public void setCategory(CategoryDto category) {
        Category = category;
    }

    public AuthorDto getAuthor() {
        return Author;
    }

    public void setAuthor(AuthorDto author) {
        Author = author;
    }
}
