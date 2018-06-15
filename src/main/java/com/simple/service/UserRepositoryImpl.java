package com.simple.service;

import static org.assertj.core.api.Assertions.assertThatIllegalStateException;

import com.simple.model.User;

public class UserRepositoryImpl implements UserRepositoryCustom{

	//Nie do końca jasne jeszcze, co by tutaj miało się znajdować
	public boolean customMethod1(User user) {
		
		return false;
	}

}
