<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<section>
<c:if test="${not empty sessionScope.id}">
<style>
#edit_wrapper {
	display: flex;
	flex-wrap: wrap;
	width: 100%;
	height: 100%;
}

#edit_form {
	display: flex;
	flex-wrap: wrap;
	width: 100%;
	height: 100%;
}

#edit_img_wrapper {
	width: 50%;
	display: flex;
	flex-wrap: wrap;
	align-items: center;
	justify-content: center;
	height: 100%;
}

#edit_input_wrapper {
	width: 50%;
	display: flex;
	flex-wrap: wrap;
	align-items: center;
	justify-content: center;
	height: 100%;
}

#login_item {
	text-align: center;
}

#edit_img {
	height: auto;
	width: 80%;
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
<div id="edit_wrapper">
<form action="${path}/shoppingmall_member_servlet/profileEdit.do" method="post"
enctype="multipart/form-data" id="profile_edit_form">
<div id="edit_form">
	<div id="edit_img_wrapper">
			<img id="edit_img" src="/profile/${dto.image}">
			<input type="file" id="file1" name="file1" accept="image/*">
	</div>
	<div id="edit_input_wrapper">
		<table id="edit_table">
			<tr>
				<td class="input">
					<label for="nickname" class="form-label">닉네임</label>
				</td>
				<td class="input">
					<input type="text" class="form-control" id="nickname" name="nickname"
					value="${dto.nickname}">
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
					<input type="text" class="form-control" id="email" name="email"
					value="${dto.email}">
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
					<input type="tel" class="form-control" id="tel" name="tel"
					value="${dto.tel}">
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
					<input type="text" class="form-control" id="address" name="address"
					value="${dto.address}">
				</td>
			</tr>
			<tr>
				<td class="alert" id="alert_address" colspan="2"></td>
			</tr>
			<tr>
				<td class="input" id="btn" colspan="2">
					<input type="button" class="btn btn-primary" id="editBtn" value="수정">
					<input type="button" class="btn btn-primary" id="passwdEditBtn" value="비밀번호 수정">
					<input type="button" class="btn btn-danger" id="deleteBtn" value="삭제">
				</td>
			</tr>
		</table>
	</div>
</div>
</form>
</div>
<script>
$(function() {
	$("#file1").on("change", function(event) {
		var file = event.target.files[0];
		
		var reader = new FileReader();
		reader.onload = function(e) {
			$("#edit_img").attr("src", e.target.result);
		}
		
		reader.readAsDataURL(file);
	});
	
	$("#editBtn").click(function() {
		var isPermit = true;
		
		clear();
		
		if (empty_alert("#email", "#alert_email", "이메일이 비어있습니다")) isPermit = false;
		if (empty_alert("#nickname", "#alert_nickname", "닉네임이 비어있습니다")) isPermit = false;
		if (empty_alert("#tel", "#alert_tel", "전화번호가 비어있습니다")) isPermit = false;
		if (empty_alert("#address", "#alert_address", "주소가 비어있습니다")) isPermit = false;
		
		if (isPermit) $("#profile_edit_form").submit();
	});
	
	$("#passwdEditBtn").click(function() {
		location.href = "${path}/shoppingmall/profile_password_edit.jsp";
	});
	
	$("#deleteBtn").click(function() {
		location.href = "${path}/shoppingmall_member_servlet/profileDelete.do";
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
</section>