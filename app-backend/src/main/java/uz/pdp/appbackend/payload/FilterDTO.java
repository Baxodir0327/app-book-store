package uz.pdp.appbackend.payload;

import lombok.Data;
import uz.pdp.appbackend.enums.FilterOperatorEnum;

import java.util.List;

@Data
public class FilterDTO {

    private String search;//undatio

    private List<String> searchingColumns;//[author, title]

    private FilterOperatorEnum filterOperator; //OR

    private List<FilterField> filterFields;//
    // [{column: 'price',
    // columnType: 'MONEY',
    // comparatorType: 'RA',
    // value: {
    //      minValue: 10,
    //      maxValue: 20}
    // },
    // {column: 'pageCount',
    //    // columnType: 'NUMBER',
    //    // comparatorType: 'GTE',
    //    // value: {
    //    //      minValue: 500
    //    }
    //    // }]


}
