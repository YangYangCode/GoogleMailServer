package javaweb.servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 執行網址"http://localhost:8080/javaweb/bmi?h=170&w=60"   // ?h=170&w=60 (帶入參數)
 * 得到bmi的資料
 **/

@WebServlet("/bmi")

public class BMIServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		// 取得?後的參數
		String h= req.getParameter("h");
		String w= req.getParameter("w");
		
		if (h==null || w==null) {
			resp.setCharacterEncoding("UTF-8");
			// resp.setContentType("text/plain;charse=utf-8");
			// resp.getWriter().print("請輸入身高與體重的參數");
			
			req.setAttribute("message", "請輸入身高與體重");
			req.getRequestDispatcher("WEB-INF/view/error.jsp").forward(req, resp);
			return;
		}
		
		// 計算bmi
		double height = Double.parseDouble(h);
		double weight = Double.parseDouble(w);
		double bmi = weight / Math.pow(height/100, 2); //bmi公式
		
		// 將資料傳給jsp
		req.setAttribute("height", height);
		req.setAttribute("weight", weight);
		req.setAttribute("bmi", bmi);
		
		// 透過jsp印出資料
		req.getRequestDispatcher("/WEB-INF/view/bmi.jsp").forward(req, resp);
	}

	
	
}
