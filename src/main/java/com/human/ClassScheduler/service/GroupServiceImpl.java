package com.human.ClassScheduler.service;

import com.human.ClassScheduler.model.Group;
import com.human.ClassScheduler.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    GroupRepository groupRepository;

    @Override
    public Group createGroup(Group group) {
        Group createdGrp = groupRepository.save(group);
        return createdGrp;
    }
}
