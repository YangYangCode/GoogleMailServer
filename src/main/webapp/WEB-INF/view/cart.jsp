<%@page import="javaweb.model.dto.UserDto"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %> <!-- 核心庫 -->
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %> <!-- 格式化庫 -->
<%@ taglib uri="jakarta.tags.functions" prefix="fn" %> <!-- 功能庫 -->

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Cart 購物車</title>
		<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/purecss@3.0.0/build/pure-min.css">
		<link rel="stylesheet" href="/javaweb/css/buttons.css">
	</head>
	
	
	
	<body>
	<!-- menu bar include -->
	<%@ include file="/WEB-INF/view/menu.jspf" %>
	
	<!-- body content -->
	<div style="padding: 15px">
		<div class="pure-form">
			<fieldset>
				<legend>${userCert.username} 的 Cart 購物車</legend>
				<table class="pure-table pure-table-bordered">
					<thead>
						<tr>
							<th>訂單Id</th><th>使用者Id</th><th>訂購日期</th><th>商品Id</th>
							<th>商品名稱</th><th>商品單價</th><th>訂購數量</th><th>小計</th>
							<th>訂單狀態</th>
						</tr>
					</thead>
					<tbody>
						<!-- 初始化"總計"變數 -->
						<c:set var="total" value="0" />
						<!-- 設定所在地 -->
						<fmt:setLocale value="${ locale }" />
						<c:forEach var="orderDto" items="${ orderDtos }">
							<tr>
								<td align="center">${ orderDto.orderId }</td>		<%-- 等同 <%=userDto.getuserId() %> --%>
								<td align="center">${ orderDto.userId }</td>
								<td>${ orderDto.orderDate }</td>
								<td align="center">${ orderDto.productId }</td>
								<td>${ orderDto.productName }</td>
								<td align="right">
									<fmt:formatNumber value="${ orderDto.unitPrice }" type="currency" maxFractionDigits="0" />
								</td>
								<td align="right">${ orderDto.quantity }</td>
								<td align="right">
									<fmt:formatNumber value="${ orderDto.subtotal }" type="currency" maxFractionDigits="0" />
								</td>
								<td>${ orderDto.orderStatus }</td>
							</tr>
						<!-- 累計 -->
						<c:set var="total" value="${ total + orderDto.subtotal }" />
						</c:forEach>
						<!-- 顯示 -->
						<tr>
							<td colspan="7" align="right">總計 (使用者位置)</td>
							<td align="right">
								<strong>
									<fmt:formatNumber value="${ total }" type="currency" />
								</strong>
							</td>
							<td></td>
						</tr>
						
						<tr>
							<td colspan="7" align="right">總計 (使用者位置+指定數值)</td>
							<td align="right">
								<strong>
									<fmt:formatNumber value="${ total }" type="currency" maxFractionDigits="0" />
								</strong>
							</td>
							<td></td>
						</tr>
						
						<tr>
							<td colspan="7" align="right">總計 (指定形式)</td>
							<td align="right">
								<strong>
									$<fmt:formatNumber value="${ total }" pattern="####,####" />
								</strong>
							</td>
							<td></td>
						</tr>
						<!-- 結帳/取消 -->
						<tr style="display: ${ fn:length(orderDtos) == 0? 'none':'' };">
							<td colspan="7" align="right">結帳</td>
							<td><a href="/javaweb/order/finish" class="button-success pure-button">結帳</a></td>
							<td><a href="/javaweb/order/cancel" class="button-warning pure-button">取消</a></td>
						</tr>
					</tbody>
				</table>
			</fieldset>
		</div>
	</div>
	</body>
	
</html>