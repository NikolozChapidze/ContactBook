package ge.gov.dga.contactbook.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorObject {
    private int status;

    private String message;

    private LocalDateTime timestamp;

    private Map<String, String> details;
}
