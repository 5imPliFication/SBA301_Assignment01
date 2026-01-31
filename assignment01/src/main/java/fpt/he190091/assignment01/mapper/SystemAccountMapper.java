package fpt.he190091.assignment01.mapper;

import fpt.he190091.assignment01.dtos.SystemAccountResponse;
import fpt.he190091.assignment01.entity.SystemAccount;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SystemAccountMapper {
    public SystemAccountResponse toResponse(SystemAccount acc) {
        if (acc == null) return null;

        SystemAccountResponse res = new SystemAccountResponse();
        res.setAccountId(acc.getAccountID());
        res.setAccountEmail(acc.getAccountEmail());
        res.setAccountName(acc.getAccountName());
        res.setAccountRole(acc.getAccountRole());

        return res;
    }

    public List<SystemAccountResponse> toResponseList(List<SystemAccount> accs) {
        return accs.stream().map(this::toResponse).toList();
    }
}
