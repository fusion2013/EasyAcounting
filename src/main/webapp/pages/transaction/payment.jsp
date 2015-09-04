<%@ include file="/pages/layout/taglib.jsp"%>
<script src="http://code.jquery.com/ui/1.11.2/jquery-ui.js"></script>
<link rel="stylesheet" href="//code.jquery.com/ui/1.11.2/themes/smoothness/jquery-ui.css">
<script>
	jQuery(document).ready(function() {
		FormElements.init();
		TableDataForPayment.init();
		$("#transactions_li").addClass("active");
		$("#transactions_li").addClass("open");
		$("#payment_li").addClass("active");
		$("#payment_form").addClass("no-display");
		getAllAccounts();
		getAllCashBankAccounts();
		showSuggestion("party0");
		showCashBankSuggestion("cash_bank");
		
		$("#saveBtn").keyup(function(event){
		    if(event.keyCode == 13){
		    	event.preventDefault();
		        submitForm();
		    }
		});
	});
	function submitForm(){
		$("#paymentForm").submit();
	}
	var accounts = [];
	function getAllAccounts() {
		$.ajax({
			url : contextURL+'/master/account/get-all',
			dataType : 'json',
			success : function(json) {
				$.unblockUI();
				var array = json.accounts;
				for(i=0;i<array.length;i++) {
					accounts[i] = array[i]; 
				}
			},
			error : function(jqXHR, exception) {
				$.unblockUI();
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
	
	var cashBankAccounts = [];
	function getAllCashBankAccounts() {
		$.ajax({
			url : contextURL+'/master/account/get-cash-bank',
			dataType : 'json',
			success : function(json) {
				$.unblockUI();
				var array = json.cashBankAccounts;
				for(i=0;i<array.length;i++) {
					cashBankAccounts[i] = array[i]; 
				}
			},
			error : function(jqXHR, exception) {
				$.unblockUI();
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
	
	function removeRow(row_num) {
		//check if single row
		var trs=0;
		$('#inner_table > tbody > tr').each(function() { trs++; });
		if(trs==3) {
			return;
		}
		
		//remove current row
		$("#inner_table_row_"+row_num).remove();
		
		//focus on prev row
		var i=1;
		while($("#party"+(row_num-i)).length==0 && (row_num-i)>=0) {
			i++;
		}
		$("#party"+(row_num-i)).focus();
	}
	
	var i=0;
	function addNewRow(event,num) {
		//if enter pressed
		if(event.which == 13) {
	    	event.preventDefault();
	    	
	    	//decide if row should be added
	    	i=0;
	    	var found=false;
	    	var add=true;
	    	$('#inner_table > tbody > tr').each(function() {
	    		if(found && ($(this).find('input').length)!=0) {
	    			add = false;
	    		}
	    		var id = $(this).attr("id");
	    		var clickedId = "inner_table_row_"+num;
	    		if(id==clickedId) {
	    			found=true;
	    		}
	    	});
	    	
	    	if(!add) {
	    		return;
	    	}
	    	
	    	//add row
	    	num=num+1;
	        $("#inner_table").append("<tr id='inner_table_row_"+num+"'>"+
					"<td>"+
					"	&nbsp;&nbsp;"+
					"	<input type='text' autocomplete='off' id='party"+num+"' class='ui-autocomplete-input'"+
					" 	name='paymentAccounts["+num+"].accountId.name' style='width:90%' />"+
					
					"</td>"+
					"<td>"+
					"	<input type='text' style='width:90%' "+
					" 	name='paymentAccounts["+num+"].remarks' />"+
					
					"</td>"+
					"<td>"+
					"	<input type='text' class='currency' id='amount"+num+"'"+ 
					"	name='paymentAccounts["+num+"].amountString' value='0.0'"+
					" 	onkeypress='addNewRow(event,"+num+")' style='width:90%'  onblur='calculateTotal()' />"+
					
					"</td>"+
					"<td>"+
					"	<input type='button' value='x' onclick='removeRow("+num+")' />"+
					"</td>"+
				 "</tr>"+
				 "<tr>"+
				 	"<td>&nbsp;</td>"+
				 	"<td>&nbsp;</td>"+
				 	"<td>&nbsp;</td>"+
				 "</tr>");
	        
	        //put focus
	        $("#party"+num).focus();
	        showSuggestion("party"+num);
	        $(".currency").maskMoney();
		} else {
			
		}
	}
	
	function calculateTotal() {
		var num=0;
		for(i=0;;i++) {
			var row = $("#inner_table_row_"+i).length;
			if(row!=0) {
				num++;
			} else {
				break;
			}
		}
		
		var index = 0;
        var total = 0;
        for(index=0; index<(num);index++) {
        	var amount = $("#amount"+index).val();
        	amount = amount.replace(/,/g , "");
        	if(parseFloat(amount)!=NaN && amount!=undefined
        			&& amount!="") {
        		total = total + parseFloat(amount);
        	}
        }
        $("#total").val(amount);
	}
	
	function showSuggestion(id) {
		$( "#"+id ).autocomplete({
		      source: accounts
		});
	}
	function showCashBankSuggestion(id) {
		$( "#"+id ).autocomplete({
		      source: cashBankAccounts
		});
	}
	
  </script>
<style>
.inner_table_header {
	background-color: #f5f5f5;
	height: 36px;
	padding-left: 40px;
	font-family: 'Open+Sans', sans-serif;
	font-size: 13px;
}
</style>

<div class="container">
	<div class="row">
		<div class="col-sm-12">
			<!-- start: PAGE TITLE & BREADCRUMB -->
			<div class="page-header">
				<h1>
					Manage Payment Entries <small>View, edit, add, delete
						entries</small>
				</h1>
			</div>
			<!-- end: PAGE TITLE & BREADCRUMB -->
		</div>
	</div>
	<c:choose>
		<c:when test="${showForm==true}">
			<script type="text/javascript">
			jQuery(document).ready(function() {
				$("#payment_form").removeClass("display");
				$("#payment_form").addClass("display");
				$("#sr_no").focus();
			});
			</script>
		</c:when>
	</c:choose>
	<div class="row" id="payment_form">
		<div class="col-sm-12">
			<!-- start: TEXT FIELDS PANEL -->
			<div class="panel panel-default">
				<div class="panel-heading">
					<i class="fa fa-external-link-square"></i> Add a new Entry
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
					<form:form class="form-horizontal" commandName="payment" id="paymentForm">
						<form:hidden path="id" />
						<table>
							<tr>
								<td width="33%"><label class="col-sm-3 control-label"
									for="form-field-1"> Sr. No </label>
									<div class="col-sm-7">
										<form:input type="text" id="sr_no"
											class="form-control" path="srNo" />
										<span class="help-block"><form:errors path="srNo" />
										</span>
									</div></td>
								<td width="34%"><label class="col-sm-4 control-label"
									for="form-field-1"> Ref. No </label>
									<div class="col-sm-7">
										<form:input type="text" id="ref_no"
											class="form-control" path="refNo" />
									</div></td>
								<td><label for="form-field-mask-1"
									class="col-sm-4 control-label"> Date <small
										class="text-success">99/99/9999</small>
								</label>
									<div class="input-group col-sm-5">
										<form:input type="text" id="form-field-mask-1" path="dateString"
											class="form-control input-mask-date" /> 
										<span class="help-block"><form:errors path="dateString" />
										</span>
									</div></td>
							</tr>
							<tr>
								<td>&nbsp;</td>
							</tr>
							<tr>
								<td colspan="3">
									<span class="help-block"><form:errors path="paymentAccounts" /></span>
									<table width="100%" style="border-collapse:separate;
									border: solid #ccc 1px;
									border-radius: 5px;" 
									id="inner_table">
										<tr class="inner_table_header">
											<td width="45%" style="border-bottom: 1px solid #ccc;">&nbsp;Party/Account</td>
											<td width="35%" style="border-bottom: 1px solid #ccc;">Remarks</td>
											<td style="border-bottom: 1px solid #ccc;">Amount</td>
											<td width="3%" style="border-bottom: 1px solid #ccc;"></td>
										</tr>
										<tr>
											<td>&nbsp;</td>
											<td>&nbsp;</td>
											<td>&nbsp;</td>
										</tr>
										<c:forEach var="partyAcc" items="${payment.paymentAccounts}" varStatus="loop">
										
										<tr id="inner_table_row_${loop.index}">
											<td>
												&nbsp;&nbsp;
												<form:input type="text" name="party${loop.index}"
															path="paymentAccounts[${loop.index}].accountId.name"
															id="party${loop.index}" style="width:90%" />
											</td>
											<td>
												<form:input type="text" name="remarks${loop.index}" id="remarks${loop.index}"
															path="paymentAccounts[${loop.index}].remarks"
												 			style="width:90%" />
											</td>
											<td>
												<form:input type="text"  name="amount${loop.index}"
															path="paymentAccounts[${loop.index}].amountString"
															class="currency" id="amount${loop.index}"
															onkeypress="addNewRow(event,${loop.index})" 
															style="width:90%" onblur="calculateTotal()" />
											</td>
											<td>
												<input type="button" value="x" onclick="removeRow(${loop.index})" />
											</td>
										</tr>
										</c:forEach>
										<tr>
											<td>&nbsp;</td>
											<td>&nbsp;</td>
											<td>&nbsp;</td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td>&nbsp;</td>
							</tr>
							<tr>
								<td colspan="2"><label class="col-sm-2 control-label"
									for="form-field-1"> Cash/Bank </label>
									<div class="col-sm-10">
										<form:input type="text" id="cash_bank" name="cashBankString"
											class="form-control" path="cashBankString" />
										<span class="help-block"><form:errors path="cashBankString" />
										</span>
									</div></td>
								<td><label class="col-sm-2 control-label" style="font-weight:bold"
									for="form-field-1"> Total </label>
									<div class="col-sm-7">
										<form:input disabled="true" style="color:blue
										;font-weight:bold" readonly="true" id="total" type="text" 
											class="form-control" path="total" ></form:input>
									</div></td>
							</tr>
							<tr>
								<td>&nbsp;</td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td>
									<button class="form-control btn btn-primary" 
									id="saveBtn" type="button" onclick="submitForm()">
										Save <i class="fa fa-arrow-circle-right"></i>
									</button>
								</td>
								<td>&nbsp;</td>
							</tr>
						</table>
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
					<i class="fa fa-external-link-square"></i> List of Entries
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
								href="<%=application.getContextPath()%>/transaction/payment/manage?showForm">
								<button class="btn btn-green add-row">
									Add New Entry <i class="fa fa-plus"></i>
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
										<th>Sr. No</th>
										<th>Ref. No</th>
										<th>Date</th>
										<th>Cash/Bank Name</th>
										<th>Total Amount</th>
										<th>View/Edit</th>
										<th>Delete</th>
									</tr>
								</thead>
								<tbody>

									<c:forEach items="${payments}" var="payment">
										<tr>
											<td>${payment.srNo}</td>
											<td>${payment.refNo}</td>
											<td>${payment.date}</td>
											<td>${payment.cashBankAccount.name}</td>
											<td>${payment.total}</td>
											<td><a href="<%=application.getContextPath() %>/transaction/payment
												/populate-for-edit/${payment.id}"
												class="edit-row" id="${payment.id}"> View/Edit </a></td>
											<td><a href="#" class="delete-row" id="${payment.id}">
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