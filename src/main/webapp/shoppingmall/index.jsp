<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Insert title here</title>
<%@ include file="../include/fragment/head/head.jsp" %>
</style>
</head>
<body>
<%@ include file="../include/fragment/header/header.jsp" %>
<%@ include file="../include/fragment/nav/nav.jsp" %>
<div id="content_wrapper">
<%@ include file="../include/fragment/aside/aside.jsp" %>
<section></section>
</div>
<%@ include file="../include/fragment/footer/footer.jsp" %>
</body>
<c:if test="${not empty param.success}">
	<script type="text/javascript">
		Swal.fire({
			icon: 'success',
			title: '${param.success}',
			showCloseButton: true
		})
	</script>
</c:if>
</html>