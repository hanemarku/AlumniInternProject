package com.example.AlumniInternProject.admin.settings;

import com.example.AlumniInternProject.entity.Setting;
import com.example.AlumniInternProject.entity.SettingCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor

public class SettingService {

    private final SettingRepository repo;

    public List<Setting> listAllSettings(){
        return repo.findAll();
    }

    public GeneralSettingBag getGeneralSettings() {
        List<Setting> settings = new ArrayList<>();

        List<Setting> generalSettings = repo.findByCategory(SettingCategory.GENERAL);

        settings.addAll(generalSettings);

        return new GeneralSettingBag(settings);
    }

    //get mail template settings

    public void saveAll(Iterable<Setting> settings) {
        repo.saveAll(settings);
    }

    public List<Setting> getMailServerSettings() {
        return repo.findByCategory(SettingCategory.MAIL_SERVER);
    }

    public List<Setting> getMailTemplateSettings() {
        return repo.findByCategory(SettingCategory.MAIL_TEMPLATES);
    }



}
