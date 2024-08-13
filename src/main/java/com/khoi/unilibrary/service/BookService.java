package com.khoi.unilibrary.service;

import com.khoi.unilibrary.entity.Book;
import com.khoi.unilibrary.entity.Borrow;
import com.khoi.unilibrary.entity.User;
import com.khoi.unilibrary.repository.BookRepository;
import com.khoi.unilibrary.repository.BookRepositoryImpl;
import com.khoi.unilibrary.repository.BorrowRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BorrowRepository borrowRepository;
    private final BookRepository bookRepository;
    private final CustomUserDetailsService customUserDetailsService;
    private final BookRepositoryImpl bookRepositoryImpl;

    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    public Optional<Book> findBookByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn);
    }

    public Book saveBook(Book book) {
        bookRepository.save(book);
        return book;
    }

    public void saveBooks(List<Book> books) {
        bookRepository.saveAll(books);
    }

    public Book saveBookByID;

    public Book findBookById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }


    public void importBooksFromCsv(String filePath) throws IOException {
        try (Reader reader = new FileReader(filePath);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withHeader())) {

            List<Book> books = new ArrayList<>();

            for (CSVRecord csvRecord : csvParser) {
                Book book = new Book();
                book.setTitle(csvRecord.get("Title"));
                book.setAuthor(csvRecord.get("Author"));
                book.setCategory(csvRecord.get("Category"));
                book.setIsbn(csvRecord.get("ISBN"));

                // Set additional fields as needed

                books.add(book);
            }

            saveBooks(books);
        }
    }

    public Book borrowBook(Long bookId, Long userId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));

        UserDetails userDetails = customUserDetailsService.loadUserById(userId);
        User user = User.builder()
                .username(userDetails.getUsername())
                .userId(userId)
                .build();
        // Check if there are available copies
        if (book.getAvailableCopies() > 0) {
            // Reduce the number of available copies
            book.setAvailableCopies(book.getAvailableCopies() - 1);
            bookRepository.save(book);

            // Create a new Borrow record
            Borrow borrow = new Borrow();
            borrow.setBook(book);
            borrow.setUser(user);
            borrow.setBorrowDate(LocalDateTime.now());
            borrow.setDueDate(LocalDateTime.now().plusWeeks(2)); // Set due date to 2 weeks from now
            borrowRepository.save(borrow);
        } else {
            throw new RuntimeException("No available copies of the book");
        }
        return book;
    }

    public long countBooks() {
        return bookRepository.count();
    }

    public List<Book> findBorrowedBooksByUser(Long userId) {
        return bookRepositoryImpl.findBorrowedBooksByUserId(userId);
    }

    public Book returnBook(Book book) {
        return book;
    }
}
