<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link rel="stylesheet" href="static/css/bootstrap.min.css">

<nav class="navbar navbar-expand-sm bg-dark navbar-dark">
    <ul class="navbar-nav">
      <li class="nav-item active">
        <a class="nav-link" href="/">Home</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="about">About</a>
      </li>
        <c:if test="${user.getUsername() != null && user.getUsername() != undefined}">
        	<li>
        		<a class="nav-link" href="profile/${user.getUsername()}">${user.getUsername()}</a>
        	</li>
      	    <li>
	        	<a class="nav-link" href="logout">Log out</a>
	      	</li>
        </c:if>
        <c:if test="${user == null || user == undefined}">
        	<a class="nav-link" href="login">Login/Register</a>
        </c:if>
    </ul>
  </nav>