package tunght.toby.be.controller;

import io.swagger.v3.oas.annotations.Operation;
import tunght.toby.be.dto.TagDto;
import tunght.toby.be.entity.AvailableTag;
import tunght.toby.be.repository.AvailableTagRepository;
import tunght.toby.be.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tags")
@RequiredArgsConstructor
public class TagController {
    private final TagService tagService;
    private final AvailableTagRepository availableTagRepository;

    @Operation(summary = "Get tags sorted by popularity for home page")
    @GetMapping
    public TagDto.TagList getTagsByPopularity() {
        return TagDto.TagList.builder().tags(tagService.getTagsByPopularity()).build();
    }

    @Operation(summary = "Get pinned tags (like: rule, notification,...) for main page")
    public TagDto.TagList getPinnedTags() {
        List<String> pinnedTags = availableTagRepository.getAllByIsPinned(1)
                .stream()
                .map(AvailableTag::getTagName)
                .collect(Collectors.toList());
        return TagDto.TagList.builder().tags(pinnedTags).build();
    }

    @Operation(summary = "Get tags list for drop-down selector when user creating articles")
    @GetMapping("/drop-down")
    public TagDto.TagList dropDownListOfTags() {
        List<String> availableTags = availableTagRepository.findAll()
                .stream()
                .map(AvailableTag::getTagName)
                .collect(Collectors.toList());
        return TagDto.TagList.builder().tags(availableTags).build();
    }
}
