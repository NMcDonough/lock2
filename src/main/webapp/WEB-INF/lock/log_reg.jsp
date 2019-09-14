<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<link rel="stylesheet" href="static/css/logreg.css">
<div class="wrapper">
	<div id="boxes">
		<div id="regBox">
			<form:form method="POST" action="api/user/register" modelAttribute="newUser">
				<table>
					<tr>
						<td>Username:</td>
						<td>
							<form:input path="username"/>
							<form:errors path="username" cssClass="red"/>
						</td>
					</tr>
					<tr>
						<td>Email:</td>
						<td>
							<form:input path="email"/><br>
							<form:errors path="email" cssClass="red"/>
						</td>
					</tr>
					<tr>
						<td>Password:</td>
						<td>
							<form:password path="password"/>
						</td>
					</tr>
					<tr>
						<td>Confirm Password:</td>
						<td>
							<form:password path="confirm"/>
							<form:errors id="regPassword" path="password" cssClass="red"/>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<button class="btn">
								Submit
							</button>
						</td>
					</tr>
				</table>
			</form:form>
		</div>
		
		<div id="logBox">
			<form:form method="POST" action="lock/login" modelAttribute="user">
			<table>
				<form:errors path="isActive" cssClass="red" />
				<form:errors path="email" cssClass="red" />
				<form:errors path="password" cssClass="red" />
				<tr>
					<td>Email:</td>
					<td>
						<form:input path="email"/>
					</td>
				</tr>
				<tr>
					<td>Password:</td>
					<td>
						<form:password path="password" />
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<button class="btn">
							Login
						</button>
					</td>
				</tr>
			</table>
		</form:form>
		</div>
	</div>
</div>
