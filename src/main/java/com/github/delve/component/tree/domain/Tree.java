package com.github.delve.component.tree.domain;

import com.github.delve.common.domain.Accessibility;
import com.github.delve.component.node.domain.Node;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "TREE")
public class Tree {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false, updatable = false)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ROOT_NODE_ID", nullable = false)
    private Node rootNode;

    @Column(name = "TITLE", nullable = false)
    private String title;

    @Column(name = "OWNER_ID", nullable = false)
    private Long ownerId;

    @Enumerated(EnumType.STRING)
    @Column(name = "ACCESSIBILITY", nullable = false)
    private Accessibility accessibility;

    public Tree(final Node rootNode, final String title, final Long ownerId, final Accessibility accessibility) {
        this.rootNode = rootNode;
        this.title = title;
        this.ownerId = ownerId;
        this.accessibility = accessibility;
    }

    public Tree() {
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Node getRootNode() {
        return rootNode;
    }

    public void setRootNode(final Node rootNode) {
        this.rootNode = rootNode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(final Long ownerId) {
        this.ownerId = ownerId;
    }

    public Accessibility getAccessibility() {
        return accessibility;
    }

    public void setAccessibility(final Accessibility accessibility) {
        this.accessibility = accessibility;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tree tree = (Tree) o;
        return Objects.equals(id, tree.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
