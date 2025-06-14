package com.example.tutorial_loan.loan;


import com.example.tutorial_loan.common.criteria.SearchCriteria;
import com.example.tutorial_loan.customer.CustomerClient;
import com.example.tutorial_loan.game.GameClient;
import com.example.tutorial_loan.loan.model.Loan;
import com.example.tutorial_loan.loan.model.LoanDto;
import com.example.tutorial_loan.loan.model.LoanSearchDto;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.UnexpectedTypeException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class LoanServiceImpl implements LoanService {

    @Autowired
    LoanRepository repository;



    @Override
    public Loan get(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<Loan> get(){
        return (List<Loan>) repository.findAll();
    }

    @Override
    public Page<Loan> findPage(LoanSearchDto dto, Long idGame, Long idCustomer, String date) {



        LocalDate lDate = null;
        if(date != null) lDate = LocalDate.parse(date);

        LoanSpecification titleSpec = new LoanSpecification(new SearchCriteria("idGame", ":", idGame));
        LoanSpecification customerSpec = new LoanSpecification(new SearchCriteria("idCustomer",":",idCustomer));
        LoanSpecification loanDate = new LoanSpecification(new SearchCriteria("fechaInicio", "<=",lDate));
        LoanSpecification returnDate = new LoanSpecification(new SearchCriteria("fechaDevolucion",">=",lDate));
        Specification<Loan> spec = Specification.where(titleSpec).and(customerSpec).and(loanDate).and(returnDate);
        return this.repository.findAll(spec,dto.getPageable().getPageable());

    }

    @Override
    public ResponseEntity save(LoanDto dto) {
        Loan loan = new Loan();
        BeanUtils.copyProperties(dto, loan, "id", "game", "customer");
        try {
            if (dto.getCustomer() != null && dto.getGame() != null) {


                boolean vDate = validateDate(dto.getFechaInicio(), dto.getFechaDevolucion());
                boolean vdBetween  = validateDaysBetween(dto.getFechaInicio(), dto.getFechaDevolucion());
                boolean vgAvailable = validateGameAvailable(dto);
                boolean vngBorrowed = validateNumberGamesBorrowed(dto);

                if(vDate && vdBetween && vgAvailable && vngBorrowed){
                    loan.setIdCustomer(dto.getCustomer().getId());
                    loan.setIdGame(dto.getGame().getId());
                    this.repository.save(loan);
                    return ResponseEntity.ok().build();
                }else{
                    return ResponseEntity.status(HttpStatus.CONFLICT).build();
                }
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error en el formulario");
            }

        }catch(UnexpectedTypeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error en el formulario");
        }catch(ConstraintViolationException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error en el formulario");
        }
    }

    @Override
    public void delete(Long id) {
        this.repository.deleteById(id);
    }

    private boolean validateDate(LocalDate loanDate, LocalDate returnDate){
        return loanDate.isBefore(returnDate);

    }

    private boolean validateDaysBetween(LocalDate loanDate, LocalDate returnDate){
        long days = ChronoUnit.DAYS.between(loanDate, returnDate);
        return (days <= 14) ? true : false;
    }

    private boolean validateGameAvailable(LoanDto dto){

        LoanSpecification idSpec = new LoanSpecification(new SearchCriteria("game.id",":",dto.getGame().getId()));
        LoanSpecification startDate = new LoanSpecification(new SearchCriteria("fechaInicio","<=",dto.getFechaDevolucion()));
        LoanSpecification returnDate = new LoanSpecification(new SearchCriteria("fechaDevolucion",">=",dto.getFechaInicio()));
        Specification<Loan> spec = Specification.where(idSpec).and(startDate).and(returnDate);
        Long count  = this.repository.count(spec);

        return !(count >= 1) ? true :  false;
    }

    private boolean validateNumberGamesBorrowed(LoanDto dto){

        LoanSpecification idCustomer = new LoanSpecification(new SearchCriteria("customer.id",":",dto.getCustomer().getId()));
        LoanSpecification startDate = new LoanSpecification(new SearchCriteria("fechaInicio","<=",dto.getFechaDevolucion()));
        LoanSpecification returnDate = new LoanSpecification(new SearchCriteria("fechaDevolucion",">=",dto.getFechaInicio()));
        Specification<Loan> spec = Specification.where(idCustomer).and(startDate).and(returnDate);
        Long count = this.repository.count(spec);

        return (count <= 1) ? true : false;
    }




}
