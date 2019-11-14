package telran.forum;

import java.time.LocalDateTime;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import telran.forum.dao.UserAccountRepository;
import telran.forum.model.UserAccount;

@SpringBootApplication
public class ForumServiceApplication implements CommandLineRunner {
	@Autowired
	UserAccountRepository userAccountRepository;

	public static void main(String[] args) {
		SpringApplication.run(ForumServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		if(!userAccountRepository.existsById("admin")) {
			String hashPassword = BCrypt.hashpw("admin", BCrypt.gensalt());
			UserAccount admin = UserAccount.builder()
								.login("admin")
								.password(hashPassword)
								.firstName("Super")
								.lastName("Admin")
								.expDate(LocalDateTime.now().plusYears(25))
								.role("Administrator")
								.role("Moderator")
								.role("User")
								.build();
			userAccountRepository.save(admin);
		}

	}

}
