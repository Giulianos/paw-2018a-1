<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>



<c:set var="enterUsername"><spring:message code="enter.username"/></c:set>
<c:set var="enterPassword"><spring:message code="enter.password"/></c:set>

<!doctype html>
<html>
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js" integrity="sha384-smHYKdLADwkXOn1EmN1qk/HfnUcbVRZyYmZ4qpPea6sjB/pTJ0euyQp0Mk8ck+5T" crossorigin="anonymous"></script>

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous">

		<!-- Custom styles -->
    <link rel="stylesheet" href='<spring:url value="/css/style.css" htmlEscape="true"/>'>
    <title>Gumpu</title>
  </head>
  <body>
    <%@ include file="navbar.jsp" %>
		<%@ include file="login-modal.jsp" %>

    <div class="container">
      <!-- Login form -->
      <div class="card my-3">
        <div class="card-header">
          <spring:message code="login"/>
        </div>
        <div class="card-body">
          <form action="<spring:url value="/login" htmlEscape="true"/>" method="post" enctype="application/x-www-form-urlencoded">
            <div class="form-group">
              <label for="username"><spring:message code="username"/></label>
              <input class="form-control" id="username" name="j_username" type="text" value="${failed_username}" placeholder="${enterUsername}"/>
            </div>
            <div  class="form-group">
              <label for="password"><spring:message code="password"/></label>
              <input class="form-control" id="password" name="j_password" type="password" placeholder="${enterPassword}"/>
              <c:if test="${not empty invalid_loginPassword || not empty invalid_loginUser}">
               <div role="alert" class="alert alert-danger mt-2"><spring:message code="login.invalid.password"/></div>
              </c:if>
            </div>
            <div class="form-group form-check">
              <input class="form-check-input" name="j_rememberme" type="checkbox">
              <label class="form-check-label" for="exampleCheck1"><spring:message code="remember_me"/></label>
            </div>
            <button type="submit" class="btn btn-gumpu"><spring:message code="login"/></button>
          </form>
          <hr/>
          <a href='<spring:url value="/register" htmlEscape="true"/>' role="button" class="btn btn-outline-gumpu">Signup</a>
        </div>
      </div>
    </div>
  </body>
</html>
