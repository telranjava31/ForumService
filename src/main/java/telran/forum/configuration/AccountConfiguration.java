package telran.forum.configuration;

import java.util.Base64;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;

import telran.forum.exceptions.UserAuthenticationException;
import telran.forum.model.UserAccount;

@Configuration
@ManagedResource
public class AccountConfiguration {
	
	Map<String, UserAccount> users = new ConcurrentHashMap<>();
	
	@Value("${exp.value}")
	long expPeriod = 90;

	@ManagedAttribute
	public long getExpPeriod() {
		return expPeriod;
	}

	@ManagedAttribute
	public void setExpPeriod(long expPeriod) {
		this.expPeriod = expPeriod;
	}
	
	public boolean addUser(String sessionId, UserAccount userAccount) {
		return users.put(sessionId, userAccount) == null;
	}
	
	public UserAccount getUser(String sessionId) {
		return users.get(sessionId);
	}
	
	public String getUserLogin(String sessionId) {
		return users.get(sessionId).getLogin();
	}
	
	public UserAccount removeUser(String sessionId) {
		return users.remove(sessionId);
	}
	
	public UserCredentials tokenDecode(String token) {
		try {
			int pos = token.indexOf(" ");
			token = token.substring(pos+1);
			String credential = new String(Base64.getDecoder().decode(token));
			String[] credentials = credential.split(":");
			return new UserCredentials(credentials[0], credentials[1]);
		} catch (Exception e) {
			throw new UserAuthenticationException();
		}
		
	}

}
