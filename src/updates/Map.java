package updates;

public class Map extends Update {
    String jsonMessage;

    public Map(String jsonMessage) {
        this.jsonMessage = jsonMessage;
    }

    public String getJsonMessage() {
        return jsonMessage;
    }
}
