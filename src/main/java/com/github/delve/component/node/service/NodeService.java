package com.github.delve.component.node.service;

import com.github.delve.common.util.MvcUrlCreator;
import com.github.delve.component.node.domain.Node;
import com.github.delve.component.node.dto.CreateNodeCommand;
import com.github.delve.component.node.dto.NodeDto;
import com.github.delve.component.node.repository.NodeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NodeService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final NodeRepository nodeRepository;
    private final UserNodeRelationService userNodeRelationService;

    @Autowired
    public NodeService(final NodeRepository nodeRepository, final UserNodeRelationService userNodeRelationService) {
        this.nodeRepository = nodeRepository;
        this.userNodeRelationService = userNodeRelationService;
    }

    @Transactional
    public Long save(final CreateNodeCommand command) {
        final Node newNode = new Node(command.title, command.description, command.imageName);

        if (command.parentId != null) {
            nodeRepository.findById(command.parentId).ifPresent(parentNode -> {
                newNode.setParent(parentNode);
                parentNode.getChildren().add(newNode);
                userNodeRelationService.deleteAllByNodeId(parentNode.getId());
            });
        }

        final Node savedNode = nodeRepository.save(newNode);
        logger.info("Saved new node with id: {}", savedNode.getId());

        return savedNode.getId();
    }

    public NodeDto getNodesFromRoot(final Long rootNodeId) {
        final Optional<Node> rootNode = nodeRepository.findById(rootNodeId);
        if (!rootNode.isPresent()) {
            throw new IllegalStateException(String.format("Could not find root node for id %s", rootNodeId));
        }

        return createDto(rootNode.get());
    }

    private NodeDto createDto(final Node node) {
        final List<NodeDto> children = node.getChildren().stream().map(this::createDto).collect(Collectors.toList());
        final String imageUrl = node.getImageName() == null ? null : MvcUrlCreator.imageUrl(node.getImageName());

        return new NodeDto(node.getId(), node.getTitle(), node.getDescription(), imageUrl, children);
    }
}
