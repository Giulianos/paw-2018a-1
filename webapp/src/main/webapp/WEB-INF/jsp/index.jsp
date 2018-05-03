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

    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js" integrity="sha384-smHYKdLADwkXOn1EmN1qk/HfnUcbVRZyYmZ4qpPea6sjB/pTJ0euyQp0Mk8ck+5T" crossorigin="anonymous"></script>

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous">

		<!-- Custom styles -->
    <link rel="stylesheet" href="css/style.css">

    <title>{PAW_PROJECT}</title>
  </head>
  <body>
    <%@ include file="navbar.jsp" %>
    <div class="modal fade" id="loginModal" tabindex="-1" role="dialog" aria-labelledby="Login" aria-hidden="true">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="exampleModalLabel">Login</h5>
            <button type="button" class="close" data-dismiss="modal" aria-label="close">
              <span aria-hidden="true">&times;</span>
            </button>
          </div>
          <!--          Login form          -->
		  <%@ include file="loginform.jsp" %>

          <div class="modal-footer">
            <a href="/register" role="button" class="btn btn-outline-primary">Signup</a>
          </div>
        </div>
      </div>
    </div>

    <div class="container">

      <security:authorize access="!isAuthenticated()">

        <!--      Signup form     -->
		<%@ include file="registrationform.jsp" %>

      </security:authorize>

      <security:authorize access="isAuthenticated()">

		<%@ include file="subscriptions.jsp" %>

      </security:authorize>

    </div>
  </body>
</html>
