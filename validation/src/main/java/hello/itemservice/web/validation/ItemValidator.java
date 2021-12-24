package hello.itemservice.web.validation;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import hello.itemservice.domain.item.Item;

@Component
public class ItemValidator implements Validator {
	@Override
	public boolean supports(Class<?> clazz) {
		return Item.class.isAssignableFrom(clazz); //clazz가 item의 자식이거나 item클래스인지 확인
	}

	@Override
	public void validate(Object target, Errors errors) {
		Item item = (Item)target;

		//검증 로직
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "itemName", "required");//바로 아래 검증과 동일. 비거나 공백같이 단순한경우만 사용.
		if (!StringUtils.hasText(item.getItemName())) {
			//bindingResult.addError(new FieldError("item", "itemName", "상품명이 없습니다."));
			//bindingResult.addError(new FieldError("item", "itemName", item.getItemName(), false, new String[]{"required.item.itemName"}, null, null));
			errors.rejectValue("itemName", "required");
		}
		if (item.getPrice() == null || item.getPrice() > 10000000 || item.getPrice() < 1000) {
			//bindingResult.addError(new FieldError("item", "price", "가격은 천원에서 백만원 사이를 허용합니다."));
			//bindingResult.addError(new FieldError("item", "price", item.getPrice(), false, new String[]{"range.item.price"}, new Object[]{1000, 100000}, null));
			errors.rejectValue("price", "range", new Object[]{1000, 100000}, null);
		}
		if (item.getQuantity() == null || item.getQuantity() >= 9999) {
			//bindingResult.addError(new FieldError("item", "quantity", "수량은 최대 9999개까지 허용합니다."));
			//bindingResult.addError(new FieldError("item", "quantity", item.getQuantity(), false, new String[]{"max.item.quantity"}, new Object[]{9999}, null));
			errors.rejectValue("quantity", "max", new Object[]{9999}, null);
		}

		//특정 필드가 아닌 복합 검증
		if (item.getPrice() != null && item.getQuantity() != null) {
			int result = item.getPrice() * item.getQuantity();
			if (result < 10000) {
				//bindingResult.addError(new ObjectError("item", new String[]{"totalPriceMin"}, new Object[]{10000, result}, null));
				errors.reject("totalPriceMin", new Object[]{10000, result}, null);
			}
		}
	}
}
