<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Shoppingmall</title>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="path" value="${pageContext.request.contextPath}" />

<script src="${path}/include/jquery-3.6.0.min.js"></script>
<script src="${path}/include/js/bootstrap.js"></script>
<link rel="stylesheet" href="${path}/include/css/bootstrap.css">
<style type="text/css">
#commentList_table {
	width: 100%;
	height: 300px;
}

#commentList_table td {
	border-bottom: 1px solid black;
}

#commentList_table .comment_profile {
	width: 20%;
}

#commentList_table .comment_profile .comment_img {
	height: 50px;
	width: auto;
}

#commentList_table .comment_content {
	width: 60%;
}

#commentList_table .comment_btn {
	width: 20%;
}

#commentList_table .comment_edit {
	display: none;
}

#commentList_table .comment_edit_content {
	width: 90%;
}

#commentList_table .comment_edit_content .content {
	width: 100%;
	border-radius: 0.5em;
	resize: none;
}

#commentList_table .btn_comment {
	width: 10%;
	border-radius: 0.5em;
	cursor: pointer;
}
</style>
</head>
<body>

<table id="commentList_table">
<c:forEach var="dto" items="${list}">
	<tr>
 		<td class="comment_profile">
			<img class="comment_img" alt="comment_img" src="/profile/${dto.image}" /><br>
			${dto.nickname}
 		</td>
 		<td class="comment_content">
 			${dto.content}
 		</td>
 		<td class="comment_btn">
 		<c:if test="${sessionScope.id == dto.writer}">
 			<input type="button" class="btn btn-primary" id="comment_editFormBtn${dto.id}" value="수정" />
 			<input type="button" class="btn btn-danger" id="comment_deleteBtn${dto.id}" value="삭제" />
 		<script>
 		$(function() {
 			$("#comment_editFormBtn${dto.id}").click(function() {
				$("#comment_edit${dto.id}").toggle();
 			});
 			
 			$("#comment_editBtn${dto.id}").click(function() {
 				location.href = "${path}/shoppingmall_board_servlet/commentEdit.do?comment_id=${dto.id}"
				+"&content="+$("#content${dto.id}").val()+"&board_id=${dto.board_id}";
 			});
 			
 			$("#comment_deleteBtn${dto.id}").click(function() {
 				location.href = "${path}/shoppingmall_board_servlet/commentDelete.do?comment_id=${dto.id}"
 				+"&board_id=${dto.board_id}";
 			});
 		});
 		</script>
 		</c:if>
 		</td>
	</tr>
	<tr id="comment_edit${dto.id}" class="comment_edit">
		<td class="comment_edit_content" colspan="2">
			<textarea rows="2" cols="80" placeholder="내용을 입력하세요" id="content${dto.id}" class="content"></textarea>
		</td>
		<td id="comment_editBtn${dto.id}" class="btn-primary btn_comment">
			<span class="btn-primary">수정<span>
		</td>
	</tr>
</c:forEach> 
</table>

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
	location.href= "${path}/shoppingmall_board_servlet/productView.do?board_id=${board_id}&curPage="+page;
}
</script>
</c:if>

</body>
</html>