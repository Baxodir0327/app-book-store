package uz.pdp.appbackend.service;

import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.pdp.appbackend.entity.Attachment;
import uz.pdp.appbackend.entity.Book;
import uz.pdp.appbackend.entity.template.AbsUUIDEntity;
import uz.pdp.appbackend.enums.RoleEnum;
import uz.pdp.appbackend.exceptions.ApiResult;
import uz.pdp.appbackend.exceptions.RestException;
import uz.pdp.appbackend.mapper.BookMapper;
import uz.pdp.appbackend.payload.*;
import uz.pdp.appbackend.repository.AttachmentRepository;
import uz.pdp.appbackend.repository.BookRepository;
import uz.pdp.appbackend.security.UserPrincipal;
import uz.pdp.appbackend.utils.CommonUtils;

import java.util.HashMap;
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
        String query = makeQuery(mainCriteriaDTO);
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

    @Override
    public String makeQuery(MainCriteriaDTO mainCriteriaDTO) {
        StringBuilder sb = new StringBuilder(250);
        sb.append("SELECT id FROM book ");

        FilterDTO filter = mainCriteriaDTO.getFilter();
        boolean isSearching = filter.getSearch() != null && !filter.getSearch().isEmpty();
        boolean isFiltering = filter.getFilterFields() != null;
        if (isSearching || isFiltering)
            sb.append("WHERE ");
        addSearchQuery(sb, filter, isSearching);
        addFilterQuery(sb, filter, isFiltering);
        addOrderByPart(mainCriteriaDTO, sb);
        addOffsetPart(mainCriteriaDTO, sb);
        return sb.toString();
    }

    private void addFilterQuery(StringBuilder sb, FilterDTO filter, boolean isFiltering) {
        if (isFiltering) {
            sb.append("AND(");

            List<FilterField> fields = filter.getFilterFields();
            for (int i = 0; i < fields.size(); i++) {
                if (i != 0)
                    sb.append(filter.getFilterOperator());
                sb.append("(");


                FilterField filterField = fields.get(i);
                ComparatorTypeEnum comparatorType = filterField.getComparatorType();
                FilterFieldValue fieldValue = filterField.getValue();
                switch (filterField.getColumnType()) {
                    case SHORT_TEXT -> {
                        switch (comparatorType) {
                            case EQ, NOT -> {
                                sb.append(filterField.getColumn());
                                if (comparatorType.equals(ComparatorTypeEnum.NOT))
                                    sb.append("!");
                                sb.append("~*'")
                                        .append(fieldValue.getSearchingValue())
                                        .append("'");
                            }
                            case IS_SET, IS_NOT_SET -> {
                                sb.append(filterField.getColumn());
                                sb.append(" IS ");
                                if (comparatorType.equals(ComparatorTypeEnum.IS_SET))
                                    sb.append("NOT ");
                                sb.append("NULL ");
                            }
                        }
                    }
                    case MONEY, NUMBER -> {
                        switch (comparatorType) {
                            case EQ -> {
                            }
                            case NOT -> {
                            }
                            case LT -> {
                            }
                            case LTE -> {
                            }
                            case GT -> {
                            }
                            case GTE -> {
                            }
                            case RA -> {
                            }
                            case IS_SET -> {
                            }
                            case IS_NOT_SET -> {
                            }
                        }
                    }
                    case CHECKBOX -> {
                        switch (comparatorType) {
                            case IS_SET -> {
                            }
                            case IS_NOT_SET -> {
                            }
                        }
                    }
                    case FILE -> {
                        switch (comparatorType) {
                            case IS_SET -> {
                            }
                            case IS_NOT_SET -> {
                            }
                        }
                    }
                    case DATE -> {
                        switch (comparatorType) {
                            case EQ -> {
                            }
                            case NOT -> {
                            }
                            case LT -> {
                            }
                            case LTE -> {
                            }
                            case GT -> {
                            }
                            case GTE -> {
                            }
                            case RA -> {
                            }
                            case IS_SET -> {
                            }
                            case IS_NOT_SET -> {
                            }
                        }
                    }
                }


                sb.append(")");
            }

            sb.append(") ");
        }
    }

    private void addSearchQuery(StringBuilder sb, FilterDTO filter, boolean isSearching) {
        String search = filter.getSearch();
        if (isSearching) {
            sb.append("(");
            List<String> searchingColumns = filter.getSearchingColumns();
            for (int i = 0; i < searchingColumns.size(); i++) {
                if (i != 0)
                    sb.append(" OR ");

                String column = searchingColumns.get(i);
                sb.append(column)
                        .append("~*'")
                        .append(search)
                        .append("'");
            }
            sb.append(") ");
        }
    }

    private void addOffsetPart(MainCriteriaDTO mainCriteriaDTO, StringBuilder sb) {
        sb.append("LIMIT ")
                .append(mainCriteriaDTO.getSize())
                .append(" OFFSET ")
                .append(mainCriteriaDTO.getPage() * mainCriteriaDTO.getSize());
    }

    private void addOrderByPart(MainCriteriaDTO mainCriteriaDTO, StringBuilder sb) {
        List<SortDTO> sorts = mainCriteriaDTO.getSorts();
        if (sorts != null && !sorts.isEmpty()) {
            sb.append("ORDER BY ");
            for (SortDTO sort : sorts)
                sb.append(sort.getColumn())
                        .append(" ")
                        .append(sort.getDirection())
                        .append(",");
            sb.replace(sb.length() - 1, sb.length(), " ");
        }
    }
}
