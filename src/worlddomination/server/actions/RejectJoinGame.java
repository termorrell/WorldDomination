package worlddomination.server.actions;

public class RejectJoinGame extends Action {
    String errorMessage;

    public RejectJoinGame(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
