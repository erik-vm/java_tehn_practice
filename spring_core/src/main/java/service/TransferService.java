package service;

import lombok.Getter;

public class TransferService {

    @Getter
    private BankService bankService;

    public void transfer(Integer amount, String fromAccount, String toAccount) {
        bankService.withdraw(amount, fromAccount);

        bankService.deposit(amount, toAccount);
    }
}