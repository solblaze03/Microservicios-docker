package com.example.tutorial_category.category;

import com.example.tutorial_category.category.category.Category;
import com.example.tutorial_category.category.category.CategoryDto;
import com.example.tutorial_category.category.category.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name="category", description = "Api of Category")
@RequestMapping(value = "/category")
@CrossOrigin(origins = "*")
@RestController
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @Autowired
    ModelMapper mapper;


    @Operation(summary = "Find", description = "Method that return a list of Categories"
    )
    @RequestMapping(path = "", method = RequestMethod.GET)
    public List<CategoryDto> findAll() {

        List<Category> categories = this.categoryService.findAll();

        return categories.stream().map(e -> mapper.map(e, CategoryDto.class)).collect(Collectors.toList());
    }


    @Operation(summary = "Save or Update", description = "Method that saves or updates a Category"
    )
    @RequestMapping(path = { "", "/{id}" }, method = RequestMethod.PUT)
    public void save(@PathVariable(name = "id", required = false) Long id, @RequestBody CategoryDto dto) {

        this.categoryService.save(id, dto);
    }


    @Operation(summary = "Delete", description = "Method that deletes a Category")
    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Long id) throws Exception {

        this.categoryService.delete(id);
    }

}
