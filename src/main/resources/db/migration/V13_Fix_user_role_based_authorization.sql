-- Drop tables if they exist
DROP TABLE IF EXISTS `user` CASCADE;
DROP TABLE IF EXISTS `user_role` CASCADE;
DROP TABLE IF EXISTS `role` CASCADE;

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
    CONSTRAINT fk_user
       FOREIGN KEY(user_id)
           REFERENCES `user`(id),
    CONSTRAINT fk_role
       FOREIGN KEY(role_id)
           REFERENCES `role`(id)
);

-- Create indexes for `user_role`
CREATE INDEX fk_user_idx ON `user_role` (user_id);
CREATE INDEX fk_role_idx ON `user_role` (role_id);

-- Insert roles into `role` table
INSERT INTO `role` (`name`) VALUES ('MEMBER');
INSERT INTO `role` (`name`) VALUES ('LIBRARIAN');
INSERT INTO `role` (`name`) VALUES ('ADMIN');

-- Insert users into `user` table
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
INSERT INTO `user_role` (user_id, role_id) VALUES (1, 3); -- user admin has role ADMIN
INSERT INTO `user_role` (user_id, role_id) VALUES (2, 2); -- user librarian has role LIBRARIAN
INSERT INTO `user_role` (user_id, role_id) VALUES (3, 1); -- user member has role MEMBER
