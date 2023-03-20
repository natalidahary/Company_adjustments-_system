package Exception;

public class TimesException extends Exception {
	public TimesException(String msg) {
		super(msg);
	}
	
	public TimesException() {
		super("Start time cannot be greater than finish time");
	}
}
