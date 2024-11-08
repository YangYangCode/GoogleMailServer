package javaweb.servlet;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import javaweb.model.dto.ProductDto;
import javaweb.model.dto.UserDto;
import javaweb.service.ProductService;
import javaweb.service.UserService;

/**
-- 商品 product
+------------+------------------+----------+----------------+
| product_id | product_name     | price    | stock_quantity |
+------------+------------------+----------+----------------+
| 1          | PC               | 30000.00 | 50             |
| 2          | Mobile           | 15000.00 | 100            |
| 3          | MusicBox         | 3000.00  | 200            |
| 4          | Pad              | 20000.00 | 75             |
| 5          | Watch            | 8000.00  | 150            |
+------------+------------------+----------+----------------+

 -- 創建商品表
create table if not exists product (
	product_id int primary key auto_increment comment '商品Id',
	product_name varchar(50) not null unique comment '商品名稱',
	price decimal(10, 2) not null comment '商品價格',
	stock_quantity int not null default 0 comment '商品庫存'
); 

insert into product(product_name, price, stock_quantity) values('PC', 30000.00, 50);
insert into product(product_name, price, stock_quantity) values('Mobile', 15000.00, 100);
insert into product(product_name, price, stock_quantity) values('MusicBox', 3000.00, 200);
insert into product(product_name, price, stock_quantity) values('Pad', 20000.00, 75);
insert into product(product_name, price, stock_quantity) values('Watch', 8000.00, 150);


 MVC + 自訂框架
  
  request   +----------------+             +----------------+          +------------+
 ---------> | ProductServlet | ----------> | ProductService | -------> | ProductDao | ------->    MySQL
            | (Controller)   | <---------- |                | <------- |            | <------- (web.product)
  			+----------------+  ProductDto +----------------+  Product +------------+
  			       |              (Dto)                       (Entity)
  			       |
  			       v
  			+-------------+
 <--------- | product.jsp |
  response	|    (View)   |
  			+-------------+                 
 
 查詢全部: GET  /product, /products
 
*/ 


@WebServlet(value = {"/product/*", "/products"})
public class ProductServlet extends HttpServlet{
	
	private ProductService productService = new ProductService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
//		String servletPath = req.getServletPath();
		String pathInfo = req.getPathInfo()+"";
//		System.out.println(servletPath);
//		System.out.println(pathInfo);
		
//		switch (servletPath) {
		switch (pathInfo) {
			case "/sales/ranking":
				req.setAttribute("salesRankingMap", productService.querySalesRanking());
				req.getRequestDispatcher("/WEB-INF/view/sales_ranking.jsp").forward(req, resp);
				break;
				
			case "/products":
				// 查詢全部
				List<ProductDto> productDtos = productService.findAllProducts();
				
				//將必要屬性加到 request 屬性中，以便交由 jsp 進行分析與呈現
				req.setAttribute("productDtos", productDtos);
				// 重導到 products.jsp
				req.getRequestDispatcher("/WEB-INF/view/products.jsp").forward(req, resp);
				return;
				
			case "/get":
				String productName = req.getParameter("productName");
				ProductDto productDto = productService.getProduct(productName);
				// 將必要資料加入到 request 屬性中，以便交由 jsp 進行分析與呈現
				req.setAttribute("productDto", productDto);
				// "內"重導到 product.jsp (可帶資料)
				req.getRequestDispatcher("/WEB-INF/view/product_update.jsp").forward(req, resp);
				return;
			
			case "/delete":
				String productId = req.getParameter("productId");
				productService.deleteProduct(productId);

				// 刪除完畢之後，重新執行指定頁面
				resp.sendRedirect("/javaweb/products");
				return;
				
			default:
				req.setAttribute("productDtos", productService.findAllProducts());
				req.getRequestDispatcher("/WEB-INF/view/products.jsp").forward(req, resp);
		}
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String pathInfo = req.getPathInfo();
		
		// 取得表單
		String productName = req.getParameter("productName");
		String price = req.getParameter("price");
		String stockQuantity = req.getParameter("stockQuantity");
		String productId = req.getParameter("productId");
		
		switch (pathInfo) {
		case "/add":
			productService.appendProduct(productName, Double.parseDouble(price), Integer.parseInt(stockQuantity));
			break;
			
		case "/update":
			productService.updateProduct(productId, price, stockQuantity);
			break;
		
		}
		
		// "外"重導到指定URL網頁(不可帶資料，單純重新進入網頁)
		// resp.sendRedirect("http://localhost:8080/javaweb/products"); (因為同伺服器，同下方網址)
		resp.sendRedirect("/javaweb/products");
	}
}

