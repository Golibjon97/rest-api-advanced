package com.epam.esm.controller;

import com.epam.esm.dto.BaseResponse;
import com.epam.esm.dto.request.TagRequestDto;
import com.epam.esm.dto.response.TagResponseDto;
import com.epam.esm.exception.InvalidInputException;
import com.epam.esm.service.certificate.CertificateService;
import com.epam.esm.service.tag.TagServiceImpl;
import com.epam.esm.util.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/tags")
public class TagController {

    private final TagServiceImpl tagService;
    private final CertificateService certificateService;

    public TagController(TagServiceImpl tagService, CertificateService certificateService) {
        this.tagService = tagService;
        this.certificateService = certificateService;
    }

    /**
     * Creates a tag by getting request dto object and checks the validation for null tag name
     * @param tag Request DTO typed tag object which has only name primitive
     * @param bindingResult General interface that represents binding results. Used to check for the validations
     * @return returns Response Entity typed tag response DTO object
     */
    @PostMapping()
    public ResponseEntity<?> create(
            @Valid @RequestBody TagRequestDto tag,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            throw new InvalidInputException(bindingResult);
        }
        TagResponseDto responseDto = tagService.create(tag);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    /**
     * Gets the tag as per the UUID provided
     * @param tagId UUID parameter to search for the tag
     * @return return the Response Entity typed response DTO tag object
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") UUID tagId) {
        TagResponseDto responseDto = tagService.get(tagId);
        return ResponseEntity.ok(responseDto);
    }

    /**
     * Returns all the tags with mentioned size for each page. Also includes page links for next and previous pages
     * @param size number of tags in each page
     * @param page page number to start the result from
     * @return returns Response Entity typed list of response DTO tags with links for next and previous pages
     */
    @GetMapping()
    public BaseResponse<List<TagResponseDto>> getAll(
            @RequestParam(required = false, defaultValue = "20") int size,
            @RequestParam(required = false, defaultValue = "1") int page
    ) {

        List<TagResponseDto> allTags = tagService.getAll(new PageRequest(page, size));
        BaseResponse<List<TagResponseDto>> response
                = new BaseResponse<>(HttpStatus.OK.value(), "tag list", allTags);
        if (!allTags.isEmpty())
            response.add(linkTo(methodOn(TagController.class)
                    .getAll(size, page + 1))
                    .withRel("next page"));

        if (page > 1) {
            response.add(linkTo(methodOn(TagController.class)
                    .getAll(size, page - 1))
                    .withRel("previous page"));
        }
        return response;
    }

    /**
     * Deletes the tag as per the given UUID
     * @param tagId UUID of the tag to find the tag
     * @return returns Response Entity status message
     */
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") UUID tagId) {
        int delete = tagService.delete(tagId);
        if (delete == 1) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    /**
     * Returns the most used tag by certificate
     * @return returns the most used tag which has indirect connection to the most expensive order
     */
    @GetMapping("/most-used")
    public BaseResponse<List<TagResponseDto>> getMostUsed() {
        List<TagResponseDto> tags
                = tagService.getTheMostWidelyUsedTagOfUserWithTheHighestCostOfAllOrders();
        return new BaseResponse(
                HttpStatus.OK.value(),
                "The most widely used tag of user with the highest cost of all orders",
                tags);
    }

}
