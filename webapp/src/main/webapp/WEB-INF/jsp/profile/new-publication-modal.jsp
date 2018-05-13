<div class="modal fade" id="publicationModal" tabindex="-1" role="dialog" aria-labelledby="Publication" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel"><spring:message code="publication.create"/></h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>

      <!--          Open modal automatically         -->
      <c:if test="${not empty publicationErrors}">
         <script type="text/javascript">
          $(window).on('load',function(){
            $('#publicationModal').modal('show');
          });
         </script>
      </c:if>

      <!--          Create publication form          -->
      <div class="modal-body">
         <c:url value="/createPublication" var="postPathPublication"/>
         <form:form modelAttribute="publicationForm" action="${postPathPublication}" method="post">
          <div class="form-group">
            <label><spring:message code="description"/></label>
            <form:input type="text" class="form-control" aria-describedby="description" placeholder="Enter description"
            path="description" id="description"/>
            <form:errors path="description" role="alert" cssClass="alert alert-danger mt-2" element="div"/>
          </div>

          <div class="form-group">
            <label><spring:message code="price"/></label>
            <form:input type="text" class="form-control" aria-describedby="price" placeholder="Enter price"
            path="price" id="price"/>
            <form:errors path="price" role="alert" cssClass="alert alert-danger mt-2" element="div"/>
          </div>

          <div class="form-group">
            <label><spring:message code="quantity"/></label>
            <form:input type="text" class="form-control" aria-describedby="quantity" placeholder="Enter quantity"
            path="quantity" id="quantity"/>
            <form:errors path="quantity" role="alert" cssClass="alert alert-danger mt-2" element="div"/>
          </div>

          <div class="form-group">
            <label><spring:message code="own.quantity"/></label>
            <form:input type="text" class="form-control" aria-describedby="ownerQuantity" placeholder="Enter quantity"
            path="ownerQuantity" id="ownerQuantity"/>
            <form:errors path="ownerQuantity" role="alert" cssClass="alert alert-danger mt-2" element="div"/>
            <c:if test="${not empty invalidQuantity}">
              <div role="alert" class="alert alert-danger mt-2"><spring:message code="publication.invalid.quantity"/></div>
            </c:if>
          </div>

          <button type="submit" class="btn btn-primary"><spring:message code="create"/></button>
        </form:form>
      </div>
    </div>
  </div>
</div>
