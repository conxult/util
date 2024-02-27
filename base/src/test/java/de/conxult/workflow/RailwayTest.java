/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.conxult.workflow;

import java.util.UUID;
import java.util.function.Predicate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 *
 * @author joergh
 */
public class RailwayTest {

    private static final String FIRST_NAME = "Willi";
    private static final String LAST_NAME  = "Butz";
    private static final String EMAIL      = "willi@butz.de";
    private static final String PASSWORD   = "ganzgeheim";

    @Test
    public void userSignupRequestWithoutNamesShouldFail() throws Exception {
        var request = new UserSignupRequest()
            .setUser(new User()
                .setEmail(EMAIL)
                .setPassword(PASSWORD));

        Railway<UserSignupResponse> railway = test(request);
        assertTrue(railway.isFailure());
        UserSignupResponse response = railway.getValue();
        var failures = railway.getValue().getFailures();
        assertEquals(3, failures.size());
        assertEquals("missing or empty", failures.get("userName"));
        assertEquals("missing or empty", failures.get("firstName"));
        assertEquals("missing or empty", failures.get("lastName"));
    }

    Railway<UserSignupResponse> test(UserSignupRequest request) {
        var response = new UserSignupResponse();
        var user     = request.getUser();
        return Railway.of(response)
            .verify((r) -> verifyUserRequest(user, response))
            .verify((r) -> verifyUserDoesNotExist(user), (r) -> new Exception("user " + user.getUserName()+ "exists"))
            .onSuccess((r) -> createUser(user))
            .onSuccess((u) -> response.setUserId(u.getId()));
    }

    boolean verifyUserRequest(User user, UserSignupResponse response) {
        Predicate<String> nullOrEmpty = (String value) -> value == null || value.isEmpty();

        if (nullOrEmpty.test(user.getFirstName())) {
            response.addFailure("firstName", "missing or empty");
        }
        if (nullOrEmpty.test(user.getLastName())) {
            response.addFailure("lastName", "missing or empty");
        }
        if (nullOrEmpty.test(user.getUserName())) {
            response.addFailure("userName", "missing or empty");
        }
        if (nullOrEmpty.test(user.getEmail())) {
            response.addFailure("email", "missing or empty");
        } else if (!user.getEmail().contains("@")) {
            response.addFailure("email", "invalid");
        }

        return response.getFailures().isEmpty();
    }

    boolean verifyUserDoesNotExist(User user) {
        return !user.getUserName().contains("exists");
    }

    User createUser(User user) throws Exception {
        if (!user.getUserName().contains("fail")) {
            return user.setId(UUID.nameUUIDFromBytes(user.getUserName().getBytes()));
        }
        throw new Exception("user creation failed");
    }
}
