package errorhandling;

public class ExceptionDTO {

    public ExceptionDTO(int code, String description) {
        this.code = code;
        this.message = description;
    }
    private final int code;
    private final String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
