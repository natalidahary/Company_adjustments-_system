package Exception;

import java.io.Serializable; 

public class ElementExistsException extends Exception {
		
		public ElementExistsException(String msg) {
			super(msg);
		}
		
		public ElementExistsException() {
			super("Element already exists !");
		}

}
