package fpt.he190091.assignment01.controller;

import fpt.he190091.assignment01.entity.SystemAccount;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api")
public class HomeController {

    @GetMapping("/auth/check")
    public ResponseEntity<?> checkAuth(HttpSession session) {
        SystemAccount user = (SystemAccount) session.getAttribute("user");
        if (user != null) {
            Map<String, Object> response = new HashMap<>();
            response.put("authenticated", true);
            response.put("accountId", user.getAccountID());
            response.put("name", user.getAccountName()); // Match your entity field
            response.put("email", user.getAccountEmail());
            response.put("role", user.getAccountRole());
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(401)
                .body(Map.of("authenticated", false));
    }

    @GetMapping("/user")
    public ResponseEntity<?> getCurrentUser(HttpSession session) {
        SystemAccount user = (SystemAccount) session.getAttribute("user");
        if (user != null) {
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.status(401).body("Not authenticated");
    }
}
