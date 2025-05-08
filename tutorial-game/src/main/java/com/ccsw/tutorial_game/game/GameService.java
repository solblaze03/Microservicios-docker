package com.ccsw.tutorial_game.game;



import com.ccsw.tutorial_game.game.model.Game;
import com.ccsw.tutorial_game.game.model.GameDto;

import java.util.List;

public interface GameService {

    Game get(Long id);

    List<Game> find(String title, Long idCategory);


    void save(Long id, GameDto dto);


}
