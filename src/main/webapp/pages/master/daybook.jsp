<%@ include file="/pages/layout/taglib.jsp"%>
<script>
jQuery(document).ready(function() {
	TableDataForDaybook.init();
	$("#masters_li").addClass("active");
	$("#masters_li").addClass("open");
	$("#daybook_li").addClass("active");
});
</script>

<div class="container">
	<!-- start: PAGE HEADER -->
	<div class="row">
		<div class="col-sm-12">
			<!-- start: PAGE TITLE & BREADCRUMB -->
			<div class="page-header">
				<h1>
					Manage Daybooks <small>View, edit, add, delete daybooks</small>
				</h1>
			</div>
			<!-- end: PAGE TITLE & BREADCRUMB -->
		</div>
	</div>
	<!-- end: PAGE HEADER -->
	<!-- start: PAGE CONTENT -->
	<div class="row">
		<div class="col-md-12">
			<div class="panel panel-default">
				<div class="panel-heading">
					<i class="fa fa-external-link-square"></i>List of Daybooks
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
								Add New Daybook <i class="fa fa-plus"></i>
							</button>
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
										<th>Edit</th>
										<th>Delete</th>
									</tr>
								</thead>
								<tbody>
								
								<c:forEach items="${daybooks}" var="daybook">
									<tr>
										<td>${daybook.name}</td>
										<td>${daybook.type}</td>
										<td><a href="#" class="edit-row" id="${daybook.id}"> Edit </a></td>
										<td><a href="#" class="delete-row" id="${daybook.id}"> Delete </a></td>
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