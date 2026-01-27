package fpt.he190091.assignment01.mapper;

import fpt.he190091.assignment01.dtos.SystemAccountDTO;
import fpt.he190091.assignment01.entity.SystemAccount;

public class SystemAccountMapper {

    public static SystemAccountDTO toDTO(SystemAccount entity) {
        SystemAccountDTO dto = new SystemAccountDTO();
        dto.setId(entity.getAccountID());
        dto.setName(entity.getAccountName());
        dto.setEmail(entity.getAccountEmail());
        dto.setRole(entity.getAccountRole());
        return dto;
    }
}

