package com.khoi.unilibrary.commandLineRunner;

import com.github.javafaker.Faker;
import com.khoi.unilibrary.model.*;
import com.khoi.unilibrary.model.enums.Status;
import com.khoi.unilibrary.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Component
public class DataSeedLoader implements CommandLineRunner {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final AuthorRepository authorRepository;

    private final CategoryRepository categoryRepository;

    private final WorkRepository workRepository;

    private final BookRepository bookRepository;

    private final PasswordEncoder passwordEncoder;

    public DataSeedLoader(UserRepository userRepository,
                          RoleRepository roleRepository,
                          AuthorRepository authorRepository,
                          CategoryRepository categoryRepository,
                          WorkRepository workRepository,
                          BookRepository bookRepository,
                          PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.authorRepository = authorRepository;
        this.categoryRepository = categoryRepository;
        this.workRepository = workRepository;
        this.bookRepository = bookRepository;
        this.passwordEncoder = passwordEncoder;

    }

    @Override
    public void run(String... args) throws Exception {
        loadUserData();
        loadAuthorData();
        loadCategoryData();
        loadWorkData();
        loadBookData();
        createAdminUser();
    }

    private void loadUserData() throws ParseException {
        var roleMember = roleRepository.findByName("MEMBER");
        Set<Role> roles = new HashSet<>();
        roles.add(roleMember);

        var user1 = new User("First Name",
                "Last Name",
                "First Name Last Name",
                "1234567890",
                "huukhoi@gmail.com",
                new Timestamp(new SimpleDateFormat("yyyy/MM/dd")
                        .parse("2000/01/01")
                        .getTime()),
                "+868686868686",
                true,
                roles);

        var users = new ArrayList<User>();
        users.add(user1);

        for (var user : users) {
            if (userRepository.findByEmail(user.getEmail()) == null) {
                System.out.println("Email " + user.getEmail() + " not found, creating user");
                userRepository.save(user);
            }
        }
    }

    private void createAdminUser() throws ParseException{
        if (userRepository.findByEmail("admin@example.com") == null) {
            Role adminRole = roleRepository.findByName("ADMIN");

            User adminUser = new User();
            adminUser.setFirstName("Admin");
            adminUser.setLastName("User");
            adminUser.setUserName("admin");
            adminUser.setContactNumber("0963538743");
            adminUser.setDateOfBirth(new Timestamp(new SimpleDateFormat("yyyy/MM/dd").parse("2005/07/23").getTime()));
            adminUser.setPassword(passwordEncoder.encode("adminpassword"));
            adminUser.setEmail("admin@example.com");
            adminUser.setEnabled(true);

            Set<Role> roles = new HashSet<>();
            roles.add(adminRole);
            adminUser.setRoles(roles);

            userRepository.save(adminUser);
        }
    }

    private void loadAuthorData() {

        if (authorRepository.count() == 0) {
            for (int i = 0; i < 10; i++) {
                var faker = new Faker();

                var firstName = faker.name().firstName();
                var lastName = faker.name().lastName();

                var author = new Author(firstName, lastName);
                authorRepository.save(author);
            }
            System.out.println("Seeded author table successfully");
        }
    }

    private void loadCategoryData() {

        if (categoryRepository.count() == 0) {
            for (int i = 0; i < 10; i++) {
                var faker = new Faker();

                var name = faker.book().genre();

                var category = new Category(name);
                categoryRepository.save(category);
            }
            System.out.println("Seeded category table successfully");
        }
    }

    private void loadWorkData() {

        if (workRepository.count() <= 5) {
            for (int i = 0; i < 20; i++) {
                var faker = new Faker();

                var title = faker.book().title();
                var description = faker.lorem().sentence();

                var author = authorRepository.getOneRandom();
                Set<Author> authors = new HashSet<>();
                authors.add(author);

                var category = categoryRepository.getOneRandom();
                Set<Category> categories = new HashSet<>();
                categories.add(category);

                var work = new Work(title, description, authors, categories);
                workRepository.save(work);
            }
            System.out.println("Seeded work table successfully");
        }
    }

    private void loadBookData() {

        if (bookRepository.count() <= 5) {
            for (int i = 0; i < 30; i++) {
                var faker = new Faker();

                var work = workRepository.getOneRandom();
                var publisherName = faker.book().publisher();
                var yearOfPublishing = new Timestamp(System.currentTimeMillis());
                var isbn = faker.code().isbn13(true);
                var bookStatus = Status.OK;
                var available = true;

                var book = new Book(work, publisherName, yearOfPublishing, isbn, bookStatus, available);
                bookRepository.save(book);
            }
            System.out.println("Seeded book table successfully");
        }
    }

}