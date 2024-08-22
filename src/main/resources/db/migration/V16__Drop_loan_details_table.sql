-- Drop tables if they exist
DROP TABLE IF EXISTS `book`;
DROP TABLE IF EXISTS `work`;
DROP TABLE IF EXISTS `work_category`;
DROP TABLE IF EXISTS `category`;
DROP TABLE IF EXISTS `work_author`;
DROP TABLE IF EXISTS `author`;
DROP TABLE IF EXISTS `loan_details`;
DROP TABLE IF EXISTS `loan`;
DROP TABLE IF EXISTS `role`;
DROP TABLE IF EXISTS `user_role`;
DROP TABLE IF EXISTS `user`;

-- Create the `user` table
CREATE TABLE IF NOT EXISTS `user` (
    id INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    `password` VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    date_of_birth TIMESTAMP NOT NULL,
    contact_number VARCHAR(255) NOT NULL,
    `enabled` BOOLEAN NULL
);

-- Create the `role` table
CREATE TABLE IF NOT EXISTS `role` (
    id INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    `name` VARCHAR(255) NOT NULL
);

-- Create the `user_role` table
CREATE TABLE IF NOT EXISTS `user_role` (
    user_id INT NOT NULL,
    role_id INT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES `user`(id),
    FOREIGN KEY (role_id) REFERENCES `role`(id)
);

-- Create indexes for `user_role`
CREATE INDEX fk_user_idx ON `user_role` (user_id);
CREATE INDEX fk_role_idx ON `user_role` (role_id);

-- Insert roles into the `role` table
INSERT INTO `role` (`name`) VALUES ('ADMIN');
INSERT INTO `role` (`name`) VALUES ('LIBRARIAN');
INSERT INTO `role` (`name`) VALUES ('MEMBER');

-- Insert users into the `user` table
INSERT INTO `user` (
    first_name,
    last_name,
    `password`,
    email,
    date_of_birth,
    contact_number,
    `enabled`
) VALUES (
    'admin',
    'admin',
    '$2a$12$3tZr1OLzCU1mojVnwAtBlOOy9WkJNM2Yifbr3iAxVQBFXlqIAlw22',
    'admin@admin.oss',
    '1980-01-10 00:00:00',
    '+1111111111',
    TRUE
);

INSERT INTO `user` (
    first_name,
    last_name,
    `password`,
    email,
    date_of_birth,
    contact_number,
    `enabled`
) VALUES (
    'librarian',
    'librarian',
    '$2a$12$3tZr1OLzCU1mojVnwAtBlOOy9WkJNM2Yifbr3iAxVQBFXlqIAlw22',
    'librarian@librarian.oss',
    '1990-02-20 00:00:00',
    '+2222222222',
    TRUE
);

INSERT INTO `user` (
    first_name,
    last_name,
    `password`,
    email,
    date_of_birth,
    contact_number,
    `enabled`
) VALUES (
    'member',
    'member',
    '$2a$12$3tZr1OLzCU1mojVnwAtBlOOy9WkJNM2Yifbr3iAxVQBFXlqIAlw22',
    'member@member.oss',
    '2000-03-30 00:00:00',
    '+3333333333',
    TRUE
);

-- Assign roles to users
INSERT INTO `user_role` (user_id, role_id) VALUES (1, 1); -- user admin has role ADMIN
INSERT INTO `user_role` (user_id, role_id) VALUES (2, 2); -- user librarian has role LIBRARIAN
INSERT INTO `user_role` (user_id, role_id) VALUES (3, 3); -- user member has role MEMBER

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
    `name` VARCHAR(255) NOT NULL
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

-- Create the `loan` table
CREATE TABLE IF NOT EXISTS `loan` (
    id INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    member_id INT NOT NULL,
    librarian_id INT NOT NULL,
    book_id INT NOT NULL,
    date_issued TIMESTAMP NOT NULL,
    date_returned TIMESTAMP,
    FOREIGN KEY (member_id) REFERENCES `user`(id),
    FOREIGN KEY (librarian_id) REFERENCES `user`(id),
    FOREIGN KEY (book_id) REFERENCES `book`(id)
);
