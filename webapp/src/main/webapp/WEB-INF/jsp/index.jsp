<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<c:set var="username"><spring:message code="username"/></c:set>
<c:set var="email"><spring:message code="email"/></c:set>
<c:set var="password"><spring:message code="register.password"/></c:set>
<c:set var="repassword"><spring:message code="register.repassword"/></c:set>
<c:set var="register_submit"><spring:message code="register.submit"/></c:set>

<!doctype html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous">

		<!-- Custom styles -->
		<style>
			body {
				padding-top: 70px;
			}
		</style>
    <link rel="stylesheet" href="styles.css">

    <title>{PAW_PROJECT}</title>
  </head>
  <body>
    <nav class="navbar navbar-expand-md navbar-dark fixed-top bg-dark">
      <a class="navbar-brand" href="#">{PAW_PROJECT}</a>
      <button class="navbar-toggler collapsed" type="button" data-toggle="collapse" data-target="#navbarCollapse" aria-controls="navbarCollapse" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="navbar-collapse collapse" id="navbarCollapse" style="">
        <ul class="navbar-nav mr-auto">
          <li class="nav-item active">
            <a class="nav-link" href="/">Home <span class="sr-only">(current)</span></a>
          </li>
        </ul>
        <form class="form-inline my-2 my-lg-0">
          <security:authorize access="!isAuthenticated()">
              <button class="btn btn-outline-light my-2 my-sm-0" data-toggle="modal" data-target="#loginModal" type="button">Login / Signup</button>
          </security:authorize>
          <security:authorize access="isAuthenticated()">
            <a class="btn btn-info my-2 my-sm-0" href="" type="button">
              <security:authentication property="principal.username" />
            </a>
          </security:authorize>
        </form>
      </div>
    </nav>
		<div class="modal fade" id="loginModal" tabindex="-1" role="dialog" aria-labelledby="Login" aria-hidden="true">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="exampleModalLabel">Login</h5>
            <button type="button" class="close" data-dismiss="modal" aria-label="close">
              <span aria-hidden="true">&times;</span>
            </button>
          </div>
          <div class="modal-body">
						<!--          Login form          -->
            <form id="login-form" action="/login" method="post" >
              <div class="form-group">
                <label for="j_username">Username</label>
                <input type="text" name="j_username" class="form-control" id="j_username" aria-describedby="username" placeholder="Enter Username" />
              </div>
              <div class="form-group">
                <label for="j_password">Password</label>
                <input type="password" name="j_password" class="form-control" id="j_password" placeholder="Password" />
              </div>
              <div class="form-group form-check">
                <input type="checkbox" class="form-check-input" id="j_rememberme" name="j_rememberme" />
                <label class="form-check-label" for="loginInputRememberMe"><spring:message code="remember_me"/></label>
              </div>
              <button type="submit" class="btn btn-primary">Login</button>
            </form>
          </div>
          <div class="modal-footer">
            <a href="/register" role="button" class="btn btn-outline-primary">Signup</a>
          </div>
        </div>
      </div>
    </div>

    <div class="container">
      <div class="row align-items-center justify-content-center">
        <div class="col">
          <h2 class="mb-3"><spring:message code="register.title"/></h2>
          <div class="border bg-light rounded p-3">
            <c:url value="/create" var="postPath"/>
        		<form:form modelAttribute="registerForm" action="${postPath}" method="post">
              <div class="form-group">
                <label for="username">${username}</label>
                <form:input type="text" path="username" class="form-control" id="username" aria-describedby="username" placeholder="${username}"/>
                <form:errors path="username" role="alert" cssClass="alert alert-danger" element="div"/>
                <c:if test="${not empty invalidUser}">
          				<spring:message code="register.invalid.user"/>
          			</c:if>
              </div>
              <div class="form-group">
                <label for="email">${email}</label>
                <form:input type="email" path="email" class="form-control" id="email" aria-describedby="email" placeholder="${email}"/>
                <form:errors path="email" role="alert" cssClass="alert alert-danger" element="div"/>
                <c:if test="${not empty invalidEmail}">
          				<spring:message code="register.invalid.email"/>
          			</c:if>
              </div>

              <div class="form-group">
                <label for="password">${password}</label>
                <form:input type="password" path="password" class="form-control" id="password" aria-describedby="password" placeholder="${password}"/>
                <form:errors path="password" role="alert" cssClass="alert alert-danger" element="div"/>
              </div>

              <div class="form-group">
                <form:input type="password" path="repeatPassword" class="form-control" id="repeatPassword" aria-describedby="repeatpassword" placeholder="${repassword}"/>
                <form:errors path="repeatPassword" role="alert" cssClass="alert alert-danger" element="div"/>

                <c:if test="${not empty invalidPassword}">
          				<spring:message code="register.invalid.password"/>
          			</c:if>
              </div>
              <button value="${register_submit}" type="submit" class="btn btn-primary">${register_submit}</button>
            </form:form>
          </div>
        </div>
      </div>
    </div>

    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js" integrity="sha384-smHYKdLADwkXOn1EmN1qk/HfnUcbVRZyYmZ4qpPea6sjB/pTJ0euyQp0Mk8ck+5T" crossorigin="anonymous"></script>
  </body>
</html>
