package com.freeroom;

import com.freeroom.dao.EmployeeDAO;
import com.freeroom.domain.Employee;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.sql.SQLException;

@Controller
@RequestMapping("/jpa")
public class FileUploadController {

    @Autowired
    EmployeeDAO employeeDAO;

    @RequestMapping(value = "/uploadEmployeeImage", method = RequestMethod.GET)
    public ModelAndView view() {
        return new ModelAndView("upload_file");
    }

    @RequestMapping(value = "/uploadEmployeeImage", method = RequestMethod.POST)
    public ModelAndView upload(@RequestParam("file") MultipartFile file, @RequestParam("employeeNo") String employeeNo) throws IOException, SQLException {
        Employee employee = employeeDAO.findByEmployeeNo(employeeNo);
        if (employee != null) {
            System.out.println(">>>>>>>>>>  " + file.getOriginalFilename());
            System.out.println(">>>>>>>>>>  " + file.getBytes().length);
            employee.setPhoto(file.getBytes());
            employeeDAO.merge(employee);
        }
        return new ModelAndView("default");
    }
}
