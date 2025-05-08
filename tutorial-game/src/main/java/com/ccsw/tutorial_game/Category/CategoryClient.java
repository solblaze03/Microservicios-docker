package com.ccsw.tutorial_game.Category;

import com.ccsw.tutorial_game.Category.model.CategoryDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Locale;

@FeignClient(value = "SPRING-CLOUD-EUREKA-CLIENT-CATEGORY", url = "${service.url}")
public interface CategoryClient {

    @GetMapping(value = "/category")
    List<CategoryDto> findAll();
}
