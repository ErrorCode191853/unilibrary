-- Drop tables if they exist
DROP TABLE IF EXISTS `book`;
DROP TABLE IF EXISTS `work`;
DROP TABLE IF EXISTS `work_category`;
DROP TABLE IF EXISTS `category`;
DROP TABLE IF EXISTS `work_author`;
DROP TABLE IF EXISTS `author`;

-- Drop the ENUM type if it exists (MySQL does not support DROP TYPE)
-- Instead, you should just recreate the ENUM in the new table

-- Create the `author` table
CREATE TABLE IF NOT EXISTS `author` (
    id INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL
);

-- Create the `work` table
CREATE TABLE IF NOT EXISTS `work` (
    id INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT NOT NULL
);

-- Create the `work_author` table
CREATE TABLE IF NOT EXISTS `work_author` (
    work_id INT NOT NULL,
    author_id INT NOT NULL,
    PRIMARY KEY (work_id, author_id),
    FOREIGN KEY (work_id) REFERENCES `work`(id),
    FOREIGN KEY (author_id) REFERENCES `author`(id)
);

-- Create the `category` table
CREATE TABLE IF NOT EXISTS `category` (
    id INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

-- Create the `work_category` table
CREATE TABLE IF NOT EXISTS `work_category` (
    work_id INT NOT NULL,
    category_id INT NOT NULL,
    PRIMARY KEY (work_id, category_id),
    FOREIGN KEY (work_id) REFERENCES `work`(id),
    FOREIGN KEY (category_id) REFERENCES `category`(id)
);

-- Create the `book` table with ENUM type directly in MySQL
CREATE TABLE IF NOT EXISTS `book` (
    id INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    work_id INT NOT NULL,
    publisher_name VARCHAR(255) NOT NULL,
    year_of_publishing TIMESTAMP NOT NULL,
    ISBN VARCHAR(13) NOT NULL,
    book_status ENUM('OK', 'DAMAGED', 'LOST') NOT NULL,
    FOREIGN KEY (work_id) REFERENCES `work`(id)
);
