package com.github.delve.component.node.controller;

import com.github.delve.component.node.dto.CreateUserNodeRelationCommand;
import com.github.delve.component.node.dto.NodeDto;
import com.github.delve.component.node.dto.UserNodeRelationDto;
import com.github.delve.component.node.service.NodeService;
import com.github.delve.component.node.service.UserNodeRelationService;
import com.github.delve.config.RestApiController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@RestApiController
@RequestMapping("/node")
@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
public class NodeController {

    private final NodeService nodeService;
    private final UserNodeRelationService userNodeRelationService;

    @Autowired
    public NodeController(final NodeService nodeService, final UserNodeRelationService userNodeRelationService) {
        this.nodeService = nodeService;
        this.userNodeRelationService = userNodeRelationService;
    }

    @GetMapping("/id/{nodeId}")
    public NodeDto getNodesFromRoot(@PathVariable final Long nodeId) {
        return nodeService.getNodesFromRoot(nodeId);
    }

    @PostMapping("/relation")
    public void setRelation(@Valid @RequestBody final CreateUserNodeRelationCommand request) {
        userNodeRelationService.save(request);
    }

    @GetMapping("relations")
    public List<UserNodeRelationDto> getRelationsByUser() {
        return userNodeRelationService.getRelationsByUser();
    }
}
