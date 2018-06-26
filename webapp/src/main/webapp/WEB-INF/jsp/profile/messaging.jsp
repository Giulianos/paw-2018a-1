<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<!doctype html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous">
    <link rel="stylesheet" href='<spring:url value="/css/style.css" htmlEscape="true"/>'>
    <link rel="stylesheet" href='<spring:url value="/css/messages_style.css" htmlEscape="true"/>'>

    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js" integrity="sha384-smHYKdLADwkXOn1EmN1qk/HfnUcbVRZyYmZ4qpPea6sjB/pTJ0euyQp0Mk8ck+5T" crossorigin="anonymous"></script>

    <script src='<spring:url value="/js/messaging.js" htmlEscape="true"/>'></script>

    <title>Gumpu</title>
  </head>
  <body>
    <%@ include file="../navbar.jsp" %>
		<%@ include file="../login-modal.jsp" %>
    <div class="row m-0">
      <div class="col-2 border-right">
        <div class="container">
          <div class="nav-btn-group btn-group-vertical">
            <a href='<spring:url value="/profile/publications" htmlEscape="true"/>' class="truncate btn btn-outline-gumpu"><spring:message code="my.publications"/></a>
            <a href='<spring:url value="/profile/subscriptions" htmlEscape="true"/>' class="truncate btn btn-outline-gumpu"><spring:message code="my.subscriptions"/></a>
            <a href='<spring:url value="/profile/subscriptions-finalized" htmlEscape="true"/>' class="truncate btn btn-outline-gumpu"><spring:message code="my.finalized.subscriptions"/></a>
          </div>
        </div>
      </div>
      <div class="col-8">
        <div class="container">
            <h2 class="text-secondary mb-4"><spring:message code="messages.for"/> ${order_id}</h2>
            <div class="message-line">
              <div class="message-box-sender" />
                <div class="message message-sender">
                  <div class="message-text">Hola, como andas?jkfhkdsjfhkjasdhfkjshkjdfhskdjfhksjdhfkjsdhfkjshdkjfhsdkjfhskjdfhksjdhfkjsdhfkjsdhfkjsdhkfjshdkjfhskjdfhskjdhfkjsdhfkjsdhfkjshdkfjhskdjfhksjdhfkjsdhfkjsdhfkjshdfkjshdkjfhskdjhfksdjfhk</div>
                  <div class="message-date">23/07/18</div>
                </div>
              </div>
              <div class="message-box-receiver">
                <div class="message message-receiver">
                  <div class="message-text">Hola! Todo bien</div>
                  <div class="message-date">23/07/18</div>
                </div>
              </div>
              <div class="message-box-receiver">
                <div class="message message-receiver">
                  <div class="message-text">Hola! Todo bien</div>
                  <div class="message-date">23/07/18</div>
                </div>
              </div>
              <div class="message-box-receiver">
                <div class="message message-receiver">
                  <div class="message-text">Hola! Todo bien</div>
                  <div class="message-date">23/07/18</div>
                </div>
              </div>
              <div class="message-box-receiver">
                <div class="message message-receiver">
                  <div class="message-text">Hola! Todo bien</div>
                  <div class="message-date">23/07/18</div>
                </div>
              </div>
              <div class="message-box-sender" />
                <div class="message message-sender">
                  <div class="message-text">Hola, como andas?jkfhkdsjfhkjasdhfkjshkjdfhskdjfhksjdhfkjsdhfkjshdkjfhsdkjfhskjdfhksjdhfkjsdhfkjsdhfkjsdhkfjshdkjfhskjdfhskjdhfkjsdhfkjsdhfkjshdkfjhskdjfhksjdhfkjsdhfkjsdhfkjshdfkjshdkjfhskdjhfksdjfhk</div>
                  <div class="message-date">23/07/18</div>
                </div>
              </div>
              <div class="message-box-sender" />
                <div class="message message-sender">
                  <div class="message-text">Hola, como andas?jkfhkdsjfhkjasdhfkjshkjdfhskdjfhksjdhfkjsdhfkjshdkjfhsdkjfhskjdfhksjdhfkjsdhfkjsdhfkjsdhkfjshdkjfhskjdfhskjdhfkjsdhfkjsdhfkjshdkfjhskdjfhksjdhfkjsdhfkjsdhfkjshdfkjshdkjfhskdjhfksdjfhk</div>
                  <div class="message-date">23/07/18</div>
                </div>
              </div>
            </div>
            <div class="new-message">
              <div class="input-group">
                <textarea id="new-message-text" class="form-control" aria-label="With textarea"></textarea>
                <div class="input-group-append">
                  <button class="btn btn-gumpu" type="button"><spring:message code="send.message"/></button>
                </div>
              </div>
            </div>
        </div>
      </div>
    </div>
  </body>
</html>
