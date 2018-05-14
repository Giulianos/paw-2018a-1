<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<%@ include file="../globals.jsp" %>

<!doctype html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous">
    <link rel="stylesheet" href="${siteRootDir}/css/style.css">

    <title>Gumpu</title>
  </head>
  <body>
    <%@ include file="../navbar.jsp" %>
		<%@ include file="../login-modal.jsp" %>

    <div class="container">
      <h1 style="text-transform: capitalize"><security:authentication property="principal.username" /></h1><hr>
      <h2 class="text-secondary"><spring:message code="summary"/></h2>
      <div class="card my-3">
        <div class="card-header">
          <spring:message code="publications"/>
        </div>
        <div class="card-body">
          <h5 class="card-title"> <span class="badge badge-secondary px-2"><c:out value="${publicationsQuantity}" /></span> <spring:message code="my.publications"/></h5>
          <a href="${siteRootDir}/profile/publications" class="btn btn-outline-primary mt-3"><spring:message code="see.my.publications"/></a>
        </div>
      </div>
      <div class="card my-3">
        <div class="card-header">
          <spring:message code="subscriptions"/>
        </div>
        <div class="card-body">
          <h5 class="card-title"> <span class="badge badge-secondary px-2"><c:out value="${subscriptionsQuantity}" /></span> <spring:message code="my.subscriptions"/></h5>
          <a href="${siteRootDir}/profile/subscriptions" class="btn btn-outline-primary mt-3"><spring:message code="see.my.subscriptions"/></a>
        </div>
      </div>
      <div class="card my-3">
        <div class="card-header">
          <spring:message code="finalized.subscriptions"/>
        </div>
        <div class="card-body">
          <h5 class="card-title"> <span class="badge badge-secondary px-2"><c:out value="${finalizedSubscriptionsQuantity}" /></span> <spring:message code="pay.subscriptions"/></h5>
          <a href="${siteRootDir}/profile/subscriptions-finalized" class="btn btn-outline-primary mt-3"><spring:message code="see.my.pay.subscriptions"/></a>
        </div>
      </div>
    </div>

    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js" integrity="sha384-smHYKdLADwkXOn1EmN1qk/HfnUcbVRZyYmZ4qpPea6sjB/pTJ0euyQp0Mk8ck+5T" crossorigin="anonymous"></script>
  </body>
</html>
