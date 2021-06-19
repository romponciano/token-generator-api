package com.rom.domain.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Document
public class Model {
    @Id
    private String id;
    private String name;
    private String userId;
    private List<Field> fields;

    public Model(String id, String modelName, String userId, List<Field> fields) {
        this.id = id;
        this.name = modelName;
        this.userId = userId;
        setFields(fields);
    }

    public Model() {
        setFields(null);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields == null
            ? new ArrayList<>()
            : fields;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Model)) return false;
        Model model = (Model) o;
        return Objects.equals(getId(), model.getId()) && Objects.equals(getName(), model.getName()) && Objects.equals(getUserId(), model.getUserId()) && Objects.equals(getFields(), model.getFields());
    }
}
