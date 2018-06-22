    <nav class="navbar navbar-expand-md navbar-dark fixed-top bg-dark">
      <a class="navbar-brand" href='<spring:url value="/" htmlEscape="true"/>'><img src='<spring:url value="/img/logo.png" htmlEscape="true"/>' height="27" /></a>
      <button class="navbar-toggler collapsed" type="button" data-toggle="collapse" data-target="#navbarCollapse" aria-controls="navbarCollapse" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="navbar-collapse collapse" id="navbarCollapse" style="">
        <ul class="navbar-nav mr-auto">
          <li class="nav-item active">
            <a class="nav-link" href='<spring:url value="/search" htmlEscape="true"/>'><spring:message code="nav.publications"/></a>
          </li>
        </ul>
        <form class="form-inline my-2 my-lg-0">
          <security:authorize access="isAuthenticated()">
            <c:set var="isAuthenticated" value="true" />
            <a style="text-transform: capitalize" class="btn btn-gumpu my-2 my-sm-0 mx-2" href='<spring:url value="/profile" htmlEscape="true"/>' type="button">
              <security:authentication property="principal.username" />
            </a>
            <a style="text-transform: capitalize" class="btn btn-danger my-2 my-sm-0 mx-2" href='<spring:url value="/logout" htmlEscape="true"/>' type="button">
              <spring:message code="logout"/>
            </a>
          </security:authorize>
          <c:if test="${empty isAuthenticated}">
              <button class="btn btn-outline-light my-2 my-sm-0" data-toggle="modal" data-target="#loginModal" type="button"><spring:message code="login"/> / <spring:message code="signup"/></button>
          </c:if>
        </form>
      </div>
    </nav>
