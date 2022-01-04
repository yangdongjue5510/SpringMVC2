package hello.login.web.login;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import hello.login.domain.login.LoginService;
import hello.login.domain.member.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

	private final LoginService loginService;

	@GetMapping("/login")
	public String loginForm(@ModelAttribute("loginForm") LoginForm form) {
		return "login/loginForm";
	}

	@PostMapping("/login")
	public String login(@Validated @ModelAttribute LoginForm loginForm, BindingResult bindingResult, HttpServletResponse response) {
		if (bindingResult.hasErrors()) {
			return "login/loginForm";
		}

		Member loginMember = loginService.login(loginForm.getLoginId(), loginForm.getPassword());

		if (loginMember == null) {
			bindingResult.reject("loginFail", "아이디 혹은 비밀번호 불일치");
			return "login/loginForm";
		}

		//로그인 성공 처리
		//쿠키에 시간 정보를 주지 않으면 세션 쿠키(브라우저 종료시 모두 종료)
		response.addCookie(new Cookie("memberId", String.valueOf(loginMember.getId())));

		return "redirect:/";
	}

	@PostMapping("/logout")
	public String logout(HttpServletResponse response) {
		expireCookie(response, "memberId");
		return "redirect:/";
	}

	private void expireCookie(HttpServletResponse response, String cookieName) {
		Cookie cookie = new Cookie(cookieName, null);
		cookie.setMaxAge(0);
		response.addCookie(cookie);
	}
}

