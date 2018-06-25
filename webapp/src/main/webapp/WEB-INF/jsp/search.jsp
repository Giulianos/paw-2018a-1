<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>



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
    <link rel="stylesheet" href='<spring:url value="/css/style.css" htmlEscape="true"/>'>

    <script>
      function checkOrderQuantity(value, id, max) {
        if (0 < value && value <= max) {
          document.getElementById("button-"+id).disabled = false;
          document.getElementById("quantity-"+id).classList.remove("is-invalid");
        } else {
          document.getElementById("button-"+id).disabled = true;
          document.getElementById("quantity-"+id).classList.add("is-invalid");
        }
      }
    </script>

    <title>Gumpu</title>
  </head>
  <body>
    <%@ include file="navbar.jsp" %>
		<%@ include file="login-modal.jsp" %>

    <div class="container pt-3">
      <div class="row align-items-center justify-content-center">
        <div class="col">
          <div class="row">
            <div class="col-sm">
              <h2 class="mb-3"><spring:message code="search"/></h2>
            </div>
            <div class="col-sm">
              <a class="btn btn-outline-gumpu ml-5 float-right" href='<spring:url value="/profile/publications?newModal=true" htmlEscape="true"/>'><spring:message code="publication.create"/></a>
            </div>
          </div>
          <div class="border bg-light rounded p-3">
            <form method="get" action='<spring:url value="/search" htmlEscape="true"/>'>
              <div class="input-group mb-3">
                    <input type="text" value='<c:out value="${searchedKeyword}" />' class="form-control" name="keywords" placeholder="${searchPlaceholder}"/>
                <div class="input-group-append">
                  <button class="btn btn-gumpu" type="submit"><spring:message code="search.button"/></button>
                </div>
              </div>
            </form>
          </div>
        </div>
      </div>
      <div class="row align-items-center justify-content-center mt-3">
        <div class="col">
          <div class="border bg-light rounded px-3 pt-3 mb-3">
            <c:if test="${empty resultList}">
              <div class="mb-3"><spring:message code="no.results"/></div>
            </c:if>
            <c:forEach var="publication" items="${resultList}">
            	<!-- Publication -->

	            <div class="row bg-white border border-secondary rounded ml-2 mr-2 mb-2">
	              <div class="column">
	                <img height="180" width="286" src="${publication.image}" class="p-2"/>
	              </div>
	              <div class="column py-2 px-3">
	                <h3><c:out value="${publication.description}" /></h3>
	                <span class="mt-3"><img height="18" src='<spring:url value="/img/dollar.svg" htmlEscape="true"/>' alt="icon name" /> ${publication.price}</span></br>
	                <span class="mt-3"><spring:message code="quantity"/></span> <span class="badge badge-pill badge-gumpu"><c:out value="${publication.remainingQuantity}" /></span></br>
                  <spring:url var='order_form_url' value="/order" htmlEscape="true"/>
	                <form:form modelAttribute="orderForm" action='${order_form_url}' method="post">
	             		<form:input type="hidden" value="${publication.id}" path="publicationId" id="publicationId-${publication.id}"/>
		                <div class="input-group input-group-sm mt-3">
		                  <form:input value='1' oninput="checkOrderQuantity(this.value, '${publication.id}', ${publication.remainingQuantity});" type="number" class="form-control" placeholder="Cantidad" path="quantity" id="quantity-${publication.id}" />
		                  <div class="input-group-append">
		                    <button id="button-${publication.id}" class="btn btn-outline-secondary" type="submit"><spring:message code="order"/></button>
		                  </div>
		                </div>
		            </form:form>
	              </div>
	            </div>
          	</c:forEach>
            <%@ include file="pagination.jsp" %>
          </div>
        </div>
      </div>
    </div>
  </body>
</html>
