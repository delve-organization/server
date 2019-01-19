package com.github.delve.component.treeboard.repository;

import com.github.delve.common.domain.Accessibility;
import com.github.delve.component.treeboard.domain.TreeBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

public interface TreeBoardRepository extends JpaRepository<TreeBoard, Long> {

    @Query("select treeboard from TreeBoard treeboard " +
            "where treeboard.ownerId = :userId " +
            "or treeboard.accessibility in (:accessibilities) ")
    Collection<TreeBoard> findAllByUserAndAccessibility(
            @Param("userId") final Long userId,
            @Param("accessibilities") final Collection<Accessibility> accessibilities);
}
