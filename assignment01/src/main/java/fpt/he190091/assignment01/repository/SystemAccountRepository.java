package fpt.he190091.assignment01.repository;

import fpt.he190091.assignment01.entity.SystemAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SystemAccountRepository extends JpaRepository<SystemAccount, Long> {

    Optional<SystemAccount> findByAccountName(String accountName);

    boolean existsByAccountEmail(String accountEmail);

    Optional<SystemAccount> findByAccountEmail(String accountEmail);
}
