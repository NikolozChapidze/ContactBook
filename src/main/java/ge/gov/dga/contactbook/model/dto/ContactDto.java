package ge.gov.dga.contactbook.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ContactDto {
    @Pattern(regexp = "^[\\p{L} .'-]+$", message = "Invalid characters in name")
    @Size(min = 5, max = 100)
    private String person;

    @Pattern(regexp = "^[+]?[0-9]{9,13}$", message = "Invalid phone number format")
    private String number;

    @Email(message = "Invalid email format")
    @Size(max = 50)
    private String email;

    @Size(min = 5, max = 100)
    private String address;
}
