package fpt.he190091.assignment01.service;

import fpt.he190091.assignment01.dtos.CreateAccountRequest;
import fpt.he190091.assignment01.dtos.LoginRequest;
import fpt.he190091.assignment01.dtos.SystemAccountDTO;
import fpt.he190091.assignment01.dtos.UpdateAccountRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface SystemAccountService {
    List<SystemAccountDTO> getAllAccount();

    Optional<SystemAccountDTO> findByAccountID(Long accountID);

    Optional<SystemAccountDTO> findByAccountName(String accountName);

    SystemAccountDTO createSystemAccount(CreateAccountRequest systemAccount);
    SystemAccountDTO updateSystemAccount(Long id, UpdateAccountRequest updatedAccount);
    void deleteSystemAccount(Long accountID);

    Optional<SystemAccountDTO> login(LoginRequest loginRequest);
}
