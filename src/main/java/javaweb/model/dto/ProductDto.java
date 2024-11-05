package javaweb.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//ProductDto 對應於 Product entity
//Dto 的屬性名稱建議可以與所對應的 Entity 物件不同

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
	private Integer productId; // 商品Id
	private String productName; // 商品名稱
	private double price; // 商品價格
	private Integer stockQuantity; // 商品庫存
};


