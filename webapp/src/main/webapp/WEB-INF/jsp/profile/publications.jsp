<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>



<script>
  function deleteConfirmation()
  {
    return confirm("<spring:message code="erase.confirm.publ"/>");
  }
</script>

<!doctype html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous">
    <link rel="stylesheet" href='<spring:url value="/css/style.css" htmlEscape="true"/>'>

    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js" integrity="sha384-smHYKdLADwkXOn1EmN1qk/HfnUcbVRZyYmZ4qpPea6sjB/pTJ0euyQp0Mk8ck+5T" crossorigin="anonymous"></script>

    <!-- Image tools for publication create -->
    <script src='<spring:url value="/js/image-tools.js" htmlEscape="true"/>'></script>

    <title>Gumpu</title>
  </head>
  <body>
    <%@ include file="../navbar.jsp" %>
		<%@ include file="../login-modal.jsp" %>

    <div class="container">
      <c:if test="${not empty publicationCreated}">
        <div class="alert alert-success" role="alert">
          <spring:message code="publication.create.success"/>
        </div>
      </c:if>
      <div class="row">
        <div class="col-sm">
          <h2 class="text-secondary mb-4"><spring:message code="my.publications"/></h2>
        </div>
        <div class="col-sm">
          <button class="btn btn-outline-gumpu ml-5 float-right" data-toggle="modal" data-target="#publicationModal" type="button"><spring:message code="publication.create"/></button>
        </div>
      </div>
      <table class="table table-striped table-bordered">
        <thead>
          <tr>
            <th scope="col"><spring:message code="description"/></th>
            <th scope="col"><spring:message code="needed.quantity"/></th>
            <th scope="col"><spring:message code="remaining.quantity"/></th>
            <th scope="col"><spring:message code="unit.price"/></th>
            <th scope="col"></th>
          </tr>
        </thead>
        <tbody>
          <c:forEach var="publ" items="${publications}">
            <tr>
              <th scope="row"><c:out value="${publ.description}"/></th>
              <td><c:out value="${publ.quantity}"/></td>
              <td><c:out value="${publ.remainingQuantity}"/></td>
              <td><c:out value="${publ.price}"/></td>
              <td style="text-align:center;">
                <c:choose>
                  <c:when test="${publ.remainingQuantity == 0}">
                    <form method="POST" action='<spring:url value="/profile/publications/check" htmlEscape="true"/>'>
                      <input type="hidden" name="publication_id" value="${publ.id}" />
                      <input type="image" src='<spring:url value="/img/circle-check.svg" htmlEscape="true"/>' height="18" border="0" alt="Submit" />
                    </form>
                  </c:when>
                  <c:otherwise>
                    <form method="POST" action='<spring:url value="/profile/publications/erase" htmlEscape="true"/>' onsubmit="return deleteConfirmation();">
                      <input type="hidden" name="publication_id" value="${publ.id}" />
                      <input type="image" src='<spring:url value="/img/trash.svg" htmlEscape="true"/>' height="18" border="0" alt="Submit" />
                    </form>
                  </c:otherwise>
                </c:choose>

              </td>
            </tr>
          </c:forEach>
        </tbody>
      </table>
    </div>
    <%@ include file="../pagination.jsp" %>
    <%@ include file="new-publication-modal.jsp" %>
  </body>
</html>
