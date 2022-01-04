package hello.login.web.login;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import hello.login.domain.login.LoginService;
import hello.login.domain.member.Member;
import hello.login.web.SessionConst;
import hello.login.web.session.SessionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

	private final LoginService loginService;
	private final SessionManager sessionManager;

	@GetMapping("/login")
	public String loginForm(@ModelAttribute("loginForm") LoginForm form) {
		return "login/loginForm";
	}

	@PostMapping("/login")
	public String login(@Validated @ModelAttribute LoginForm loginForm, BindingResult bindingResult, HttpServletRequest request) {
		if (bindingResult.hasErrors()) {
			return "login/loginForm";
		}

		Member loginMember = loginService.login(loginForm.getLoginId(), loginForm.getPassword());

		if (loginMember == null) {
			bindingResult.reject("loginFail", "아이디 혹은 비밀번호 불일치");
			return "login/loginForm";
		}

		//로그인 성공 처리
		//param true : create session if request's session is null. Else, return exists session.
		//param false : do not create session if don't exists. return null.
		HttpSession session = request.getSession();
		session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);

		return "redirect:/";
	}

	@PostMapping("/logout")
	public String logout(HttpServletRequest request) {
		//find session if it exists.
		HttpSession session = request.getSession(false);

		if (session != null) {
			//invalid session if exists.
			session.invalidate();
		}
		return "redirect:/";
	}
}

