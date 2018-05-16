<c:set var="enterUsername"><spring:message code="enter.username"/></c:set>
<c:set var="password"><spring:message code="password"/></c:set>
<c:set var="enterPassword"><spring:message code="enter.password"/></c:set>

<div class="modal fade" id="loginModal" tabindex="-1" role="dialog" aria-labelledby="Login" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel"><spring:message code="login"/></h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
        <div class="modal-body">
          <form id="login-form" action="${siteRootDir}/login" method="post" >
            <div class="form-group">
              <label for="j_username"><spring:message code="username"/></label>
              <input type="text" name="j_username" class="form-control" id="j_username" aria-describedby="username" placeholder="${enterUsername}" />
            </div>
            <div class="form-group">
              <label for="j_password"><spring:message code="password"/></label>
              <input type="password" name="j_password" class="form-control" id="j_password" placeholder="${enterPassword}" />
            </div>
            <div class="form-group form-check">
              <input type="checkbox" class="form-check-input" id="j_rememberme" name="j_rememberme" />
              <label class="form-check-label" for="loginInputRememberMe"><spring:message code="remember_me"/></label>
            </div>
            <button type="submit" class="btn btn-gumpu"><spring:message code="login"/></button>
          </form>
        </div>
        <div class="modal-footer">
          <a href="${siteRootDir}/register" role="button" class="btn btn-outline-gumpu"><spring:message code="signup"/></a>
        </div>
      </div>
    </div>
  </div>
