package fpt.he190091.assignment01.controller;

import fpt.he190091.assignment01.dtos.CreateAccountRequest;
import fpt.he190091.assignment01.dtos.LoginRequest;
import fpt.he190091.assignment01.dtos.SystemAccountDTO;
import fpt.he190091.assignment01.dtos.UpdateAccountRequest;
import fpt.he190091.assignment01.entity.SystemAccount;
import fpt.he190091.assignment01.service.SystemAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/accounts")
@RequiredArgsConstructor
public class SystemAccountController {
    private final SystemAccountService systemAccountService;

    @GetMapping
    public List<SystemAccountDTO> getAllSystemAccount() {
        return systemAccountService.getAllAccount();
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return systemAccountService.findByAccountID(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{accountName}")
    public ResponseEntity<?> getById(@PathVariable String name) {
        return systemAccountService.findByAccountName(name)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping()
    public ResponseEntity<?> createAccount(@RequestBody CreateAccountRequest request) {
        return ResponseEntity.ok(systemAccountService.createSystemAccount(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAccount(
            @PathVariable Long id,
            @RequestBody UpdateAccountRequest account) {
        return ResponseEntity.ok(systemAccountService.updateSystemAccount(id, account));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAccount(@PathVariable Long id) {
        systemAccountService.deleteSystemAccount(id);
        return ResponseEntity.ok("Deleted");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        return systemAccountService.login(request)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(401).body(null));
    }
}
