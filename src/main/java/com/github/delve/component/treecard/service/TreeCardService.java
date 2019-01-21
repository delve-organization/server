package com.github.delve.component.treecard.service;

import com.github.delve.common.domain.Accessibility;
import com.github.delve.common.util.MvcUrlCreator;
import com.github.delve.component.tree.dto.TreeDto;
import com.github.delve.component.tree.service.TreeService;
import com.github.delve.component.treecard.domain.TreeCard;
import com.github.delve.component.treecard.dto.CreateTreeCardCommand;
import com.github.delve.component.treecard.dto.DeleteTreeCardCommand;
import com.github.delve.component.treecard.dto.EditTreeCardCommand;
import com.github.delve.component.treecard.dto.TreeCardDto;
import com.github.delve.component.treecard.repository.TreeCardRepository;
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
public class TreeCardService {

    private final TreeCardRepository treeCardRepository;
    private final TreeService treeService;
    private final UserService userService;

    @Autowired
    public TreeCardService(final TreeCardRepository treeCardRepository, final TreeService treeService, final UserService userService) {
        this.treeCardRepository = treeCardRepository;
        this.treeService = treeService;
        this.userService = userService;
    }

    public Long save(final CreateTreeCardCommand command) {
        final UserPrinciple user = UserUtil.currentUser();
        final TreeDto tree = treeService.findById(command.treeId);
        if (!tree.editable && !PUBLIC.equals(tree.accessibility)) {
            throw new IllegalStateException(String.format("Can not saveFile tree card for tree id %s, because tree is not public and user is not owner of the tree.", tree.id));
        }

        final TreeCard newTreeCard = new TreeCard(command.treeId, command.title, command.description,
                command.imageName, command.color, user.getId(), command.accessibility);
        return treeCardRepository.save(newTreeCard).getId();
    }

    public List<TreeCardDto> findAllAvailable() {
        final UserPrinciple user = UserUtil.currentUser();
        final List<Accessibility> accessibilities = isAdmin(user) ? asList(Accessibility.values()) : singletonList(PUBLIC);

        return treeCardRepository.findAllByUserAndAccessibility(user.getId(), accessibilities).stream()
                .map(treeCard -> createDto(treeCard, user))
                .collect(Collectors.toList());
    }

    public TreeCardDto findById(final Long treeCardId) {
        final UserPrinciple user = UserUtil.currentUser();
        final TreeCard treeCard = tryToFindById(treeCardId);

        return createDto(treeCard, user);
    }

    public Long edit(final EditTreeCardCommand command) {
        final TreeCard treeCard = tryToFindByIdForEdit(command.treeCardId);

        treeCard.setTitle(command.title);
        treeCard.setDescription(command.description);
        treeCard.setTreeId(command.treeId);
        treeCard.setColor(command.color);
        treeCard.setImageName(command.imageName);
        treeCard.setAccessibility(command.accessibility);

        return treeCardRepository.save(treeCard).getId();
    }

    public void delete(final DeleteTreeCardCommand command) {
        final TreeCard treeCard = tryToFindByIdForEdit(command.treeCardId);
        treeCardRepository.delete(treeCard);
    }

    private TreeCardDto createDto(final TreeCard treeCard, final UserPrinciple user) {
        final boolean editable = isAdmin(user) || user.getId().equals(treeCard.getOwnerId());
        final String imageUrl = MvcUrlCreator.imageUrl(treeCard.getImageName());
        final String ownerName = userService.getUserNameById(treeCard.getOwnerId());

        return new TreeCardDto(
                treeCard.getId(), treeCard.getTreeId(), treeCard.getTitle(),
                treeCard.getDescription(), treeCard.getImageName(), imageUrl, treeCard.getColor(),
                treeCard.getAccessibility(), editable, ownerName);
    }

    private TreeCard tryToFindByIdForEdit(final Long treeCardId) {
        final UserPrinciple user = UserUtil.currentUser();
        final TreeCard treeCard = tryToFindById(treeCardId);
        checkEditable(treeCard, user);

        return treeCard;
    }

    private TreeCard tryToFindById(final Long treeCardId) {
        final Optional<TreeCard> optionalTreeCard = treeCardRepository.findById(treeCardId);
        if (!optionalTreeCard.isPresent()) {
            throw new IllegalStateException(String.format("Could not find tree card for id: %s.", treeCardId));
        }

        return optionalTreeCard.get();
    }

    private void checkEditable(final TreeCard treeCard, final UserPrinciple user) {
        if (!isAdmin(user) && !user.getId().equals(treeCard.getOwnerId())) {
            throw new IllegalStateException("tree card is not owned by user.");
        }
    }
}
