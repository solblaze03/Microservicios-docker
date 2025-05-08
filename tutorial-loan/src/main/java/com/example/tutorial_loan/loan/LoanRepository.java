package com.example.tutorial_loan.loan;


import com.example.tutorial_loan.loan.model.Loan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;


public interface LoanRepository extends CrudRepository<Loan, Long> , JpaSpecificationExecutor<Loan> {




    @EntityGraph(attributePaths = {"idGame", "idCustomer"})
    @Override
    Page<Loan> findAll(Specification<Loan> spec, Pageable pageable);


}
