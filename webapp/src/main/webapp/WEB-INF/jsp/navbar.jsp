    <nav class="navbar navbar-expand-md navbar-dark fixed-top bg-dark">
      <a class="navbar-brand" href="#">{PAW_PROJECT}</a>
      <button class="navbar-toggler collapsed" type="button" data-toggle="collapse" data-target="#navbarCollapse" aria-controls="navbarCollapse" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="navbar-collapse collapse" id="navbarCollapse" style="">
        <ul class="navbar-nav mr-auto">
          <li class="nav-item active">
            <a class="nav-link" href="/"><spring:message code="nav.home"/> <span class="sr-only">(current)</span></a>
          </li>
          <li class="nav-item active">
            <a class="nav-link" href="/search"><spring:message code="nav.publications"/></a>
          </li>
        </ul>
        <form class="form-inline my-2 my-lg-0">
          <security:authorize access="!isAuthenticated()">
              <button class="btn btn-outline-light my-2 my-sm-0" data-toggle="modal" data-target="#loginModal" type="button">Login / Signup</button>
          </security:authorize>
          <security:authorize access="isAuthenticated()">
            <a style="text-transform: capitalize" class="btn btn-info my-2 my-sm-0" href="/profile" type="button">
              <security:authentication property="principal.username" />
            </a>
          </security:authorize>
        </form>
      </div>
    </nav>
