package uz.pdp.appbackend.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import uz.pdp.appbackend.entity.Book;
import uz.pdp.appbackend.payload.BookAddDTO;
import uz.pdp.appbackend.payload.BookDTO;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMapper {
    Book toBook(BookAddDTO bookAddDTO);

    BookDTO toBookDTO(Book book);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(BookAddDTO bookAddDTO, @MappingTarget Book book);

    List<BookDTO> toBookDTOList(List<Book> books);

}
