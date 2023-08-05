package com.example.AlumniInternProject.Events.export;

import com.example.AlumniInternProject.user.DTOs.UserGetDto;
import jakarta.servlet.http.HttpServletResponse;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import java.io.IOException;
import java.util.List;

public class EventCsvExporter extends AbstractExporter {
    public void export(List<UserGetDto> listUsers, HttpServletResponse response) throws IOException{
        super.setResponseHeader(response, "text/csv", ".csv");

        ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
        String[] csvHeader = {"First Name", "Last Name"};
        String[] fieldMapping = {"firstname", "lastname"};

        csvWriter.writeHeader(csvHeader);

        for (UserGetDto user : listUsers) {
            csvWriter.write(user, fieldMapping);
        }

        csvWriter.close();
    }
}
