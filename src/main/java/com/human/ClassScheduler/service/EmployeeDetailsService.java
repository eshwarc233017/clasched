package com.human.ClassScheduler.service;

import com.human.ClassScheduler.model.EmployeeDetails;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface EmployeeDetailsService {

    List<EmployeeDetails> createEmployeeGroups(MultipartFile file);
}
