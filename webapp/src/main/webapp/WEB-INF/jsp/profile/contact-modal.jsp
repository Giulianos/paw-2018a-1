<div class="modal fade" id="contactModal-${subs.publication_id}" tabindex="-1" role="dialog" aria-labelledby="Contact" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel"><spring:message code="contacts"/></h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        <ul class="list-group list-group-flush">
          <c:choose>
            <c:when test="${subs.publication.supervisor == subs.subscriber}">
              <!-- show all emails -->
              <c:forEach var="subscriber" items="${subs.publication.subscribers}">
                <li class="list-group-item">
                  <span class="bold capitalized"><c:out value="${subscriber.username}"/>:</span>
                  <c:out value="${subscriber.email}"/>
                </li>
              </c:forEach>
              <li class="list-group-item">
                <span class="bold capitalized"><spring:message code="me"/></span>
              </li>
            </c:when>
            <c:otherwise>
              <!-- show supervisors email -->
              <li class="list-group-item">
                <span class="bold capitalized">
                  <c:out value="${subs.publication.supervisorUser.username}"/>
                </span>
                <c:out value="${subs.publication.supervisorUser.email}"/>
              </li>
            </c:otherwise>
          </c:choose>
        </ul>

      </div>
      <div class="modal-footer">
        <a href='<spring:url value="/profile/subscriptions-finalized" htmlEscape="true"/>'>
      </div>
    </div>
  </div>
</div>
