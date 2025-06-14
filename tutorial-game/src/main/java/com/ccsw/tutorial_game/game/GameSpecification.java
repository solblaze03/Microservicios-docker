
package com.ccsw.tutorial_game.game;


import com.ccsw.tutorial_game.common.criteria.SearchCriteria;
import com.ccsw.tutorial_game.game.model.Game;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

public class GameSpecification implements Specification<Game> {

    private static final long serialVersionUID = 1L;

    private final SearchCriteria criteria;

    public GameSpecification(SearchCriteria searchCriteria){
        this.criteria = searchCriteria;
    }


    @Override
    public Predicate toPredicate(Root<Game> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        if(criteria.getOperation().equalsIgnoreCase(":") && criteria.getValue() != null) {

            Path<String> path = getPath(root);
            if(path.getJavaType() == String.class){
                return criteriaBuilder.like(path, "%" + criteria.getValue()+ "%");
            }else{
                return criteriaBuilder.equal(path, criteria.getValue());
            }
        }
        return null;
    }

    private Path<String> getPath(Root<Game> root){
        String key = criteria.getKey();
        String[] split = key.split("[.]", 0);

        Path<String> expression = root.get(split[0]);
        for (int i = 1; i < split.length; i++) {
            expression = expression.get(split[i]);
        }
        return expression;
    }

}
