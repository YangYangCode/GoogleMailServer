<%@page import="javaweb.model.dto.UserDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
UserDto userDto = (UserDto) request.getAttribute("userDto");
%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Insert title here</title>
		<link rel="stylesheet"
			href="https://cdn.jsdelivr.net/npm/purecss@3.0.0/build/pure-min.css">
		<link rel="stylesheet" href="/javaweb/css/buttons.css">
	</head>
	
	<body>
		<!-- menu bar include -->
		<%@ include file="/WEB-INF/view/menu.jspf"%>
	
		<div style="padding: 15px">
			<table style="padding: 15px">
				<td valign="top">
					<form class="pure-form" method="post"
						action="/javaweb/product/update">
						<fieldset>
							<legend>User 修改</legend>
							商品ID: <input type="text" name="productId"
								value="${ productDto.productId }" readonly> 
							<p />
							商品名稱: <input type="text" name="productName"
								value="${ productDto.productName }" readonly>
							<p />
							商品價格: <input type="text" name="old_price"
								value="${ productDto.price }" readonly>
							更新價格: <input type="number" name="price"
								style="width: 100px" value="${ productDto.price }" min="0">
							<p />
							庫存數量: <input type="text" name="old_stockQuantity"
								value="${ productDto.stockQuantity }" readonly>
							更新數量: <input type="number" name="stockQuantity"
								style="width: 100px" value="${ productDto.stockQuantity }" min="0">
							<p />
							<button type="submit" class="button-success pure-button">Update</button>
						</fieldset>
					</form>
				</td>
			</table>
		</div>
	</body>
</html>