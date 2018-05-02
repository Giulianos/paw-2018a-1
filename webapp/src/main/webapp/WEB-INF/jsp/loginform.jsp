          <div class="modal-body">
            <form id="login-form" action="/login" method="post" >
              <div class="form-group">
                <label for="j_username">Username</label>
                <input type="text" name="j_username" class="form-control" id="j_username" aria-describedby="username" placeholder="Enter Username" />
              </div>
              <div class="form-group">
                <label for="j_password">Password</label>
                <input type="password" name="j_password" class="form-control" id="j_password" placeholder="Password" />
              </div>
              <div class="form-group form-check">
                <input type="checkbox" class="form-check-input" id="j_rememberme" name="j_rememberme" />
                <label class="form-check-label" for="loginInputRememberMe"><spring:message code="remember_me"/></label>
              </div>
              <button type="submit" class="btn btn-primary">Login</button>
            </form>
          </div>