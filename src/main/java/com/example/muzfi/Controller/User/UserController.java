package com.example.muzfi.Controller.User;

import com.example.muzfi.Model.User;
import com.example.muzfi.Services.AuthService;
import com.example.muzfi.Services.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    private final AuthService authService;

    @Autowired
    public UserController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        try {
            Optional<List<User>> users = userService.getAllUsers();

            if (users.isPresent()) {
                return new ResponseEntity<>(users, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("No Users Available", HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            return new ResponseEntity<>("an unknown error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //TODO: Remove later: For testing purposes
    @GetMapping("/okta/{userId}")
    public ResponseEntity<?> getAUserByOktaId(@PathVariable("userId") String oktaUserId) {
        try {
            Optional<User> user = userService.getUserByOktaId(oktaUserId);

            if (user.isPresent()) {
                return new ResponseEntity<>(user, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("No User Available", HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            return new ResponseEntity<>("an unknown error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasAuthority('Muzfi_Member')")
    @GetMapping("/{loggedInUserId}/follow/{followingUserId}")
    public ResponseEntity<?> followUser(@PathVariable("followingUserId") String followingUserId, @PathVariable(name = "loggedInUserId") String loggedInUserId) {
        try {
            boolean isLoggedInUser = authService.isLoggedInUser(loggedInUserId);

            if (!isLoggedInUser) {
                return new ResponseEntity<>("Access denied: You are not eligible to perform this action.", HttpStatus.UNAUTHORIZED);
            }

            Optional<String> follow = userService.followUser(loggedInUserId, followingUserId);

            if (follow.isPresent()) {
                return new ResponseEntity<>(follow.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>("User following action failed", HttpStatus.NO_CONTENT);
            }
        } catch (Exception ex) {
            return new ResponseEntity<>("an unknown error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasAuthority('Muzfi_Member')")
    @GetMapping("/{loggedInUserId}/unfollow/{unFollowingUserId}")
    public ResponseEntity<?> unfollowUser(@PathVariable("unFollowingUserId") String unFollowingUserId, @PathVariable(name = "loggedInUserId") String loggedInUserId) {
        try {
            boolean isLoggedInUser = authService.isLoggedInUser(loggedInUserId);

            if (!isLoggedInUser) {
                return new ResponseEntity<>("Access denied: You are not eligible to perform this action.", HttpStatus.UNAUTHORIZED);
            }

            Optional<String> unFollow = userService.unFollowUser(loggedInUserId, unFollowingUserId);

            if (unFollow.isPresent()) {
                return new ResponseEntity<>(unFollow.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>("User unfollowing action failed", HttpStatus.NO_CONTENT);
            }
        } catch (Exception ex) {
            return new ResponseEntity<>("an unknown error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasAuthority('Muzfi_Member')")
    @GetMapping("/{loggedInUserId}/block/{blockUserId}")
    public ResponseEntity<?> blockUser(@PathVariable("blockUserId") String blockUserId, @PathVariable(name = "loggedInUserId") String loggedInUserId) {
        try {
            boolean isLoggedInUser = authService.isLoggedInUser(loggedInUserId);

            if (!isLoggedInUser) {
                return new ResponseEntity<>("Access denied: You are not eligible to perform this action.", HttpStatus.UNAUTHORIZED);
            }

            Optional<String> block = userService.blockUser(loggedInUserId, blockUserId);

            if (block.isPresent()) {
                return new ResponseEntity<>(block.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>("User blocking action failed", HttpStatus.NO_CONTENT);
            }
        } catch (Exception ex) {
            return new ResponseEntity<>("an unknown error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasAuthority('Muzfi_Member')")
    @GetMapping("/{loggedInUserId}/unblock/{unBlockUserId}")
    public ResponseEntity<?> unBlockUser(@PathVariable("unBlockUserId") String unBlockUserId, @PathVariable(name = "loggedInUserId") String loggedInUserId) {
        try {
            boolean isLoggedInUser = authService.isLoggedInUser(loggedInUserId);

            if (!isLoggedInUser) {
                return new ResponseEntity<>("Access denied: You are not eligible to perform this action.", HttpStatus.UNAUTHORIZED);
            }

            Optional<String> unblock = userService.unBlockUser(loggedInUserId, unBlockUserId);

            if (unblock.isPresent()) {
                return new ResponseEntity<>(unblock.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>("User unblocking action failed", HttpStatus.NO_CONTENT);
            }
        } catch (Exception ex) {
            return new ResponseEntity<>("an unknown error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
