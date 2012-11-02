package com.freeroom;

import com.freeroom.dao.EmployeeDAO;
import com.freeroom.dao.impl.EmployeeDAOImpl;
import com.freeroom.domain.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/jpa")
public class MyController {
    @Autowired
    EmployeeDAO employeeDAO;

    @RequestMapping(value = "/persist", method = RequestMethod.GET)
    public ModelAndView persist() {
        Employee employee = new Employee();
        employee.setEmployeeNo("TW1238");
        employee.setEmployeeName("Richard Li");

        employeeDAO.persist(employee);
        return new ModelAndView("default");
    }

    @RequestMapping(value = "/find/{employeeNo}", method = RequestMethod.GET)
    public ModelAndView find(@PathVariable String employeeNo) {
        Employee employee = employeeDAO.findByEmployeeNo(employeeNo);
        if (employee != null) {
            System.out.println("Find >>>>>>>>> " + employee.getEmployeeName());
        }
        return new ModelAndView("default");
    }

    @RequestMapping(value = "/update/{employeeNo}/{newName}", method = RequestMethod.GET)
    public ModelAndView update(@PathVariable String employeeNo, @PathVariable String newName) {
        Employee employee = employeeDAO.findByEmployeeNo(employeeNo);
        if (employee != null) {
            employee.setEmployeeName(newName);
            employeeDAO.merge(employee);
            System.out.println("Update >>>>>>>>> " + employee.getEmployeeName());
        }
        return new ModelAndView("default");
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public ModelAndView test(HttpServletResponse response) {
//        String string = response.encodeRedirectURL("http://www.lly.com/abc?a=12&b=lyli@thoughtworks.com&c=%23&d=t#tag");
//        System.out.println(">>>>>>>>>>  " + string);

         return new ModelAndView(new RedirectView("http://www.lly.com/abc/bbb?a=12&b=lyli@thoughtworks.com&c=%23&d=t#tag"));
    }
}

