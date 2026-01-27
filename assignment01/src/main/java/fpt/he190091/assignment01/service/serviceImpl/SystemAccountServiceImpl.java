package fpt.he190091.assignment01.service.serviceImpl;

import fpt.he190091.assignment01.config.PasswordUtil;
import fpt.he190091.assignment01.dtos.CreateAccountRequest;
import fpt.he190091.assignment01.dtos.LoginRequest;
import fpt.he190091.assignment01.dtos.SystemAccountDTO;
import fpt.he190091.assignment01.dtos.UpdateAccountRequest;
import fpt.he190091.assignment01.entity.SystemAccount;
import fpt.he190091.assignment01.mapper.SystemAccountMapper;
import fpt.he190091.assignment01.repository.SystemAccountRepository;
import fpt.he190091.assignment01.service.SystemAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SystemAccountServiceImpl implements SystemAccountService {

    private final SystemAccountRepository systemAccountRepository;

    @Override
    public List<SystemAccountDTO> getAllAccount() {
        return systemAccountRepository.findAll()
                .stream()
                .map(SystemAccountMapper::toDTO)
                .toList();
    }

    @Override
    public Optional<SystemAccountDTO> findByAccountID(Long accountID) {
        return systemAccountRepository.findById(accountID)
                .map(SystemAccountMapper::toDTO);
    }

    @Override
    public Optional<SystemAccountDTO> findByAccountName(String accountName) {
        return systemAccountRepository.findByAccountName(accountName)
                .map(SystemAccountMapper::toDTO);
    }

    @Override
    public SystemAccountDTO createSystemAccount(CreateAccountRequest systemAccount) {
        if (systemAccountRepository.existsByAccountEmail(systemAccount.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        SystemAccount entity = new SystemAccount();
        entity.setAccountName(systemAccount.getName());
        entity.setAccountEmail(systemAccount.getEmail());
        entity.setAccountRole(systemAccount.getRole());
        entity.setAccountPassword(
                PasswordUtil.hashPassword(systemAccount.getPassword())
        );

        return SystemAccountMapper.toDTO(systemAccountRepository.save(entity));
    }

    @Override
    public SystemAccountDTO updateSystemAccount(Long id, UpdateAccountRequest updatedAccount) {
        SystemAccount existing = systemAccountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        existing.setAccountName(updatedAccount.getName());
        existing.setAccountRole(updatedAccount.getRole());
        if (updatedAccount.getName() != null && !updatedAccount.getName().isEmpty()) {
            existing.setAccountName(updatedAccount.getName());
        }
        if (updatedAccount.getRole() != null) {
            existing.setAccountRole(updatedAccount.getRole());
        }

        // Email change check
        if (!existing.getAccountEmail().equals(updatedAccount.getEmail())) {
            if (systemAccountRepository.existsByAccountEmail(updatedAccount.getEmail())) {
                throw new RuntimeException("Email already exists");
            }
            existing.setAccountEmail(updatedAccount.getEmail());
        }

        // Update password only if provided
        if (updatedAccount.getPassword() != null &&
                !updatedAccount.getPassword().isBlank()) {

            existing.setAccountPassword(
                    PasswordUtil.hashPassword(updatedAccount.getPassword())
            );
        }

        return SystemAccountMapper.toDTO(systemAccountRepository.save(existing));
    }

    @Override
    public void deleteSystemAccount(Long accountID) {
        systemAccountRepository.deleteById(accountID);
    }

    @Override
    public Optional<SystemAccountDTO> login(LoginRequest request) {

        return systemAccountRepository.findByAccountEmail(request.getEmail())
                .filter(acc -> PasswordUtil.checkPassword(
                        request.getPassword(),
                        acc.getAccountPassword()
                ))
                .map(SystemAccountMapper::toDTO);
    }
}
