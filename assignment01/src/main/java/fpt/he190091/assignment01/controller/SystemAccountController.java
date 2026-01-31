package fpt.he190091.assignment01.controller;

import fpt.he190091.assignment01.dtos.CreateAccountRequest;
import fpt.he190091.assignment01.dtos.LoginRequest;
import fpt.he190091.assignment01.dtos.SystemAccountResponse;
import fpt.he190091.assignment01.dtos.UpdateAccountRequest;
import fpt.he190091.assignment01.mapper.SystemAccountMapper;
import fpt.he190091.assignment01.service.SystemAccountService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Limit;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/accounts")
@RequiredArgsConstructor
public class SystemAccountController {
    private final SystemAccountService systemAccountService;
    private final SystemAccountMapper mapper;

    @GetMapping
    public List<SystemAccountResponse> getAllSystemAccount() {
        return mapper.toResponseList(systemAccountService.getAllAccount());
    }

    @GetMapping("/{accountId}")
    public SystemAccountResponse getById(@PathVariable Long accountId) {
        return mapper.toResponse(systemAccountService.findByAccountID(accountId));
    }

    @GetMapping("/by-name/{accountName}")
    public SystemAccountResponse getById(@PathVariable String accountName) {
        return mapper.toResponse(systemAccountService.findByAccountName(accountName, Limit.of(10)));
    }

    @PostMapping
    public SystemAccountResponse createAccount(@RequestBody CreateAccountRequest request) {
        return mapper.toResponse(systemAccountService.createSystemAccount(request));
    }

    @PutMapping("/{id}")
    public SystemAccountResponse updateAccount(
            @PathVariable Long id,
            @RequestBody UpdateAccountRequest account) {
        return mapper.toResponse(systemAccountService.updateSystemAccount(id, account));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAccount(@PathVariable Long id) {
        systemAccountService.deleteSystemAccount(id);
        return ResponseEntity.ok("Deleted");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req,
                                   HttpSession session) {

        return systemAccountService.login(req)
                .map(acc -> {
                    session.setAttribute("user", acc);
                    return ResponseEntity.ok("Login success");
                })
                .orElse(ResponseEntity.status(401).body("Invalid credentials"));
    }
}
