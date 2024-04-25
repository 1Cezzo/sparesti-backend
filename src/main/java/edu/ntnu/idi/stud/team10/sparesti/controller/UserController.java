package edu.ntnu.idi.stud.team10.sparesti.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import edu.ntnu.idi.stud.team10.sparesti.dto.AccountDto;
import edu.ntnu.idi.stud.team10.sparesti.dto.UserDto;
import edu.ntnu.idi.stud.team10.sparesti.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/** Controller for handling user requests. */
@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:5173")
@Tag(name = "User", description = "Operations related to creating and deleting a user")
public class UserController {

  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  /**
   * Connects a created bank account to either user's savings or checking account.
   *
   * @param accountDto (AccountDto) The account being connected
   * @param isSavings (boolean) Whether it is a savings (true) or checking (false) account
   * @return (ResponseEntity &lt;Void&gt;) ACCEPTED HTTP status message on success.
   */
  @PostMapping("/account/assign")
  @Operation(summary = "Assign an existing bank account as user's savings or checking account")
  public ResponseEntity<Void> assignAccountToUser(
      @RequestBody AccountDto accountDto, @RequestParam boolean isSavings) {
    userService.setUserAccount(accountDto, isSavings);
    return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    // Can be moved anywhere else easily (just change the mapping)
    // needs user repository though.
  }

  /**
   * Get a user by username.
   *
   * @param username the username of the user
   * @return the user with the given username
   */
  @GetMapping("/{username}")
  @Operation(summary = "Access the user data")
  public UserDto getUserByUsername(@PathVariable String username) {
    return userService.getUserByEmail(username);
  }

  /**
   * Create a new user.
   *
   * @param userDTO the user to create
   * @return the response entity
   */
  @PostMapping("/create")
  @Operation(summary = "Create a new user")
  public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDTO) {
    return ResponseEntity.status(HttpStatus.CREATED).body(userService.addUser(userDTO));
  }

  /**
   * Delete a user by id.
   *
   * @param id The id of the user to delete.
   * @return ResponseEntity with status code.
   */
  @DeleteMapping("/delete/{id}")
  @Operation(summary = "Delete a user")
  public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
    userService.deleteUser(id);
    return ResponseEntity.noContent().build();
  }

  /**
   * Update a user.
   *
   * @param userDTO the user to update
   * @return the response entity
   */
  @PostMapping("/update")
  @Operation(summary = "Update a user")
  public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDTO) {
    return ResponseEntity.ok(userService.updateUser(userDTO));
  }
}
