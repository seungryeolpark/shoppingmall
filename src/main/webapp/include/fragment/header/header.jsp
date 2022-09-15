<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style>
.link {
	text-decoration: none;
	color: black;
}

#Shm_cart {
	width: 50px;
	height: auto;
}

#title {
	color: red;
	font-weight: bold;
	font-family: Impact;
	margin-top: 10px;
}
</style>
<header>
<div class="row">
	<div class="col-4">
		<span id="header_menu">
			<c:if test="${empty sessionScope.id}">
				<a class="link" href="${path}/shoppingmall/signup.jsp">회원가입</a>
				<a class="link" href="${path}/shoppingmall/login.jsp">로그인</a>
			</c:if>
			<c:if test="${not empty sessionScope.id}">
				<a class="link" href="${path}/shoppingmall_member_servlet/cartList.do">장바구니</a>
			</c:if>
		</span>
	</div>
	<div class="col-7">
		<img alt="Shoppingmall_cart" src="${path}/include/images/icon/shopping_cart.png" 
		id="Shm_cart" align="left">
		<a class="link" href="${path}/shoppingmall/index.jsp">
			<h2 id="title">Shoppingmall</h2>
		</a>
	</div>
	<div class="col-1"></div>
</div>
</header>
<!-- ../images/icon/shopping_cart.png -->