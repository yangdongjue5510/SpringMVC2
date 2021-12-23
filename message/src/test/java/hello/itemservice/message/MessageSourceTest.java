package hello.itemservice.message;

import static org.assertj.core.api.Assertions.*;

import java.util.Locale;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

@SpringBootTest
public class MessageSourceTest {
	@Autowired
	MessageSource ms;

	@Test
	void helloMessage() {
		String result = ms.getMessage("hello", null, null);
		assertThat(result).isEqualTo("안녕");
	}

	@Test
	void notFountMessageCode() {
		assertThatThrownBy(() -> ms.getMessage("no_code", null, null))
			.isInstanceOf(NoSuchMessageException.class);
	}

	@Test
	void notFountMessageDefaultCode() {
		String result = ms.getMessage("no_code", null, "Default", null);
		assertThat(result).isEqualTo("Default");
	}

	@Test
	void argumentMessageCode() {
		String result = ms.getMessage("hello.name", new Object[] {"스프링"}, null);
		assertThat(result).isEqualTo("안녕 스프링");
	}

	@Test
	void enMessageCode() {
		String result = ms.getMessage("hello", null, Locale.ENGLISH);
		assertThat(result).isEqualTo("hello");
	}
}
