package service;

public interface BankService {

    void deposit(Integer amount, String toAccount);

    void withdraw(Integer amount, String fromAccount);

}