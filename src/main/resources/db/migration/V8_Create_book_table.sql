CREATE TABLE IF NOT EXISTS work_category (
    work_id INT NOT NULL,
    category_id INT NOT NULL,
    PRIMARY KEY (work_id, category_id),
    CONSTRAINT fk_work
        FOREIGN KEY (work_id)
        REFERENCES work(id),
    CONSTRAINT fk_category
        FOREIGN KEY (category_id)
        REFERENCES category(id)
);
