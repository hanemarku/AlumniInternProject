package com.example.AlumniInternProject.admin.settings;

import ch.qos.logback.core.model.Model;
import com.example.AlumniInternProject.FileUploadUtil;
import com.example.AlumniInternProject.Verfication.VerificationTokenService;
import com.example.AlumniInternProject.entity.Setting;
import com.example.AlumniInternProject.entity.VerificationToken;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins="http://localhost:4200")
@RestController
@RequestMapping(path = "api/v1/settings")
@RequiredArgsConstructor

public class SettingController {

    private final SettingService settingService;
    private final VerificationTokenService verificationTokenService;


    @GetMapping
    public ResponseEntity<List<Setting>> setting() {
        List<Setting> listSettings = settingService.listAllSettings();
        return ResponseEntity.ok().body(listSettings);
    }




//    @GetMapping("/listr")
//    public List<Setting> listAll(){
//        List<Setting> listSettings = settingService.listAllSettings();
//
//        return listSettings;
//    }


//    @PostMapping("/save_general")
//
//    public String saveGeneralSettings(HttpServletRequest request) throws IOException {
//        GeneralSettingBag settingBag = settingService.getGeneralSettings();
//
//        // Update setting values from form
//        updateSettingValuesFromForm(request, settingBag.list());
//
//        return "General settings have been saved.";
//    }
//
//    private void updateSettingValuesFromForm(HttpServletRequest request, List<Setting> listSettings) {
//        for (Setting setting : listSettings) {
//            String value = request.getParameter(setting.getKey());
//            if (value != null) {
//                setting.setValue(value);
//            }
//        }
//        settingService.saveAll(listSettings);
//    }
//    private void saveSiteLogo(MultipartFile multipartFile, GeneralSettingBag settingBag) throws IOException, IOException {
//        if (!multipartFile.isEmpty()) {
//            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
//            String value = "/site-logo/" + fileName;
//            settingBag.updateSiteLogo(value);
//
//            String uploadDir = "../site-logo/";
//            FileUploadUtil.cleanDir(uploadDir);
//            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
//        }
//    }
//
//
//
//    private void updateSettingValuesFromForm(HttpServletRequest request, List<Setting> listSettings) {
//        for (Setting setting : listSettings) {
//            String value = request.getParameter(setting.getKey());
//            if (value != null) {
//                setting.setValue(value);
//            }
//        }
//        settingService.saveAll(listSettings);
//    }
    private void updateSettingValuesFromForm(List<Setting> newSettings, List<Setting> existingSettings) {
        for (Setting newSetting : newSettings) {
            Setting existingSetting = existingSettings.stream()
                    .filter(setting -> setting.getKey().equals(newSetting.getKey()))
                    .findFirst()
                    .orElse(null);

            if (existingSetting != null) {
                existingSetting.setValue(newSetting.getValue());
            }
        }
    }

    @PostMapping("/save_mail_server")
    public ResponseEntity<Map<String, String>> saveMailServerSettings(@RequestBody List<Setting> mailServerSettings) {
        System.out.println("Received settings from frontend:");
        for (Setting setting : mailServerSettings) {
            System.out.println(setting.getKey() + ": " + setting.getValue());
        }

        List<Setting> existingMailServerSettings = settingService.getMailServerSettings();
        updateSettingValuesFromForm(mailServerSettings, existingMailServerSettings);
        settingService.saveAll(existingMailServerSettings);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Mail server settings have been saved");

        return ResponseEntity.ok().body(response);
    }


    @PostMapping("/save_mail_templates")
    public ResponseEntity<Map<String, String>> saveMailTemplateSetttings(@RequestBody List<Setting> mailServerSettings) {
        List<Setting> mailTemplateSettings = settingService.getMailTemplateSettings();
        updateSettingValuesFromForm(mailServerSettings, mailTemplateSettings);
        settingService.saveAll(mailTemplateSettings);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Mail template settings have been saved");

        return ResponseEntity.ok().body(response);
    }

}
