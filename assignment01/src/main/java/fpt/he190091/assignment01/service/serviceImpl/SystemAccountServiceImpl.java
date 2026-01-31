package fpt.he190091.assignment01.service.serviceImpl;

import fpt.he190091.assignment01.dtos.CreateAccountRequest;
import fpt.he190091.assignment01.dtos.LoginRequest;
import fpt.he190091.assignment01.dtos.UpdateAccountRequest;
import fpt.he190091.assignment01.entity.SystemAccount;
import fpt.he190091.assignment01.repository.SystemAccountRepository;
import fpt.he190091.assignment01.service.SystemAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Limit;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class SystemAccountServiceImpl implements SystemAccountService {

    private final SystemAccountRepository accountRepo;

    @Override
    public List<SystemAccount> getAllAccount() {
        return accountRepo.findAll();
    }

    @Override
    public SystemAccount findByAccountID(Long accountID) {
        return accountRepo.findById(accountID).orElse(null);
    }

    @Override
    public SystemAccount findByAccountName(String accountName, Limit limit) {
        return accountRepo.findSystemAccountByAccountNameContaining(accountName, limit).orElse(null);
    }

    @Override
    public SystemAccount createSystemAccount(CreateAccountRequest dto) {
        SystemAccount acc = new SystemAccount();
        acc.setAccountName(dto.getName());
        acc.setAccountEmail(dto.getEmail());
        acc.setAccountPassword(dto.getPassword());
        acc.setAccountRole(dto.getRole());
        return accountRepo.save(acc);
    }

    @Override
    public SystemAccount updateSystemAccount(Long id, UpdateAccountRequest dto) {
        SystemAccount acc = accountRepo.findById(id).orElse(null);
        if (acc == null) return null;

        if (dto.getName() != null)
            acc.setAccountName(dto.getName());

        if (dto.getEmail() != null)
            acc.setAccountEmail(dto.getEmail());

        if (dto.getPassword() != null)
            acc.setAccountPassword(dto.getPassword());

        if (dto.getRole() != null)
            acc.setAccountRole(dto.getRole());

        return accountRepo.save(acc);
    }

    @Override
    public void deleteSystemAccount(Long accountID) {
        accountRepo.deleteById(accountID);
    }

    @Override
    public Optional<SystemAccount> login(LoginRequest loginRequest) {
        Optional<SystemAccount> opt =
                accountRepo.findByAccountEmail(loginRequest.getEmail());

        if (opt.isEmpty()) {
            System.err.println(loginRequest.getEmail() + " not found");
            return Optional.empty();
        }
        SystemAccount acc = opt.get();

        if (!acc.getAccountPassword().equals(loginRequest.getPassword())) {
            System.err.println("Wrong Password");
            return Optional.empty();
        }
        return Optional.of(acc);
    }
}
