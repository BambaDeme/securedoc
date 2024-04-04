package deme.ahmadou.securedoc.exceptions;

public class ApiException extends RuntimeException{

    public ApiException(String message){
        super(message);
    }

    public ApiException(){
        super("An error occurred");
    }
}
