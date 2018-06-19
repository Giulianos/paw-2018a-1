      <c:if test="${not empty pages}">
	      <div class="row align-items-center justify-content-center">
	        <nav aria-label="Page navigation example">
	          <ul class="pagination">
	            <li class="page-item">
	              <c:if test="${not empty prevDisable}">
		          <a class="btn btn-outline-secondary disabled" tabindex="-1">
	              </c:if>
	              <c:if test="${empty prevDisable}">
		          <a class="btn btn-outline-gumpu" href="<c:if test="${not empty searchedKeyword}">${searchedKeyword}&</c:if>start=${currentPageIndex-step}" aria-label="Previous">
	              </c:if>
	                <span aria-hidden="true">&laquo;</span>
	                <span class="sr-only">Previous</span>
	              </a>
	            </li>
	            <c:set var="index" value="${firstPageIndex}"/>
	            <c:forEach var="page" begin="${firstPage}" end="${lastPage}">
		            <li class="page-item">
		              <a class="btn btn-outline-gumpu <c:if test="${page == currentPage}">disabled</c:if>" href="<c:if test="${not empty searchedKeyword}">${searchedKeyword}&</c:if>start=${index}">
		                ${page}
		              </a>
		            </li>
	            <c:set var="index" value="${index+step}"/>
	            </c:forEach>
	            <li class="page-item">
	              <c:if test="${not empty nextDisable}">
		          <a class="btn btn-outline-secondary disabled" tabindex="-1">
	              </c:if>
	              <c:if test="${empty nextDisable}">
		          <a class="btn btn-outline-gumpu" href="<c:if test="${not empty searchedKeyword}">${searchedKeyword}&</c:if>start=${currentPageIndex+step}" aria-label="Next">
	              </c:if>
	                <span aria-hidden="true">&raquo;</span>
	                <span class="sr-only">Next</span>
	              </a>
	            </li>
	          </ul>
	        </nav>
	      </div>
      </c:if>