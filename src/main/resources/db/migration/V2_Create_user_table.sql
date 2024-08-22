CREATE TABLE IF NOT EXISTS user (
    id INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    passwd TEXT NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    date_of_birth TIMESTAMP NOT NULL,
    user_role ENUM('member', 'librarian', 'admin') NOT NULL,
    contact_number VARCHAR(255) NOT NULL
);
