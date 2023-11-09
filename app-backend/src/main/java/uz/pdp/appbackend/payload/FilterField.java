package uz.pdp.appbackend.payload;

import lombok.Data;

@Data
public class FilterField {

    private ComparatorTypeEnum comparatorType;

    private String column;

    private FilterFieldValue value;

    /*
value
:
{searchingValue: "bah"}
searchingValue
:
"bah"
     */
}
