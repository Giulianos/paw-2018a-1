<div class="modal fade" id="contactModal-${subs.publication.id}" tabindex="-1" role="dialog" aria-labelledby="Contact" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel"><spring:message code="send.msg.to"/></h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        <ul class="list-group list-group-flush">
          <c:choose>
            <c:when test="${subs.publication.supervisor == subs.subscriber}">
              <!-- show all emails -->
              <c:forEach var="order" items="${subs.publication.orders}">
                <li class="list-group-item">
                  <span class="bold capitalized"><c:out value="${order.subscriber.username}"/>
                    <a href='<spring:url value="/profile/messaging/${order.publication.id}/${order.subscriber.id}" htmlEscape="true"/>'>
                      <img height="18" src='<spring:url value="/img/msg.svg" htmlEscape="true"/>' alt="icon name" />
                    </a>
                  </span>
                </li>
              </c:forEach>
            </c:when>
            <c:otherwise>
              <!-- show supervisors email -->
              <li class="list-group-item">
                <span class="bold capitalized">
                  <c:out value="${subs.publication.supervisor.username}"/>
                  <a href='<spring:url value="/profile/messaging/${subs.publication.id}" htmlEscape="true"/>'>
                  <img height="18" src='<spring:url value="/img/msg.svg" htmlEscape="true"/>' alt="icon name" />
                  </a>
                  </span>
              </li>
            </c:otherwise>
          </c:choose>
        </ul>
      </div>
      <div class="modal-footer">
      </div>
    </div>
  </div>
</div>
