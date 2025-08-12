package mylab.bank.control;

import mylab.bank.entity.Account;
import mylab.bank.entity.Bank;
import mylab.bank.entity.SavingsAccount;
import mylab.bank.exception.AccountNotFoundException;
import mylab.bank.exception.InsufficientBalanceException;
import mylab.bank.exception.WithdrawalLimitExceededException;

public class BankDemo {
    public static void main(String[] args) {
        Bank bank = new Bank();

        System.out.println("=== 계좌 생성 ===");
        System.out.println(bank.createSavingsAccount("홍길동", 10000, 3.0));
        System.out.println(bank.createCheckingAccount("김철수", 20000, 5000));
        System.out.println(bank.createSavingsAccount("이영희", 30000, 2.0));
        System.out.println();

        System.out.println("=== 모든 계좌 목록 ===");
        bank.printAllAccounts();
        System.out.println("===================\n");

        try {
            System.out.println("=== 입금/출금 테스트 ===");
            bank.deposit("AC1000", 5000);
            bank.withdraw("AC1001", 3000);
            System.out.println();

            System.out.println("=== 이자 적용 테스트 ===");
            Account foundAccount = bank.findAccount("AC1000");
            if (foundAccount instanceof SavingsAccount) {
                ((SavingsAccount) foundAccount).applyInterest();
            }
            System.out.println();


            System.out.println("=== 계좌 이체 테스트 ===");
            bank.transfer("AC1002", "AC1001", 5000.0);
            System.out.println();

        } catch (AccountNotFoundException | InsufficientBalanceException e) {
            System.out.println("예외 발생: " + e.getMessage());
        }


        System.out.println("=== 모든 계좌 목록 ===");
        bank.printAllAccounts();
        System.out.println("===================");

        try {
            bank.withdraw("AC1001", 6000.0);
        } catch (AccountNotFoundException | InsufficientBalanceException e) {
            System.out.println("예외 발생: " + e.getMessage());
        }

        try {
            bank.withdraw("AC1001", 5001.0);
        } catch (WithdrawalLimitExceededException e) {
            System.out.println("예외 발생: " + e.getMessage());
        } catch (AccountNotFoundException | InsufficientBalanceException e) {
            System.out.println("처리 중 다른 예외 발생: " + e.getMessage());
        }

        try {
            bank.findAccount("AC9999");
        } catch (AccountNotFoundException e) {
            System.out.println("예외 발생: " + e.getMessage());
        }
    }
}