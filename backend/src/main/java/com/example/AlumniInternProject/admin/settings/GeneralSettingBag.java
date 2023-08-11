package com.example.AlumniInternProject.admin.settings;

import com.example.AlumniInternProject.entity.Setting;
import com.example.AlumniInternProject.entity.SettingBag;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
public class GeneralSettingBag extends SettingBag {

    public GeneralSettingBag(List<Setting> listSettings) {
        super(listSettings);
    }

    public void updateSiteLogo(String value) {
        super.update("SITE_LOGO", value);
    }
}
