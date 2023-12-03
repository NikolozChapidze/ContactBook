package ge.gov.dga.contactbook.controller;

import ge.gov.dga.contactbook.model.ResponseWrapper;
import ge.gov.dga.contactbook.model.dto.ContactDto;
import ge.gov.dga.contactbook.model.entity.Contact;
import ge.gov.dga.contactbook.service.ContactService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/contact")
@Tag(name = "Contact")
public class ContactController {
    private final ContactService contactService;

    @GetMapping("/{id}")
    @Operation(description = "Get Contact by id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Successfully retrieved contact",
            content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found"),
            @ApiResponse(responseCode = "500", description = "Server error")})
    @Cacheable(value = "contact", key = "#id")
    public ResponseEntity<ResponseWrapper<ContactDto>> getContact(@PathVariable Long id, Principal connectedUser) {
        ContactDto contact = contactService.findById(id, connectedUser);
        return ResponseEntity.ok(new ResponseWrapper<>("Successfully retrieved contact", contact));
    }

    @GetMapping
    @Operation(description = "Get paginated and sorted contacts")
    public ResponseEntity<Page<ContactDto>> getCustomers(Pageable pageable, Principal connectedUser) {
        return ResponseEntity.ok(contactService.getPage(pageable, connectedUser));
    }

    @PutMapping("/{id}")
    @Operation(description = "Update contact")
    @CachePut(value = "contact", key = "#id")
    public ResponseEntity<ResponseWrapper<Contact>> update(@PathVariable Long id, @RequestBody @Valid Contact updated, Principal connectedUser) {
        Contact contact = contactService.update(id, updated, connectedUser);
        return ResponseEntity.ok(new ResponseWrapper<>("Contact updated successfully", contact));
    }

    @PostMapping
    @Operation(description = "Create new contact")
    public ResponseEntity<ResponseWrapper<Contact>> create(@RequestBody @Valid ContactDto created, Principal connectedUser) {
        Contact contact = contactService.create(created, connectedUser);
        return new ResponseEntity<>(new ResponseWrapper<>("Contact created successfully", contact),
                HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Delete contact")
    @CacheEvict(value = "contact", key = "#id")
    public ResponseEntity<ResponseWrapper<String>> delete(@PathVariable Long id, Principal connectedUser) {
        contactService.delete(id, connectedUser);
        return ResponseEntity.ok(new ResponseWrapper<>("Contact deleted successfully", null));
    }

    @GetMapping("/search")
    @Operation(description = "Get Contact by keyword")
    public ResponseEntity<ResponseWrapper<List<ContactDto>>> getContactByKeyword(@RequestParam String keyword, Principal connectedUser) {
        List<ContactDto> contacts = contactService.getContactsWithKeyWord(keyword, connectedUser);
        return ResponseEntity.ok(new ResponseWrapper<>("Successfully retrieved contacts", contacts));
    }
}
