<%@ include file="/pages/layout/taglib.jsp"%>
<script>
	jQuery(document).ready(function() {
		FormElements.init();
		TableDataForAccount.init();
		$("#masters_li").addClass("active");
		$("#masters_li").addClass("open");
		$("#account_li").addClass("active");
		$("#account_form").addClass("no-display");
	});
</script>

<div class="container">
	<!-- start: PAGE HEADER -->
	<div class="row">
		<div class="col-sm-12">
			<!-- start: PAGE TITLE & BREADCRUMB -->
			<div class="page-header">
				<h1>
					Manage Accounts <small>View, edit, add, delete accounts</small>
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
				$("#account_form").removeClass("display");
				$("#account_form").addClass("display");
			});
			</script>
		</c:when>
	</c:choose>
	<div class="row" id="account_form">
		<div class="col-sm-12">
			<!-- start: TEXT FIELDS PANEL -->
			<div class="panel panel-default">
				<div class="panel-heading">
					<i class="fa fa-external-link-square"></i> Add a new Account
					<div class="panel-tools">
						<a class="btn btn-xs btn-link panel-collapse collapses" href="#">
						</a> 
						<a class="btn btn-xs btn-link panel-config" href="#panel-config"
							data-toggle="modal"> <i class="fa fa-wrench"></i>
						</a> 
						<a class="btn btn-xs btn-link panel-refresh" href="#"> <i
							class="fa fa-refresh"></i>
						</a> 
						<a class="btn btn-xs btn-link panel-expand" href="#"> <i
							class="fa fa-resize-full"></i>
						</a> 
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
					<form:form class="form-horizontal" commandName="account">
						<form:hidden path="id" />
						<div class="form-group">
							<label class="col-sm-2 control-label" for="form-field-1">
								Account Name </label>
							<div class="col-sm-9">
								<form:input type="text" placeholder="Enter name" id="form-field-1"
									class="form-control" path="name" />
								<span class="help-block"><form:errors path="name" />
								</span>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label" for="form-field-2">
								Group </label>
							<div class="col-sm-2">
								<form:select class="form-control" path="profitLoss">
									<form:option value="true">P</form:option>
									<form:option value="false">B</form:option>
								</form:select>
							</div>
							<div class="col-sm-4">
								<label class="control-label" for="form-field-2"> 
								(P)rofit & Loss / (B)alancesheet</label>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label" for="form-field-3">
								Debtor/Creditor? </label>
							<div class="col-sm-2">
								<form:select class="form-control" path="debtor">
									<form:option value="true">Yes</form:option>
									<form:option value="false">No</form:option>
								</form:select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label" for="form-field-3">
								Cash/Bank? </label>
							<div class="col-sm-2">
								<form:select class="form-control" path="cashBank">
									<form:option value="true">Yes</form:option>
									<form:option value="false">No</form:option>
								</form:select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label" for="form-field-4">
								Opening Balance </label>
							<div class="col-sm-2">
								<form:input type="text" id="form-field-mask-5" path="openingBalanceString" 
								class="form-control currency" />
							</div>
							<div class="col-sm-2">
								<form:select class="form-control" path="credit">
									<form:option value="true">Credit</form:option>
									<form:option value="false">Debit</form:option>
								</form:select>
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-2">
							</div>
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
					<i class="fa fa-external-link-square"></i> List of Accounts
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
							<a href="<%=application.getContextPath()%>/master/account/manage?showForm">
							<button class="btn btn-green add-row" >
								Add New Account <i class="fa fa-plus"></i>
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
										<th>Group</th>
										<th>Debtor/Creditor?</th>
										<th>Cash/Bank?</th>
										<th>Opening Balance</th>
										<th>Edit</th>
										<th>Delete</th>
									</tr>
								</thead>
								<tbody>
								
								<c:forEach items="${accounts}" var="account">
									<tr>
										<td>${account.name}</td>
										<c:choose>
											<c:when test="${account.profitLoss==true}">
											<td>P</td>
											</c:when>
											<c:otherwise>
											<td>B</td>
											</c:otherwise>
										</c:choose>
										<c:choose>
											<c:when test="${account.debtor==true}">
											<td>Yes</td>
											</c:when>
											<c:otherwise>
											<td>No</td>
											</c:otherwise>
										</c:choose>
										<c:choose>
											<c:when test="${account.cashBank==true}">
											<td>Yes</td>
											</c:when>
											<c:otherwise>
											<td>No</td>
											</c:otherwise>
										</c:choose>
										<c:choose>
											<c:when test="${account.credit==true}">
											<td>${account.openingBalance}(credit)</td>
											</c:when>
											<c:otherwise>
											<td>${account.openingBalance}(debit)</td>
											</c:otherwise>
										</c:choose>
										<td><a href="<%=application.getContextPath() %>/master/account/populate-for-edit/${account.id}" class="edit-row" id="${account.id}"> Edit </a></td>
										<td><a href="#" class="delete-row" id="${account.id}"> Delete </a></td>
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