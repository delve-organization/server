package com.github.delve.component.tree.controller;

import com.github.delve.component.tree.service.TreeService;
import com.github.delve.component.tree.dto.TreeDto;
import com.github.delve.config.RestApiController;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RestApiController
@RequestMapping("tree")
@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
public class TreeController {

    private final TreeService treeService;

    public TreeController(final TreeService treeService) {
        this.treeService = treeService;
    }

    @GetMapping("/all")
    public List<TreeDto> getAllTrees() {
        return treeService.findAll();
    }

    @GetMapping("/id/{treeId}")
    public TreeDto getTreeById(@PathVariable final Long treeId) {
        return treeService.findById(treeId);
    }
}
