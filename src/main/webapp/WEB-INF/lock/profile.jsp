<!doctype html>

<html lang="en">
<head>
  <meta charset="utf-8">

  <title>${ userProfile.getUsername() }</title>
  <meta name="description" content="The HTML5 Herald">
  <meta name="author" content="SitePoint">
  <link rel="stylesheet" href="css/styles.css?v=1.0">
  <base href="/" />

</head>

<body>
  <%@ include file="titlebar.jsp" %>
	<div class="profileWrapper">
	  <div class="content">
	    <h1>${ userProfile.getUsername() }</h1>
	    <h3>User since ${ userProfile.getCreatedAt() }</h3>
	    <h3>High Scores: none</h3>
	  </div>
	</div>
</body>
</html>