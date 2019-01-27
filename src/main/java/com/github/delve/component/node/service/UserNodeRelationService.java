package com.github.delve.component.node.service;

import com.github.delve.common.exception.DelveException;
import com.github.delve.component.node.domain.Node;
import com.github.delve.component.node.domain.UserNodeRelation;
import com.github.delve.component.node.repository.NodeRepository;
import com.github.delve.component.node.repository.UserNodeRelationRepository;
import com.github.delve.component.node.dto.CreateUserNodeRelationCommand;
import com.github.delve.component.node.dto.UserNodeRelationDto;
import com.github.delve.security.service.user.UserPrinciple;
import com.github.delve.security.util.UserUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserNodeRelationService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final UserNodeRelationRepository userNodeRelationRepository;
    private final NodeRepository nodeRepository;

    public UserNodeRelationService(final UserNodeRelationRepository userNodeRelationRepository, final NodeRepository nodeRepository) {
        this.userNodeRelationRepository = userNodeRelationRepository;
        this.nodeRepository = nodeRepository;
    }

    @Transactional
    public void deleteAllByNodeId(final Long nodeId) {
        final List<UserNodeRelation> relationsOfParentNode = userNodeRelationRepository.findByNodeId(nodeId);
        userNodeRelationRepository.deleteAll(relationsOfParentNode);

        logger.info("Deleted all user-node relations for node id: {}", nodeId);
    }

    @Transactional
    public void save(final CreateUserNodeRelationCommand command) {
        final UserPrinciple user = UserUtil.currentUser();

        final Optional<Node> optionalNode = nodeRepository.findById(command.getNodeId());
        if (!optionalNode.isPresent()) {
            throw new DelveException("Could not find root node for id %s", command.getNodeId());
        }

        final Node node = optionalNode.get();
        if (!node.getChildren().isEmpty()) {
            throw new DelveException("Cannot set visited property for node (%d) because it is not a leaf node.", command.getNodeId());
        }

        final Optional<UserNodeRelation> optionalRelation = userNodeRelationRepository
                .findByUserIdAndNodeId(user.getId(), command.getNodeId());
        if (optionalRelation.isPresent()) {
            optionalRelation.get().setVisited(command.getVisited());
        } else {
            final UserNodeRelation newRelation = new UserNodeRelation(user.getId(), command.getNodeId(), command.getVisited());
            userNodeRelationRepository.save(newRelation);

            logger.info("Created new user-node relation for user id: {}, and node id: {}", user.getId(), node.getId());
        }
    }

    public List<UserNodeRelationDto> getRelationsByUser() {
        final UserPrinciple user = UserUtil.currentUser();
        return userNodeRelationRepository.findByUserId(user.getId()).stream()
                .map(relation -> new UserNodeRelationDto(relation.getNodeId(), relation.getVisited()))
                .collect(Collectors.toList());
    }
}
