package br.com.braccomercecategory.service.exceptions;

public class ObjectNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 6594474834347415674L;

    public ObjectNotFoundException(String msg) {
	super(msg);
    }

}
