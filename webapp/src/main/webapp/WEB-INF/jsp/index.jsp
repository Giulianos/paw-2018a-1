<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<%@ include file="globals.jsp" %>

<c:set var="searchPlaceholder"><spring:message code="search.what"/></c:set>

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
    <link rel="stylesheet" href="${siteRootDir}/css/style.css">

    <title>Gumpu</title>
  </head>
  <body class="index-body">
    <%@ include file="navbar.jsp" %>
          <!--          Login form          -->
		<%@ include file="login-modal.jsp" %>

    <div class="container" style="display:flex;justify-content:center;align-items:center;">
      <div>
        <h1 class="logo-title">Gumpu</h1>
        <p class="lead slogan px-3 bg-dark"><spring:message code="slogan"/></p>

        <form method="get" action="${siteRootDir}/search">
          <div class="input-group mb-3 input-group-lg">
            <input type="text" class="form-control" name="keywords" placeholder="${searchPlaceholder}">
              <div class="input-group-append">
                <button class="btn btn-secondary" type="submit"><spring:message code="search"/></button>
              </div>
            </div>
          </form>
      </div>
    </div>
  </body>
</html>
