package uz.pdp.appbackend.payload;

import lombok.Data;
import uz.pdp.appbackend.enums.FilterOperatorEnum;

import java.util.List;

@Data
public class FilterDTO {

    private FilterOperatorEnum filterOperator;

    private String search;

    private List<String> searchingColumns;

    private FilterField filterField;


}
