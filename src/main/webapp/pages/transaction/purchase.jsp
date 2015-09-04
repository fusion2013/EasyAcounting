<%@ include file="/pages/layout/taglib.jsp"%>
<script>
	jQuery(document).ready(function() {
		FormElements.init();
		TableDataForAccount.init();
		$("#transactions_li").addClass("active");
		$("#transactions_li").addClass("open");
		$("#purchase_li").addClass("active");
	});
</script>

<div class="container">
	<div class="row">
		<div class="col-sm-12">
			<!-- start: PAGE TITLE & BREADCRUMB -->
			<div class="page-header">
				<h1>
					Manage Purchase Entries <small>View, edit, add, delete entries</small>
				</h1>
			</div>
			<!-- end: PAGE TITLE & BREADCRUMB -->
		</div>
	</div>
</div>