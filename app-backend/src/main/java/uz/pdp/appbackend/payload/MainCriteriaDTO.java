package uz.pdp.appbackend.payload;

import lombok.Data;
import org.springframework.data.domain.Sort;

import java.util.List;

@Data
public class MainCriteriaDTO {

    private int page;

    private int size;

    private List<SortDTO> sorts;

    private FilterDTO filter;
}
