package com.human.ClassScheduler.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Set;


@Entity
@Table(name = "newgroup")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Group extends AuditModel {


    @Id
    @GeneratedValue(generator = "group_generator")
    @SequenceGenerator(
            name = "group_generator",
            sequenceName = "group_sequence",
            initialValue = 1000
    )

    private Long groupId;

    @Column(name = "is_empty")
    private Boolean isEmpty;

    public Integer getTeamCount() {
        return teamCount;
    }

    public void setTeamCount(Integer teamCount) {
        this.teamCount = teamCount;
    }

    public Set<EmployeeDetails> getEmployeeDetails() {
        return employeeDetails;
    }

    public void setEmployeeDetails(Set<EmployeeDetails> employeeDetails) {
        this.employeeDetails = employeeDetails;
    }

    @Column(name = "team_count")
    private Integer teamCount;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private Set<EmployeeDetails> employeeDetails;

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long id) {
        this.groupId = id;
    }

    public Boolean getEmpty() {
        return isEmpty;
    }

    public void setEmpty(Boolean empty) {
        isEmpty = empty;
    }
}
