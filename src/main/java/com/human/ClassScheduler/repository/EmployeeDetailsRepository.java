package com.human.ClassScheduler.repository;

import com.human.ClassScheduler.model.EmployeeDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeDetailsRepository extends JpaRepository<EmployeeDetails,Long> {
//    List<EmployeeDetails> findEmployeeDetails(Long Id);
}
