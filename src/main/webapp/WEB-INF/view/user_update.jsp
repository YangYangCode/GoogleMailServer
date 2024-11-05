<%@page import="javaweb.model.dto.UserDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
 <%
 	UserDto userDto = (UserDto)request.getAttribute("userDto");
 %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Insert title here</title>
		<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/purecss@3.0.0/build/pure-min.css">
		<link rel="stylesheet" href="/javaweb/css/buttons.css">
	</head>
	
		<body>
		<!-- menu bar include -->
		<%@ include file="/WEB-INF/view/menu.jspf" %>
		
		<div style="padding: 15px">
			<table style="padding: 15px">
			<td valign="top">
			<form class="pure-form" method="post" action="/javaweb/user/update">
						<fieldset>
							<legend>User 修改</legend>
							序號: <input type="text" name="userId" value="<%=userDto.getUserId() %>" readonly /><p /> 
							帳號: <input type="text" name="username" value="<%=userDto.getUsername() %>" disabled /><p /> 
							電郵: <input type="email" name="email" value="<%=userDto.getEmail() %>" disabled /><p />
							狀態: <input type="radio" name="active" value="True" <%=userDto.getActive()?"checked":"" %>/>True
								  <input type="radio" name="active" value="False" <%=userDto.getActive()?"":"checked" %>/>False <p />
								  
							權限: <select type="text" name="role" >
									<option value="ROLE_ADMIN" <%=userDto.getRole().equals("ROLE_ADMIN")?"selected":"" %>>ADMIN</option>
									<option value="ROLE_USER" <%=userDto.getRole().equals("ROLE_USER")?"selected":"" %>>USER</option>
									value="<%=userDto.getRole() %>" /><p />
								  </select>
							<button type="submit" class="button-success pure-button">Update</button>	  
						</fieldset>
					</form>
			</td>
				
			<td style="padding-left: 30px" valign="top">
			<form>
				<fieldset>
					<legend>User 修改密碼</legend>
							<input type="hidden" name="userId" value="<%=userDto.getUserId() %>" readonly /><p /> 
							<input type="hidden" name="username" value="<%=userDto.getUsername() %>" readonly /><p />
							舊密碼: <input type="password" name="password" ><p /> 
							新密碼: <input type="password" name="newPassword" ><p /> 
							<button type="submit" class="button-success pure-button">Update Password</button>	
				</fieldset>
			</form>
			</td>
			</table>
		</body>
	</div>
</html>