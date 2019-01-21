package com.github.delve.component.treecard.repository;

import com.github.delve.common.domain.Accessibility;
import com.github.delve.component.treecard.domain.TreeCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

public interface TreeCardRepository extends JpaRepository<TreeCard, Long> {

    @Query("select treecard from TreeCard treecard " +
            "where treecard.ownerId = :userId " +
            "or treecard.accessibility in (:accessibilities) ")
    Collection<TreeCard> findAllByUserAndAccessibility(
            @Param("userId") final Long userId,
            @Param("accessibilities") final Collection<Accessibility> accessibilities);
}
