package uz.pdp.appbackend.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaginationDTO<T> {

    private List<T> content;

    private int number;

    private int size;

    private int totalPages;

    private long totalElements;

    private int numberOfElements;

    private PaginationDTO(List<T> content, int number, int size) {
        this.content = content;
        this.number = number;
        this.size = size;
    }

    public static <E> PaginationDTO<E> makePagination(List<E> content, int number, int size) {
        return new PaginationDTO<>(content, number, size);
    }


}
