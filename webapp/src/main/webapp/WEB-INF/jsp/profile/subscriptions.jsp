<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>



<script>
  function deleteConfirmation()
  {
    return confirm("<spring:message code="erase.confirm.sub"/>");
  }
</script>

<c:set var="person"><img src='<spring:url value="/img/person.svg" htmlEscape="true"/>' height="15" border="0"/></c:set>

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
    <link rel="stylesheet" href='<spring:url value="/css/style.css" htmlEscape="true"/>'>

    <title>Gumpu</title>
  </head>
  <body>
    <%@ include file="../navbar.jsp" %>
		<%@ include file="../login-modal.jsp" %>
    <div class="row">
      <div class="col-2 border-right">
        <div class="container">
          <div class="btn-group-vertical">
            <a href='<spring:url value="/profile/publications" htmlEscape="true"/>' class="btn btn-outline-gumpu">My publications</a>
            <a class="btn active btn-outline-gumpu">My subscriptions</a>
            <a href='<spring:url value="/profile/subscriptions-finalized" htmlEscape="true"/>' class="btn btn-outline-gumpu">My finalized subscriptions</a>
          </div>
        </div>
      </div>
      <div class="col-8">
        <div class="container">
          <h2 class="text-secondary mb-4"><spring:message code="my.subscriptions"/></h2>
          <c:if test="${anyHasNoSupervisor}">
            <div class="alert alert-warning" role="alert">
              <spring:message code="no.supervisor.warning" arguments="${person}"/>
            </div>
          </c:if>
          <c:if test="${empty subscriptions}">
            <div class="mb-3"><spring:message code="no.subscriptions"/></div>
          </c:if>
          <c:if test="${not empty subscriptions}">
            <table class="table table-striped table-bordered">
              <thead>
                <tr>
                  <th scope="col"><spring:message code="description"/></th>
                  <th scope="col"><spring:message code="ordered.quantity"/></th>
                  <th scope="col"><spring:message code="remaining.quantity"/></th>
                  <th scope="col"><spring:message code="unit.price"/></th>
                  <th scope="col"></th>
                </tr>
              </thead>
              <tbody>
                <c:forEach var="subs" items="${subscriptions}">
                  <c:choose>
                    <c:when test="${not empty subs.publication.supervisor}">
                      <tr>
                        <th scope="row"><c:out value="${subs.publication.description}"/></th>
                        <td><c:out value="${subs.quantity}"/></td> <!-- quantity ordered -->
                        <td><c:out value="${subs.publication.remainingQuantity}"/></td> <!-- remaning quantity -->
                        <td><c:out value="${subs.publication.price}"/></td>
                        <td>
                          <form method="POST" action="subscriptions/erase" onsubmit="return deleteConfirmation();">
                            <input type="hidden" name="publication_id" value="${subs.publication.id}" />
                            <input type="image" src='<spring:url value="/img/trash.svg" htmlEscape="true"/>' height="18" border="0" alt="Submit" />
                          </form>
                        </td>
                      </tr>
                    </c:when>
                    <c:otherwise>
                      <tr class="table-warning">
                        <th scope="row"><c:out value="${subs.publication.description}"/></th>
                        <td><c:out value="${subs.quantity}"/></td> <!-- quantity ordered -->
                        <td><c:out value="${subs.publication.remainingQuantity}"/></td> <!-- remaning quantity -->
                        <td><c:out value="${subs.publication.price}"/></td>
                        <td>
                          <div class="row">
                            <div class="col-sm">
                              <form method="POST" action="subscriptions/erase" onsubmit="return deleteConfirmation();">
                                <input type="hidden" name="publication_id" value="${subs.publication.id}" />
                                <input type="image" src='<spring:url value="/img/trash.svg" htmlEscape="true"/>' height="18" border="0" alt="Submit" />
                              </form>
                            </div>
                            <div class="col-sm">
                              <form method="POST" action="subscriptions/supervise">
                                <input type="hidden" name="publication_id" value="${subs.publication.id}" />
                                <input type="image" src='<spring:url value="/img/person.svg" htmlEscape="true"/>' height="18" border="0" alt="Submit" />
                              </form>
                            </div>
                          </div>
                        </td>
                      </tr>
                    </c:otherwise>
                  </c:choose>
                </c:forEach>
              </tbody>
            </table>
          </c:if>
        </div>
      </div>
    </div>
    <%@ include file="../pagination.jsp" %>
  </body>
</html>
