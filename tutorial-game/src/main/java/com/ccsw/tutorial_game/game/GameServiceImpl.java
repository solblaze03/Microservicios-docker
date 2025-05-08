package com.ccsw.tutorial_game.game;


import com.ccsw.tutorial_game.common.criteria.SearchCriteria;
import com.ccsw.tutorial_game.game.model.Game;
import com.ccsw.tutorial_game.game.model.GameDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class GameServiceImpl implements GameService{

    @Autowired
    GameRepository gameRepository;




    @Override
    public Game get(Long id) {
        return this.gameRepository.findById(id).orElse(null);
    }

    @Override
    public List<Game> find(String title, Long idCategory) {

        GameSpecification titleSpec = new GameSpecification(new SearchCriteria("title",":",title));
        GameSpecification categorySpec = new GameSpecification(new SearchCriteria("idCategory", ":",idCategory));
        Specification<Game> spec = Specification.where(titleSpec).and(categorySpec);

        return this.gameRepository.findAll(spec);
    }




    @Override
    public void save(Long id, GameDto dto) {
        Game game;

        if(id == null){
            game = new Game();
        }else{
            game = this.gameRepository.findById(id).orElse(null);
        }

        BeanUtils.copyProperties(dto, game, "id" , "author", "category");

       game.setIdAuthor(dto.getAuthor().getId());
       game.setIdCategory(dto.getCategory().getId());
        this.gameRepository.save(game);
    }




}
