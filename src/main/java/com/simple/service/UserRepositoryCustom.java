package com.simple.service;

import com.simple.model.User;

public interface UserRepositoryCustom {
	public boolean isUserInDatabase(User user);
}
