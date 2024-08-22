CREATE TABLE IF NOT EXISTS loan_details (
    id INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    loan_id INT NOT NULL,
    book_id INT NOT NULL,
    CONSTRAINT fk_loan
        FOREIGN KEY (loan_id)
        REFERENCES loan(id),
    CONSTRAINT fk_book
        FOREIGN KEY (book_id)
        REFERENCES book(id),
    date_returned TIMESTAMP NOT NULL
);
