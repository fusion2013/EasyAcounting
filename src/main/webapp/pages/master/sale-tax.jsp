<%@ include file="/pages/layout/taglib.jsp"%>
<script>
	jQuery(document).ready(function() {
		FormElements.init();
		TableDataForSaleTax.init();
		$("#masters_li").addClass("active");
		$("#masters_li").addClass("open");
		$("#sale_tax_li").addClass("active");
		$("#sale_tax_form").addClass("no-display");
	});
</script>

<div class="container">
	<!-- start: PAGE HEADER -->
	<div class="row">
		<div class="col-sm-12">
			<!-- start: PAGE TITLE & BREADCRUMB -->
			<div class="page-header">
				<h1>
					Manage Sale Taxes <small>View, edit, add, delete sale taxes</small>
				</h1>
			</div>
			<!-- end: PAGE TITLE & BREADCRUMB -->
		</div>
	</div>
	<!-- end: PAGE HEADER -->
	<!-- start: PAGE CONTENT -->
	<c:choose>

		<c:when test="${showForm==true}">
			<script type="text/javascript">
				jQuery(document).ready(function() {
					$("#sale_tax_form").removeClass("display");
					$("#sale_tax_form").addClass("display");
				});
			</script>
		</c:when>
	</c:choose>
	<div class="row" id="sale_tax_form">
		<div class="col-sm-12">
			<!-- start: TEXT FIELDS PANEL -->
			<div class="panel panel-default">
				<div class="panel-heading">
					<i class="fa fa-external-link-square"></i> Add a new Sale Tax
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
						<a class="btn btn-xs btn-link panel-close" href="#"> <i
							class="fa fa-times"></i>
						</a>
					</div>
				</div>
				<div class="panel-body">
					<div class="col-md-12" id="invalidCred">
						<div class="errorHandler alert alert-danger">
							<i class="fa fa-remove-sign"></i>You have some form errors.
							Please check below.
						</div>
					</div>
					<form:form class="form-horizontal" commandName="saleTax">
						<form:hidden path="id" />
						<div class="form-group">
							<label class="col-sm-2 control-label" for="form-field-1">
								Sale Tax Name </label>
							<div class="col-sm-9">
								<form:input type="text" placeholder="Enter name"
									id="form-field-1" class="form-control" path="name" />
								<span class="help-block"><form:errors path="name" /> </span>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label" for="form-field-2">
								(S)ale/(P)urchase </label>
							<div class="col-sm-2">
								<form:select class="form-control" path="forSale">
									<form:option value="true">Sale</form:option>
									<form:option value="false">Purchase</form:option>
								</form:select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label" for="form-field-1">
								Percentage(%) </label>
							<div class="col-sm-9">
								<form:input type="text" id="form-field-mask-5" path="perc"
									class="form-control currency" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label" for="form-field-3">
								Post Account </label>
							<div class="col-sm-2">
								<select class="form-control" name="accountId">
									<c:forEach items="${accounts}" var="account">
										<c:choose>
											<c:when test="${saleTax.postAccount.id.equals(account.id)}">
												<option value="${account.id}" selected="selected">${account.name}</option>
											</c:when>
											<c:otherwise>
												<option value="${account.id}">${account.name}</option>
											</c:otherwise>
										</c:choose>
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
					</form:form>
				</div>
			</div>
			<!-- end: TEXT FIELDS PANEL -->
		</div>
	</div>

	<div class="row">
		<div class="col-sm-12">
			<!-- start: TEXT FIELDS PANEL -->
			<div class="panel panel-default">
				<div class="panel-heading">
					<i class="fa fa-external-link-square"></i> List of SaleTaxes
					<div class="panel-tools">
<!-- 						<a class="btn btn-xs btn-link panel-collapse collapses" href="#"> -->
<!-- 						</a> <a class="btn btn-xs btn-link panel-config" href="#panel-config" -->
<!-- 							data-toggle="modal"> <i class="fa fa-wrench"></i> -->
<!-- 						</a> <a class="btn btn-xs btn-link panel-refresh" href="#"> <i -->
<!-- 							class="fa fa-refresh"></i> -->
<!-- 						</a> <a class="btn btn-xs btn-link panel-expand" href="#"> <i -->
<!-- 							class="fa fa-resize-full"></i> -->
<!-- 						</a> <a class="btn btn-xs btn-link panel-close" href="#"> <i -->
<!-- 							class="fa fa-times"></i> -->
<!-- 						</a> -->
					</div>
				</div>
				<div class="panel-body">
					<div class="row">
						<div class="col-md-12 space20">
							<a
								href="<%=application.getContextPath()%>/master/sale-tax/manage?showForm">
								<button class="btn btn-green add-row">
									Add New Sale Tax <i class="fa fa-plus"></i>
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
										<th>Name</th>
										<th>(S)ale/(P)urchase</th>
										<th>Percentage(%)</th>
										<th>Post Account</th>
										<th>Edit</th>
										<th>Delete</th>
									</tr>
								</thead>
								<tbody>

									<c:forEach items="${saleTaxes}" var="saleTax">
										<tr>
											<td>${saleTax.name}</td>
											<c:choose>
												<c:when test="${saleTax.forSale==true}">
													<td>Sale</td>
												</c:when>
												<c:otherwise>
													<td>Purchase</td>
												</c:otherwise>
											</c:choose>
											<td>${saleTax.perc}</td>
											<td>${saleTax.postAccount.name}</td>
											<td><a
												href="<%=application.getContextPath() %>/master/sale-tax/populate-for-edit/${saleTax.id}"
												class="edit-row" id="${saleTax.id}"> Edit </a></td>
											<td><a href="#" class="delete-row" id="${saleTax.id}">
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
	<!-- end: PAGE CONTENT-->
	<!-- end: PAGE -->
</div>