package com.human.ClassScheduler.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "employeeDetails")
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmployeeDetails extends AuditModel {

    @Id
    private Long empId;

    @NotBlank
    @Size(min = 3, max = 100)
    private String employeeName;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    @Column(name = "nihongo_level")
    private String nihongoLevel;

    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public String getNihongoLevel() {
        return nihongoLevel;
    }

    public void setNihongoLevel(String nihongoLevel) {
        this.nihongoLevel = nihongoLevel;
    }
}
