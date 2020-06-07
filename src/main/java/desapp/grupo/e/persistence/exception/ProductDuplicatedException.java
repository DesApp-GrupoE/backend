package desapp.grupo.e.persistence.exception;

public class ProductDuplicatedException extends RuntimeException {

    public ProductDuplicatedException(String msg) {
        super(msg);
    }
}