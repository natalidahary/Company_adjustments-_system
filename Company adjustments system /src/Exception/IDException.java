package Exception;

import java.io.Serializable;

public class IDException extends Exception{
	
	public IDException(String message) {
		super(message);
	}

	public IDException() {
		super("ID has to contain 9 digits");
	}

}
