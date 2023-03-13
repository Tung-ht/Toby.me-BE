package tunght.gr2.domain.user.controller;

import tunght.gr2.domain.user.dto.UserDto;
import tunght.gr2.domain.user.service.UserService;
import tunght.gr2.security.AuthUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public UserDto currentUser(@AuthenticationPrincipal AuthUserDetails authUserDetails) {
        return userService.currentUser(authUserDetails);
    }

    @PutMapping
    public UserDto update(@Valid @RequestBody UserDto.Update update, @AuthenticationPrincipal AuthUserDetails authUserDetails) {
        return userService.update(update, authUserDetails);
    }
}
