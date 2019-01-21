package com.github.delve.component.treecard.domain;

import com.github.delve.common.domain.Accessibility;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "TREE_CARD")
public class TreeCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false, updatable = false)
    private Long id;

    @Column(name = "TREE_ID", nullable = false)
    private Long treeId;

    @Column(name = "TITLE", nullable = false)
    private String title;

    @Column(name = "DESCRIPTION", nullable = false)
    private String description;

    @Column(name = "IMAGE_NAME", nullable = false)
    private String imageName;

    @Column(name = "COLOR")
    private String color;

    @Column(name = "OWNER_ID", nullable = false)
    private Long ownerId;

    @Enumerated(EnumType.STRING)
    @Column(name = "ACCESSIBILITY", nullable = false)
    private Accessibility accessibility;

    public TreeCard(final Long treeId, final String title, final String description, final String imageName, final String color, final Long ownerId, final Accessibility accessibility) {
        this.treeId = treeId;
        this.title = title;
        this.description = description;
        this.imageName = imageName;
        this.color = color;
        this.ownerId = ownerId;
        this.accessibility = accessibility;
    }

    public TreeCard() {
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Long getTreeId() {
        return treeId;
    }

    public void setTreeId(final Long treeId) {
        this.treeId = treeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(final String imageName) {
        this.imageName = imageName;
    }

    public String getColor() {
        return color;
    }

    public void setColor(final String color) {
        this.color = color;
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
    public String toString() {
        return "TreeCard{" +
                "id=" + id +
                ", treeId=" + treeId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", imageName='" + imageName + '\'' +
                ", color='" + color + '\'' +
                ", ownerId=" + ownerId +
                ", accessibility=" + accessibility +
                '}';
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final TreeCard treeCard = (TreeCard) o;
        return Objects.equals(id, treeCard.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
