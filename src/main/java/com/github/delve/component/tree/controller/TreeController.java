package com.github.delve.component.tree.controller;

import com.github.delve.component.tree.dto.CreateTreeCommand;
import com.github.delve.component.tree.dto.CreateTreeRequest;
import com.github.delve.component.tree.dto.TreeDto;
import com.github.delve.component.tree.service.TreeService;
import com.github.delve.config.RestApiController;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@RestApiController
@RequestMapping("tree")
@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
public class TreeController {

    private final TreeService treeService;

    public TreeController(final TreeService treeService) {
        this.treeService = treeService;
    }

    @GetMapping("/all-available")
    public List<TreeDto> getAllAvailableTrees() {
        return treeService.findAllAvailable();
    }

    @GetMapping("/id/{treeId}")
    public TreeDto getTreeById(@PathVariable final Long treeId) {
        return treeService.findById(treeId);
    }

    @PostMapping("/create")
    public TreeDto create(@Valid @RequestBody final CreateTreeRequest request) {
        final Long savedTreeId = treeService.save(
                new CreateTreeCommand(request.getRootNodeId(), request.getTitle(), request.getAccessibility()));
        return treeService.findById(savedTreeId);
    }
}
