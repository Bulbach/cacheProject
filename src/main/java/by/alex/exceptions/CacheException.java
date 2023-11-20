package by.alex.exceptions;

public class CacheException extends RuntimeException {

    public CacheException() {};

    public CacheException(String s) {
        super(s);
    }

    public CacheException(String message, Throwable cause) {
        super(message, cause);
    }
}
