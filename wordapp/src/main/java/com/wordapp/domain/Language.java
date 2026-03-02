package com.wordapp.domain;

import jakarta.persistence.*;

@Entity
@Table(name="Languages")
public class Language {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="language_id", nullable = false, updatable = false)
    private Long id;
    @Column(name="language")
    private String name;
    public Language(){}

    public Language(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long languageid) {
        this.id = languageid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    };



}
