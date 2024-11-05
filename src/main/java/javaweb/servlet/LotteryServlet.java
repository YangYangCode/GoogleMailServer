package javaweb.servlet;

import java.io.IOException;
import java.util.Random;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/* 
 * 模式: Model 1
 * Servlet負責邏輯
 * JSP負責呈現
 * 
 */

// @WebServlet(urlPatterns = {"/lottery"})
@WebServlet("/lottery")
public class LotteryServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		// 產生電腦選號(四星彩)
		Random random = new Random();
		int n1 = random.nextInt(10); // 0-9的隨機數
		int n2 = random.nextInt(10); // 0-9的隨機數
		int n3 = random.nextInt(10); // 0-9的隨機數
		int n4 = random.nextInt(10); // 0-9的隨機數
		
		// 利用req.setAttribute() 將資訊傳遞給jsp
		req.setAttribute("n1", n1);
		req.setAttribute("n2", n2);
		req.setAttribute("n3", n3);
		req.setAttribute("n4", n4);
		
		// 利用RequestDispatcher 重導到制定jsp
		req.getRequestDispatcher("/WEB-INF/view/lottery.jsp").forward(req, resp);


	}
	
	
}
