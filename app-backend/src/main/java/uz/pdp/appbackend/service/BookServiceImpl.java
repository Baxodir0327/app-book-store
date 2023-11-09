package uz.pdp.appbackend.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.pdp.appbackend.entity.Attachment;
import uz.pdp.appbackend.entity.Book;
import uz.pdp.appbackend.exceptions.ApiResult;
import uz.pdp.appbackend.exceptions.RestException;
import uz.pdp.appbackend.mapper.BookMapper;
import uz.pdp.appbackend.payload.*;
import uz.pdp.appbackend.repository.AttachmentRepository;
import uz.pdp.appbackend.repository.BookRepository;
import uz.pdp.appbackend.utils.MakeQuery;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public record BookServiceImpl(BookRepository bookRepository,
                              BookMapper bookMapper,
                              AttachmentRepository attachmentRepository) implements BookService {


    @Override
    public ApiResult<BookDTO> create(BookAddDTO bookAddDTO) {
//        UserPrincipal userPrincipal = CommonUtils.getCurrentUserIfNullThrow();
//        boolean b = userPrincipal
//                .getAuthorities().stream()
//                .anyMatch(grantedAuthority -> grantedAuthority
//                        .getAuthority().equals(RoleEnum.ROLE_ADMIN.name()));

        Book book = bookMapper.toBook(bookAddDTO);

        if (bookAddDTO.readSampleId() != null)
            book.setReadSample(attachmentRepository.findById(bookAddDTO.readSampleId()).orElseThrow(() -> RestException.restThrow("Attachment not found: " + bookAddDTO.readSampleId(), HttpStatus.NOT_FOUND)));

        if (bookAddDTO.photoIds() != null) {
            List<Attachment> attachments = attachmentRepository.findAllById(bookAddDTO.photoIds());
            if (attachments.size() != bookAddDTO.photoIds().size()) {
                String notIds = bookAddDTO.photoIds().stream()
                        .filter(uuid -> attachments.stream().noneMatch(attachment -> attachment.getId().equals(uuid)))
                        .map(UUID::toString)
                        .collect(Collectors.joining("/"));
                throw RestException.restThrow("Attachments not found by this ids: " + notIds, HttpStatus.NOT_FOUND);
            }
            book.setPhotos(attachments);
        }
        bookRepository.save(book);
        BookDTO bookDTO = bookMapper.toBookDTO(book);
        return ApiResult.successResponse(bookDTO);
    }

    @Override
    public ApiResult<BookDTO> update(UUID id, BookAddDTO bookAddDTO) {
        Book book = bookRepository.findById(id).orElseThrow(() -> RestException.restThrow("Book not found: " + id, HttpStatus.NOT_FOUND));
        bookMapper.update(bookAddDTO, book);

        if (bookAddDTO.readSampleId() != null)
            book.setReadSample(attachmentRepository.findById(bookAddDTO.readSampleId()).orElseThrow(() -> RestException.restThrow("Attachment not found: " + bookAddDTO.readSampleId(), HttpStatus.NOT_FOUND)));

        if (bookAddDTO.photoIds() != null) {
            List<Attachment> attachments = attachmentRepository.findAllById(bookAddDTO.photoIds());
            if (attachments.size() != bookAddDTO.photoIds().size()) {
                String notIds = bookAddDTO.photoIds().stream()
                        .filter(uuid -> attachments.stream().noneMatch(attachment -> attachment.getId().equals(uuid)))
                        .map(UUID::toString)
                        .collect(Collectors.joining("/"));
                throw RestException.restThrow("Attachments not found by this ids: " + notIds, HttpStatus.NOT_FOUND);
            }
            book.setPhotos(attachments);
        }
        bookRepository.save(book);
        BookDTO bookDTO = bookMapper.toBookDTO(book);
        return ApiResult.successResponse(bookDTO);
    }

    @Override
    public ApiResult<BookDTO> changeStatus(UUID id) {
        return null;
    }

    @Override
    public ApiResult<String> delete(UUID id) {
        //todo delete if not bind
        return null;
    }

    @Override
    public ApiResult<PaginationDTO<BookDTO>> listForAdmin(MainCriteriaDTO mainCriteriaDTO) {
        String query = MakeQuery.makeQuery(mainCriteriaDTO, "book");
        System.out.println(query);
        List<UUID> idList = bookRepository.findAllByMyQuery(query);

//        List<Book> books = bookRepository.findAllById(idList);
//        Map<UUID, Book> map = new HashMap<>();
//        books.forEach(book -> map.put(book.getId(), book));
//        Function<Book, Book> function = Function.identity();

        Map<UUID, Book> bookMap = bookRepository.findAllById(idList).stream()
                .collect(Collectors.toMap(Book::getId, Function.identity()));

        List<Book> bookList = idList.stream().map(bookMap::get).collect(Collectors.toList());

        List<BookDTO> bookDTOList = bookMapper.toBookDTOList(bookList);
        return ApiResult.successResponse(PaginationDTO.makePagination(bookDTOList, 0, 10));
    }

    @Override
    public ApiResult<PaginationDTO<BookDTO>> listForUser(MainCriteriaDTO mainCriteriaDTO) {
        return null;
    }
}
