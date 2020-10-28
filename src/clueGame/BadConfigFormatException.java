package clueGame;


// An exception to indicate data files have bad format
@SuppressWarnings("serial")
public class BadConfigFormatException extends Exception{

	public BadConfigFormatException() {
		super("Error, bad configurationin in format of files");
	}
	
	public BadConfigFormatException(String message) {
		super(message);
	}

}