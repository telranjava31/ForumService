package telran.forum.service;

import java.util.Set;

import telran.forum.dto.NewUserDto;
import telran.forum.dto.UserEditDto;
import telran.forum.dto.UserProfileDto;

public interface UserAccountService {
	
	UserProfileDto register(NewUserDto newUserDto);
	
	UserProfileDto findUser(String login);
	
	UserProfileDto removeUser(String login);
	
	UserProfileDto editUser(UserEditDto userEditDto, String login);
	
	Set<String> addRole(String login, String role);
	
	Set<String> removeRole(String login, String role);
	
	void changePassword(String login, String password);
}
