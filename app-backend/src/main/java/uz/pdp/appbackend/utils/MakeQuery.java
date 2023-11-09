package uz.pdp.appbackend.utils;

import uz.pdp.appbackend.payload.*;

import java.util.List;

public class MakeQuery {

    public static String makeQuery(MainCriteriaDTO mainCriteriaDTO, String tableName) {
        StringBuilder sb = new StringBuilder(250);
        sb.append("SELECT id FROM ")
                .append(tableName)
                .append(" ");

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

    private static void addFilterQuery(StringBuilder sb, FilterDTO filter, boolean isFiltering) {
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
                appendByColumnType(sb, filterField, comparatorType, fieldValue);
                sb.append(")");
            }
            sb.append(") ");
        }
    }

    private static void appendByColumnType(StringBuilder sb, FilterField filterField, ComparatorTypeEnum comparatorType, FilterFieldValue fieldValue) {
        switch (filterField.getColumnType()) {
            case SHORT_TEXT -> appendForShortText(sb, filterField, comparatorType, fieldValue);
            case MONEY, NUMBER -> appendForNumberOrMoney(sb, filterField, comparatorType, fieldValue);
            case CHECKBOX -> appendForCheckbox(sb, filterField, comparatorType, fieldValue);
            case FILE -> appendForFile(sb, filterField, comparatorType, fieldValue);
            case DATE -> appendForDate(sb, filterField, comparatorType, fieldValue);
        }
    }

    private static void appendForDate(StringBuilder sb, FilterField filterField, ComparatorTypeEnum comparatorType, FilterFieldValue fieldValue) {

    }

    private static void appendForFile(StringBuilder sb, FilterField filterField, ComparatorTypeEnum comparatorType, FilterFieldValue fieldValue) {
        switch (comparatorType) {
            case IS_SET -> {
            }
            case IS_NOT_SET -> {
            }
        }
    }

    private static void appendForCheckbox(StringBuilder sb, FilterField filterField, ComparatorTypeEnum comparatorType, FilterFieldValue fieldValue) {
        switch (comparatorType) {
            case IS_SET -> {
            }
            case IS_NOT_SET -> {
            }
        }
    }

    private static void appendForNumberOrMoney(StringBuilder sb, FilterField filterField, ComparatorTypeEnum comparatorType, FilterFieldValue fieldValue) {
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

    private static void appendForShortText(StringBuilder sb, FilterField filterField, ComparatorTypeEnum comparatorType, FilterFieldValue fieldValue) {
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

    private static void addSearchQuery(StringBuilder sb, FilterDTO filter, boolean isSearching) {
        if (isSearching) {
            String search = filter.getSearch();
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

    private static void addOffsetPart(MainCriteriaDTO mainCriteriaDTO, StringBuilder sb) {
        sb.append("LIMIT ")
                .append(mainCriteriaDTO.getSize())
                .append(" OFFSET ")
                .append(mainCriteriaDTO.getPage() * mainCriteriaDTO.getSize());
    }

    private static void addOrderByPart(MainCriteriaDTO mainCriteriaDTO, StringBuilder sb) {
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
