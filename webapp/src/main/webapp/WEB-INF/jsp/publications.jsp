<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<!doctype html>
<html>
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js" integrity="sha384-smHYKdLADwkXOn1EmN1qk/HfnUcbVRZyYmZ4qpPea6sjB/pTJ0euyQp0Mk8ck+5T" crossorigin="anonymous"></script>

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous">

		<!-- Custom styles -->
    <link rel="stylesheet" href="css/style.css">

    <title>{PAW_PROJECT}</title>
  </head>
  <body>
    <%@ include file="navbar.jsp" %>
    <div class="modal fade" id="loginModal" tabindex="-1" role="dialog" aria-labelledby="Login" aria-hidden="true">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="exampleModalLabel">Login</h5>
            <button type="button" class="close" data-dismiss="modal" aria-label="close">
              <span aria-hidden="true">&times;</span>
            </button>
          </div>
          <!--          Login form          -->
		  <%@ include file="loginform.jsp" %>

          <div class="modal-footer">
            <a href="/register" role="button" class="btn btn-outline-primary">Signup</a>
          </div>
        </div>
      </div>
    </div>

    <div class="container">
      <div class="row align-items-center justify-content-center">
        <div class="col">
          <h2 class="mb-3">Buscar...</h2>
          <div class="border bg-light rounded p-3">
            <form>
              <div class="input-group mb-3">
                <input type="text" class="form-control" placeholder="Qué estás buscando?">
                <div class="input-group-append">
                  <button class="btn btn-primary" type="button">Buscar</button>
                </div>
              </div>
            </form>
          </div>
        </div>
      </div>
      <div class="row align-items-center justify-content-center mt-3">
        <div class="col">
          <div class="border bg-light rounded p-3">

            <c:forEach var="publication" items="${resultList}">
            	<!-- Publication -->

	            <div class="row bg-white border border-secondary rounded ml-2 mr-2 mb-2">
	              <div class="column">
	                <img height="180" width="286" src="data:image/png;base64,${publication.image}" />
	              </div>
	              <div class="column py-2 px-3">
	                <h3><c:out value="${publication.description}" /></h3>
	                <span class="mt-3"><img height="18" src="svg/map-marker.svg" alt="icon name" /> Argentina</span></br>
	                <span class="mt-3">Cantidad disponible: </span><span class="badge badge-pill badge-success"><c:out value="${publication.remainingQuantity}" /></span></br>
	                <form:form modelAttribute="orderForm" action="/order" method="post">
	             		<form:input type="hidden" value="${publication.id}" path="publicationId" id="publicationId"/>
		                <div class="input-group input-group-sm mt-3">
		                  <form:input type="number" class="form-control" placeholder="Cantidad" path="quantity" id="quantity" />
		                  <div class="input-group-append">
		                    <button class="btn btn-outline-secondary" type="submit">Ordenar</button>
		                  </div>
		                </div>
		            </form:form>
	              </div>
	            </div>
          	</c:forEach>
          </div>
        </div>
      </div>
    </div>

  </body>
</html>
