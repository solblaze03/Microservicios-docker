package com.ccsw.tutorial_game.game;



import com.ccsw.tutorial_game.Author.AuthorClient;
import com.ccsw.tutorial_game.Author.model.AuthorDto;
import com.ccsw.tutorial_game.Category.CategoryClient;
import com.ccsw.tutorial_game.Category.model.CategoryDto;
import com.ccsw.tutorial_game.game.model.Game;
import com.ccsw.tutorial_game.game.model.GameDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Game",description = "API of game")
@RequestMapping(value = "/game")
@RestController
@CrossOrigin(origins = "*")
public class GameController {

    @Autowired
    GameService gameService;

    @Autowired
    ModelMapper mapper;

    @Autowired
    CategoryClient categoryClient;

    @Autowired
    AuthorClient authorClient;

    @Operation(summary = "Find", description = "Method that return a filtered list of games")
    @GetMapping(path = "")
    public List<GameDto> find(@RequestParam(value = "title", required = false) String title,
                              @RequestParam(value = "idCategory" , required = false) Long idCategory){

        List<CategoryDto> categories = categoryClient.findAll();
        List<AuthorDto> authors = authorClient.findAll();


        return gameService.find(title, idCategory).stream().map(game -> {
           GameDto gameDto = new GameDto();

           gameDto.setId(game.getId());
            gameDto.setTitle(game.getTitle());
            gameDto.setAge(game.getAge());
            gameDto.setCategory(categories.stream().filter(category -> category.getId().equals(game.getIdCategory())).findFirst().orElse(null));
            gameDto.setAuthor(authors.stream().filter(author -> author.getId().equals(game.getIdAuthor())).findFirst().orElse(null));
            return gameDto;
        }).collect(Collectors.toList());
    }

    @Operation(summary = "Save or Update", description = "Method that saves or updates a Game")
    @RequestMapping(path = {"", "/{id}"}, method = RequestMethod.PUT)
    public void save(@PathVariable(name = "id",required = false) Long id, @RequestBody GameDto dto){

        gameService.save(id, dto);
    }








}
