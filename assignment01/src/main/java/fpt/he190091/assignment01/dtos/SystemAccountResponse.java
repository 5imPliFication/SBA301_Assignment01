package fpt.he190091.assignment01.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class SystemAccountResponse {
    private Long id;
    private String email;
    private String name;
    private Integer role;
}

