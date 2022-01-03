package hello.login.domain.member;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class Member {
	private Long id;

	@NotEmpty
	private String loginId;
	@NotEmpty
	private String name;
	private String password;
}
