package com.rom.domain.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Objects;

@Document
public class Model {
    @Id
    private String id;
    private List<Field> fields;

    public Model(String id, List<Field> fields) {
        this.id = id;
        this.fields = fields;
    }

    public Model() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

    @Override
    public String toString() {
        return "Model{" +
                "id='" + id + '\'' +
                ", fields=" + fields +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Model)) return false;
        Model model = (Model) o;
        return Objects.equals(id, model.id) && Objects.equals(fields, model.fields);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fields);
    }
}
