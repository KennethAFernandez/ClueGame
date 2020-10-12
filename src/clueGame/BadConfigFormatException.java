package clueGame;


// An exception to indicate data files have bad format
@SuppressWarnings("serial")
public class BadConfigFormatException extends Exception{

	public BadConfigFormatException() {
		super("Error");
	}
	
	public BadConfigFormatException(String message) {
		super(message);
	}

}