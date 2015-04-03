<%@ include file="layout/taglib.jsp"%>
<div class="container">
	<!-- start: PAGE HEADER -->
	<div class="row">
		<div class="col-sm-12">
			<!-- start: PAGE TITLE & BREADCRUMB -->
			<div class="page-header">
				<h1>
					Company Profile <small>company profile page</small>
				</h1>
			</div>
			<!-- end: PAGE TITLE & BREADCRUMB -->
		</div>
	</div>
	<!-- end: PAGE HEADER -->
	<!-- start: PAGE CONTENT -->
	<div class="row">
		<div class="col-sm-12">
			<div class="tabbable">
				<div class="tab-content">
					<div id="panel_edit_account" class="tab-pane in active">
						<form:form commandName="company">
							<div class="row">
								<div class="col-md-12">
									<h3>Company Info</h3>
									<hr>
								</div>
								<div class="col-md-12" id="invalidCred">
									<div class="errorHandler alert alert-danger">
										<i class="fa fa-remove-sign"></i>You have some form errors. Please check below.
									</div>
								</div>
								<c:choose>
									<c:when test="${admin==true}">
										<div class="col-md-6">
											<div class="form-group">
												<label class="control-label"> Name* </label>
												<form:input class="form-control" id="name" path="name" />
												<span class="help-block"> <form:errors path="name" />
												</span>
											</div>
											<div class="form-group">
												<label class="control-label"> Contact No </label>
												<form:input class="form-control" id="contactNo"
													path="contactNo" />
											</div>
											<div class="form-group">
												<label class="control-label"> Address </label>
												<form:input class="form-control" id="address" path="address" />
											</div>
											<div class="form-group">
												<label class="control-label"> City </label>
												<form:input class="form-control" id="city" path="city" />
											</div>
											<div class="form-group">
												<label class="control-label"> State </label>
												<form:input class="form-control" id="state" path="state" />
											</div>
											<div class="form-group">
												<label class="control-label"> Zipcode </label>
												<form:input class="form-control" id="zipcode" path="zipcode" />
											</div>
										</div>
									</c:when>
									<c:otherwise>
										<div class="col-md-6">
											<div class="form-group">
												<label class="control-label"> Name* </label>
												<form:input class="form-control" id="name" path="name"
													disabled="true" />
											</div>
											<div class="form-group">
												<label class="control-label"> Contact No </label>
												<form:input class="form-control" id="contactNo"
													path="contactNo" disabled="true" />
											</div>
											<div class="form-group">
												<label class="control-label"> Address </label>
												<form:input class="form-control" id="address" path="address"
													disabled="true" />
											</div>
											<div class="form-group">
												<label class="control-label"> City </label>
												<form:input class="form-control" id="city" path="city"
													disabled="true" />
											</div>
											<div class="form-group">
												<label class="control-label"> State </label>
												<form:input class="form-control" id="state" path="state"
													disabled="true" />
											</div>
											<div class="form-group">
												<label class="control-label"> Zipcode </label>
												<form:input class="form-control" id="zipcode" path="zipcode"
													disabled="true" />
											</div>
										</div>
									</c:otherwise>
								</c:choose>

							</div>

							<div class="row">
								<div class="col-md-12">
									<div>
										Required Fields*
										<hr>
									</div>
								</div>
							</div>

							<c:choose>
								<c:when test="${admin==true}">
									<div class="row">
										<div class="col-md-8">
											<p>By clicking UPDATE, you are agreeing to the Policy and
												Terms &amp; Conditions.</p>
										</div>
										<div class="col-md-4">
											<button class="btn btn-teal btn-block" type="submit">
												Update <i class="fa fa-arrow-circle-right"></i>
											</button>
										</div>
									</div>
								</c:when>
								<c:otherwise>

								</c:otherwise>
							</c:choose>
						</form:form>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>