package br.com.braccomercecategory.service.exceptions;

public class DataIntegrityException extends RuntimeException {

    private static final long serialVersionUID = -7426884705679134922L;

    public DataIntegrityException(String msg, Throwable cause) {
	super(msg, cause);
    }

}
