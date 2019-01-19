package com.github.delve.component.tree.service;

import com.github.delve.common.domain.Accessibility;
import com.github.delve.component.node.domain.Node;
import com.github.delve.component.node.repository.NodeRepository;
import com.github.delve.component.tree.domain.Tree;
import com.github.delve.component.tree.dto.CreateTreeCommand;
import com.github.delve.component.tree.dto.TreeDto;
import com.github.delve.component.tree.repository.TreeRepository;
import com.github.delve.security.service.user.UserPrinciple;
import com.github.delve.security.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.github.delve.common.domain.Accessibility.PUBLIC;
import static com.github.delve.security.service.user.UserPrinciple.ADMIN_AUTHORITY;
import static com.github.delve.security.util.UserUtil.isAdmin;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

@Service
public class TreeService {

    private final TreeRepository treeRepository;
    private final NodeRepository nodeRepository;

    @Autowired
    public TreeService(final TreeRepository treeRepository, final NodeRepository nodeRepository) {
        this.treeRepository = treeRepository;
        this.nodeRepository = nodeRepository;
    }

    public Long save(final CreateTreeCommand command) {
        final UserPrinciple user = UserUtil.currentUser();

        final Optional<Node> optionalNode = nodeRepository.findById(command.rootNodeId);
        if (!optionalNode.isPresent()) {
            throw new IllegalStateException(String.format("Could not find root node for id %s", command.rootNodeId));
        }

        final Tree newTree = new Tree(optionalNode.get(), command.title, user.getId(), command.accessibility);
        return treeRepository.save(newTree).getId();
    }

    public List<TreeDto> findAllAvailable() {
        final UserPrinciple user = UserUtil.currentUser();
        final List<Accessibility> accessibilities = isAdmin(user) ? asList(Accessibility.values()) : singletonList(PUBLIC);

        return treeRepository.findAllByUserAndAccessibility(user.getId(), accessibilities).stream()
                .map(tree -> this.createDto(tree, user))
                .collect(Collectors.toList());
    }

    public TreeDto findById(final Long treeId) {
        final UserPrinciple user = UserUtil.currentUser();
        final List<Accessibility> accessibilities = isAdmin(user) ? asList(Accessibility.values()) : singletonList(PUBLIC);

        final Optional<Tree> tree = treeRepository.findByIdByUserAndAccessibility(treeId, user.getId(), accessibilities);

        if (!tree.isPresent()) {
            throw new IllegalStateException(String.format("Could not find tree for id %s", treeId));
        }

        return createDto(tree.get(), user);
    }

    private TreeDto createDto(final Tree tree, final UserPrinciple user) {
        final boolean editable = isAdmin(user) || tree.getOwnerId().equals(user.getId());

        return new TreeDto(tree.getId(), tree.getRootNode().getId(), tree.getTitle(), editable, tree.getAccessibility());
    }
}
