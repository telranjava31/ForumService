package telran.forum.service;

import java.util.Set;

import telran.forum.dto.NewUserDto;
import telran.forum.dto.UserEditDto;
import telran.forum.dto.UserProfileDto;

public interface UserAccountService {
	
	UserProfileDto register(NewUserDto newUserDto);
	
	UserProfileDto findUser(String token);
	
	UserProfileDto removeUser(String token);
	
	UserProfileDto editUser(UserEditDto userEditDto, String token);
	
	Set<String> addRole(String login, String role, String token);
	
	Set<String> removeRole(String login, String role, String token);
	
	void changePassword(String token, String password);
}
