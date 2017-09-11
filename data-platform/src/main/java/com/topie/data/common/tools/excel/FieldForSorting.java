package com.topie.data.common.tools.excel;

import java.lang.reflect.Field;

/**
 * The <code>FieldForSorting</code>
 *
 * @author sargeras.wang
 * @version 1.0, Created at 2013年9月17日
 */
public class FieldForSorting {

    private Field field;

    private int index;

    private String head;

    /**
     * @param field
     */
    public FieldForSorting(Field field) {
        super();
        this.field = field;
    }

    /**
     * @param field
     * @param index
     */
    public FieldForSorting(Field field, int index) {
        super();
        this.field = field;
        this.index = index;
    }

    public FieldForSorting(Field field, int index, String head) {
        super();
        this.field = field;
        this.index = index;
        this.head = head;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    /**
     * @return the field
     */
    public Field getField() {
        return field;
    }

    /**
     * @param field the field to set
     */
    public void setField(Field field) {
        this.field = field;
    }

    /**
     * @return the index
     */
    public int getIndex() {
        return index;
    }

    /**
     * @param index the index to set
     */
    public void setIndex(int index) {
        this.index = index;
    }

}
