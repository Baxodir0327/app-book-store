package uz.pdp.appbackend.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.appbackend.exceptions.ApiResult;
import uz.pdp.appbackend.payload.BookAddDTO;
import uz.pdp.appbackend.payload.BookDTO;
import uz.pdp.appbackend.payload.MainCriteriaDTO;
import uz.pdp.appbackend.payload.PaginationDTO;
import uz.pdp.appbackend.service.BookService;

import java.util.UUID;

@RestController
@Slf4j
public record BookControllerImpl(BookService bookService) implements BookController {


    @Override
    public HttpEntity<ApiResult<BookDTO>> add(BookAddDTO bookAddDTO) {
        ApiResult<BookDTO> result = bookService.create(bookAddDTO);
        return ResponseEntity.status(201).body(result);
    }

    @Override
    public HttpEntity<ApiResult<BookDTO>> edit(UUID id, BookAddDTO bookAddDTO) {
        ApiResult<BookDTO> result = bookService.update(id, bookAddDTO);
        return ResponseEntity.status(202).body(result);
    }

    @Override
    public HttpEntity<ApiResult<BookDTO>> changeStatus(UUID id) {
        ApiResult<BookDTO> result = bookService.changeStatus(id);
        return ResponseEntity.status(202).body(result);
    }

    @Override
    public HttpEntity<ApiResult<String>> remove(UUID id) {
        ApiResult<String> result = bookService.delete(id);
        return ResponseEntity.status(202).body(result);
    }

    @Override
    public HttpEntity<ApiResult<PaginationDTO<BookDTO>>> forAdmin(MainCriteriaDTO mainCriteriaDTO) {
        ApiResult<PaginationDTO<BookDTO>> result = bookService.listForAdmin(mainCriteriaDTO);
        return ResponseEntity.status(200).body(result);
    }

    @Override
    public HttpEntity<ApiResult<PaginationDTO<BookDTO>>> forUser(MainCriteriaDTO mainCriteriaDTO) {
        ApiResult<PaginationDTO<BookDTO>> result = bookService.listForUser(mainCriteriaDTO);
        return ResponseEntity.status(200).body(result);
    }
}
