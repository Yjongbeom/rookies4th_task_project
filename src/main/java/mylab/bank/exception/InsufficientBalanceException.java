package mylab.bank.exception;

public class InsufficientBalanceException extends Exception{
	public InsufficientBalanceException(String errMessage) {
		super(errMessage);
	}
}
