package com.topie.data.common.utils;

import java.io.Serializable;

/**
 * Created by chenguojun on 9/5/16.
 */
public class Option implements Serializable {

    private static final long serialVersionUID = -2973418153744261705L;

    private String text;

    private Object value;

    private Object hiddenValue;

    public Option() {
    }

    public Option(String text, Object value) {
        this.text = text;
        this.value = value;
    }

    public Option(String text, Object value, Object hiddenValue) {
        this.text = text;
        this.value = value;
        this.hiddenValue = hiddenValue;
    }

    public Object getHiddenValue() {
        return hiddenValue;
    }

    public void setHiddenValue(Object hiddenValue) {
        this.hiddenValue = hiddenValue;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
