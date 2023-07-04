package tunght.toby.be.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;
import tunght.toby.be.consts.EUserAction;
import tunght.toby.be.dto.UserDto;
import tunght.toby.be.service.UserService;
import lombok.RequiredArgsConstructor;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UsersController {
    private final UserService userService;

    @PostMapping
    public UserDto.RegistrationResponse registration(@Valid @RequestBody UserDto.Registration registration) {
        return userService.registration(registration);
    }

    @Operation(summary = "api login, return access_token")
    @PostMapping("/login")
    public String login(@Valid @RequestBody UserDto.Login login) {
        return userService.login(login);
    }

    @PostMapping("/registration/verify")
    public void registrationVerify(@RequestBody UserDto.RegistrationOTP registrationOTP) {
        userService.registrationVerify(registrationOTP);
    }

    @PostMapping("/resend-otp")
    public void resendOTP(@RequestParam(value = "action") EUserAction action, @RequestParam(value = "email") String email) {
        userService.resendOTP(action, email);
    }
}
