package esm.controller;

import esm.dto.BaseResponse;
import esm.dto.request.CertificateRequestDto;
import esm.dto.response.CertificateResponseDto;
import esm.service.certificate.CertificateService;
import esm.util.PageRequest;
import esm.util.Sort;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/certificates")
public class CertificateController {
    private final CertificateService certificateService;
    private final ModelMapper modelMapper;

    public CertificateController(CertificateService certificateService, ModelMapper modelMapper) {
        this.certificateService = certificateService;
        this.modelMapper = modelMapper;
    }

    /**
     * Creates a certificate by getting request dto object with tags may be included.
     * Tags get connected to the certificate with many to many relation and get created
     * if it doesn't exist
     * @param createCertificate DTO object which has inclusion of list of tags
     * @return Response entity typed response DTO which has inclusion of list of tags and time setting function
     */
    @PostMapping()
    public ResponseEntity<?> create(@RequestBody CertificateRequestDto createCertificate) {
        CertificateResponseDto responseDto = certificateService.create(createCertificate);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    /**
     * Gets the certificate as per the UUID provided
     * @param certificateId return the certificate
     * @return returns the certificate with tags connected
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> get(
            @PathVariable("id") UUID certificateId
    ) {
        CertificateResponseDto responseDto = certificateService.get(certificateId);
        return ResponseEntity.ok(responseDto);
    }

    /**
     * Return the list of Certificate as per the given searchWord(certificate name) or the tag name.
     * The result is returned as many as mentioned in pagination parameters with the inclusion of
     * the next page link.
     * @param searchWord Certificate name which gets searched with like operator.
     * @param tagName Name of the tag to be searched with exact matches (where operator).
     * @param size Size to return the amount of certificates in each page.
     * @param page The page number to start the page result from.
     * @param sort Sort type to be searched. The type has SortType and SortField object
     * @return returns BaseResponse typed list of certificates along with page link for the next and previous pages
     */
    @GetMapping()
    public BaseResponse<List<CertificateResponseDto>> getAll(
            @RequestParam(required = false, name = "search") String searchWord,
            @RequestParam(required = false, name = "tag_name") String tagName,
            @RequestParam(required = false, defaultValue = "20") int size,
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false) String sort
    ) {
        List<CertificateResponseDto> certificates
                = certificateService.get(searchWord, tagName, new PageRequest(page, size, new Sort(sort).validate()));

        BaseResponse<List<CertificateResponseDto>> response = new BaseResponse<>(
                HttpStatus.OK.value(),
                "certificate list",
                certificates
        );
        if (!certificates.isEmpty())
            response.add(linkTo(methodOn(CertificateController.class)
                    .getAll(searchWord, tagName, size, page + 1, sort))
                    .withRel("next page"));

        if (page > 1) {
            response.add(linkTo(methodOn(CertificateController.class)
                    .getAll(searchWord, tagName, size, page - 1, sort))
                    .withRel("previous page"));
        }
        return response;
    }

    /**
     * Finds all the Tags of the certificates by given tag name or list of tag names with inclusion of
     * the next and previous page links
     * @param tags one tag name or list of tag names to be searched
     * @param size Size to return the amount of Certificates Tags in each page.
     * @param page The page number to start the page result from.
     * @return return the BaseResponse typed list of certificates with tags connected as well as
     * the next and previous page links
     */
    @GetMapping("/tags")
    public BaseResponse<List<CertificateResponseDto>> getWithMultipleTags(
            @RequestParam(value = "name") List<String> tags,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "1") int page) {

        List<CertificateResponseDto> certificates
                = certificateService.getByMultipleTags(tags, new PageRequest(page, size));

        BaseResponse<List<CertificateResponseDto>> response = new BaseResponse<>(
                HttpStatus.OK.value(), "certificate list", certificates);

        if (!certificates.isEmpty())
            response.add(linkTo(methodOn(CertificateController.class)
                    .getWithMultipleTags(tags, size, page + 1))
                    .withRel("next page"));

        if (page > 1) {
            response.add(linkTo(methodOn(CertificateController.class)
                    .getWithMultipleTags(tags, size, page - 1))
                    .withRel("previous page"));
        }
        return response;
    }

    /**
     * Find the certificate by given UUID and updates it by provided data.
     * The rest of the data remains unchanged
     * @param certificateId UUID of certificate to find the certificate
     * @param requestDto requestDTO typed certificate object to change the data of the certificate
     * @return returns Response entity typed certificate response DTO object
     */
    @PatchMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable("id") UUID certificateId,
            @RequestBody CertificateRequestDto requestDto
    ) {
        CertificateResponseDto responseDto = certificateService.update(requestDto, certificateId);
        return ResponseEntity.ok(responseDto);
    }

    /**
     * Deletes the certificate by given UUID
     * @param id UUID to search for the certificate for deletion
     * @return returns Response entity typed status message
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(
            @PathVariable UUID id) {
        int delete = certificateService.delete(id);
        if (delete == 1) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}
