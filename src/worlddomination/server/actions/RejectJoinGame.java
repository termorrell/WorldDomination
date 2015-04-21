package worlddomination.server.actions;

public class RejectJoinGame extends Action{
    String errorMessage;

    /**
     * Stores the attributes necessary for sending and receiving an 'accept_join_game' command
     * @param errorMessage message explaining why rejected
     */
    public RejectJoinGame(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
