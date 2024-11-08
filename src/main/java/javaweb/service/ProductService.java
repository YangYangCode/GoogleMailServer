package javaweb.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javaweb.model.dto.ProductDto;
import javaweb.model.dto.UserDto;
import javaweb.model.entity.Product;
import javaweb.model.entity.User;
import javaweb.repository.ProductDao;
import javaweb.repository.ProductDaoImpl;
import javaweb.utils.Hash;

public class ProductService {

		private ProductDao productDao = new ProductDaoImpl();
		
		// 所有Product
		public List<ProductDto> findAllProducts(){
			// 向 productDao 索取 List<Product> 集合
			List<Product> products = productDao.findAllProducts();
			List<ProductDto> productDtos = new ArrayList<>();
			// 將 Product 轉 PriductDto
			
			
//			stream寫法，使用建構子(有不可再次修改之優點)
//			products.stream().forEach((p) -> {
//				productDtos.add(new ProductDto(p.getProductId(), p.getProductName(), p.getPrice(), p.getStockQuantity()));
//			});
			
			
			for (Product product : products) {
				// 一個一個將 Product 轉成 ProductDto 並放在 productDtos 集合中
				ProductDto pruductDto = new ProductDto();
				pruductDto.setProductId(product.getProductId());
				pruductDto.setProductName(product.getProductName());
				pruductDto.setPrice(product.getPrice());
				pruductDto.setStockQuantity(product.getStockQuantity());

				productDtos.add(pruductDto);
			}
			return productDtos;
		}
		
		public void appendProduct(String productName, Double price, Integer stockQuantity) {
			Product product = new Product();
			product.setProductName(productName);
			product.setPrice(price);
			product.setStockQuantity(stockQuantity);
			System.out.println(product);
			
			productDao.addProduct(product);
		}
		
		public void updateProduct(String productId, String price, String stockQuantity) {
			if(!price.isEmpty()) {
				productDao.updateProductPrice(Integer.parseInt(productId), Double.parseDouble(price));
			}
			if(!stockQuantity.isEmpty()) {
				productDao.updateProductStockQuantity(Integer.parseInt(productId), Integer.parseInt(stockQuantity));
			}
		}
		
		public ProductDto getProduct(String productName) {
			Product product = productDao.getProduct(productName);
			
			if(product == null) {
				return null;
			}
			
			// Product -> ProductDto
			ProductDto pruductDto = new ProductDto();
			pruductDto.setProductId(product.getProductId());
			pruductDto.setProductName(product.getProductName());
			pruductDto.setPrice(product.getPrice());
			pruductDto.setStockQuantity(product.getStockQuantity());
			return pruductDto;
		}
		
		public void deleteProduct(String productId) {
			productDao.deleteProduct(Integer.parseInt(productId));
		}
		
		public Map<String, Double> querySalesRanking(){
			return productDao.querySalesRanking();
		}
}
