<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="username"><spring:message code="username"/></c:set>
<c:set var="email"><spring:message code="email"/></c:set>
<c:set var="password"><spring:message code="register.password"/></c:set>
<c:set var="repassword"><spring:message code="register.repassword"/></c:set>
<c:set var="register_submit"><spring:message code="register.submit"/></c:set>

<html>
	<head>
		<link rel="stylesheet" href="<c:url value="/css/style.css"/>" />
	</head>
	<body>
		<h2><spring:message code="register.title"/></h2>
		<c:url value="/create" var="postPath"/>
		<form:form modelAttribute="registerForm" action="${postPath}" method="post">
			<div>
				<form:input type="text" path="username" placeholder="${username}"/>
				<form:errors path="username" cssClass="formError" element="p"/>
			</div>
			<c:if test="${not empty invalidUser}">
				<spring:message code="register.invalid.user"/>  
			</c:if>
			<div>
				<form:input type="text" path="email" placeholder="${email}"/>
				<form:errors path="email" cssClass="formError" element="p"/>
			</div>
			<c:if test="${not empty invalidEmail}">
				<spring:message code="register.invalid.email"/>
			</c:if>
			<div>
				<form:input type="password" path="password" placeholder="${password}"/>
				<form:errors path="password" cssClass="formError" element="p"/>
			</div>
			<div>
				<form:input type="password" path="repeatPassword" placeholder="${repassword}"/>
				<form:errors path="repeatPassword" cssClass="formError" element="p"/>
			</div>
			<c:if test="${not empty invalidPassword}">
				<spring:message code="register.invalid.password"/>
			</c:if>
			<div>
				<input type="submit" value="${register_submit}"/>
			</div>
		</form:form>
	</body>
</html>