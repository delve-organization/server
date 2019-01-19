package com.github.delve.component.treeboard.controller;

import com.github.delve.component.treeboard.dto.CreateTreeBoardCommand;
import com.github.delve.component.treeboard.dto.DeleteTreeBoardCommand;
import com.github.delve.component.treeboard.dto.TreeBoardDto;
import com.github.delve.component.treeboard.service.TreeBoardService;
import com.github.delve.config.RestApiController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@RestApiController
@RequestMapping("tree-board")
@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
public class TreeBoardController {

    private final TreeBoardService treeBoardService;

    @Autowired
    public TreeBoardController(final TreeBoardService treeBoardService) {
        this.treeBoardService = treeBoardService;
    }

    @GetMapping("/all-available")
    public List<TreeBoardDto> getAllAvailableTreeBoards() {
        return treeBoardService.findAllAvailable();
    }

    @PostMapping("/create")
    public void create(@Valid @RequestBody final CreateTreeBoardCommand request) {
        treeBoardService.save(request);
    }

    @PostMapping("/delete")
    public void delete(@Valid @RequestBody final DeleteTreeBoardCommand request) {
        treeBoardService.delete(request);
    }
}
