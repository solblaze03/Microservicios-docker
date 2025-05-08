package com.example.tutorial_loan.loan;


import com.example.tutorial_loan.customer.CustomerClient;
import com.example.tutorial_loan.customer.model.CustomerDto;
import com.example.tutorial_loan.game.GameClient;
import com.example.tutorial_loan.game.model.GameDto;
import com.example.tutorial_loan.loan.model.Loan;
import com.example.tutorial_loan.loan.model.LoanDto;
import com.example.tutorial_loan.loan.model.LoanSearchDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "", description = "Api of Loan")
@RequestMapping(value = "/loan")
@RestController
@CrossOrigin("*")
public class LoanController {

    @Autowired
    LoanService loanService;

    @Autowired
    GameClient gameClient;

    @Autowired
    CustomerClient customerClient;

    @Autowired
    ModelMapper modelMapper;

    @PostMapping(path = "")
    public Page<LoanDto> findPage(@RequestBody LoanSearchDto dto,
                                  @RequestParam(name = "title", required = false) Long idTitle,
                                  @RequestParam(name = "customer", required = false) Long idCustomer,
                                  @RequestParam(name = "date", required = false) String date
    ) {

        List<CustomerDto> allCustomer = customerClient.findAll();
        List<GameDto> allGames = gameClient.find();

        Page<Loan> page = this.loanService.findPage(dto, idTitle, idCustomer, date);


        return new PageImpl<>(page.getContent().stream().map(e -> {
            LoanDto loanDto = new LoanDto();
            loanDto.setId(e.getId());
            loanDto.setCustomer(allCustomer.stream().filter(category -> category.getId().equals(e.getIdCustomer())).findFirst().orElse(null));
            loanDto.setGame(allGames.stream().filter(game -> game.getId().equals(e.getIdGame())).findFirst().orElse(null));
            loanDto.setFechaInicio(e.getFechaInicio());
            loanDto.setFechaDevolucion(e.getFechaDevolucion());
            return loanDto;

        }).map(e -> modelMapper.map(e, LoanDto.class)).collect(Collectors.toList()), page.getPageable(), page.getTotalElements());
    }


    @Operation(summary = "Save", description = "Method that saves or updates a Loan")
    @RequestMapping(path = "", method = RequestMethod.PUT)
    public ResponseEntity save(@RequestBody LoanDto dto) {

        return this.loanService.save(dto);
    }

    @Operation(summary = "Delete", description = "Method than delete a Loan")
    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable(name = "id") Long id) {
        this.loanService.delete(id);
    }


}
