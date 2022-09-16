<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<aside>
<c:if test="${not empty sessionScope.id}">
<style>
#login_wrapper {
	display: flex;
	justify-content: center;
	align-items: center;
	height: 100%;
}

#login_item {
	text-align: center;
}

#profile_img {
	width: 80%;
	height: auto;
}
</style>
<div id="login_wrapper">
	<div id="login_item">
		<div id="login_img">
			<img id="profile_img" src="/profile/${sessionScope.image}">
		</div>
		<div id="login_nickname">
			<span>
				${sessionScope.nickname}님 반갑습니다
			</span>
		</div>
		<div id="login_btn">
			<a href="${path}/shoppingmall_member_servlet/editView.do" class="btn btn-primary">수정하기</a>
			<a href="${path}/shoppingmall_member_servlet/logout.do" class="btn btn-danger">로그아웃</a>
		</div>
	</div>
</div>
<script>
$(function () {
	$("aside").show();
})
</script>
</c:if>
</aside>