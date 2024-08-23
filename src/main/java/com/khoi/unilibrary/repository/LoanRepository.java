package com.khoi.unilibrary.repository;

import com.khoi.unilibrary.model.Book;
import com.khoi.unilibrary.model.Loan;
import com.khoi.unilibrary.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Integer> {

    Page<Loan> findAll(Pageable pageable);

    Page<Loan> findByBookId(Integer bookId, Pageable pageable);

    Page<Loan> findByMemberIdAndDateReturned(Integer memberId, Timestamp dateReturned, Pageable pageable);

    Page<Loan> findByMemberIdAndDateReturnedBefore(Integer memberId, Timestamp dateReturned, Pageable pageable);

    Loan findTopByBookOrderByDateIssuedDesc(Book book);

    long countByMemberAndDateReturned(User member, Timestamp dateReturned);
}
