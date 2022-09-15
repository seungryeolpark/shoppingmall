<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- 
1)JSTL(JSP Standard Tag Library, JSP 표준 태그 라이브러리)
2)JSTL 사용 이유 : model1은 jsp페이지 중심, model2는 java코드(서블릿, 모델)중심
jsp 웹페이지에서 java코드를 쓸 때가 많다. 이를 최소화하기 위해 JSTL과 EL기법을 씀,
즉 jsp 내부의 복잡한 자바코드를 대체하기 위한 태그
3) taglib prifix="태그 접두어" uri="태그라이브러리의 식별자"
4) Core tag(핵심태그, 제일 자주 사용되는 태그들)
 -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="path" value="${pageContext.request.contextPath}" />

<script src="${path}/include/js/jquery-3.6.0.min.js"></script>
<script src="${path}/include/js/bootstrap.js"></script>
<script src="${path}/include/js/bootstrap.bundle.js"></script>
<link rel="stylesheet" href="${path}/include/css/bootstrap.css">
<link rel="stylesheet" href="${path}/include/css/shoppingmall/index.css">
<script src="//cdn.jsdelivr.net/npm/sweetalert2@11"></script>