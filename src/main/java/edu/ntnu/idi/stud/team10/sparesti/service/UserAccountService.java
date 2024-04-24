package edu.ntnu.idi.stud.team10.sparesti.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.ntnu.idi.stud.team10.sparesti.dto.AccountDto;
import edu.ntnu.idi.stud.team10.sparesti.mapper.AccountMapper;
import edu.ntnu.idi.stud.team10.sparesti.model.User;
import edu.ntnu.idi.stud.team10.sparesti.repository.UserRepository;
import edu.ntnu.idi.stud.team10.sparesti.repository.bank.AccountRepository;
import edu.ntnu.idi.stud.team10.sparesti.util.NotFoundException;

/** Service for User-Account interaction, connects users and bank accounts */
@Service
public class UserAccountService {
  private final UserRepository userRepository;

  @Autowired
  public UserAccountService(
      UserRepository userRepository,
      BankService bankService,
      AccountRepository accountRepository,
      AccountMapper accountMapper) {
    this.userRepository = userRepository;
  }

  /**
   * Sets a user's savings account to a created bank account that has an ownerId.
   *
   * @param accountDto DTO representing the account.
   * @param isSavings boolean declaring whether it is a savings account (true) or checking account
   *     (false)
   */
  @Transactional
  public void setUserAccount(AccountDto accountDto, boolean isSavings) {
    User user = findUserById(accountDto.getOwnerId());
    if (isSavings) {
      user.setSavingsAccountNr(accountDto.getAccountNr());
    } else {
      user.setCheckingAccountNr(accountDto.getAccountNr());
    }
    userRepository.save(user);
  }

  /**
   * Retrieves a user entity using its id.
   *
   * @param id (Long): the id of the user
   * @return the User entity, if found.
   */
  private User findUserById(Long id) {
    return userRepository
        .findById(id)
        .orElseThrow(() -> new NotFoundException("User with id " + id + " not found"));
  }
}
