package esm.util.enums;

public enum SortField {
    NAME("name"),
    DATE("createdDate"),
    NOT_SORT,
    UNKNOWN;

    public String name;

    SortField(String name) {
        this.name = name;
    }

    SortField() {
    }

    public static SortField findByName(String sortingType) {
        if (sortingType == null)
            return NOT_SORT;

        for (SortField value : values()) {
            if (value.name().equalsIgnoreCase(sortingType))
                return value;
        }
        return UNKNOWN;
    }

}
