package edu.ntnu.idi.stud.team10.sparesti.repository.bank;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import edu.ntnu.idi.stud.team10.sparesti.model.Account;
import jakarta.persistence.LockModeType;

/** Repository for Account entities. */
public interface AccountRepository extends JpaRepository<Account, Long> {
  Optional<Account> findByAccountNr(int accountNr);

  Set<Account> findAllByOwnerId(Long ownerId);

  @Query("SELECT a FROM Account a WHERE a.accountNr = :accountNr")
  @Lock(LockModeType.PESSIMISTIC_WRITE)
  Optional<Account> findByAccountNrWithLock(int accountNr);
}
