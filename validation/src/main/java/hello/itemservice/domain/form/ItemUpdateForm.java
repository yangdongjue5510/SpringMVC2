package hello.itemservice.domain.form;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

import lombok.Data;

@Data
public class ItemUpdateForm {

	@NotNull
	private  Long id;

	@NotBlank
	private String itemName;

	@NotNull
	@Range(min = 1000, max = 10000000)
	private Integer price;

	//수정시에는 자유롭게 변경 가능
	private Integer quantity;
}
