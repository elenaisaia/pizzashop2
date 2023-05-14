package pizzashop;

public class PizzaException extends Exception{
    public PizzaException(String message) {
        super(message);
    }
    public PizzaException(String message, Throwable cause) {
        super(message, cause);
    }
    public PizzaException(Throwable cause) {
        super(cause);
    }
    public PizzaException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
