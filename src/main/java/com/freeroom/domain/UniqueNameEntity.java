package com.freeroom.domain;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
public class UniqueNameEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(unique = true)
    private String name;

    protected String getName() {
        return name;
    }

    protected void setName(String name) {
        this.name = name;
    }
}
