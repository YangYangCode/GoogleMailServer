package javaweb.repository;

import java.util.List;
import java.util.Map;

import javaweb.model.entity.Product;
import javaweb.model.entity.User;

public interface ProductDao {
	// 多筆: 查詢所有使用者
	List<Product> findAllProducts();
	
	// 單筆: 根據 productName 查詢該筆使用者
	Product getProduct(String productName);
	
	// 新增
	void addProduct(Product product);
	
	// 刪除: 根據 userId 來刪除
	void deleteProduct(Integer productId);
	
	// 修改 product_price
	void updateProductPrice(Integer productId, Double productPrice);
	
	// 修改 product_stock_quantity
	void updateProductStockQuantity(Integer productId, Integer stockQuantity);

	// Map<商品名稱(product_name), 銷售金額 (total_sales)>
		Map<String, Double> querySalesRanking(); // 銷售排行
}




