<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="org.springframework.security.core.context.SecurityContextHolder"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ include file="taglib.jsp" %>
<!-- Template Name: Clip-One - Responsive Admin Template build with Twitter Bootstrap 3.x Version: 1.4 Author: ClipTheme -->
<!--[if IE 8]><html class="ie8 no-js" lang="en"><![endif]-->
<!--[if IE 9]><html class="ie9 no-js" lang="en"><![endif]-->
<!--[if !IE]><!-->
<html lang="en" class="no-js">
	<!--<![endif]-->
	<!-- start: HEAD -->
	<head>
	<link rel="shortcut icon" type="image/ico" href="<%=application.getContextPath() %>/favicon.ico">
	<script type="text/javascript">
		var contextURL = '<%= application.getContextPath() %>'
	</script>
		<title><tiles:getAsString name="title" /></title>
		<!-- start: META -->
		<meta charset="utf-8" />
		<!--[if IE]><meta http-equiv='X-UA-Compatible' content="IE=edge,IE=9,IE=8,chrome=1" /><![endif]-->
		<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="apple-mobile-web-app-status-bar-style" content="black">
		<meta content="" name="description" />
		<meta content="" name="author" />
		<!-- end: META -->
		<!-- start: MAIN CSS -->
		<link rel="stylesheet" href="<%=application.getContextPath() %>/assets/plugins/bootstrap/css/bootstrap.min.css">
		<link rel="stylesheet" href="<%=application.getContextPath() %>/assets/plugins/font-awesome/css/font-awesome.min.css">
		<link rel="stylesheet" href="<%=application.getContextPath() %>/assets/fonts/style.css">
		<link rel="stylesheet" href="<%=application.getContextPath() %>/assets/css/main.css">
		<link rel="stylesheet" href="<%=application.getContextPath() %>/assets/css/main-responsive.css">
		<link rel="stylesheet" href="<%=application.getContextPath() %>/assets/plugins/iCheck/skins/all.css">
		<link rel="stylesheet" href="<%=application.getContextPath() %>/assets/plugins/bootstrap-colorpalette/css/bootstrap-colorpalette.css">
		<link rel="stylesheet" href="<%=application.getContextPath() %>/assets/plugins/perfect-scrollbar/src/perfect-scrollbar.css">
		<link rel="stylesheet" href="<%=application.getContextPath() %>/assets/css/theme_light.css" type="text/css" id="skin_color">
		<link rel="stylesheet" href="<%=application.getContextPath() %>/assets/css/print.css" type="text/css" media="print"/>
		<!--[if IE 7]>
		<link rel="stylesheet" href="assets/plugins/font-awesome/css/font-awesome-ie7.min.css">
		<![endif]-->
		<!-- end: MAIN CSS -->
		<!-- start: CSS REQUIRED FOR THIS PAGE ONLY -->
		<link rel="stylesheet" href="<%=application.getContextPath() %>/assets/plugins/fullcalendar/fullcalendar/fullcalendar.css">
		<!-- end: CSS REQUIRED FOR THIS PAGE ONLY -->
		<link rel="stylesheet" type="text/css" href="<%=application.getContextPath() %>/assets/plugins/select2/select2.css" />
		<link rel="stylesheet" href="<%=application.getContextPath() %>/assets/plugins/DataTables/media/css/DT_bootstrap.css" />
		<link rel="shortcut icon" href="favicon.ico" />
		<!-- start: CSS REQUIRED FOR ACCOUNT PAGE ONLY -->
		<link rel="stylesheet" href="<%=application.getContextPath() %>/assets/plugins/select2/select2.css">
		<link rel="stylesheet" href="<%=application.getContextPath() %>/assets/plugins/datepicker/css/datepicker.css">
		<link rel="stylesheet" href="<%=application.getContextPath() %>/assets/plugins/bootstrap-timepicker/css/bootstrap-timepicker.min.css">
		<link rel="stylesheet" href="<%=application.getContextPath() %>/assets/plugins/bootstrap-daterangepicker/daterangepicker-bs3.css">
		<link rel="stylesheet" href="<%=application.getContextPath() %>/assets/plugins/bootstrap-colorpicker/css/bootstrap-colorpicker.css">
		<link rel="stylesheet" href="<%=application.getContextPath() %>/assets/plugins/jQuery-Tags-Input/jquery.tagsinput.css">
		<link rel="stylesheet" href="<%=application.getContextPath() %>/assets/plugins/bootstrap-fileupload/bootstrap-fileupload.min.css">
		<link rel="stylesheet" href="<%=application.getContextPath() %>/assets/plugins/summernote/build/summernote.css">
		<!-- end: CSS REQUIRED FOR ACCOUNT PAGE ONLY -->
		
		<!-- start: MAIN JAVASCRIPTS -->
		<!--[if lt IE 9]>
		<script src="assets/plugins/respond.min.js"></script>
		<script src="assets/plugins/excanvas.min.js"></script>
		<script type="text/javascript" src="assets/plugins/jQuery-lib/1.10.2/jquery.min.js"></script>
		<![endif]-->
		<!--[if gte IE 9]><!-->
		<script src="<%=application.getContextPath() %>/assets/plugins/jQuery-lib/2.0.3/jquery.min.js"></script>
		<!--<![endif]-->
		<script src="<%=application.getContextPath() %>/assets/plugins/jquery-ui/jquery-ui-1.10.2.custom.min.js"></script>
		<script src="<%=application.getContextPath() %>/assets/plugins/bootstrap/js/bootstrap.min.js"></script>
		<script src="<%=application.getContextPath() %>/assets/plugins/bootstrap-hover-dropdown/bootstrap-hover-dropdown.min.js"></script>
		<script src="<%=application.getContextPath() %>/assets/plugins/blockUI/jquery.blockUI.js"></script>
		<script src="<%=application.getContextPath() %>/assets/plugins/iCheck/jquery.icheck.min.js"></script>
		<script src="<%=application.getContextPath() %>/assets/plugins/perfect-scrollbar/src/jquery.mousewheel.js"></script>
		<script src="<%=application.getContextPath() %>/assets/plugins/perfect-scrollbar/src/perfect-scrollbar.js"></script>
		<script src="<%=application.getContextPath() %>/assets/plugins/less/less-1.5.0.min.js"></script>
		<script src="<%=application.getContextPath() %>/assets/plugins/jquery-cookie/jquery.cookie.js"></script>
		<script src="<%=application.getContextPath() %>/assets/plugins/bootstrap-colorpalette/js/bootstrap-colorpalette.js"></script>
		<script src="<%=application.getContextPath() %>/assets/js/main.js"></script>
		<!-- end: MAIN JAVASCRIPTS -->
		<!-- start: JAVASCRIPTS REQUIRED FOR THIS PAGE ONLY -->
		<script src="<%=application.getContextPath() %>/assets/plugins/flot/jquery.flot.js"></script>
		<script src="<%=application.getContextPath() %>/assets/plugins/flot/jquery.flot.pie.js"></script>
		<script src="<%=application.getContextPath() %>/assets/plugins/flot/jquery.flot.resize.min.js"></script>
		<script src="<%=application.getContextPath() %>/assets/plugins/jquery.sparkline/jquery.sparkline.js"></script>
		<script src="<%=application.getContextPath() %>/assets/plugins/jquery-easy-pie-chart/jquery.easy-pie-chart.js"></script>
		<script src="<%=application.getContextPath() %>/assets/plugins/jquery-ui-touch-punch/jquery.ui.touch-punch.min.js"></script>
		<script src="<%=application.getContextPath() %>/assets/plugins/fullcalendar/fullcalendar/fullcalendar.js"></script>
		<script src="<%=application.getContextPath() %>/assets/js/index.js"></script>
		<!-- end: JAVASCRIPTS REQUIRED FOR THIS PAGE ONLY -->
		<script type="text/javascript" src="<%=application.getContextPath() %>/assets/plugins/bootbox/bootbox.min.js"></script>
		<script type="text/javascript" src="<%=application.getContextPath() %>/assets/plugins/jquery-mockjax/jquery.mockjax.js"></script>
		<script type="text/javascript" src="<%=application.getContextPath() %>/assets/plugins/select2/select2.min.js"></script>
		<script type="text/javascript" src="<%=application.getContextPath() %>/assets/plugins/DataTables/media/js/jquery.dataTables.min.js"></script>
		<script type="text/javascript" src="<%=application.getContextPath() %>/assets/plugins/DataTables/media/js/DT_bootstrap.js"></script>
		<script src="<%=application.getContextPath() %>/assets/js/table-data.js"></script>
		<!-- start: JAVASCRIPTS REQUIRED FOR ACCOUNT PAGE ONLY -->
		<script src="<%=application.getContextPath() %>/assets/plugins/jquery-inputlimiter/jquery.inputlimiter.1.3.1.min.js"></script>
		<script src="<%=application.getContextPath() %>/assets/plugins/autosize/jquery.autosize.min.js"></script>
		<script src="<%=application.getContextPath() %>/assets/plugins/select2/select2.min.js"></script>
		<script src="<%=application.getContextPath() %>/assets/plugins/jquery.maskedinput/src/jquery.maskedinput.js"></script>
		<script src="<%=application.getContextPath() %>/assets/plugins/jquery-maskmoney/jquery.maskMoney.js"></script>
		<script src="<%=application.getContextPath() %>/assets/plugins/bootstrap-datepicker/js/bootstrap-datepicker.js"></script>
		<script src="<%=application.getContextPath() %>/assets/plugins/bootstrap-timepicker/js/bootstrap-timepicker.min.js"></script>
		<script src="<%=application.getContextPath() %>/assets/plugins/bootstrap-daterangepicker/moment.min.js"></script>
		<script src="<%=application.getContextPath() %>/assets/plugins/bootstrap-daterangepicker/daterangepicker.js"></script>
		<script src="<%=application.getContextPath() %>/assets/plugins/bootstrap-colorpicker/js/bootstrap-colorpicker.js"></script>
		<script src="<%=application.getContextPath() %>/assets/plugins/bootstrap-colorpicker/js/commits.js"></script>
		<script src="<%=application.getContextPath() %>/assets/plugins/jQuery-Tags-Input/jquery.tagsinput.js"></script>
		<script src="<%=application.getContextPath() %>/assets/plugins/bootstrap-fileupload/bootstrap-fileupload.min.js"></script>
		<script src="<%=application.getContextPath() %>/assets/plugins/summernote/build/summernote.min.js"></script>
		<script src="<%=application.getContextPath() %>/assets/plugins/ckeditor/ckeditor.js"></script>
		<script src="<%=application.getContextPath() %>/assets/plugins/ckeditor/adapters/jquery.js"></script>
		<script src="<%=application.getContextPath() %>/assets/js/form-elements.js"></script>
		<!-- end: JAVASCRIPTS REQUIRED FOR ACCOUNT PAGE ONLY -->
		<script src="<%=application.getContextPath()%>/assets/plugins/jquery-validation/dist/jquery.validate.min.js"></script>
		<script>
			jQuery(document).ready(function() {
				Main.init();
				Index.init();
				
				$( ".help-block" ).each(function() {
					if(this.innerHTML.trim()!="") {
						$(this).closest('.form-group').removeClass('has-success').addClass('has-error');
						$("#invalidCred").addClass("display");
					} else {
						$(this).closest('.form-group').removeClass('has-error');
						$("#invalidCred").addClass("no-display");
					}
				});
			});
		</script>
	</head>
	<!-- end: HEAD -->
	<!-- start: BODY -->
	<body>
		<!-- start: HEADER -->
		<div class="navbar navbar-inverse navbar-fixed-top">
			<tiles:insertAttribute name="header" />
		</div>
		<!-- end: HEADER -->
		<!-- start: MAIN CONTAINER -->
		<div class="main-container">
			<div class="navbar-content">
				<tiles:insertAttribute name="menu" />
			</div>
			<!-- start: PAGE -->
			<div class="main-content">
				<tiles:insertAttribute name="body" />
			</div>
			<!-- end: PAGE -->
		</div>
		<!-- end: MAIN CONTAINER -->
		<!-- start: FOOTER -->
		<div class="footer clearfix">
			<tiles:insertAttribute name="footer" />
		</div>
		<!-- end: FOOTER -->
		
		<div id="event-management" class="modal fade" tabindex="-1" data-width="760" style="display: none;">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
							&times;
						</button>
						<h4 class="modal-title">Event Management</h4>
					</div>
					<div class="modal-body"></div>
					<div class="modal-footer">
						<button type="button" data-dismiss="modal" class="btn btn-light-grey">
							Close
						</button>
						<button type="button" class="btn btn-danger remove-event no-display">
							<i class='fa fa-trash-o'></i> Delete Event
						</button>
						<button type='submit' class='btn btn-success save-event'>
							<i class='fa fa-check'></i> Save
						</button>
					</div>
				</div>
			</div>
		</div>
		
	</body>
	<!-- end: BODY -->
</html>