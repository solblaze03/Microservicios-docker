package com.ccsw.tutorial_game.Author;

import com.ccsw.tutorial_game.Author.model.AuthorDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


@FeignClient(value = "SPRING-CLOUD-EUREKA-CLIENT-AUTHOR", url = "${service.url}")
public interface AuthorClient {

    @GetMapping(value = "/author")
    List<AuthorDto> findAll();
}
