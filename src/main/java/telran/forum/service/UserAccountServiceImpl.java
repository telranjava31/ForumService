package telran.forum.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import telran.forum.dao.UserAccountRepository;
import telran.forum.dto.NewUserDto;
import telran.forum.dto.UserProfileDto;
import telran.forum.exeptions.UserExistsException;
import telran.forum.model.UserAccount;

@Service
public class UserAccountServiceImpl implements UserAccountService {
	
	@Autowired
	UserAccountRepository accountRepository;
	
	long expPeriod = 30;

	@Override
	public UserProfileDto register(NewUserDto newUserDto) {
		if (accountRepository.existsById(newUserDto.getLogin())) {
			throw new UserExistsException();
		}
		UserAccount userAccount = UserAccount.builder()
									.login(newUserDto.getLogin())
									.password(newUserDto.getPassword())
									.firstName(newUserDto.getFirstName())
									.lastName(newUserDto.getLastName())
									.role("User")
									.expDate(LocalDateTime.now().plusDays(expPeriod))
									.build();
		accountRepository.save(userAccount);
		return userAccountToUserProfileDto(userAccount);
	}

	private UserProfileDto userAccountToUserProfileDto(UserAccount userAccount) {
		return UserProfileDto.builder()
				.login(userAccount.getLogin())
				.firstName(userAccount.getFirstName())
				.lastName(userAccount.getLastName())
				.roles(userAccount.getRoles())
				.build();
	}

}
