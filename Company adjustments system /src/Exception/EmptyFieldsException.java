package Exception;

public class EmptyFieldsException extends Exception{
	
	public EmptyFieldsException(String msg) {
		super(msg);
	}
	
	public EmptyFieldsException() {
		super("One or more of the fileds are empty !");
	}
}
