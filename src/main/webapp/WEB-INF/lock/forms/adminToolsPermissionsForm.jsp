<div id="permissionBoxes">
		<div id="formBox">
			<h1>Permissions</h1>
			<select id="permFormSelect" class="form-control" onchange="showPermissionsForm(event)">
				<option value="" disabled selected>Select form</option>
				<option value="new">New Permission</option>
				<option value="edit">Edit Permission</option>
			</select>
			
			<div id="editPermBox" style="display:none;">
				<div id='selectBox'>
					<select class="form-control" onChange="getPermission(event)">
						<option class="form-control" value="" disabled selected>Select permission</option>
						<c:forEach var="permission" items="${permissions}">
							<option class="form-control" value="${ permission.getId() }">${permission.getName()}</option>
						</c:forEach>
					</select>
				</div>
				<form:form id="editObj" modelAttribute="editPermission" method="PUT" onsubmit="editPermission(event)">
					<table>
						<tr>
							<td>
								Name:
							</td>
							<td>
								<form:input cssClass="form-control" id="name" path="name"/>
							</td>
						</tr>
						<tr>
							<td>
								User can edit user accounts?
							</td>
							<td>
								<form:checkbox cssClass="form-control" id="canEditUsers" path="canEditUsers" />
							</td>
						</tr>
						<tr>
							<td>
								User can edit pages?
							</td>
							<td>
								<form:checkbox cssClass="form-control" id="canEditPages" path="canEditPages" />
							</td>
						</tr>
						<tr>
							<td>
								User can access this page?
							</td>
							<td>
								<form:checkbox cssClass="form-control" id="canAccessModPage" path="canAccessModPage" />
							</td>
						</tr>
						<tr>
							<td>
								User can edit moderator accounts?
							</td>
							<td>
								<form:checkbox cssClass="form-control" id="canEditMods" path="canEditMods" />
							</td>
						</tr>
					</table>
					<form:hidden id="id" path="id" />
					<div class="btn-group btn-group-med">
						<button class="btn" type="submit">Submit</button>
						<button class="btn" id="delete" onclick="deletePerm(event)">Delete</button>
						<button class="btn" id="closePerm" onclick="toggleEditPermBox(event)">Close</button>
					</div>
				</form:form>
			</div>
		</div>
		
		<div id="newPermBox"  style="display:none">
			<h3>New Permission</h3>
			<form:form id="newPermission" method="POST" onsubmit="makePermission(event)" modelAttribute="newPermission">
				<table>
					<tr>
						<td>
							Name:
						</td>
						<td>
							<form:input cssClass="form-control" path="name" /><br>
							<form:errors path="name" cssClass="red"/>
						</td>
					</tr>
					<tr>
						<td>
							User can edit user accounts?
						</td>
						<td>
							<form:checkbox cssClass="form-control" path="canEditUsers"/>
						</td>
					</tr>
					<tr>
						<td>
							User can edit moderator accounts?
						</td>
						<td>
							<form:checkbox cssClass="form-control" path="canEditMods"/>
						</td>
					</tr>
					<tr>
						<td>
							User can edit pages?
						</td>
						<td>
							<form:checkbox cssClass="form-control" path="canEditPages"/>
						</td>
					</tr>
					<tr>
						<td>
							User can access this page?
						</td>
						<td>
							<form:checkbox cssClass="form-control" path="canAccessModPage"/>
						</td>
					</tr>
				</table>
				<div class="btn-group btn-group-med">
					<button class="btn">Submit</button>
					<button class="btn" onclick="closeNewFormBox(event)">Close</button>
				</div>
			</form:form>
		</div>
	</div>
<script type="text/javascript">
	function deletePerm(e) {
		e.preventDefault();
		$.ajax(
			url="http://localhost:8080/api/permissions/delete/" + e.target.value,
			method="post"
		).done(res => {
		});
	}
	function getPermission(e){
		var obj = {};
		$.ajax(
			url="http://localhost:8080/api/permissions/" + e.target.value
		).done(res => {
			let data = JSON.parse(res);
			delete data.users;
			var element = $("#editBox");
			var keys = Object.keys(data);
	
			for(index in keys) {
				let key = keys[index];
				let temp = $("#" + key);
				if(key != "name") {
					temp.prop("checked", data[key]);
				} else {
					temp.val(data[key]);
				}
			}
			$("#delete").val(data.id);
			if(!element.is(":visible")){
				element.toggle("slow");
			}
		})
	}
	function makePermission(e) {
		e.preventDefault();
		$.ajax({
			url: "http://localhost:8080/api/makePermission",
			type: "post",
			data: $("#newPermission").serialize(),
			success: res => {
				alert("Data updated successfully");
			}
		})
	}
	function editPermission(e) {
		e.preventDefault();
		$.ajax({
			url:"http:localhost:8080/api/makePermission"
		})
	}
	function closeNewFormBox(e) {
		e.preventDefault();
		$("#newPermBox").toggle("fast");
		$("#permFormSelect").toggle("fast");
	}
	
	function showPermissionsForm(e) {
		e.preventDefault();
		switch(e.target.value){
		
		case "new":
			if(!$("#newPermBox").is(":visible")) {
				$("#newPermBox").toggle("fast");
			}
			if($("#editPermBox").is(":visible")) {
				$("#editPermBox").toggle("fast");
			}
			break;
		case "edit":
			if(!$("#editPermBox").is(":visible")) {
				$("#editPermBox").toggle("fast");
			}
			if($("#newPermBox").is(":visible")) {
				$("#newPermBox").toggle("fast");
			}
		default:
			break;
		}
		
		if($("#permFormSelect").is(":visible")) {
			$("#permFormSelect").toggle("fast");
		}
	}
	function toggleEditPermBox(e) {
		e.preventDefault();
		if($("#editPermBox").is(":visible")) {
			$("#editPermBox").toggle("fast");
		}
		$("#permFormSelect").toggle("fast");
	}
</script>