<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style>
#search,#searchBtn {
	border: 1px solid black;
}
</style>
<nav>
<div class="row">
	<div class="col-11">
		<div class="input-group">
			<select id="search_option">
				<option value="all" selected>전체</option>
				<option value="food">식품 & 생필품</option>
				<option value="home">가전제품</option>
				<option value="sport">스포츠</option>
			</select>
			<input type="text" class="form-control" id="search">
			<button id="searchBtn" class="btn" type="button">
				<img alt="search" src="${path}/include/images/icon/search.png"
				width="33" height="33">
			</button>
		</div>
	</div>
	<div class="col-1"></div>
</div>
<div class="navbar navbar-expand-lg navbar-dark bg-dark">
  <div class="container-fluid">
    <a class="navbar-brand" href="#">메뉴</a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNavDropdown" aria-controls="navbarNavDropdown" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNavDropdown">
      <ul class="navbar-nav">
        <li class="nav-item">
          <a class="nav-link" href="${path}/shoppingmall/index.jsp">Home</a>
        </li>
        <li class="nav-item dropdown">
          <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
            상품
          </a>
          <ul class="dropdown-menu">
            <li><a class="dropdown-item" href="${path}/shoppingmall_board_servlet/list.do?type=food">식품 & 생필품</a></li>
            <li><a class="dropdown-item" href="${path}/shoppingmall_board_servlet/list.do?type=home">가전제품</a></li>
            <li><a class="dropdown-item" href="${path}/shoppingmall_board_servlet/list.do?type=sport">스포츠</a></li>
          </ul>
        </li>
      </ul>
    </div>
  </div>
</div>

<script type="text/javascript">
$(function() {
	$("#searchBtn").click(function() {
		var search_option = $("#search_option").val();
		var search = $("#search").val();
		location.href = "${path}/shoppingmall_board_servlet/searchList.do?search_option="+search_option
		+"&search="+search;
	});
});
</script>

</nav>