<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<section>
<c:if test="${not empty sessionScope.id}">
<style>
#insert_wrapper {
	display: flex;
	flex-wrap: wrap;
	width: 100%;
	height: 100%;
}

#insert_form {
	display: flex;
	flex-wrap: wrap;
	width: 100%;
	height: 100%;
}

#insert_img_wrapper {
	width: 50%;
	display: flex;
	flex-wrap: wrap;
	align-items: center;
	justify-content: center;
	height: 100%;
}

#insert_input_wrapper {
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

#insert_img {
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

#sale_percent_input {
	display: none;
}
</style>
<div id="insert_wrapper">
<form action="${path}/shoppingmall_board_servlet/productInsert.do" method="post"
enctype="multipart/form-data" id="profile_insert_form">
<div id="insert_form">
	<div id="insert_img_wrapper">
			<img id="insert_img" src="/product/noImage.png">
			<input type="file" id="file1" name="file1" accept="image/*">
	</div>
	<div id="insert_input_wrapper">
		<table id="insert_table">
			<tr>
				<td class="input">
					<label for="subject" class="form-label">제목</label>
				</td>
				<td class="input">
					<input type="text" class="form-control" id="subject" name="subject">
				</td>
			</tr>
			<tr>
				<td class="alert" id="alert_subject" colspan="2"></td>
			</tr>
			<tr>
				<td class="input" colspan="2">
					<select id="type" name="type">
						<option value="food" selected>식품 & 생필품</option>
						<option value="home">가전제품</option>
						<option value="sport">스포츠</option>
					</select>
				</td>
			</tr>
			<tr>
				<td class="input">
					<label for="product_name" class="form-label">제품명</label>
				</td>
				<td class="input">
					<input type="text" class="form-control" id="product_name" name="product_name">
				</td>
			</tr>
			<tr>
				<td class="alert" id="alert_product_name" colspan="2"></td>
			</tr>
			<tr>
				<td class="input">
					<label for="price" class="form-label">가격</label>
				</td>
				<td class="input">
					<input type="number" class="form-control" id="price" name="price">
				</td>
			</tr>
			<tr>
				<td class="alert" id="alert_price" colspan="2"></td>
			</tr>
			<tr>
				<td class="input" colspan="2">
					<div class="form-check">
						<input class="form-check-input" type="checkbox" value="y" id="isSale">
						<label class="form-check-label" for="isSale">
							할인
						</label>
					</div>
				</td>
			</tr>
			<tr id="sale_percent_input">
				<td class="input">
					<label for="sale_percent" class="form-label">할인률</label>
				</td>
				<td class="input">
					<input type="number" class="form-control" id="sale_percent" name="sale_percent">
				</td>
			</tr>
			<tr>
				<td class="alert" id="alert_sale_percent" colspan="2"></td>
			</tr>
			<tr>
				<td class="input" id="btn" colspan="2">
					<input type="button" class="btn btn-primary" id="insertBtn" value="등록">
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
			$("#insert_img").attr("src", e.target.result);
		}
		
		reader.readAsDataURL(file);
	});
	
	$("#isSale").change(function() {
		if ($("#isSale").is(":checked")) {
			$("#sale_percent_input").show();
		} else {
			$("#sale_percent_input").hide();
		}
	});
	
	$("#insertBtn").click(function() {
		var isPermit = true;
		
		clear();
		
		if (empty_alert("#subject", "#alert_subject", "제목이 비어있습니다")) isPermit = false;
		if (empty_alert("#product_name", "#alert_product_name", "제품명이 비어있습니다")) isPermit = false;
		if (empty_alert("#price", "#alert_price", "가격이 비어있습니다")) isPermit = false;
		if ($("#isSale").is(":checked")) {
			if (empty_alert("#sale_percent", "#alert_sale_percent", "할인률 비어있습니다")) isPermit = false;
		}
		
		if (isPermit) $("#profile_insert_form").submit();
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