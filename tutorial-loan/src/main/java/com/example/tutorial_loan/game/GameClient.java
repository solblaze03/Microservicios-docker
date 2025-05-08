package com.example.tutorial_loan.game;


import com.example.tutorial_loan.game.model.GameDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(value = "SPRING-CLOUD-EUREKA-CLIENT-GAME", url = "${service.url}")
public interface GameClient {

    @GetMapping(value = "/game")
    List<GameDto> find();
}
