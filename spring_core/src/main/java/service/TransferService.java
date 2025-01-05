package service;

import lombok.Getter;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE )
public class TransferService {

    @Getter
    private BankService bankService;

    public TransferService(BankService bankService) {
        this.bankService = bankService;
    }

    public void transfer(Integer amount, String fromAccount, String toAccount) {
        bankService.withdraw(amount, fromAccount);

        bankService.deposit(amount, toAccount);
    }
}