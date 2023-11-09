package uz.pdp.appbackend.payload;

import lombok.Data;
import org.springframework.data.domain.Sort;

import java.util.List;

@Data
public class MainCriteriaDTO {

    private int page = 0;

    private int size = 10;

    private List<SortDTO> sorts;//[] null

    private FilterDTO filter;
}
