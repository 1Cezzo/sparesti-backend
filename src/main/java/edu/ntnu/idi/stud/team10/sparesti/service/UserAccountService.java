package edu.ntnu.idi.stud.team10.sparesti.service;

import edu.ntnu.idi.stud.team10.sparesti.dto.AccountDto;
import edu.ntnu.idi.stud.team10.sparesti.dto.UserDto;
import edu.ntnu.idi.stud.team10.sparesti.mapper.AccountMapper;
import edu.ntnu.idi.stud.team10.sparesti.model.Account;
import edu.ntnu.idi.stud.team10.sparesti.model.User;
import edu.ntnu.idi.stud.team10.sparesti.repository.UserRepository;
import edu.ntnu.idi.stud.team10.sparesti.repository.bank.AccountRepository;
import edu.ntnu.idi.stud.team10.sparesti.util.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Service for User-Account interaction, connects users and bank accounts */
@Service
public class UserAccountService {
    private final UserRepository userRepository;
    private final BankService bankService;
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    @Autowired
    public UserAccountService(UserRepository userRepository, BankService bankService, AccountRepository accountRepository, AccountMapper accountMapper) {
        this.userRepository = userRepository;
        this.bankService = bankService;
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
    }

    /**
     * Sets a user's savings account to a created bank account.
     *
     * @param accountDto DTO representing the account.
     * @param isSavings boolean declaring whether it is a savings account (true) or checking account (false)
     */
    @Transactional
    public void setUserAccount(AccountDto accountDto, boolean isSavings) {
        User user = findUserById(accountDto.getId());
        if (isSavings) {
            user.setSavingsAccountNr(accountDto.getAccountNr());
        } else {
            user.setCheckingAccountNr(accountDto.getAccountNr());
        }
        userRepository.save(user);
        // TODO: Connect an existing bank account as a user's savings account?
        // The create bank account method already has an ownerID as a necessary prerequisite.
        // If we want to mimic the real world the user would already have an existing account in the bank,
        // and then connect that bank account to their profile, which is where one would set the ownerID.
        // currently, createaccount creates an account in the bank with any owner id (even a non-existent one)
    }

    /**
     * Retrieves a user entity using its id.
     *
     * @param id (Long): the id of the user
     * @return the User entity, if found.
     */
    private User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow( ()
                -> new NotFoundException("User with id " + id + " not found"));
    }
}
