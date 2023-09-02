package com.example.AlumniInternProject.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "settings")
@Getter
@Setter

public class Setting {
    @Id
    @Column(name = "`key`",nullable = false, length = 128)
    private String key;

    @Column(nullable = false , length = 600)
    private String value;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 65)
    private SettingCategory category;


    public Setting(String key, String value, SettingCategory category) {
        this.key = key;
        this.value = value;
        this.category = category;
    }

    public Setting() {

    }

    public Setting(String key) {
        this.key = key;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Setting setting = (Setting) o;

        return key != null ? key.equals(setting.key) : setting.key == null;
    }

    @Override
    public int hashCode() {
        return key != null ? key.hashCode() : 0;
    }

}
