package tunght.toby.be.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tunght.toby.be.consts.EUserAction;
import tunght.toby.be.dto.UserDto;
import tunght.toby.be.service.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "422", description = "Failed, unknown error"),
        @ApiResponse(responseCode = "404", description = "Not Found"),
})
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

    @Operation(summary = "api verify OTP for REGISTRATION and RESET_PASSWORD")
    @PostMapping("/verify")
    public void verifyOTP(@RequestParam(value = "action") EUserAction action, @RequestBody UserDto.RequestOTP registrationOTP) {
        userService.requestVerify(action, registrationOTP);
    }

    @Operation(summary = "api send OTP for REGISTRATION and RESET_PASSWORD")
    @PostMapping("/send-otp")
    public void sendOTP(@RequestParam(value = "action") EUserAction action, @RequestParam(value = "email") String email) {
        userService.sendOTP(action, email);
    }
}
