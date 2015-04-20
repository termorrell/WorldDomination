package worlddomination.shared.updates;

@SuppressWarnings("serial")
public class ErrorMessage extends Update {

    String errorMessage;

    public ErrorMessage(String errorMessage){
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
