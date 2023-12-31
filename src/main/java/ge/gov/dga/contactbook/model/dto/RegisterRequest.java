package ge.gov.dga.contactbook.model.dto;

import ge.gov.dga.contactbook.model.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
  private String username;
  private String password;
  private String phone;
  private Role role;
}
