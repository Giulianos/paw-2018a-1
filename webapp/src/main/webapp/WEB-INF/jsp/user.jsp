<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@ include file="globals.jsp" %>

<html>
	<head>
		<link rel="stylesheet" href="<c:url value="${siteRootDir}/css/style.css"/>" />
	</head>
	<body>
		<h2><spring:message code="user.greeting" arguments="${user.username}"/></h2>
		<h5><spring:message code="user.id" arguments="${user.id}"/></h5>
	</body>
</html>
