<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style>
#view_form {
	display: flex;
	flex-wrap: wrap;
	width: 100%;
	height: 300px;
}

#view_img {
	width: 50%;
	display: flex;
	flex-wrap: wrap;
	align-items: center;
	justify-content: center;
}

#view_input {
	width: 50%;
}

#view_btn {
	width: 100%;
	text-align: center;
}

#img_view {
	width: 70%;
	height: 300px;
}

#view_table .input {
	padding-top: 10px;
}

#salePercent, #salePrice {
	color: red;
}

#salePrice, #oriPrice {
	display: inline;
}

#oriPrice {
	text-decoration: line-through;
}

#img_like {
	height: 50px;
	width: auto;
}

#comment_list {
	margin-top: 20px;
}

#comment_table {
	margin-top: 10px;
	width: 100%;
}

#comment_table #content_comment {
	width: 90%;
}

#comment_table #btn_comment {
	width: 10%;
	border-radius: 0.5em;
	cursor: pointer;
}

#comment_table #content_comment #content {
	width: 100%;
	border-radius: 0.5em;
	resize: none;
}
</style>
<section>
<div id="view_form">
	<div id="view_img">
		<img id="img_view" src="/product/${dto.image}">
	</div>
	<div id="view_input">
		<table id="view_table">
			<tr>
				<td class="input">
					<h3>${dto.subject}</h3>
				</td>
				<td class="input">
				<c:choose>
					<c:when test="${dto.type == 'home'}">
						(가전제품)
					</c:when>
					<c:when test="${dto.type == 'sport'}">
						(스포츠)
					</c:when>
					<c:otherwise>
						(식품 & 생필품)
					</c:otherwise>
				</c:choose>
				</td>
			</tr>
			<tr>
				<td colspan="2" class="input">
					<h6>${dto.product_name}</h6>
				</td>
			</tr>
			<tr>
				<td class="input">
					조회수 : ${dto.read_count}
				</td>
				<td class="input">
					추천수 : ${dto.like_count}
				</td>
			</tr>
			<tr>
				<td colspan="2" class="input">
				<c:if test="${dto.isSale == 'n'}">
					<h2>
						<fmt:formatNumber value="${dto.price}" pattern="#,###" />
					</h2>
				</c:if>
				<c:if test="${dto.isSale == 'y'}">
					<span id="salePercent">${dto.sale_percent}% </span>
					<h2 id="salePrice">
						<fmt:formatNumber value="${salePrice}" pattern="#,###" />
					</h2>
					<span id="oriPrice">
						<fmt:formatNumber value="${dto.price}" pattern="#,###" />
					</span>
				</c:if>
				</td>
			</tr>
			<tr>
				<td colspan="2" class="input">
					<button id="view_like"><img id="img_like" alt="like" src="${path}/include/images/icon/like.png"></button>
				</td>
			</tr>
			<tr>
				<td class="input">
					<input type="number" class="form-control" id="basketNum">
				</td>
				<td class="input">
					<input type="button" class="btn btn-primary" id="basketBtn" value="장바구니담기">
				</td>
			</tr>
		</table>
	</div>
</div>
<div id="comment_list"></div>
<c:if test="${not empty sessionScope.id}">
<table id="comment_table">
	<tr>
		<td id="content_comment">
			<textarea rows="2" cols="80" placeholder="내용을 입력하세요" id="content"></textarea>
		</td>
		<td class="btn-primary" id="btn_comment">
			<span class="btn-primary">등록<span>
		</td>
	</tr>
</table>
</c:if>
<script>
$(function() {
	comment_list();
	
	$("#basketBtn").click(function() {
		var param="board_id=${dto.id}&num="+$("#basketNum").val();
		$.ajax({
			type: "post",
			url: "${path}/shoppingmall_member_servlet/cartInsert.do",
			data: param,
			success: function(){
				Swal.fire({
					icon: 'success',
					title: '상품을 장바구니에 넣었습니다',
					showCloseButton: true
				})
			}
		});
	});
	
	$("#btn_comment").click(function() {
		comment_add();
	});
	
	$("#view_like").click(function() {
		location.href="${path}/shoppingmall_board_servlet/productPlusLike.do?board_id=${dto.id}";
	});
});

function comment_add(){
	var param="board_id=${dto.id}&content="+$("#content").val();
	$.ajax({
		type: "post",
		url: "${path}/shoppingmall_board_servlet/commentAdd.do",
		data: param,
		success: function(){
			$("#content").val("");
			comment_list();
		}
	});
}

function comment_list(){
	$.ajax({
		type: "post",
		url: "${path}/shoppingmall_board_servlet/commentList.do",
		data: "board_id=${dto.id}&curPage=${curPage}",
		success: function(result){
			$("#comment_list").html(result);
		}
	});
}
</script>
</section>