package hello.login.web.session;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import hello.login.domain.member.Member;

class SessionManagerTest {

	SessionManager sessionManager = new SessionManager();

	@Test
	void sessionTest() {
		//create session.
		Member member = new Member();
		MockHttpServletResponse response = new MockHttpServletResponse();
		sessionManager.createSession(member, response);

		//response cookie save by request.
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setCookies(response.getCookies());

		//retrieve cookie
		Object result = sessionManager.getSession(request);
		assertThat(result).isEqualTo(member);

		//expire session
		sessionManager.expire(request);
		Object expired = sessionManager.getSession(request);
		assertThat(expired).isNull();
	}
}