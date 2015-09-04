<%@ include file="/pages/layout/taglib.jsp"%>
<script>
	jQuery(document).ready(function() {
		TableDataForGroup.init();
		$("#masters_li").addClass("active");
		$("#masters_li").addClass("open");
		$("#account_group_li").addClass("active");
		updateGroup($("#accountDD").val());
	});

	function updateGroup(accountId) {
		if (accountId != undefined) {
			var values = {
				"accountId" : accountId
			};
			$.ajax({
				url : contextURL + '/master/account-group/get-group-by-account',
				dataType : 'json',
				data : values,
				success : function(json) {
					if (json.say == "ok") {
						$('#groupDD').val(json.groupId);
					}
				},
				error : function(jqXHR, exception) {
					if (jqXHR.status === 0) {
						alert('Not connect.\n Verify Network.');
					} else if (jqXHR.status == 404) {
						alert('Requested page not found. [404]');
					} else if (jqXHR.status == 500) {
						alert('Internal Server Error [500].');
					} else if (exception === 'parsererror') {
						alert('Requested JSON parse failed.');
					} else if (exception === 'timeout') {
						alert('Time out error.');
					} else if (exception === 'abort') {
						alert('Ajax request aborted.');
					} else {
						alert('Uncaught Error.\n' + jqXHR.responseText);
					}
				}
			});
		}
	}
</script>

<div class="container">
	<!-- start: PAGE HEADER -->
	<div class="row">
		<div class="col-sm-12">
			<!-- start: PAGE TITLE & BREADCRUMB -->
			<div class="page-header">
				<h1>
					Manage Account Groups <small>View, edit, add, delete, tag groups</small>
				</h1>
			</div>
			<!-- end: PAGE TITLE & BREADCRUMB -->
		</div>
	</div>
	<!-- end: PAGE HEADER -->
	<!-- start: PAGE CONTENT -->

	<c:choose>
		<c:when test="${showTagForm==true}">
			<div class="row" id="account_tag_form">
				<div class="col-sm-12">
					<!-- start: TEXT FIELDS PANEL -->
					<div class="panel panel-default">
						<div class="panel-heading">
							<i class="fa fa-external-link-square"></i> Tag Account-Group
							<div class="panel-tools">
	<!-- 								<a class="btn btn-xs btn-link panel-collapse collapses" href="#"> -->
	<!-- 								</a>  -->
	<!-- 								<a class="btn btn-xs btn-link panel-config" -->
	<!-- 									href="#panel-config" data-toggle="modal"> <i -->
	<!-- 									class="fa fa-wrench"></i> -->
	<!-- 								</a>  -->
	<!-- 								<a class="btn btn-xs btn-link panel-refresh" href="#"> <i -->
	<!-- 									class="fa fa-refresh"></i> -->
	<!-- 								</a>  -->
	<!-- 								<a class="btn btn-xs btn-link panel-expand" href="#"> <i -->
	<!-- 									class="fa fa-resize-full"></i> -->
	<!-- 								</a>  -->
								<a class="btn btn-xs btn-link panel-close" href="#"> <i
									class="fa fa-times"></i>
								</a>
							</div>
						</div>
						<div class="panel-body">

							<form class="form-horizontal" method="post"
								action="<%=application.getContextPath()%>/master/account-group/tag">

								<div class="form-group">
									<label class="col-sm-2 control-label" for="form-field-2">
										Account </label>
									<div class="col-sm-4">
										<select class="form-control" name="accountId" id="accountDD"
											onchange="updateGroup(this.value);">
											<c:forEach items="${accounts}" var="account">
												<option value="${account.id}">${account.name}</option>
											</c:forEach>
										</select>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label" for="form-field-2">
										Group </label>
									<div class="col-sm-4">
										<select class="form-control" name="groupId" id="groupDD">
											<option value="-1">Select Group</option>
											<c:forEach items="${groups}" var="group">
												<option value="${group.id}">${group.description}</option>
											</c:forEach>
										</select>
									</div>
								</div>

								<div class="form-group">
									<div class="col-sm-2"></div>
									<div class="col-sm-4">
										<button class="form-control btn btn-primary" type="submit">
											Save <i class="fa fa-arrow-circle-right"></i>
										</button>
									</div>
								</div>
							</form>
						</div>
					</div>
					<!-- end: TEXT FIELDS PANEL -->
				</div>
			</div>
		</c:when>
	</c:choose>

	<div class="row">
		<div class="col-md-12">
			<div class="panel panel-default">
				<div class="panel-heading">
					<i class="fa fa-external-link-square"></i>List of Groups
					<div class="panel-tools">
						<!-- 						<a class="btn btn-xs btn-link panel-collapse collapses" href="#"> -->
						<!-- 						</a>  -->
						<!-- 						<a class="btn btn-xs btn-link panel-config" href="#panel-config" -->
						<!-- 							data-toggle="modal"> <i class="fa fa-wrench"></i> -->
						<!-- 						</a>  -->
						<!-- 						<a class="btn btn-xs btn-link panel-refresh" href="#"> <i -->
						<!-- 							class="fa fa-refresh"></i> -->
						<!-- 						</a>  -->
						<!-- 						<a class="btn btn-xs btn-link panel-expand" href="#"> <i -->
						<!-- 							class="fa fa-resize-full"></i> -->
						<!-- 						</a>  -->
						<!-- 						<a class="btn btn-xs btn-link panel-close" href="#"> <i -->
						<!-- 							class="fa fa-times"></i> -->
						<!-- 						</a> -->
					</div>
				</div>
				<div class="panel-body">
					<div class="row">
						<div class="col-md-12 space20">
							<button class="btn btn-green add-row">
								Add New Group <i class="fa fa-plus"></i>
							</button>
							<a
								href="<%=application.getContextPath()%>/master/account-group/manageTag">
								<button class="btn btn-green">
									Tag Account-Group <i class="fa fa-plus"></i>
								</button>
							</a>
						</div>

					</div>
					<div class="table-responsive">
						<div id="sample_2_wrapper" class="dataTables_wrapper form-inline"
							role="grid">
							<table class="table table-striped table-hover" id="sample_2">
								<thead>
									<tr>
										<th>Description</th>
										<th>Type</th>
										<th>Edit</th>
										<th>Delete</th>
									</tr>
								</thead>
								<tbody>

									<c:forEach items="${groups}" var="group">
										<tr>
											<td>${group.description}</td>
											<c:choose>
												<c:when test="${group.profitLoss==true}">
													<td>P</td>
												</c:when>
												<c:otherwise>
													<td>B</td>
												</c:otherwise>
											</c:choose>
											<td><a href="#" class="edit-row" id="${group.id}">
													Edit </a></td>
											<td><a href="#" class="delete-row" id="${group.id}">
													Delete </a></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>