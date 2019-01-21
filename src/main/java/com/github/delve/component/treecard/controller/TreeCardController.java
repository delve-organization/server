package com.github.delve.component.treecard.controller;

import com.github.delve.component.treecard.dto.CreateTreeCardCommand;
import com.github.delve.component.treecard.dto.DeleteTreeCardCommand;
import com.github.delve.component.treecard.dto.EditTreeCardCommand;
import com.github.delve.component.treecard.dto.TreeCardDto;
import com.github.delve.component.treecard.service.TreeCardService;
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
@RequestMapping("tree-card")
@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
public class TreeCardController {

    private final TreeCardService treeCardService;

    @Autowired
    public TreeCardController(final TreeCardService treeCardService) {
        this.treeCardService = treeCardService;
    }

    @GetMapping("/all-available")
    public List<TreeCardDto> getAllAvailableTreeCards() {
        return treeCardService.findAllAvailable();
    }

    @PostMapping("/create")
    public TreeCardDto create(@Valid @RequestBody final CreateTreeCardCommand request) {
        final Long savedTreeCardId = treeCardService.save(request);
        return treeCardService.findById(savedTreeCardId);
    }

    @PostMapping("/edit")
    public TreeCardDto edit(@Valid @RequestBody final EditTreeCardCommand request) {
        final Long savedTreeCardId = treeCardService.edit(request);
        return treeCardService.findById(savedTreeCardId);
    }

    @PostMapping("/delete")
    public void delete(@Valid @RequestBody final DeleteTreeCardCommand request) {
        treeCardService.delete(request);
    }
}
