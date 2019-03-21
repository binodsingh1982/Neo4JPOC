package com.stufusion.oauth2.endpoint;

import java.util.List;

import javax.management.relation.RoleNotFoundException;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.stufusion.oauth2.model.UserReq;
import com.stufusion.oauth2.model.UserResp;
import com.stufusion.oauth2.service.TokenVerificationService;
import com.stufusion.oauth2.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserEndpoint {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenVerificationService tokenVerificationService;

    /**
     * @param user
     * @return
     * @throws DuplicateUserException
     * @throws RoleNotFoundException
     * @throws InvalidPasswordException
     * @throws InvalidUserException
     */
    @RequestMapping(value = {"/register"}, method = RequestMethod.POST)
    public IdResponse registerUser(@RequestBody @Valid UserReq userReq) {
        Long userId = userService.createUserWithEmailVerification(userReq);
        return IdResponse.get(userId);
    }

    @RequestMapping(value = {"/email"}, method = RequestMethod.GET)
    @ResponseBody
    public UserResp getUserByEmail(@RequestParam String email) {
        return userService.getUser(email);
    }

    @RequestMapping(value = {"/{id}"}, method = RequestMethod.GET)
    @ResponseBody
    public UserResp getUserById(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<UserResp> getUsers() {
        return userService.getUsers();
    }

    /**
     * @param token
     * @return
     * @throws UserNotFoundException
     */
    @RequestMapping(value = "/confirmation/{token}", method = RequestMethod.POST)
    public boolean confirmRegistration(@PathVariable("token") @NotNull String token) {
        return tokenVerificationService.verifyToken(token);
    }

}
