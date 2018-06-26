<c:set var="enterDescription"><spring:message code="enter.description"/></c:set>
<c:set var="enterPrice"><spring:message code="enter.price"/></c:set>
<c:set var="enterQuantity"><spring:message code="enter.quantity"/></c:set>
<c:set var="enterTags"><spring:message code="enter.tags"/></c:set>

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
      <c:if test="${not empty publicationErrors or not empty shouldShowModal}">
         <script type="text/javascript">
          $(window).on('load',function(){
            $('#publicationModal').modal('show');
          });
         </script>
      </c:if>

      <!--          Create publication form          -->
      <div class="modal-body">
         <c:url value="/profile/publications" var="postPathPublication"/>
         <form:form modelAttribute="publicationForm" action="${postPathPublication}" method="post">
          <div class="form-group">
            <label><spring:message code="description"/></label>
            <form:input type="text" class="form-control" aria-describedby="description" placeholder="${enterDescription}"
            path="description" id="description"/>
            <form:errors path="description" role="alert" cssClass="alert alert-danger mt-2" element="div"/>
          </div>

          <div class="form-group">
            <label><spring:message code="price"/></label>
            <form:input type="text" class="form-control" aria-describedby="price" placeholder="${enterPrice}"
            path="price" id="price"/>
            <form:errors path="price" role="alert" cssClass="alert alert-danger mt-2" element="div"/>
          </div>

          <div class="form-group">
            <label><spring:message code="publication.total.quantity"/></label>
            <form:input type="text" class="form-control" aria-describedby="quantity" placeholder="${enterQuantity}"
            path="quantity" id="quantity"/>
            <form:errors path="quantity" role="alert" cssClass="alert alert-danger mt-2" element="div"/>
          </div>

          <div class="form-group">
            <label><spring:message code="own.quantity"/></label>
            <form:input type="text" class="form-control" aria-describedby="ownerQuantity" placeholder="${enterQuantity}"
            path="ownerQuantity" id="ownerQuantity"/>
            <form:errors path="ownerQuantity" role="alert" cssClass="alert alert-danger mt-2" element="div"/>
            <c:if test="${not empty invalidQuantity}">
              <div role="alert" class="alert alert-danger mt-2"><spring:message code="publication.invalid.quantity"/></div>
            </c:if>
          </div>
          <div class="form-group">
            <label><spring:message code="tags"/></label>
            <form:input type="text" class="form-control" aria-describedby="tags" placeholder="${enterTags}"
            path="tags" id="tags"/>
          </div>
          <label><spring:message code="publication.image"/></label>
          <div class="input-group mb-3">
            <div class="custom-file">
              <input type="file" onchange="loadImage();" class="custom-file-input" id="imgfile">
              <label class="custom-file-label" for="inputGroupFile04"><spring:message code="publication.image.choose"/></label>
            </div>
          </div>
          <div class="form-group">
            <div class="form-control" style="text-align: center;">
              <canvas id="pub-image-canvas" class="publication-canvas bg-light rounded m-2"></canvas>
            </div>
          </div>
          <form:input type="hidden" path="image" id="image-field" />
          <button type="submit" class="btn btn-gumpu"><spring:message code="create"/></button>
        </form:form>
      </div>
    </div>
  </div>
</div>
