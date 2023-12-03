package ge.gov.dga.contactbook.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseWrapper<T> {
    private String message;
    private T data;
    private boolean success;

    public ResponseWrapper(String message, T data, boolean success) {
        this.message = message;
        this.data = data;
        this.success = success;
    }

    public ResponseWrapper(String message, T data) {
        this(message, data, true);
    }

}
