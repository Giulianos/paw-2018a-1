        <div class="row align-items-center justify-content-center">
          <div class="col">
            <h2 class="mb-3"><spring:message code="register.title"/></h2>
            <div class="border bg-light rounded p-3">
              <c:url value="/create" var="postPath"/>
          		<form:form modelAttribute="registerForm" action="${postPath}" method="post">
                <div class="form-group">
                  <label for="username">${username}</label>
                  <form:input type="text" path="username" class="form-control" id="username" aria-describedby="username" placeholder="${username}"/>
                  <form:errors path="username" role="alert" cssClass="alert alert-danger" element="div"/>
                  <c:if test="${not empty invalidUser}">
                    <span role="alert" cssClass="alert alert-danger"><spring:message code="register.invalid.user"/></span>
                  </c:if>
                </div>
                <div class="form-group">
                  <label for="email">${email}</label>
                  <form:input type="email" path="email" class="form-control" id="email" aria-describedby="email" placeholder="${email}"/>
                  <form:errors path="email" role="alert" cssClass="alert alert-danger" element="div"/>
                  <c:if test="${not empty invalidEmail}">
                    <span role="alert" cssClass="alert alert-danger"><spring:message code="register.invalid.email"/></span>
                  </c:if>
                </div>

                <div class="form-group">
                  <label for="password">${password}</label>
                  <form:input type="password" path="password" class="form-control" id="password" aria-describedby="password" placeholder="${password}"/>
                  <form:errors path="password" role="alert" cssClass="alert alert-danger" element="div"/>
                </div>

                <div class="form-group">
                  <form:input type="password" path="repeatPassword" class="form-control" id="repeatPassword" aria-describedby="repeatpassword" placeholder="${repassword}"/>
                  <form:errors path="repeatPassword" role="alert" cssClass="alert alert-danger" element="div"/>
                  <c:if test="${not empty invalidPassword}">
                    <div role="alert" cssClass="alert alert-danger"><spring:message code="register.invalid.password"/></div>
                  </c:if>
                </div>
                <button value="${register_submit}" type="submit" class="btn btn-primary">${register_submit}</button>
              </form:form>
            </div>
          </div>
        </div>