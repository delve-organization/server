package com.github.delve.component.treeboard.service;

import com.github.delve.common.domain.Accessibility;
import com.github.delve.common.util.MvcUrlCreator;
import com.github.delve.component.tree.dto.TreeDto;
import com.github.delve.component.tree.service.TreeService;
import com.github.delve.component.treeboard.domain.TreeBoard;
import com.github.delve.component.treeboard.dto.CreateTreeBoardCommand;
import com.github.delve.component.treeboard.dto.DeleteTreeBoardCommand;
import com.github.delve.component.treeboard.dto.EditTreeBoardCommand;
import com.github.delve.component.treeboard.dto.TreeBoardDto;
import com.github.delve.component.treeboard.repository.TreeBoardRepository;
import com.github.delve.security.service.user.UserPrinciple;
import com.github.delve.security.service.user.UserService;
import com.github.delve.security.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.github.delve.common.domain.Accessibility.PUBLIC;
import static com.github.delve.security.util.UserUtil.isAdmin;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

@Service
public class TreeBoardService {

    private final TreeBoardRepository treeBoardRepository;
    private final TreeService treeService;
    private final UserService userService;

    @Autowired
    public TreeBoardService(final TreeBoardRepository treeBoardRepository, final TreeService treeService, final UserService userService) {
        this.treeBoardRepository = treeBoardRepository;
        this.treeService = treeService;
        this.userService = userService;
    }

    public Long save(final CreateTreeBoardCommand command) {
        final UserPrinciple user = UserUtil.currentUser();
        final TreeDto tree = treeService.findById(command.treeId);
        if (!tree.editable && !PUBLIC.equals(tree.accessibility)) {
            throw new IllegalStateException(String.format("Can not saveFile tree board for tree id %s, because tree is not public and user is not owner of the tree.", tree.id));
        }

        final TreeBoard newTreeBoard = new TreeBoard(command.treeId, command.title, command.description,
                command.imageName, command.color, user.getId(), command.accessibility);
        return treeBoardRepository.save(newTreeBoard).getId();
    }

    public List<TreeBoardDto> findAllAvailable() {
        final UserPrinciple user = UserUtil.currentUser();
        final List<Accessibility> accessibilities = isAdmin(user) ? asList(Accessibility.values()) : singletonList(PUBLIC);

        return treeBoardRepository.findAllByUserAndAccessibility(user.getId(), accessibilities).stream()
                .map(treeBoard -> createDto(treeBoard, user))
                .collect(Collectors.toList());
    }

    public TreeBoardDto findById(final Long treeBoardId) {
        final UserPrinciple user = UserUtil.currentUser();
        final TreeBoard treeBoard = tryToFindById(treeBoardId);

        return createDto(treeBoard, user);
    }

    public Long edit(final EditTreeBoardCommand command) {
        final TreeBoard treeBoard = tryToFindByIdForEdit(command.treeBoardId);

        treeBoard.setTitle(command.title);
        treeBoard.setDescription(command.description);
        treeBoard.setTreeId(command.treeId);
        treeBoard.setColor(command.color);
        treeBoard.setImageName(command.imageName);
        treeBoard.setAccessibility(command.accessibility);

        return treeBoardRepository.save(treeBoard).getId();
    }

    public void delete(final DeleteTreeBoardCommand command) {
        final TreeBoard treeBoard = tryToFindByIdForEdit(command.treeBoardId);
        treeBoardRepository.delete(treeBoard);
    }

    private TreeBoardDto createDto(final TreeBoard treeBoard, final UserPrinciple user) {
        final boolean editable = isAdmin(user) || user.getId().equals(treeBoard.getOwnerId());
        final String imageUrl = MvcUrlCreator.imageUrl(treeBoard.getImageName());
        final String ownerName = userService.getUserNameById(treeBoard.getOwnerId());

        return new TreeBoardDto(
                treeBoard.getId(), treeBoard.getTreeId(), treeBoard.getTitle(),
                treeBoard.getDescription(), treeBoard.getImageName(), imageUrl, treeBoard.getColor(),
                treeBoard.getAccessibility(), editable, ownerName);
    }

    private TreeBoard tryToFindByIdForEdit(final Long treeBoardId) {
        final UserPrinciple user = UserUtil.currentUser();
        final TreeBoard treeBoard = tryToFindById(treeBoardId);
        checkEditable(treeBoard, user);

        return treeBoard;
    }

    private TreeBoard tryToFindById(final Long treeBoardId) {
        final Optional<TreeBoard> optionalTreeBoard = treeBoardRepository.findById(treeBoardId);
        if (!optionalTreeBoard.isPresent()) {
            throw new IllegalStateException(String.format("Could not find tree board for id: %s.", treeBoardId));
        }

        return optionalTreeBoard.get();
    }

    private void checkEditable(final TreeBoard treeBoard, final UserPrinciple user) {
        if (!isAdmin(user) && !user.getId().equals(treeBoard.getOwnerId())) {
            throw new IllegalStateException("Tree board is not owned by user.");
        }
    }
}
