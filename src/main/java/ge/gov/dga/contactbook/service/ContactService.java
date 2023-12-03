package ge.gov.dga.contactbook.service;

import ge.gov.dga.contactbook.exception.ResourceNotFoundException;
import ge.gov.dga.contactbook.model.dto.ContactDto;
import ge.gov.dga.contactbook.model.entity.Contact;
import ge.gov.dga.contactbook.model.entity.User;
import ge.gov.dga.contactbook.repository.ContactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContactService {
    private final ContactRepository contactRepository;

    @Transactional(readOnly = true)
    public ContactDto findById(Long id, Principal connectedUser) {
        var user = getConnectedUser(connectedUser);
        return contactRepository.findByIdAndUserId(id, user.getId())
                .map(this::contactToContactDto)
                .orElseThrow(() -> new ResourceNotFoundException("contact", id));
    }

    @Transactional(readOnly = true)
    public Page<ContactDto> getPage(Pageable pageable, Principal connectedUser) {
        var user = getConnectedUser(connectedUser);
        return contactRepository.findAllByUser_Id(user.getId(), pageable)
                .map(this::contactToContactDto);
    }

    @Transactional
    public Contact update(Long id, Contact updated, Principal connectedUser) {
        var user = getConnectedUser(connectedUser);
        Contact contact = contactRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("contact", id));
        populateNotNulls(contact, updated.getEmail(), updated.getPerson(),
                updated.getNumber(), updated.getAddress());
        return contactRepository.save(contact);
    }

    @Transactional
    public Contact create(ContactDto contactDto, Principal connectedUser) {
        var user = getConnectedUser(connectedUser);
        Contact contact = new Contact();
        populateNotNulls(contact, contactDto.getEmail(), contactDto.getPerson(),
                contactDto.getNumber(), contactDto.getAddress());
        contact.setUser(user);
        return contactRepository.save(contact);
    }

    @Transactional(readOnly = true)
    public List<ContactDto> getContactsWithKeyWord(String keyword, Principal connectedUser) {
        var user = getConnectedUser(connectedUser);
        return contactRepository.findByUserIdAndKeyword(user.getId(), keyword).stream()
                .map(this::contactToContactDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long id, Principal connectedUser) {
        var user = getConnectedUser(connectedUser);
        contactRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("contact", id));
        contactRepository.deleteById(id);
    }

    private void populateNotNulls(Contact contact, String email, String person, String number, String address) {
        if (email != null) contact.setEmail(email);
        if (person != null) contact.setPerson(person);
        if (number != null) contact.setNumber(number);
        if (address != null) contact.setAddress(address);
    }

    private ContactDto contactToContactDto(Contact contact) {
        return new ContactDto(contact.getPerson(), contact.getNumber(), contact.getEmail(), contact.getAddress());
    }

    private User getConnectedUser(Principal connectedUser) {
        return (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
    }
}
