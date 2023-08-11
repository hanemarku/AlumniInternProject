package com.example.AlumniInternProject.admin.settings;

import com.example.AlumniInternProject.entity.Setting;
import com.example.AlumniInternProject.entity.SettingCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SettingRepository extends JpaRepository<Setting, String> {
    public List<Setting> findByCategory(SettingCategory category);
}
