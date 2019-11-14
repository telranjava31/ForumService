package telran.forum.service;

import java.time.LocalDateTime;
import java.util.Set;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import telran.forum.configuration.AccountConfiguration;
import telran.forum.configuration.UserCredentials;
import telran.forum.dao.UserAccountRepository;
import telran.forum.dto.NewUserDto;
import telran.forum.dto.UserEditDto;
import telran.forum.dto.UserProfileDto;
import telran.forum.exceptions.ForbiddenException;
import telran.forum.exceptions.UserAuthenticationException;
import telran.forum.exceptions.UserExistsException;
import telran.forum.model.UserAccount;

@Service
public class UserAccountServiceImpl implements UserAccountService {
	
	@Autowired
	UserAccountRepository accountRepository;
	
	@Autowired
	AccountConfiguration accountConfiguration;

	@Override
	public UserProfileDto register(NewUserDto newUserDto) {
		if (accountRepository.existsById(newUserDto.getLogin())) {
			throw new UserExistsException();
		}
		String hashPassword = BCrypt.hashpw(newUserDto.getPassword(), BCrypt.gensalt());
		UserAccount userAccount = UserAccount.builder()
									.login(newUserDto.getLogin())
									.password(hashPassword)
									.firstName(newUserDto.getFirstName())
									.lastName(newUserDto.getLastName())
									.role("User")
									.expDate(LocalDateTime.now().plusDays(accountConfiguration.getExpPeriod()))
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

	@Override
	public UserProfileDto findUser(String token) {
		UserCredentials userCredentials = accountConfiguration.tokenDecode(token);
		UserAccount userAccount = accountRepository.findById(userCredentials.getLogin())
				.orElseThrow(UserAuthenticationException::new);
		if (!BCrypt.checkpw(userCredentials.getPassword(), userAccount.getPassword())) {
			throw new ForbiddenException();
		}
		return userAccountToUserProfileDto(userAccount);
	}

	@Override
	public UserProfileDto removeUser(String token) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserProfileDto editUser(UserEditDto userEditDto, String token) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> addRole(String login, String role, String token) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> removeRole(String login, String role, String token) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void changePassword(String token, String password) {
		// TODO Auto-generated method stub
		
	}

}
