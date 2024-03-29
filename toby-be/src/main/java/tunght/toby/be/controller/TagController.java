package tunght.toby.be.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.RequestParam;
import tunght.toby.be.dto.TagDto;
import tunght.toby.be.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tags")
@RequiredArgsConstructor
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "422", description = "Failed, unknown error"),
        @ApiResponse(responseCode = "404", description = "Not Found"),
})
public class TagController {
    private final TagService tagService;

    @Operation(summary = "Get tags sorted by popularity for home page or approving page",
    description = "Default is homepage, isApproved = 1; For approving page, isApproved = 0")
    @GetMapping
    public TagDto.TagList getTagsByPopularity(@RequestParam(defaultValue = "1") Integer isApproved) {
        return TagDto.TagList.builder().tags(tagService.getTagsByPopularity(isApproved)).build();
    }

    @Operation(summary = "Get pinned tags (like: rule, notification,...) for main page")
    @GetMapping("/pinned")
    public TagDto.TagList getPinnedTags() {
        return TagDto.TagList.builder().tags(tagService.getPinnedTags()).build();
    }

    @Operation(summary = "Get tags list for drop-down selector when user creating articles")
    @GetMapping("/drop-down")
    public TagDto.TagList dropDownListOfTags() {
        return TagDto.TagList.builder().tags(tagService.getDropDownTags()).build();
    }
}
