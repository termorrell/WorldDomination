package updates;

public class Rejection extends Update {
	String errorMessage;

	public Rejection(String errorMessage) {
		super();
		this.errorMessage = errorMessage;
	}

	public String getErrorMessage() {
		return errorMessage;
	}
	
}
