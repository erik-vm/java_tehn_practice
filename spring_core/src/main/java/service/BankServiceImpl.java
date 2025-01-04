package service;

import org.springframework.stereotype.Service;

@Service
public class BankServiceImpl implements BankService {

    @Override
    public void deposit(Integer amount, String toAccount) {
        System.out.println("depositing ...");
    }

    @Override
    public void withdraw(Integer amount, String fromAccount) {
        System.out.println("withdrawing ...");
    }

}
