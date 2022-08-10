package se.maokei.kanban.web;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.maokei.kanban.domain.User;
import se.maokei.kanban.payloads.JWTLoginSucessResponse;
import se.maokei.kanban.payloads.LoginRequest;
import se.maokei.kanban.security.JwtTokenProvider;
import se.maokei.kanban.services.MapValidationErrorService;
import se.maokei.kanban.services.UserService;
import se.maokei.kanban.validator.UserValidator;

import javax.validation.Valid;

import static se.maokei.kanban.security.Constants.TOKEN_PREFIX;

@AllArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {
    private MapValidationErrorService mapValidationErrorService;
    private UserService userService;
    private UserValidator userValidator;
    private JwtTokenProvider tokenProvider;
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, BindingResult result){
        ResponseEntity<?> errorMap = mapValidationErrorService.mapValidationErrorService(result);
        if(errorMap != null) return errorMap;

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = TOKEN_PREFIX +  tokenProvider.generateJwtToken(authentication);

        return ResponseEntity.ok(new JWTLoginSucessResponse(true, jwt));
    }

    @PostMapping("/register")//@Valid
    public ResponseEntity<?> registerUser(@RequestBody User user, BindingResult result) {
        // Validate passwords match
        userValidator.validate(user, result);

        ResponseEntity<?> errorMap = mapValidationErrorService.mapValidationErrorService(result);
        if (errorMap != null) return errorMap;

        User newUser = userService.saveUser(user);

        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }
}
