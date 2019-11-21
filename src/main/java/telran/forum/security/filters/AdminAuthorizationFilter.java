package telran.forum.security.filters;

import java.io.IOException;
import java.security.Principal;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import telran.forum.dao.UserAccountRepository;
import telran.forum.model.UserAccount;

@Service
@Order(30)
public class AdminAuthorizationFilter implements Filter {

	@Autowired
	UserAccountRepository accountRepository;

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		Principal principal = request.getUserPrincipal();
		if (principal != null) {
			UserAccount userAccount = accountRepository.findById(principal.getName()).get();
			if (checkPointCut(userAccount.getRoles(), request.getServletPath())) {
				response.sendError(403, "Administrator rights required");
				return;
			}
		}
		chain.doFilter(request, response);
	}

	private boolean checkPointCut(Set<String> roles, String path) {
		boolean check = !roles.contains("Administrator") && path.matches("/account/user/(\\w*)/role/(\\w*)");
		return check;
	}

}
