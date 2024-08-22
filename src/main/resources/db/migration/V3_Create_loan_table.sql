CREATE TABLE IF NOT EXISTS loan (
    id INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    member_id INT NOT NULL,
    librarian_id INT NOT NULL,
    CONSTRAINT fk_member
        FOREIGN KEY (member_id)
            REFERENCES `user`(id),
    CONSTRAINT fk_librarian
        FOREIGN KEY (librarian_id)
            REFERENCES `user`(id),
    date_issued TIMESTAMP NOT NULL
);
