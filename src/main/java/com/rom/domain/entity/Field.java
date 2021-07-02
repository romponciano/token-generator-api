package com.rom.domain.entity;

import java.util.Objects;

public class Field {
    private String name;
    private Integer order;
    private String type;
    private Object value;

    public Field(String name, Integer order, String type, Object value) {
        this.name = name;
        this.order = order;
        this.type = type;
        this.value = value;
    }

    public Field() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Field)) return false;
        Field field = (Field) o;
        return Objects.equals(getName(), field.getName()) && Objects.equals(getType(), field.getType()) && Objects.equals(getValue(), field.getValue()) && Objects.equals(getOrder(), field.getOrder());
    }
}
