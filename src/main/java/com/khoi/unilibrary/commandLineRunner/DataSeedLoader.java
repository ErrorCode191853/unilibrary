package com.khoi.unilibrary.commandLineRunner;

import com.github.javafaker.Faker;
import com.khoi.unilibrary.model.*;
import com.khoi.unilibrary.model.enums.Status;
import com.khoi.unilibrary.repository.*;
import org.springframework.boot.CommandLineRunner;
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

    public DataSeedLoader(UserRepository userRepository,
                          RoleRepository roleRepository,
                          AuthorRepository authorRepository,
                          CategoryRepository categoryRepository,
                          WorkRepository workRepository,
                          BookRepository bookRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.authorRepository = authorRepository;
        this.categoryRepository = categoryRepository;
        this.workRepository = workRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadUserData();
        loadAuthorData();
        loadCategoryData();
        loadWorkData();
        loadBookData();
    }

    private void loadUserData() throws ParseException {
        var roleMember = roleRepository.findByName("MEMBER");
        Set<Role> roles = new HashSet<>();
        roles.add(roleMember);

        var user1 = new User("Huu Khoi", "Nguyen", "$2a$12$3tZr1OLzCU1mojVnwAtBlOOy9WkJNM2Yifbr3iAxVQBFXlqIAlw22", "khoinguyenhuu20@gmail.com", new Timestamp(new SimpleDateFormat("yyyy/MM/dd").parse("2005/07/23").getTime()), "+4444444444", true, roles);

        var users = new ArrayList<User>();
        users.add(user1);

        for (var user : users) {
            if (userRepository.findByEmail(user.getEmail()) == null) {
                System.out.println("Email " + user.getEmail() + " not found, creating user");
                userRepository.save(user);
            }
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