      <c:if test="${not empty pages}">
	      <div class="row align-items-center justify-content-center m-0">
	        <nav aria-label="Page navigation example">
	          <ul class="pagination">
	            <li class="page-item">
                <form action="">
                  <c:if test="${not empty searchedKeyword}">
                    <input type="hidden" name="keywords" value="${searchedKeyword}" />
                  </c:if>
                  <input type="hidden" name="start" value="${currentPageIndex-step}" />
                  <button type="submit" class="btn btn-outline-gumpu" <c:if test="${not empty prevDisable}">disabled</c:if>>&laquo;</button>
                </form>
	            </li>
	            <c:set var="index" value="${firstPageIndex}"/>
	            <c:forEach var="page" begin="${firstPage}" end="${lastPage}">
		            <li class="page-item">
                  <form action="">
                    <c:if test="${not empty searchedKeyword}">
                      <input type="hidden" name="keywords" value="${searchedKeyword}" />
                    </c:if>
                    <input type="hidden" name="start" value="${index}" />
                    <button type="submit" class="btn btn-outline-gumpu" <c:if test="${page == currentPage}">disabled</c:if>>${page}</button>
                  </form>
		            </li>
	            <c:set var="index" value="${index+step}"/>
	            </c:forEach>
	            <li class="page-item">
                <form action="">
                  <c:if test="${not empty searchedKeyword}">
                    <input type="hidden" name="keywords" value="${searchedKeyword}" />
                  </c:if>
                  <input type="hidden" name="start" value="${currentPageIndex+step}" />
                  <button type="submit" class="btn btn-outline-gumpu" <c:if test="${not empty nextDisable}">disabled</c:if>>&raquo;</button>
                </form>
	            </li>
	          </ul>
	        </nav>
	      </div>
      </c:if>
