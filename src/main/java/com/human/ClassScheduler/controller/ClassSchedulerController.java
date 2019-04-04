package com.human.ClassScheduler.controller;

import com.human.ClassScheduler.model.EmployeeDetails;
import com.human.ClassScheduler.repository.GroupRepository;
import com.human.ClassScheduler.service.EmployeeDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@Transactional
public class ClassSchedulerController {

    @Autowired
    private EmployeeDetailsService employeeDetailsService;

    @Autowired
    private GroupRepository groupRepository;

    @PostMapping(value = "/saveEmployees", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<EmployeeDetails> createEmployeeDetails(@Valid @RequestParam(value = "file") MultipartFile file, HttpServletRequest request) {
        List<EmployeeDetails> empList = employeeDetailsService.createEmployeeGroups(file);
        return empList;
    }
}
