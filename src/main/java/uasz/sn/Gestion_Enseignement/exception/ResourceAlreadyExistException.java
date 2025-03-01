package uasz.sn.Gestion_Enseignement.exception;

public class ResourceAlreadyExistException extends RuntimeException {
    public ResourceAlreadyExistException(String message){
        super(message);
    }
}
