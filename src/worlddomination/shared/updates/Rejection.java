package worlddomination.shared.updates;

@SuppressWarnings("serial")
public class Rejection extends Update {

	private String errorMessage;
	
	public Rejection() {
		super();
	}
	
	public Rejection(String errorMessage) {
		super();
		this.errorMessage = errorMessage;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}
}
