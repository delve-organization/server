package com.github.delve.component.tree.repository;

import com.github.delve.common.domain.Accessibility;
import com.github.delve.component.tree.domain.Tree;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.Optional;

public interface TreeRepository extends JpaRepository<Tree, Long> {

    @Query("select tree from Tree tree " +
            "where tree.id = :treeId " +
            "and (" +
            "   tree.ownerId = :userId " +
            "or tree.accessibility in (:accessibilities)" +
            ")")
    Optional<Tree> findByIdByUserAndAccessibility(
            @Param("treeId") final Long treeId,
            @Param("userId") final Long userId,
            @Param("accessibilities") final Collection<Accessibility> accessibilities);

    @Query("select tree from Tree tree " +
            "where tree.ownerId = :userId " +
            "or tree.accessibility in (:accessibilities) ")
    Collection<Tree> findAllByUserAndAccessibility(
            @Param("userId") final Long userId,
            @Param("accessibilities") final Collection<Accessibility> accessibilities);
}
