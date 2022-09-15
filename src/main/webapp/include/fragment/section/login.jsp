<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<section>
<c:if test="${empty sessionScope.id}">
<style>
#login_table {
	margin: auto;
}

.input {
	padding-top: 10px;
}

.alert {
	color: red;
	display: none;
}

#btn {
	text-align: center;
}
</style>
<form action="${path}/shoppingmall_member_servlet/login.do" method="post" id="login_form">
<table id="login_table">
	<tr>
		<td class="input">
			<label for="id" class="form-label">아이디</label>
		</td>
		<td class="input">
			<input type="text" class="form-control" id="id" name="id">
		</td>
	</tr>
	<tr>
		<td class="alert" id="alert_id" colspan="2"></td>
	</tr>
	<tr>
		<td class="input">
			<label for="passwd" class="form-label">비밀번호</label>
		</td>
		<td class="input">
			<input type="password" class="form-control" id="passwd" name="passwd">
		</td>
	</tr>
	<tr>
		<td class="alert" id="alert_passwd" colspan="2"></td>
	</tr>
	<tr>
		<td class="input" id="btn" colspan="2">
			<input type="button" class="btn btn-primary" id="loginBtn" value="로그인">
		</td>
	</tr>
</table>
</form>
<script type="text/javascript">
$(function() {
	$("#loginBtn").click(function() {
		var isPermit = true;
		
		clear();
		
		if (empty_alert("#id", "#alert_id", "아이디가 비어있습니다")) isPermit = false;
		if (empty_alert("#passwd", "#alert_passwd", "비밀번호가 비어있습니다")) isPermit = false;
		
		if (isPermit) $("#login_form").submit();
	});
});

function clear() {
	$(".alert").hide();
}

function empty_alert(id, alert, message) {
	if ($(id).val() == '') {
		$(alert).show();
		$(alert).text(message);
		return true;
	}
	return false;
}
</script>
</c:if>
<c:if test="${not empty param.error}">
	<script type="text/javascript">
		Swal.fire({
			icon: 'error',
			title: '${param.error}',
			showCloseButton: true
		})
	</script>
</c:if>
</section>