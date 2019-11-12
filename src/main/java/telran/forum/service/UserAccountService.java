package telran.forum.service;

import telran.forum.dto.NewUserDto;
import telran.forum.dto.UserProfileDto;

public interface UserAccountService {
	
	UserProfileDto register(NewUserDto newUserDto);
}
