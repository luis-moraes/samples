package br.com.luisca.sample.exception;

public class ApiException extends Exception  {

	private static final long serialVersionUID = 1345L;

	public ApiException(int status, String message) {
		super(message);
	}
}
