package hello.itemservice.domain.item;

import java.util.List;

import lombok.Data;

@Data
public class Item {

    private Long id;
    private String itemName;
    private Integer price;
    private Integer quantity;

    private boolean open; //판매 여부
    private List<String> regions; //등록지역
    private ItemType itemType; //상품 종류
    private String deliveryCode; //배송 방식

   public Item() {
    }

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }

    public boolean getOpen() {
       return open;
    }
}
