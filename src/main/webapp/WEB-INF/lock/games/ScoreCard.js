<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<form:form>
    <form:hidden id="gameScore" value="0" />
    <form:hidden id="userId" value="${user.getId()}" />
</form:form>