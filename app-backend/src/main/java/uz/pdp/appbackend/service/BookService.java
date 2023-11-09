package uz.pdp.appbackend.service;

import uz.pdp.appbackend.exceptions.ApiResult;
import uz.pdp.appbackend.payload.BookAddDTO;
import uz.pdp.appbackend.payload.BookDTO;
import uz.pdp.appbackend.payload.MainCriteriaDTO;
import uz.pdp.appbackend.payload.PaginationDTO;

import java.util.UUID;

public interface BookService {

    ApiResult<BookDTO> create(BookAddDTO bookAddDTO);

    ApiResult<BookDTO> update(UUID id, BookAddDTO bookAddDTO);

    ApiResult<BookDTO> changeStatus(UUID id);

    ApiResult<String> delete(UUID id);

    ApiResult<PaginationDTO<BookDTO>> listForAdmin(MainCriteriaDTO mainCriteriaDTO);

    ApiResult<PaginationDTO<BookDTO>> listForUser(MainCriteriaDTO mainCriteriaDTO);
}
