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

#sale_percent_input {
	display: none;
}
</style>
<div id="edit_wrapper">
<form action="${path}/shoppingmall_board_servlet/productEdit.do" method="post"
enctype="multipart/form-data" id="product_edit_form">
<input type="hidden" id="board_id" name="board_id" value="${dto.id}">
<div id="edit_form">
	<div id="edit_img_wrapper">
			<img id="edit_img" src="/product/${dto.image}">
			<input type="file" id="img_file" name="img_file" accept="image/*">
	</div>
	<div id="edit_input_wrapper">
		<table id="edit_table">
			<tr>
				<td class="input">
					<label for="subject" class="form-label">제목</label>
				</td>
				<td class="input">
					<input type="text" class="form-control" id="subject" name="subject" value="${dto.subject}">
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
					<input type="text" class="form-control" id="product_name" name="product_name" value="${dto.product_name}">
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
					<input type="number" class="form-control" id="price" name="price" value="${dto.price}">
				</td>
			</tr>
			<tr>
				<td class="alert" id="alert_price" colspan="2"></td>
			</tr>
			<tr>
				<td class="input" colspan="2">
					<div class="form-check">
						<input class="form-check-input" type="checkbox" value="y" id="isSale" name="isSale">
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
					<input type="number" class="form-control" id="sale_percent" name="sale_percent" value="${dto.sale_percent}">
				</td>
			</tr>
			<tr>
				<td class="alert" id="alert_sale_percent" colspan="2"></td>
			</tr>
			<tr>
				<td class="input" id="btn" colspan="2">
					<input type="button" class="btn btn-primary" id="editBtn" value="수정">
				</td>
			</tr>
		</table>
	</div>
</div>
</form>
</div>
<script>
$(function() {
	$("#img_file").on("change", function(event) {
		var file = event.target.files[0];
		
		var reader = new FileReader();
		reader.onload = function(e) {
			$("#edit_img").attr("src", e.target.result);
		}
		
		reader.readAsDataURL(file);
	});
	
	if ("${dto.isSale}" == 'y') {
		$("input:checkbox[id='isSale']").prop("checked", true);
		$("#sale_percent_input").show();
	} else {
		$("input:checkbox[id='isSale']").prop("checked", false);
	}
	
	$("#isSale").change(function() {
		if ($("#isSale").is(":checked")) {
			$("#sale_percent_input").show();
		} else {
			$("#sale_percent_input").hide();
		}
	});
	
	$("#editBtn").click(function() {
		var isPermit = true;
		
		clear();
		
		if (empty_alert("#subject", "#alert_subject", "제목이 비어있습니다")) isPermit = false;
		if (empty_alert("#product_name", "#alert_product_name", "제품명이 비어있습니다")) isPermit = false;
		if (empty_alert("#price", "#alert_price", "가격이 비어있습니다")) isPermit = false;
		if ($("#isSale").is(":checked")) {
			if (empty_alert("#sale_percent", "#alert_sale_percent", "할인률 비어있습니다")) isPermit = false;
		}
		
		if (isPermit) $("#product_edit_form").submit();
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