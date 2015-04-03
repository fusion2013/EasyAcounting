 <!-- start: SIDEBAR -->
<div class="main-navigation navbar-collapse collapse">
	<!-- start: MAIN MENU TOGGLER BUTTON -->
	<div class="navigation-toggler">
		<i class="clip-chevron-left"></i> <i class="clip-chevron-right"></i>
	</div>
	<!-- end: MAIN MENU TOGGLER BUTTON -->
	<!-- start: MAIN NAVIGATION MENU -->
	<ul class="main-navigation-menu">
		<li><a href="<%=application.getContextPath() %>/dashboard"><i
				class="clip-home-3"></i> <span class="title"> Dashboard </span><span
				class="selected"></span> </a></li>
		<li id="masters_li"><a href="javascript:void(0)"><i class="clip-screen"></i>
				<span class="title"> Masters </span><i class="icon-arrow"></i> <span
				class="selected"></span> </a>
			<ul class="sub-menu">
			<li id="file_li"><a href="<%=application.getContextPath() %>/master/file/manage"> <span
						class="title"> File </span> 
				</a></li>
				<li id="account_li"><a href="<%=application.getContextPath() %>/master/account/manage"> <span
						class="title"> Account </span> 
				</a></li>
				<li id="account_group_li"><a href="<%=application.getContextPath() %>/master/account-group/manage"> <span
						class="title"> Account Group </span>
				</a></li>
				
				<li id="item_li"><a href="<%=application.getContextPath() %>/master/item/manage"> <span
						class="title"> Item  </span>
				</a></li>
				<li id="item_group_li"><a href="<%=application.getContextPath() %>/master/item-group/manage"> <span
						class="title"> Item Group </span>
				</a></li>
				<li id="sale_tax_li"><a href="<%=application.getContextPath() %>/master/sale-tax/manage"> <span
						class="title"> Sale Tax </span>
				</a></li>
				<li id="vat_li"><a href="<%=application.getContextPath() %>/master/vat/manage"> <span
						class="title"> VAT </span>
				</a></li>
				<li id="state_li"><a href="<%=application.getContextPath() %>/master/state/manage"> <span
						class="title"> State </span>
				</a></li>
				<li id="charges_li"><a href="<%=application.getContextPath() %>/master/charge/manage"> <span
						class="title"> Charges </span>
				</a></li>
				<li id="daybook_li"><a href="<%=application.getContextPath() %>/master/daybook/manage"> <span
						class="title"> Daybook </span>
				</a></li>
			</ul></li>
		
		<li id="transactions_li"><a href="javascript:void(0)"><i class="clip-cog-2"></i> <span
				class="title"> Transactions </span><i class="icon-arrow"></i> <span
				class="selected"></span> </a>
			<ul class="sub-menu">
				<li id="purchase_li"><a href="<%=application.getContextPath() %>/transaction/purchase/manage"> 
					<span class="title"> Purchase </span>
				</a></li>
				<li id="sale_li"><a href="<%=application.getContextPath() %>/transaction/sale/manage"> 
					<span class="title"> Sale </span>
				</a></li>
				<li id="payment_li"><a href="<%=application.getContextPath() %>/transaction/payment/manage"> 
					<span class="title"> Payment </span>
				</a></li>
				<li id="receipt_li"><a href="<%=application.getContextPath() %>/transaction/receipt/manage"> 
					<span class="title"> Receipt </span>
				</a></li>
				<li id="journal_li"><a href="<%=application.getContextPath() %>/transaction/journal/manage"> 
					<span class="title"> Journal </span>
				</a></li>
				
			</ul></li>
		<li><a href="javascript:void(0)"><i class="clip-grid-6"></i>
				<span class="title"> Reports </span><i class="icon-arrow"></i> <span
				class="selected"></span> </a>
			<ul class="sub-menu">
				<li><a href="table_static.html"> <span class="title">Static
							Tables</span>
				</a></li>
				<li><a href="table_responsive.html"> <span class="title">Responsive
							Tables</span>
				</a></li>
				<li><a href="table_data.html"> <span class="title">Data
							Tables</span>
				</a></li>
				<li><a href="table_export.html"> <span class="title">Table
							Export</span>
				</a></li>
			</ul></li>
		<li><a href="javascript:void(0)"><i class="clip-attachment-2"></i>
								<span class="title">Utilities</span><i class="icon-arrow"></i>
								<span class="selected"></span>
							</a>
			<ul class="sub-menu">
				<li><a href="form_elements.html"> <span class="title">Form
							Elements</span>
				</a></li>
				<li><a href="form_wizard.html"> <span class="title">Form
							Wizard</span>
				</a></li>
				<li><a href="form_validation.html"> <span class="title">Form
							Validation</span>
				</a></li>
				<li><a href="form_inline.html"> <span class="title">Inline
							Editor</span>
				</a></li>
				<li><a href="form_x_editable.html"> <span class="title">Form
							X-editable</span>
				</a></li>
				<li><a href="form_image_cropping.html"> <span class="title">Image
							Cropping</span>
				</a></li>
				<li><a href="form_multiple_upload.html"> <span
						class="title">Multiple File Upload</span>
				</a></li>
				<li><a href="form_dropzone.html"> <span class="title">Dropzone
							File Upload</span>
				</a></li>
			</ul></li>
		
	</ul>
	<!-- end: MAIN NAVIGATION MENU -->
</div>
<!-- end: SIDEBAR -->