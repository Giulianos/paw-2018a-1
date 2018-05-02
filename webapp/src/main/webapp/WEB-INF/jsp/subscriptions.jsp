<div>
	<div><spring:message code="subscriptions.open"/></div>
	<div>Add list</div>
	<div><spring:message code="subscriptions.pay"/></div>
	<div>Add list</div>
	
	<button class="btn my-2 my-sm-0" data-toggle="modal" data-target="#publicationModal" type="button"><spring:message code="publication.create"/></button>
    <div class="modal fade" id="publicationModal" tabindex="-1" role="dialog" aria-labelledby="Publication" aria-hidden="true">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="exampleModalLabel"><spring:message code="publication.create"/></h5>
            <button type="button" class="close" data-dismiss="modal" aria-label="close">
              <span aria-hidden="true">&times;</span>
            </button>
          </div>
          <!--          Create publication form          -->
          <div class="modal-body">
            <form id="publicationForm" action="/login" method="post" >
              <div class="form-group">
                <label><spring:message code="description"/></label>
                <input type="text" class="form-control" aria-describedby="description" placeholder="Enter description" />
              </div>
              
              <div class="form-group">
                <label><spring:message code="price"/></label>
                <input type="text" class="form-control" aria-describedby="price" placeholder="Enter price" />
              </div>
              
              <div class="form-group">
                <label><spring:message code="quantity"/></label>
                <input type="text" class="form-control" aria-describedby="quantity" placeholder="Enter quantity" />
              </div>
              
              <button type="submit" class="btn btn-primary"><spring:message code="create"/></button>
            </form>
          </div>
        </div>
      </div>
    </div>
</div>