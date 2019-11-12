package telran.forum.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Singular;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
public class UserProfileDto {
	String login;
	String firstName;
	String lastName;
	@Singular
	Set<String> roles;
}
