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
}

.item .content_item {
	width: 60%;
	border-left: 1px solid black;
	border-right: 1px solid black;;
}

.item .basket_item {
	width: 20%;
	height: 40%;
}

#img_view {
	width: 70%;
	height: 100px;
}

#content_subject {
	text-align: left;
}

#content_price {
	text-align: left;
}

#content_count {
	text-align: left;
}

#edit_delete_btn {
	margin: 10px 0px;
	height: 33px;
	text-align: center;
}

#pagination_item {
	width: 500px;
	margin: 0 auto;
}

#content_price .salePercent {
	display: inline;
}

#content_price .oriPrice {
	text-decoration: line-through;
	display: inline;
}

#content_price .salePrice {
	color: red;
	display: inline;
}

#product_insert {
	text-align: center;
}
</style>
<section>
<c:forEach var="dto" items="${list}"> 
<div class="item">
	<div class="img_item">
		<a href="${path}/shoppingmall_board_servlet/productView.do?board_id=${dto.id}"><img id="img_view" src="/product/${dto.image}"></a>
	</div>
	<div class="content_item">
		<div id="content_subject">
			<h3>${dto.subject} (${dto.comment_count})</h3>
		</div>
		<div id="content_price">
			<c:if test="${dto.isSale == 'y' }">
				<h3 class="salePercent">${dto.sale_percent}% </h3> 
				<span class="oriPrice">
					<fmt:formatNumber value="${dto.price}" pattern="#,###" />
				</span>
				<h2 class="salePrice">
					<fmt:formatNumber value="${dto.sale_price}" pattern="#,###" />
				</h2>
			</c:if>
			<c:if test="${dto.isSale == 'n' }">
				<h2>
					<fmt:formatNumber value="${dto.price}" pattern="#,###" />
				</h2>
			</c:if>
		</div>
		<div id="content_count">
			<span id="content_like_count">
				추천수:${dto.like_count}
			</span>
			<span id="content_read_count">
				조회수:${dto.read_count}
			</span>
		</div>
	</div>
	<div class="basket_item">
		<div id="edit_delete_btn">
			<c:if test="${sessionScope.isAdmin == 'y'}">
				<button class="btn btn-primary" id="editBtn${dto.id}">수정</button>
				<button class="btn btn-danger" id="deleteBtn${dto.id}">삭제</button>
				
				<script>
				$(function() {
					$("#editBtn${dto.id}").click(function() {
						location.href="${path}/shoppingmall_board_servlet"
						+"/productEditView.do?board_id=${dto.id}&type=${param.type}";
					});
					
					$("#deleteBtn${dto.id}").click(function() {
						Swal.fire({
							title: '이 게시물을 삭제하시겠습니까?',
							showDenyButton: true,
							confirmButtonText: '삭제',
							denyButtonText: '아니오'
						}).then((result) => {
							if (result.isConfirmed) {
								location.href="${path}/shoppingmall_board_servlet"
								+"/productDelete.do?board_id=${dto.id}&type=${param.type}";
							} else if (result.isDenied) {}
						})
					});
				});
				</script>
			</c:if>
		</div>
		<div class="input-group">
			<input type="number" class="form-control" id="basket_num${dto.id}" name="basket_num${dto.id}">
			<input type="button" class="btn btn-primary" id="basket_btn${dto.id}" value="장바구니담기">
		</div>
	</div>
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
	location.href = "${path}/shoppingmall_board_servlet/searchList.do?type=${type}&curPage="+page;
}
</script>
</c:if>

<c:if test="${sessionScope.isAdmin == 'y'}">
<div id="product_insert">
	<a href="${path}/shoppingmall/product_insert.jsp" class="btn btn-primary">상품 등록</a>
</div>
</c:if>
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