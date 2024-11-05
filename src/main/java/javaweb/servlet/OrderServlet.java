package javaweb.servlet;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import javaweb.model.dto.OrderDto;
import javaweb.model.dto.UserCert;
import javaweb.service.OrderService;

@WebServlet("/order/*")
public class OrderServlet extends HttpServlet{

	private OrderService orderService = new OrderService();	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String pathInfo = req.getPathInfo() + "";
		HttpSession session = req.getSession();
		UserCert userCert = (UserCert)session.getAttribute("userCert");
		Integer userId = userCert.getUserId();
		
		switch (pathInfo) {
			case "/finish":		// 訂單結帳
				System.out.print(pathInfo);
				orderService.updateOrderStatus(userId, "Pending", "Finished");
				req.setAttribute("message", "購物車結帳完畢");
				req.getRequestDispatcher("/WEB-INF/view/result.jsp").forward(req, resp);
				break;
				
			case "/cancel":		// 訂單取消
				System.out.print(pathInfo);
				orderService.updateOrderStatus(userId, "Pending", "Cancel");
				req.setAttribute("message", "購物車取消完畢");
				req.getRequestDispatcher("/WEB-INF/view/result.jsp").forward(req, resp);
				break;
				
			case "/history":	// 歷史訂單
				// 包含已結帳、取消清單
				List<OrderDto> orderFinishedDtos = orderService.findAllOrders(userId, "Finished");	// 歷史訂單-結帳
				List<OrderDto> orderCancelDtos = orderService.findAllOrders(userId, "Cancel");		// 歷史訂單-取消
				req.setAttribute("orderFinishedDtos", orderFinishedDtos);
				req.setAttribute("orderCancelDtos", orderCancelDtos);
				req.getRequestDispatcher("/WEB-INF/view/history.jsp").forward(req, resp);
				break;
				
			default:
				req.getRequestDispatcher("/WEB-INF/view/order.jsp").forward(req, resp);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String[] productIds = req.getParameterValues("productId");	// 商品 ids
		String[] unitPrices = req.getParameterValues("unitPrice");	// 商品單價
		String[] amounts = req.getParameterValues("amount");		// 購買數量
		
		System.out.println(Arrays.toString(productIds) + "<- productIds");
		System.out.println(Arrays.toString(unitPrices) + "<- unitPrices");
		System.out.println(Arrays.toString(amounts) + "<- amouns");
		
		// 如何調用 
		HttpSession session = req.getSession();
		UserCert userCret = (UserCert)session.getAttribute("userCert");
		orderService.batchAddOrders(userCret.getUserId(), productIds, unitPrices, amounts);
		
		resp.getWriter().println("OK");
		
		// 外重導到購物車
		resp.sendRedirect("/javaweb/cart");
	}
	
	
}
