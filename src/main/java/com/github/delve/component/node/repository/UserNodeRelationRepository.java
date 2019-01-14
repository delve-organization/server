package com.github.delve.component.node.repository;

import com.github.delve.component.node.domain.UserNodeRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserNodeRelationRepository extends JpaRepository<UserNodeRelation, Long> {

    @Query("select userNodeRelation from UserNodeRelation userNodeRelation " +
            " where userNodeRelation.userId = :userId " +
            " and userNodeRelation.nodeId = :nodeId ")
    Optional<UserNodeRelation> findByUserIdAndNodeId(@Param("userId") final Long userId, @Param("nodeId") final Long nodeId);

    @Query("select userNodeRelation from UserNodeRelation userNodeRelation " +
            " where userNodeRelation.userId = :userId ")
    List<UserNodeRelation> findByUserId(@Param("userId") final Long userId);

    @Query("select userNodeRelation from UserNodeRelation userNodeRelation " +
            " where userNodeRelation.nodeId = :nodeId ")
    List<UserNodeRelation> findByNodeId(@Param("nodeId") final Long nodeId);
}
