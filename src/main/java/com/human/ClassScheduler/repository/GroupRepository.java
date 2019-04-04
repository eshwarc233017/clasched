package com.human.ClassScheduler.repository;

import com.human.ClassScheduler.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Group g SET g.teamCount = :teamCount WHERE g.groupId = :groupId")
    int updateTeamCount(@Param("teamCount") int teamCount, @Param("groupId") Long groupId);
}
