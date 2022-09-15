<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style>
#password_edit_table {
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
<form action="${path}/shoppingmall_member_servlet/profilePasswordEdit.do" 
id="password_edit_form" method="post">
<table id="password_edit_table">
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
		<td class="input" id="btn" colspan="2">
			<input type="button" class="btn btn-primary" id="passwordEditBtn" value="비밀번호 수정">
		</td>
	</tr>
</table>
</form>
<script type="text/javascript">
$(function() {
	$("#passwordEditBtn").click(function() {
		var isPermit = true;
		
		clear();
		
		if (empty_alert("#passwd", "#alert_passwd", "비밀번호가 비어있습니다")) isPermit = false;
		if (empty_alert("#crf_passwd", "#alert_passwd", "비밀번호가 비어있습니다")) isPermit = false;
		
		if (isPermit) $("#password_edit_form").submit();
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