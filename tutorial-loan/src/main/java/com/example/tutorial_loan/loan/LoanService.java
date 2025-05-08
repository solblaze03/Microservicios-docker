package com.example.tutorial_loan.loan;


import com.example.tutorial_loan.loan.model.Loan;
import com.example.tutorial_loan.loan.model.LoanDto;
import com.example.tutorial_loan.loan.model.LoanSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

public interface LoanService {
    Loan get(Long id);

    Page<Loan> findPage(LoanSearchDto dto, Long title, Long customer, String date);

    ResponseEntity save(LoanDto dto);

    void delete(Long id);
}
