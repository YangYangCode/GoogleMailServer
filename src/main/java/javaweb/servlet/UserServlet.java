package javaweb.servlet;

import java.io.IOException;
import java.util.List;

import com.mysql.cj.exceptions.PasswordExpiredException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import javaweb.exception.UserNotFoundException;
import javaweb.model.dto.UserCert;
import javaweb.model.dto.UserDto;
import javaweb.service.UserService;

/**
 * request +-------------+ +-------------+ +---------+ ----------> | UserServlet
 * | -----> | UserService | ------> | UserDao |-----> MySQL | (Controller)| | |
 * <------ | |<-----(web.users) +-------------+ UserDto +-------------+ entity
 * +---------+ | (Dto) (Entity) | v +-------------+ <-------- | user.jsp |
 * response | (view) | +-------------+
 * 
 * 查詢所有: GET /user, /users 新增單筆: POST /user/add 查詢單筆: GET
 * /user/get?username=admin 修改單筆: POST /user/update?userId=1 刪除單筆: GET
 * /user/delete?userId=1 修改密碼: GET /user/update/password (得到修改密碼頁面) 修改密碼: POST
 * /user/update/password (修改密碼處理程序)
 */

@WebServlet(urlPatterns = { "/user/*", "/users" })
public class UserServlet extends HttpServlet {

	private UserService userService = new UserService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String pathInfo = req.getPathInfo();
		// resp.getWriter().print(pathInfo); // 看輸入的關鍵字
		if (pathInfo == null || pathInfo.equals("/*")) {
			// 查詢全部
			List<UserDto> userDtos = userService.findAll();
			// resp.getWriter().print(userDtos);
			// 將必要資料加入到 request 屬性中，以便交由 jsp 進行分析與呈現
			req.setAttribute("userDtos", userDtos);
			// "內"重導到 user.jsp (可帶資料)
			req.getRequestDispatcher("/WEB-INF/view/user.jsp").forward(req, resp);
			return;

		} else if (pathInfo.equals("/delete")) {
			String userId = req.getParameter("userId");
			userService.deleteUser(userId);

			// 刪除完畢之後，重新執行指定頁面
			resp.sendRedirect("/javaweb/users");
			return;

		} else if (pathInfo.equals("/get")) { // 取得user 資料並導入到修改頁面
			String username = req.getParameter("username");
			UserDto userDto = userService.getUser(username);
			// 將必要資料加入到 request 屬性中，以便交由 jsp 進行分析與呈現
			req.setAttribute("userDto", userDto);
			// "內"重導到 user.jsp (可帶資料)
			req.getRequestDispatcher("/WEB-INF/view/user_update.jsp").forward(req, resp);
			return;

		} else if (pathInfo.equals("/update/password")) {
			req.getRequestDispatcher("/WEB-INF/view/update_password.jsp").forward(req, resp);
			return;
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String pathInfo = req.getPathInfo();

		// 取得表單資料
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		String email = req.getParameter("email");
		String role = req.getParameter("role");
		String active = req.getParameter("active"); // update專用
		String userId = req.getParameter("userId"); // update專用
		String oldPassword = req.getParameter("oldPassword"); // update/password 專用
		String newPassword = req.getParameter("newPassword"); // update/password 專用

		switch (pathInfo) {
		case "/add":
			userService.appendUser(username, password, email, role);
			break;

		case "/update":
			userService.updateUser(userId, active, role);
			break;

		case "/update/password":

			// 修改密碼需在已登入環境下
			HttpSession session = req.getSession();
			try {
				UserCert userCert = (UserCert)session.getAttribute("userCert"); // 取得 session 登入憑證
				userService.updatePassword(userCert.getUserId(), userCert.getUsername(), oldPassword, newPassword);
				// 修改完成後訊息
				req.setAttribute("message", "密碼更新成功");
				req.getRequestDispatcher("/WEB-INF/view/result.jsp").forward(req, resp);

			} catch (Exception e) {
				req.setAttribute("message", e.getMessage());
				req.getRequestDispatcher("/WEB-INF/view/error.jsp").forward(req, resp);
			}

			return; // 直接跳出，不執行下方程式
			
		}

		// "外"重導到指定URL網頁(不可帶資料，單純重新進入網頁)
		// resp.sendRedirect("http://localhost:8080/javaweb/user"); (因為同伺服器，同下方網址)
		resp.sendRedirect("/javaweb/user");
	}

}
