<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!doctype html>

<html lang="en">
<head>
  <meta charset="utf-8">

  <title>Lock</title>
  <meta name="description" content="A game website">
  <meta name="author" content="Noah McDonough">
  <link rel="stylesheet" href="/static/css/fonts.css">
  <base href="/lock"/>
</head>

<body>
	<%@ include file="titlebar.jsp" %>
	<jsp:include page="${page}.jsp" />
</body>
</html>