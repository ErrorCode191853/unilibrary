CREATE TABLE IF NOT EXISTS work_author (
    work_id INT NOT NULL,
    author_id INT NOT NULL,
    PRIMARY KEY (work_id, author_id),
    CONSTRAINT fk_work
        FOREIGN KEY (work_id)
        REFERENCES work(id),
    CONSTRAINT fk_author
        FOREIGN KEY (author_id)
        REFERENCES author(id)
);
