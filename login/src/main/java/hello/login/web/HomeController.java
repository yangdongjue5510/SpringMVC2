package hello.login.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import hello.login.web.session.SessionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemberRepository memberRepository;
    private final SessionManager sessionManager;
    //@GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/")
    public String homeLogin(HttpServletRequest request, Model model) {

        HttpSession session = request.getSession(false);
        Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        if (member == null) {
            return "home";
        }

        //로그인
        if (member == null) {
            return "home";
        }
        model.addAttribute("member", member);
        return "loginHome";
    }
}