<%@ include file="/pages/layout/taglib.jsp"%>
<script>
	jQuery(document).ready(function() {
		TableDataForFiles.init();
		$("input:checkbox").click(function() {
		    if ($(this).is(":checked")) {
		        var group = "input:checkbox[name='" + $(this).attr("name") + "']";
		        $(group).prop("checked", false);
		        $(this).prop("checked", true);
		    } else {
		        $(this).prop("checked", false);
		    }
		});
		$("#masters_li").addClass("active");
		$("#masters_li").addClass("open");
		$("#file_li").addClass("active");
	});
</script>

<div class="container">
	<!-- start: PAGE HEADER -->
	<div class="row">
		<div class="col-sm-12">
			<!-- start: PAGE TITLE & BREADCRUMB -->
			<div class="page-header">
				<h1>
					Manage Files <small>View, edit, add, delete files</small>
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
					<i class="fa fa-external-link-square"></i>List of Files
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
								Add New File <i class="fa fa-plus"></i>
							</button>
						</div>
					</div>
					<div class="table-responsive">
						<div id="sample_2_wrapper" class="dataTables_wrapper form-inline"
							role="grid">
							<table class="table table-striped table-hover" id="sample_2">
								<thead>
									<tr>
									    <th>Select</th>
										<th>Name</th>
										<th>Company Name</th>
										<th>Edit</th>
										<th>Delete</th>
									</tr>
								</thead>
								<tbody>
								
								<c:forEach items="${files}" var="file">
									<tr>
										<c:choose>
										
											<c:when test="${selectedFile.id==file.id}">
												<td><input type="checkbox" name="file" id="${file.id}"
											 	class="select-file" checked="checked" /></td>
											</c:when>
											<c:otherwise>
												<td><input type="checkbox" name="file" id="${file.id}"
											 	class="select-file" /></td>
											</c:otherwise>
										</c:choose>
										
										<td id="name">${file.name}</td>
										<td id="companyName">${file.companyName}</td>
										<td><a href="#" class="edit-row" id="${file.id}"> Edit </a></td>
										<td><a href="#" class="delete-row" id="${file.id}"> Delete </a></td>
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