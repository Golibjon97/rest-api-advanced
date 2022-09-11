package esm.util;


import esm.exception.InvalidSortException;
import esm.util.enums.SortField;
import esm.util.enums.SortType;

import java.util.ArrayList;
import java.util.List;

public class Sort {

    private List<SortField> fields;
    private SortType sortType;

    public Sort(List<SortField> fields, SortType sortType) {
        this.fields = fields;
        this.sortType = sortType;
    }

    {
        fields = new ArrayList<>();
    }

    public Sort(String sort) {
        if (sort == null) {
            return;
        }

        String[] elements = sort.split("_");  // _ASC

        sortType = SortType.findByName(elements[elements.length - 1]);
        for (int i = 0; i < elements.length - 1; i++) {
            fields.add(SortField.findByName(elements[i]));
        }
    }

    public Sort validate() {
        if (sortType == null && fields.isEmpty()) {
            return null;
        }
        if (sortType == SortType.UNKNOWN || fields.contains(SortField.UNKNOWN)) {
            throw new InvalidSortException();
        }
        return this;
    }

    public List<SortField> getFields() {
        return fields;
    }

    public SortType getSortType() {
        return sortType;
    }
}
