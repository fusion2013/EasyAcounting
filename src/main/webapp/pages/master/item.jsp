<%@ include file="/pages/layout/taglib.jsp"%>
<script>
	jQuery(document).ready(function() {
		FormElements.init();
		TableDataForItem.init();
		$("#masters_li").addClass("active");
		$("#masters_li").addClass("open");
		$("#item_li").addClass("active");
		$("#item_form").addClass("no-display");
	});
</script>

<div class="container">
	<!-- start: PAGE HEADER -->
	<div class="row">
		<div class="col-sm-12">
			<!-- start: PAGE TITLE & BREADCRUMB -->
			<div class="page-header">
				<h1>
					Manage Items <small>View, edit, add, delete items</small>
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
				$("#item_form").removeClass("display");
				$("#item_form").addClass("display");
			});
			</script>
		</c:when>
	</c:choose>
	<div class="row" id="item_form">
		<div class="col-sm-12">
			<!-- start: TEXT FIELDS PANEL -->
			<div class="panel panel-default">
				<div class="panel-heading">
					<i class="fa fa-external-link-square"></i> Add a new Item
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
					<form:form class="form-horizontal" commandName="item">
						<form:hidden path="id" />
						<div class="form-group">
							<label class="col-sm-2 control-label" for="form-field-1">
								Item Name </label>
							<div class="col-sm-9">
								<form:input type="text" placeholder="Enter name" id="form-field-1"
									class="form-control" path="name" />
								<span class="help-block"><form:errors path="name" />
								</span>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label" for="form-field-2">
								Item Type </label>
							<div class="col-sm-2">
								<form:select class="form-control" path="goods">
									<form:option value="true">G</form:option>
									<form:option value="false">S</form:option>
								</form:select>
							</div>
							<div class="col-sm-4">
								<label class="control-label" for="form-field-2"> 
								(G)oods / (S)ervice</label>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label" for="form-field-1">
								Purchase Rate </label>
							<div class="col-sm-9">
								<form:input type="text" id="form-field-mask-5" path="purchaseRateString" 
									class="form-control currency" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label" for="form-field-1">
								Sale Rate </label>
							<div class="col-sm-9">
								<form:input type="text" id="form-field-mask-5" path="saleRateString" 
									class="form-control currency" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label" for="form-field-1">
								Op. Qty </label>
							<div class="col-sm-9">
								<form:input type="text" id="form-field-mask-5" path="openingQtyString" 
									class="form-control currency" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label" for="form-field-1">
								Op. Value </label>
							<div class="col-sm-9">
								<form:input type="text" id="form-field-mask-5" path="openingValueString" 
									class="form-control currency" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label" for="form-field-1">
								Purchase VAT </label>
							<div class="col-sm-9">
<%-- 								<form:input type="text" id="form-field-mask-5" path="purchaseVAT"  --%>
<%-- 									class="form-control currency" /> --%>
								<select class="form-control" name="purchaseVatId">
									<c:forEach items="${vatsForPurchase}" var="purchaseVat">
										<c:choose>
											<c:when test="${item.purchaseVat.id.equals(purchaseVat.id)}">
												<option value="${purchaseVat.id}" selected="selected">
													${purchaseVat.name}
												</option>
											</c:when>
											<c:otherwise>
												<option value="${purchaseVat.id}">
													${purchaseVat.name}
												</option>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label" for="form-field-1">
								Sale VAT </label>
							<div class="col-sm-9">
<%-- 								<form:input type="text" id="form-field-mask-5" path="saleVAT"  --%>
<%-- 									class="form-control currency" /> --%>
								<select class="form-control" name="saleVatId">
									<c:forEach items="${vatsForSale}" var="saleVat">
										<c:choose>
											<c:when test="${item.saleVat.id.equals(saleVat.id)}">
												<option value="${saleVat.id}" selected="selected">
													${saleVat.name}
												</option>
											</c:when>
											<c:otherwise>
												<option value="${saleVat.id}">
													${saleVat.name}
												</option>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</select>
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
					<i class="fa fa-external-link-square"></i> List of Items
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
							<a href="<%=application.getContextPath()%>/master/item/manage?showForm">
							<button class="btn btn-green add-row" >
								Add New Item <i class="fa fa-plus"></i>
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
										<th>Type</th>
										<th>Purchase Rate</th>
										<th>Sale Rate</th>
										<th>Op. Qty</th>
										<th>Op. Value</th>
										<th>Purchase VAT</th>
										<th>Sale VAT</th>
										<th>Edit</th>
										<th>Delete</th>
									</tr>
								</thead>
								<tbody>
								
								<c:forEach items="${items}" var="item">
									<tr>
										<td>${item.name}</td>
										<c:choose>
											<c:when test="${item.goods==true}">
											<td>G</td>
											</c:when>
											<c:otherwise>
											<td>S</td>
											</c:otherwise>
										</c:choose>
										<td>${item.purchaseRate}</td>
										<td>${item.saleRate}</td>
										<td>${item.openingQty}</td>
										<td>${item.openingValue}</td>
										<td>${item.purchaseVat.name}</td>
										<td>${item.saleVat.name}</td>
										<td><a href="<%=application.getContextPath() %>/master/item/populate-for-edit/${item.id}" class="edit-row" id="${item.id}"> Edit </a></td>
										<td><a href="#" class="delete-row" id="${item.id}"> Delete </a></td>
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