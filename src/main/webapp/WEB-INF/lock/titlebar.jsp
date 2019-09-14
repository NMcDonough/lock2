<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link rel="stylesheet" href="static/css/bootstrap.min.css">

<nav class="navbar navbar-expand-sm bg-dark navbar-dark">
    <ul class="navbar-nav">
      <li class="nav-item active">
        <a class="nav-link" href="/lock">Home</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="/lock/about">About</a>
      </li>
        <c:if test="${user.getUsername() != null && user.getUsername() != undefined}">
        	<li>
        		<a class="nav-link" href="lock/user/${user.getUsername()}">${user.getUsername()}</a>
			</li>
			<c:if test="${user.getPermission().getCanAccessModPage()}">
				<li>
					<a class="nav-link" href="lock/admin">Administrator Tools</a>
				</li>
			</c:if>
      	    <li>
	        	<a class="nav-link" href="lock/logout">Log out</a>
	      	</li>
        </c:if>
        <c:if test="${user == null || user == undefined}">
        	<li>
        		<a class="nav-link" href="/lock/login">Login/Register</a>
        	</li>
        </c:if>
    </ul>
  </nav>