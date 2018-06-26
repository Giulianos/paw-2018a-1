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
            <h2 class="text-secondary mb-4"><spring:message code="messages.for"/> ${order.publication.description}</h2>
            <div class="message-line">
              <c:if test="${empty messages}">
                No messages found!
              </c:if>
              <c:forEach var="message" items="${messages}">
                <c:if test="${message.from.username == my_username}">
                  <div class="message-box-sender">
                    <div class="message message-sender">
                      <div class="message-text">${message.text}</div>
                      <div class="message-date">${message.sentTime}</div>
                    </div>
                  </div>
                </c:if>
                <c:if test="${message.to.username == my_username}">
                  <div class="message-box-receiver">
                    <div class="message message-receiver">
                      <div class="message-text">${message.text}</div>
                      <div class="message-date">${message.sentTime}</div>
                    </div>
                  </div>
                </c:if>
              </c:forEach>
            </div>
            <div class="new-message">
              <form action="" method="post">
                <div class="input-group">
                  <textarea name="message_body" id="new-message-text" class="form-control" aria-label="With textarea"></textarea>
                  <div class="input-group-append">
                    <button class="btn btn-gumpu" type="submit"><spring:message code="send.message"/></button>
                  </div>
                </div>
              </form>
            </div>
        </div>
      </div>
      <div class="col-2 border-left">
        <div class="card" style="width: 18rem;">
          <div class="card-header">
              <spring:message code="order.information"/>
          </div>
          <ul class="list-group list-group-flush">
            <li class="list-group-item">
              <c:if test="${my_username == order.subscriber.username}">
                <spring:message code="user"/>: ${order.publication.supervisor.username}
              </c:if>
              <c:if test="${my_username != order.subscriber.username}">
                <spring:message code="user"/>: ${order.subscriber.username}
              </c:if>
            </li>
            <li class="list-group-item">
              <c:if test="${my_username == order.subscriber.username}">
                <spring:message code="email"/>: ${order.publication.supervisor.email}
              </c:if>
              <c:if test="${my_username != order.subscriber.username}">
                <spring:message code="email"/>: ${order.subscriber.email}
              </c:if>
            </li>
            <c:if test="${my_username != order.subscriber.username}">
              <li class="list-group-item">
              <spring:message code="quantity"/>: ${order.quantity}
              </li>
            </c:if>
          </ul>
        </div>
        <div class="mt-3 card" style="width: 18rem;">
              <c:if test="${my_username == order.subscriber.username}">
                <c:if test="${empty order.supervisorReputation}">
                  <div class="card-header">
                    <spring:message code="rate"/>
                  </div>
                  <ul class="list-group list-group-flush">
                    <li class="list-group-item">
                  <c:forEach begin="1" end="5" varStatus="i">
                    <form class="rate-star" action="/profile/rate" method="post">
                      <input type="hidden" value="${order.subscriber.username}" name="subscriberUsername"/>
                      <input type="hidden" value="${order.publication.id}" name="publicationId"/>
                      <input type="hidden" value="1" name="rateSupervisor"/>
                      <button id="button-rate-${i.index}" class="rate-star" type="submit" name="stars" value="${i.index}">
                        <img id="star-${i.index}" style="display:inline" height="45" class="pb-3" src='<spring:url value="/img/greyStar.svg" htmlEscape="true"/>' alt="icon name" />
                      </button>
                    </form>
                  </c:forEach>
                </c:if>
                <c:if test="${not empty order.supervisorReputation}">
                  <div class="card-header">
                    <spring:message code="rated"/>
                  </div>
                  <ul class="list-group list-group-flush">
                    <li class="list-group-item">
                  <c:forEach begin="1" end="${order.supervisorReputation}" varStatus="i">
                      <img style="display:inline" height="45" class="pb-3" src='<spring:url value="/img/star.svg" htmlEscape="true"/>' alt="icon name" />
                  </c:forEach>
                  <c:forEach begin="${order.supervisorReputation+1}" end="5" varStatus="i">
                      <img style="display:inline" height="45" class="pb-3" src='<spring:url value="/img/greyStar.svg" htmlEscape="true"/>' alt="icon name" />
                  </c:forEach>
                </c:if>
              </c:if>
              <c:if test="${my_username != order.subscriber.username}">
                <c:if test="${empty order.subscriberReputation}">
                  <div class="card-header">
                    <spring:message code="rate"/>
                  </div>
                  <ul class="list-group list-group-flush">
                    <li class="list-group-item">
                  <c:forEach begin="1" end="5" varStatus="i">
                    <form class="rate-star" action="/profile/rate" method="post">
                      <input type="hidden" value="${order.subscriber.username}" name="subscriberUsername"/>
                      <input type="hidden" value="${order.publication.id}" name="publicationId"/>
                      <input type="hidden" value="0" name="rateSupervisor"/>
                      <button class="rate-star" type="submit" name="stars" value="${i.index}">
                        <img id="star-${i.index}" style="display:inline" height="45" class="pb-3" src='<spring:url value="/img/greyStar.svg" htmlEscape="true"/>' alt="icon name" />
                      </button>
                    </form>
                  </c:forEach>
                </c:if>
                <c:if test="${not empty order.subscriberReputation}">
                  <div class="card-header">
                    <spring:message code="rated"/>
                  </div>
                  <ul class="list-group list-group-flush">
                    <li class="list-group-item">
                  <c:forEach begin="1" end="${order.subscriberReputation}" varStatus="i">
                      <img style="display:inline" height="45" class="pb-3" src='<spring:url value="/img/star.svg" htmlEscape="true"/>' alt="icon name" />
                  </c:forEach>
                  <c:forEach begin="${order.subscriberReputation+1}" end="5" varStatus="i">
                      <img style="display:inline" height="45" class="pb-3" src='<spring:url value="/img/greyStar.svg" htmlEscape="true"/>' alt="icon name" />
                  </c:forEach>
                </c:if>
              </c:if>
            </li>
          </ul>
        </div>
      </div>
    </div>
  </body>
</html>
