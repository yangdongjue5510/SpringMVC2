package hello.itemservice.web.validation;

import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hello.itemservice.domain.form.ItemSaveForm;

@RestController
@RequestMapping("/validation/api/items")
public class ValidationItemApiController {

	@PostMapping("/add") //json 바인딩 실패시, 컨트롤러 실행 자체가 안됨. (오브젝트 에러는 컨트롤러 실행 가능)
	public Object addItem(@RequestBody @Validated ItemSaveForm form, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return bindingResult.getAllErrors();
		}
		return form;
	}
}
