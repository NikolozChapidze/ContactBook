package ge.gov.dga.contactbook.service;

import ge.gov.dga.contactbook.exception.ResourceNotFoundException;
import ge.gov.dga.contactbook.model.dto.ContactRes;
import ge.gov.dga.contactbook.model.entity.Contact;
import ge.gov.dga.contactbook.model.entity.User;
import ge.gov.dga.contactbook.repository.ContactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class ContactService {

    private ContactRepository contactRepository;
    public ContactRes findById(Long id) {
//        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        return contactRepository.findById(id).map(this::contactToContactDto)
                .orElseThrow(() -> new ResourceNotFoundException("contact", id));
//        return contactRepository.findByIdAndUserId(id, user.getId()).map(this::contactToContactDto)
//                .orElseThrow(() -> new ResourceNotFoundException("contact", id));
    }

    private ContactRes contactToContactDto(Contact contact){
        return new ContactRes(contact.getPerson(), contact.getNumber(), contact.getEmail(), contact.getAddress());
    }
}
