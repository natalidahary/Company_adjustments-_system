package Exception;

public class DigitsException extends Exception{
	public DigitsException(String msg) {
		super(msg);
	}
	
	public DigitsException() {
		super("Some fields has to contain digits only");
	}
}
