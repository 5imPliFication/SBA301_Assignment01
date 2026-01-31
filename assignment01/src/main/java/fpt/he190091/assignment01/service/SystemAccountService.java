package fpt.he190091.assignment01.service;

import fpt.he190091.assignment01.dtos.CreateAccountRequest;
import fpt.he190091.assignment01.dtos.LoginRequest;
import fpt.he190091.assignment01.dtos.SystemAccountResponse;
import fpt.he190091.assignment01.dtos.UpdateAccountRequest;
import fpt.he190091.assignment01.entity.SystemAccount;
import org.springframework.data.domain.Limit;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface SystemAccountService {
    List<SystemAccount> getAllAccount();

    SystemAccount findByAccountID(Long accountID);

    SystemAccount findByAccountName(String accountName, Limit lim);

    SystemAccount createSystemAccount(CreateAccountRequest systemAccount);
    SystemAccount updateSystemAccount(Long id, UpdateAccountRequest updatedAccount);
    void deleteSystemAccount(Long accountID);

    Optional<SystemAccount> login(LoginRequest loginRequest);
}
