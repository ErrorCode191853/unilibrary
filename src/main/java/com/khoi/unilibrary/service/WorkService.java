package com.khoi.unilibrary.service;

import com.khoi.unilibrary.dto.WorkPayload;
import com.khoi.unilibrary.model.Book;
import com.khoi.unilibrary.model.Work;
import org.springframework.data.domain.Page;

public interface WorkService {
    Page<Work> getAllWorks(String keyword, String categoryName, int page, int size, String[] sort);

    void deleteWorkById(Integer id);

    Work createWork(WorkPayload workPayload);

    Work editWork(Integer id, WorkPayload workPayload);

    Page<Book> getBooksByWorkId(Integer workId, String keyword, String statusName, int page, int size);
}