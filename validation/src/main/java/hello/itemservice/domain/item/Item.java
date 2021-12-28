package hello.itemservice.domain.item;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.ScriptAssert;

import lombok.Data;

@Data
//오브젝트 오류를 검증하는 어노테이션. 비추함. 컨트롤러에서 자바코드로 하는게 맞다.
//@ScriptAssert(lang = "javascript", script = "_this.price * _this.quantity >= 10000", message = "최소 10000원 넘게 입력해주세요.")
public class Item {

    private Long id;

    @NotBlank
    private String itemName;

    @NotNull
    @Range(min = 1000, max = 1000000)
    private Integer price;

    @NotNull
    @Max(9999)
    private Integer quantity;

    public Item() {
    }

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
