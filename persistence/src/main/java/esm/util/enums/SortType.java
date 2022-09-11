package esm.util.enums;

public enum SortType {
    ASC,
    DESC,
    UNKNOWN;

    public static SortType findByName(String sortingType) {
        for (SortType value : values()) {
            if (value.name().equalsIgnoreCase(sortingType))
                return value;
        }
        return UNKNOWN;
    }

}
