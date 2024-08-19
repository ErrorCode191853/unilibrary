package com.khoi.unilibrary.service;

import com.khoi.unilibrary.dto.LoanPayload;
import com.khoi.unilibrary.model.Loan;
import org.springframework.data.domain.Page;

public interface LoanService {
    Loan createLoan(Integer bookId, LoanPayload loanPayload);

    Loan endLoan(Integer bookId);

    Page<Loan> getAllLoans(int page, int size, String[] sort);

    Page<Loan> getCurrentLoans(Integer memberId, int page, int size, String[] sort);

    Page<Loan> getPreviousLoans(Integer memberId, int page, int size, String[] sort);

    Page<Loan> getLoansByBookId(Integer bookId, int page, int size, String[] sort);
}
