package ge.gov.dga.contactbook.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ContactRes {
    private String person;

    private String number;

    private String email;

    private String address;
}
