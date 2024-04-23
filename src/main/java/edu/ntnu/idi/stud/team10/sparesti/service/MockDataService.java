package edu.ntnu.idi.stud.team10.sparesti.service;

import edu.ntnu.idi.stud.team10.sparesti.dto.AccountDto;
import edu.ntnu.idi.stud.team10.sparesti.dto.TransactionDto;
import edu.ntnu.idi.stud.team10.sparesti.dto.UserInfoDto;
import edu.ntnu.idi.stud.team10.sparesti.enums.CategoryEnum;
import edu.ntnu.idi.stud.team10.sparesti.mapper.AccountMapper;
import edu.ntnu.idi.stud.team10.sparesti.mapper.TransactionMapper;
import edu.ntnu.idi.stud.team10.sparesti.model.User;
import edu.ntnu.idi.stud.team10.sparesti.model.UserInfo;
import edu.ntnu.idi.stud.team10.sparesti.repository.UserInfoRepository;
import edu.ntnu.idi.stud.team10.sparesti.repository.UserRepository;
import edu.ntnu.idi.stud.team10.sparesti.repository.bank.AccountRepository;
import edu.ntnu.idi.stud.team10.sparesti.util.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.util.Random;

/** Service for mock data creation */
@Service
public class MockDataService {
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final AccountMapper accountMapper;
    private final TransactionMapper transactionMapper;
    private final BankService bankService;
    private final UserService userService;
    private final UserInfoRepository userInfoRepository;
    private final UserInfoService userInfoService;

    @Autowired
    public MockDataService(AccountRepository accountRepository,
                           UserRepository userRepository,
                           AccountMapper accountMapper,
                           TransactionMapper transactionMapper,
                           BankService bankService,
                           UserService userService, UserInfoRepository userInfoRepository, UserInfoService userInfoService) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.accountMapper = accountMapper;
        this.transactionMapper = transactionMapper;
        this.bankService = bankService;
        this.userService = userService;
        this.userInfoRepository = userInfoRepository;
        this.userInfoService = userInfoService;
    }

    /**
     * Creates and returns a random transaction to an account.
     * Intended for creation of mock data for bank.
     *
     * @param accountNr number of the account (who the transaction is coming from)
     * @return a randomly generated transaction
     */
    private TransactionDto generateRandomPurchase(Long accountNr) {
        // method should be simple enough to be moved anywhere else where it fits better.
        // TODO: move into TransactionDto as a public static method?
        DecimalFormat df = new DecimalFormat("#.##");
        TransactionDto transactionDto = new TransactionDto();
        Random random = new Random();
        transactionDto.setAmount(Double.parseDouble(df.format(random.nextDouble(-500, -20))));
        //bounds of transaction could be moved to args for this method, which would allow for more dynamic transactions.
        transactionDto.setCategory(CategoryEnum.getRandomCategory());
        transactionDto.setAccountNr(Integer.parseInt(String.valueOf(accountNr)));
        return transactionDto;
    }

    /**
     * Creates a new mock account and adds it to the user.
     *
     * @param email (String): The email of the user the account is being added to
     * @param accountNr (Integer): The account number being created
     * @param isSavingsAcc (boolean): Whether the account is going to be the savings account
     *                     (if false; will be checking account)
     * @return the AccountDto
     */
    @Transactional
    public AccountDto addMockBankAccount(String email, Integer accountNr, boolean isSavingsAcc) {
        // Can change to use a different arg than displayName. ATM I don#t know what is stored on the front-end.
        // account nr can also be randomized. to a number like 1 - 1000000.
        User mockUser = findUserByEmail(email);
        UserInfoDto mockUserInfo = userInfoService.getUserInfoByEmail(email);
        AccountDto accountDto = new AccountDto();
        accountDto.setAccountNr(accountNr);
        accountDto.setOwnerId(mockUser.getId());
        String name = mockUserInfo.getFirstName() + " " + mockUserInfo.getLastName();

        if (isSavingsAcc) {
            accountDto.setName(name + "'s savings account");
            mockUser.setSavingsAccountNr(accountNr);
        } else {
            accountDto.setName(accountDto.getName() + "'s checking account");
            mockUser.setCheckingAccountNr(accountNr);
        }

        userRepository.save(mockUser);
        return bankService.createAccount(accountDto);
    }

    private User findUserByEmail(String email) {
        return userRepository.findById(userService.getUserByEmail(email).getId())
                .orElseThrow(() -> new NotFoundException("user with email" + email + " not found"));
    }
}
