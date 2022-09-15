<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style>
#signup_table {
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
<section>
<form action="${path}/shoppingmall_member_servlet/signup.do" id="signup_form" method="post">
<table id="signup_table">
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
		<td class="input">
			<label for="crf_passwd" class="form-label">비밀번호 확인</label>
		</td>
		<td class="input">
			<input type="password" class="form-control" id="crf_passwd" name="crf_passwd">
		</td>
	</tr>
	<tr>
		<td class="input">
			<label for="nickname" class="form-label">닉네임</label>
		</td>
		<td class="input">
			<input type="text" class="form-control" id="nickname" name="nickname">
		</td>
	</tr>
	<tr>
		<td class="alert" id="alert_nickname" colspan="2"></td>
	</tr>
	<tr>
		<td class="input">
			<label for="email" class="form-label">이메일</label>
		</td>
		<td class="input">
			<input type="text" class="form-control" id="email" name="email">
		</td>
	</tr>
	<tr>
		<td class="alert" id="alert_email" colspan="2"></td>
	</tr>
	<tr>
		<td class="input">
			<label for="tel" class="form-label">전화번호</label>
		</td>
		<td class="input">
			<input type="tel" class="form-control" id="tel" name="tel">
		</td>
	</tr>
	<tr>
		<td class="alert" id="alert_tel" colspan="2"></td>
	</tr>
	<tr>
		<td class="input">
			<label for="address" class="form-label">주소</label>
		</td>
		<td class="input">
			<input type="text" class="form-control" id="address" name="address">
		</td>
	</tr>
	<tr>
		<td class="alert" id="alert_address" colspan="2"></td>
	</tr>
	<tr>
		<td class="input" id="btn" colspan="2">
			<input type="button" class="btn btn-primary" id="signupBtn" value="가입">
		</td>
	</tr>
</table>
</form>
<script type="text/javascript">
$(function() {
	$("#signupBtn").click(function() {
		var isPermit = true;
		
		clear();
		
		if (empty_alert("#id", "#alert_id", "아이디가 비어있습니다")) isPermit = false;
		if (empty_alert("#passwd", "#alert_passwd", "비밀번호가 비어있습니다")) isPermit = false;
		if (empty_alert("#crf_passwd", "#alert_passwd", "비밀번호가 비어있습니다")) isPermit = false;
		if (empty_alert("#email", "#alert_email", "이메일이 비어있습니다")) isPermit = false;
		if (empty_alert("#nickname", "#alert_nickname", "닉네임이 비어있습니다")) isPermit = false;
		if (empty_alert("#tel", "#alert_tel", "전화번호가 비어있습니다")) isPermit = false;
		if (empty_alert("#address", "#alert_address", "주소가 비어있습니다")) isPermit = false;
		
		if (isPermit) $("#signup_form").submit();
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