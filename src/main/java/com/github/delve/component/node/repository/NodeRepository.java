package com.github.delve.component.node.repository;

import com.github.delve.component.node.domain.Node;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NodeRepository extends JpaRepository<Node, Long> {
}
