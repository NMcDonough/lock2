<div id="userBoxes">
		<h1>Users</h1>
		<div id="userBox">
			<select id="userSelect" onchange="getUser(event)" class="form-control">
				<option class="form-control" value="" disabled selected>Select user</option>
				<c:forEach var="userItem" items="${users}">
					<option class="form-control" value="${ userItem.getId() }">${userItem.getUsername()}</option>
				</c:forEach>
			</select>
		</div>
		<div id="editUserBox" style="display:none">
			<form:form id="editUserForm" modelAttribute="editUser" method="PUT" onsubmit="submitUserEditForm(event)" disabled="true">
				<table>
					<tr>
						<td>
							Username:
						</td>
						<td>
							<form:input cssClass="form-control" id="username" path="username" />
							<form:errors cssClass="red" path="username" />
						</td>
					</tr>
					<tr>
						<td>
							Email:
						</td>
						<td>
							<form:input cssClass="form-control" id="email" path="email" />
							<form:errors cssClass="red" path="email" />
						</td>
					</tr>
					<!-- <tr>
						<td>
							Password:
						</td>
						<td>
							<form:input id="password" path="password" />
							<form:errors cssClass="red" path="password" />
						</td>
					</tr> -->
					<tr>
						<td>
							Account is active
						</td>
						<td>
							<form:checkbox cssClass="form-control" id="isActive" path="isActive" />
						</td>
					</tr>
					<tr>
						<td>
							Permissions:
						</td>
						<td>
							<select class="form-control" id="userPermission" path="permission" onchange="testMethod(event)">
								<option value="" disabled selected>Select permission</option>
								<c:forEach var="permission" items="${permissions}">
									<option class="form-control" value="${ permission.getId() }">${permission.getName()}</option>
								</c:forEach>
							</select>
						</td>
					</tr>
				</table>
				<form:hidden id="userId" path="id" />
				<div class="btn-group btn-group-med">
					<button class="btn" type="submit">Save</button>
					<button class="btn" onclick="toggleUsers(event)">Close</button>
				</div>
			</form:form>
		</div>
	</div>
<script type="text/javascript">
	function getUser(e) {
	
		var obj = {};
	
		$.ajax(
			url="http://localhost:8080/api/user/" + e.target.value
		).done(res => {
			let data = JSON.parse(res);
			let element = $("#editUserBox")
			data["userId"] = data.id;
			delete data.id;
			delete data.password;
			let keys = Object.keys(data);
			for(index in keys) {
				let key = keys[index];
				let temp = $("#" +  key);
				if(key != "isActive"){
					console.log(key + " : " + data[key]);
					temp.val(data[key]);
				} else {
					temp.prop("checked", data[key]);
				}
			}
			$("#deleteUser").val(data.id);
			if(!element.is(":visible")){
				element.toggle("fast");
			}
			$("#userSelect").toggle("fast");
		});
	}
	
	function submitUserEditForm(e) {
		e.preventDefault();
		let x = e.path[0][5].value;
		$.ajax({
			url: "http://localhost:8080/api/user/edit/" + x,
			type: 'put',
			data: $("#editUserForm").serialize(),
			success: res => {
				alert("User data updated successfully");
			}
		});
	}
	
	function testMethod(e) {
		console.log(e.target.value);
	}
	
	function toggleUsers(e) {
		e.preventDefault();
		if($("#editUserBox").is(":visible")) {
			$("#editUserBox").toggle("fast");
		}
		$("#userSelect").toggle("slow");
	}
</script>