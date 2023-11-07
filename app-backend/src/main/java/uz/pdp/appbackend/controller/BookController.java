package uz.pdp.appbackend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.appbackend.entity.Book;
import uz.pdp.appbackend.repository.BookRepository;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookRepository bookRepository;

    @PostMapping("/api/book")
    public Book add() {

        Book build = Book.builder()
                .price(1)
                .author("dd")
                .pageCount(25)
                .status(true)
                .title("Bla")
                .build();
        return bookRepository.save(build);
    }
}
