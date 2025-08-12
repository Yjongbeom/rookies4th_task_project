package mylab.bank.exception;

public class WithdrawalLimitExceededException extends InsufficientBalanceException {
	public WithdrawalLimitExceededException(String message) {
		super(message);
	}
}
