package javaweb.model.entity;

import lombok.Data;

@Data
public class Product {
	private Integer productId; // 商品Id
	private String productName; // 商品名稱
	private double price; // 商品價格
	private Integer stockQuantity; // 商品庫存
}

