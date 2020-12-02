package com.bd.xchoice.service;

import com.bd.xchoice.model.User;

public interface UserService {

    User putUser(String email);

    User getUser(String email);

    User getUser(int id);
}
