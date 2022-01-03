package hello.login.web.login;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
	public String login(@Validated @ModelAttribute LoginForm loginForm, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "login/loginForm";
		}

		Member loginMember = loginService.login(loginForm.getLoginId(), loginForm.getPassword());

		if (loginMember == null) {
			bindingResult.reject("loginFail", "아이디 혹은 비밀번호 불일치");
			return "login/loginForm";
		}

		//로그인 성공 처리 TODO

		return "redirect:/";
	}
}

