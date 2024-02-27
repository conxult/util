package com.example;

import de.conxult.util.railway.Result;


public class UserRepo {
    public Result<User, String> find(String username) {
        return Result.withValue(new User(username));
    }

    public Result<User, String> update(User user) {
        return Result.withValue(user);
    }
}
