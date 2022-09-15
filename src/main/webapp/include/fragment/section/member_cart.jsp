<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style>
.item {
	display: flex;
	border-bottom: 1px solid black;
}

.item .img_item {
	width: 20%;
	display: flex;
	align-items: center;
	justify-content: center;
	border-right: 1px solid black;
}

.item .content {
	width: 15%;
}

.item .basket_item {
	width: 20%;
	text-align: center;
}

.img_view {
	width: 70%;
	height: 100px;
}

.content_price {
	text-align: left;
}

.content_count {
	text-align: left;
}

#pagination_item {
	width: 500px;
	margin: 0 auto;
}

#purchace_item {
	text-align: center;
}
</style>
<section>
<c:forEach var="dto" items="${list}"> 
<div class="item">
	<div class="img_item">
		<img class="img_view" src="/product/${dto.image}">
	</div>
	<div class="content">
		<h3>${dto.product_name}</h3>
	</div>
	<div class="content">
		<h2>
			<fmt:formatNumber value="${dto.price}" pattern="#,###" />
		</h2>
	</div>
	<div class="content">
		<h3>${dto.product_count}개</h3>
	</div>
	<div class="content">
		<h2>
			<fmt:formatNumber value="${dto.sum}" pattern="#,###" />
		</h2>
	</div>
	<c:if test="${not empty sessionScope.id}">
	<div class="basket_item">
		<input type="button" class="btn btn-danger" id="deleteBtn${dto.id}" value="삭제">
	</div>
	<script>
	$(function() {
		$("#deleteBtn${dto.id}").click(function() {
			Swal.fire({
				title: '이 장바구니를 삭제하시겠습니까?',
				showDenyButton: true,
				confirmButtonText: '삭제',
				denyButtonText: '아니오'
			}).then((result) => {
				if (result.isConfirmed) {
					var param="id=${dto.id}";
					$.ajax({
						type: "post",
						url: "${path}/shoppingmall_member_servlet/cartDelete.do",
						data: param,
						success: function(){
							history.go(0);
						}
					});
				} else if (result.isDenied) {}
			})
		});
	});
	</script>
	</c:if>
</div>
</c:forEach>

<c:if test="${page.totPage > 1}">
<div id="pagination">
	<ul class="pagination" id="pagination_item">
		<c:if test="${page.curPage > 1}">
	    	<li class="page-item"><a class="page-link" href="#" onclick="list('1')">처음</a></li>
    	</c:if>
    	<c:if test="${page.curBlock > 1}">
	    	<li class="page-item"><a class="page-link" href="#" onclick="list('${page.prevPage}')">&laquo;</a></li>
    	</c:if>
	    <c:forEach var="num" begin="${page.blockStart}" end="${page.blockEnd}">
			<c:choose>
				<c:when test="${num == page.curPage}">
					<li class="page-item active" aria-current="page">
						<a class="page-link" href="#">${num}</a>
					</li>
				</c:when>
				<c:otherwise>
					<li class="page-item"><a class="page-link" href="#" onclick="list('${num}')">${num}</a></li>
				</c:otherwise>
			</c:choose>
		</c:forEach>
		<c:if test="${page.curBlock < page.totBlock}">
	    	<li class="page-item"><a class="page-link" href="#" onclick="list('${page.nextPage}')">&raquo;</a></li>
    	</c:if>
    	<c:if test="${page.curPage < page.totPage}">
	    	<li class="page-item"><a class="page-link" href="#" onclick="list('${page.totPage}')">끝</a></li>
    	</c:if>
  	</ul>
</div>
<script>
function list(page) {
	location.href= "${path}/shoppingmall_member_servlet/cartList.do?curPage="+page;
}
</script>
</c:if>

<div id="purchace_item">
	<h2>
		총 가격 : <fmt:formatNumber value="${total_price}" pattern="#,###" />
	</h2>
	<input type="button" id="purchaseBtn" class="btn btn-danger" value="구입">
</div>

<c:if test="${not empty param.success}">
	<script type="text/javascript">
		Swal.fire({
			icon: 'success',
			title: '${param.success}',
			showCloseButton: true
		})
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