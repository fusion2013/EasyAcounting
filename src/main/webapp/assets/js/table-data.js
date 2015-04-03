var TableDataForFiles = function() {
	// function to initiate DataTable
	// DataTable is a highly flexible tool, based upon the foundations of
	// progressive enhancement,
	// which will add advanced interaction controls to any HTML table
	// For more information, please visit https://datatables.net/
	var runDataTable = function() {
		var oTable = $('#sample_1').dataTable({
			"aoColumnDefs" : [ {
				"aTargets" : [ 0 ]
			} ],
			"oLanguage" : {
				"sLengthMenu" : "Show _MENU_ Rows",
				"sSearch" : "",
				"oPaginate" : {
					"sPrevious" : "",
					"sNext" : ""
				}
			},
			"aaSorting" : [ [ 1, 'asc' ] ],
			"aLengthMenu" : [ [ 5, 10, 15, 20, -1 ], [ 5, 10, 15, 20, "All" ] // change
			// per
			// page
			// values
			// here
			],
			// set the initial value
			"iDisplayLength" : 10,
		});
		$('#sample_1_wrapper .dataTables_filter input').addClass(
				"form-control input-sm").attr("placeholder", "Search");
		// modify table search input
		$('#sample_1_wrapper .dataTables_length select').addClass(
				"m-wrap small");
		// modify table per page dropdown
		$('#sample_1_wrapper .dataTables_length select').select2();
		// initialzie select2 dropdown
		$('#sample_1_column_toggler input[type="checkbox"]').change(function() {
			/*
			 * Get the DataTables object again - this is not a recreation, just
			 * a get of the object
			 */
			var iCol = parseInt($(this).attr("data-column"));
			var bVis = oTable.fnSettings().aoColumns[iCol].bVisible;
			oTable.fnSetColumnVis(iCol, (bVis ? false : true));
		});
	};

	var runEditableTable = function() {

		var newRow = false;
		var actualEditingRow = null;

		function restoreRow(oTable, nRow) {
			var aData = oTable.fnGetData(nRow);
			var jqTds = $('>td', nRow);

			for (var i = 0, iLen = jqTds.length; i < iLen; i++) {
				oTable.fnUpdate(aData[i], nRow, i, false);
			}

			oTable.fnDraw();
		}

		function editRow(oTable, nRow) {
			editRow(oTable, nRow, "");
		}
		
		function editRow(oTable, nRow, id) {
			var aData = oTable.fnGetData(nRow);
			var jqTds = $('>td', nRow);
			var ids = ["name","companyName"];
			for (var i = 1; i < aData.length - 2; i++) {
				jqTds[i].setAttribute("id",ids[i-1]);
				jqTds[i].innerHTML = '<input type="text" class="form-control" value="'
						+ aData[i] + '">';
			}
			jqTds[i].innerHTML = '<a class="save-row" id="'+id+'" href="">Save</a>';
			jqTds[++i].innerHTML = '<a class="cancel-row" id="'+id+'" href="">Cancel</a>';

		}

		function saveRow(oTable, nRow) {
			saveRow(oTable, nRow, "");
		}
		
		function saveRow(oTable, nRow, id) {
			var jqInputs = $('input[type="text"]', nRow);
			oTable.fnUpdate('<input type="checkbox" name="file" id="'+id+'" class="select-file" />', nRow, 0,
					false);
			for (var i = 1; i < jqInputs.length+1; i++) {
				oTable.fnUpdate(jqInputs[i-1].value, nRow, i, false);
			}

			oTable.fnUpdate('<a class="edit-row" href="" id="'+id+'">Edit</a>', nRow, i,
					false);
			oTable.fnUpdate('<a class="delete-row" id="'+id+'" href="">Delete</a>', nRow,
					++i, false);
			oTable.fnDraw();
			newRow = false;
			actualEditingRow = null;
		}

		$('body').on('click', '.add-row', function(e) {
			e.preventDefault();
			if (newRow == false) {
				if (actualEditingRow) {
					restoreRow(oTable, actualEditingRow);
				}
				newRow = true;
				var aiNew = oTable.fnAddData([ '', '', '', '', '' ]);
				var nRow = oTable.fnGetNodes(aiNew[0]);
				editRow(oTable, nRow);
				actualEditingRow = nRow;
			}
		});
		$('#sample_2').on('click', '.cancel-row', function(e) {

			e.preventDefault();
			if (newRow) {
				newRow = false;
				actualEditingRow = null;
				var nRow = $(this).parents('tr')[0];
				oTable.fnDeleteRow(nRow);

			} else {
				restoreRow(oTable, actualEditingRow);
				actualEditingRow = null;
			}
		});
		$('#sample_2').on('click','.delete-row',function(e) {
			e.preventDefault();
			if (newRow && actualEditingRow) {
				oTable.fnDeleteRow(actualEditingRow);
				newRow = false;
			}
			var nRow = $(this).parents('tr')[0];
			var id = parseInt($(this).attr("id"));
			bootbox.confirm("Are you sure to delete this row?",function(result) {
				if (result) {
					$.blockUI({
						message : '<i class="fa fa-spinner fa-spin"></i> Loading...'
					});
					$.ajax({
						url : contextURL+'/master/file/delete/'+id,
						dataType : 'json',
						success : function(json) {
							$.unblockUI();
							if (json.say == "ok") {
								oTable.fnDeleteRow(nRow);
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
			});
		});
		$('#sample_2').on('click','.save-row',function(e) {
			
			$('.has-error').each(function(i, obj) {
				$(this).removeClass("has-error");
				$(this).find("span").remove();
			});
			
			e.preventDefault();
			var nRow = $(this).parents('tr')[0];
			$.blockUI({
				message : '<i class="fa fa-spinner fa-spin"></i> Loading...'
			});
			var id = $(this).attr("id");
			var name = $('.form-control')[1].value;
			var cname = $('.form-control')[2].value;
			var values = { "id" : id, "name" : name, "companyName" : cname};
			$.ajax({
				url : contextURL+'/master/file/save',
				dataType : 'json',
				data : values,
				success : function(json) {
					$.unblockUI();
					if (json.say == "ok") {
						saveRow(oTable, nRow, json.id);
						
						$("#sample_2 tr").each(function () {
						    $('td', this).each(function () {
						        $(this).removeAttr("id");
						     })
						})
						
					} else if(json.say="invalid-inputs") {
						$.each(json, function(key,value) {
						  if(key!="say") {
							  $("#"+key).append("<span class='help-block'>" +
							  		value+"</span>");
							  $("#"+key).addClass("has-error");
						  }
						});
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
		});
		$('#sample_2').on('click', '.edit-row', function(e) {
			e.preventDefault();
			if (actualEditingRow) {
				if (newRow) {
					oTable.fnDeleteRow(actualEditingRow);
					newRow = false;
				} else {
					restoreRow(oTable, actualEditingRow);
				}
			}
			var nRow = $(this).parents('tr')[0];
			editRow(oTable, nRow, $(this).attr("id"));
			actualEditingRow = nRow;

		});
		$('#sample_2').on('click', '.select-file', function(e) {
			e.preventDefault();
			var id = $(this).attr("id");
			var values = { "id" : id };
			$.blockUI({
				message : '<i class="fa fa-spinner fa-spin"></i> Loading...'
			});
			$.ajax({
				url : contextURL+'/master/file/select/'+id,
				dataType : 'json',
				success : function(json) {
					$.unblockUI();
					if (json.say == "ok") {
						window.location=contextURL+"/master/file/manage";
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
		});
		var oTable = $('#sample_2').dataTable({
			"aoColumnDefs" : [ {
				"aTargets" : [ 0 ]
			} ],
			"oLanguage" : {
				"sLengthMenu" : "Show _MENU_ Rows",
				"sSearch" : "",
				"oPaginate" : {
					"sPrevious" : "",
					"sNext" : ""
				}
			},
			"aaSorting" : [ [ 1, 'asc' ] ],
			"aLengthMenu" : [ [ 5, 10, 15, 20, -1 ], [ 5, 10, 15, 20, "All" ] // change
			// per
			// page
			// values
			// here
			],
			// set the initial value
			"iDisplayLength" : 10,
		});
		$('#sample_2_wrapper .dataTables_filter input').addClass(
				"form-control input-sm").attr("placeholder", "Search");
		// modify table search input
		$('#sample_2_wrapper .dataTables_length select').addClass(
				"m-wrap small");
		// modify table per page dropdown
		$('#sample_2_wrapper .dataTables_length select').select2();
		// initialzie select2 dropdown
		$('#sample_2_column_toggler input[type="checkbox"]').change(function() {
			/*
			 * Get the DataTables object again - this is not a recreation, just
			 * a get of the object
			 */
			var iCol = parseInt($(this).attr("data-column"));
			var bVis = oTable.fnSettings().aoColumns[iCol].bVisible;
			oTable.fnSetColumnVis(iCol, (bVis ? false : true));
		});

	};

	return {
		//main function to initiate template pages
		init : function() {
			runDataTable();
			runEditableTable();
		}
	};
}();

var TableDataForGroup = function() {
	// function to initiate DataTable
	// DataTable is a highly flexible tool, based upon the foundations of
	// progressive enhancement,
	// which will add advanced interaction controls to any HTML table
	// For more information, please visit https://datatables.net/
	var runDataTable = function() {
		var oTable = $('#sample_1').dataTable({
			"aoColumnDefs" : [ {
				"aTargets" : [ 0 ]
			} ],
			"oLanguage" : {
				"sLengthMenu" : "Show _MENU_ Rows",
				"sSearch" : "",
				"oPaginate" : {
					"sPrevious" : "",
					"sNext" : ""
				}
			},
			"aaSorting" : [ [ 1, 'asc' ] ],
			"aLengthMenu" : [ [ 5, 10, 15, 20, -1 ], [ 5, 10, 15, 20, "All" ] // change
			// per
			// page
			// values
			// here
			],
			// set the initial value
			"iDisplayLength" : 10,
		});
		$('#sample_1_wrapper .dataTables_filter input').addClass(
				"form-control input-sm").attr("placeholder", "Search");
		// modify table search input
		$('#sample_1_wrapper .dataTables_length select').addClass(
				"m-wrap small");
		// modify table per page dropdown
		$('#sample_1_wrapper .dataTables_length select').select2();
		// initialzie select2 dropdown
		$('#sample_1_column_toggler input[type="checkbox"]').change(function() {
			/*
			 * Get the DataTables object again - this is not a recreation, just
			 * a get of the object
			 */
			var iCol = parseInt($(this).attr("data-column"));
			var bVis = oTable.fnSettings().aoColumns[iCol].bVisible;
			oTable.fnSetColumnVis(iCol, (bVis ? false : true));
		});
	};

	var runEditableTable = function() {

		var newRow = false;
		var actualEditingRow = null;

		function restoreRow(oTable, nRow) {
			var aData = oTable.fnGetData(nRow);
			var jqTds = $('>td', nRow);

			for (var i = 0, iLen = jqTds.length; i < iLen; i++) {
				oTable.fnUpdate(aData[i], nRow, i, false);
			}

			oTable.fnDraw();
		}

		function editRow(oTable, nRow) {
			editRow(oTable, nRow, "");
		}
		
		function editRow(oTable, nRow, id) {
			var aData = oTable.fnGetData(nRow);
			var jqTds = $('>td', nRow);
			jqTds[0].setAttribute("id","description");
			jqTds[0].innerHTML = '<input type="text" class="form-control" value="'
				+ aData[0] + '">';
			if(aData[1]=="B") {
				jqTds[1].innerHTML = '<select class="form-control"><option>P</option><option selected>B</option></select>';
			} else {
				jqTds[1].innerHTML = '<select class="form-control"><option selected>P</option><option>B</option></select>';
			}
			
			jqTds[2].innerHTML = '<a class="save-row" id="'+id+'" href="">Save</a>';
			jqTds[3].innerHTML = '<a class="cancel-row" id="'+id+'" href="">Cancel</a>';

		}

		function saveRow(oTable, nRow) {
			saveRow(oTable, nRow, "");
		}
		
		function saveRow(oTable, nRow, id) {
			var text = $('input[type="text"]', nRow);
			var select = $('select', nRow);
			
			oTable.fnUpdate(text[0].value, nRow, 0, false);
			oTable.fnUpdate(select[0].value, nRow, 1, false);
			
			oTable.fnUpdate('<a class="edit-row" href="" id="'+id+'">Edit</a>', nRow, 2,
					false);
			oTable.fnUpdate('<a class="delete-row" id="'+id+'" href="">Delete</a>', nRow,
					3, false);
			oTable.fnDraw();
			newRow = false;
			actualEditingRow = null;
		}

		$('body').on('click', '.add-row', function(e) {
			e.preventDefault();
			if (newRow == false) {
				if (actualEditingRow) {
					restoreRow(oTable, actualEditingRow);
				}
				newRow = true;
				var aiNew = oTable.fnAddData([ '', '', '', '' ]);
				var nRow = oTable.fnGetNodes(aiNew[0]);
				editRow(oTable, nRow);
				actualEditingRow = nRow;
				$(".container").find('#account_tag_form').remove();
			}
		});
		$('#sample_2').on('click', '.cancel-row', function(e) {

			e.preventDefault();
			if (newRow) {
				newRow = false;
				actualEditingRow = null;
				var nRow = $(this).parents('tr')[0];
				oTable.fnDeleteRow(nRow);

			} else {
				restoreRow(oTable, actualEditingRow);
				actualEditingRow = null;
			}
		});
		$('#sample_2').on('click','.delete-row',function(e) {
			e.preventDefault();
			if (newRow && actualEditingRow) {
				oTable.fnDeleteRow(actualEditingRow);
				newRow = false;
			}
			var nRow = $(this).parents('tr')[0];
			var id = parseInt($(this).attr("id"));
			bootbox.confirm("Are you sure to delete this row?",function(result) {
				if (result) {
					$.blockUI({
						message : '<i class="fa fa-spinner fa-spin"></i> Loading...'
					});
					$.ajax({
						url : contextURL+'/master/account-group/delete/'+id,
						dataType : 'json',
						success : function(json) {
							$.unblockUI();
							if (json.say == "ok") {
								oTable.fnDeleteRow(nRow);
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
			});
		});
		$('#sample_2').on('click','.save-row',function(e) {
			
			$('.has-error').each(function(i, obj) {
				$(this).removeClass("has-error");
				$(this).find("span").remove();
			});
			
			e.preventDefault();
			var nRow = $(this).parents('tr')[0];
			$.blockUI({
				message : '<i class="fa fa-spinner fa-spin"></i> Loading...'
			});
			var id = $(this).attr("id");
			var description = $('.form-control')[1].value;
			var type = $('.form-control')[2].value;
			var values = { "id" : id, "description" : description, "type" : type};
			$.ajax({
				url : contextURL+'/master/account-group/save',
				dataType : 'json',
				data : values,
				success : function(json) {
					$.unblockUI();
					if (json.say == "ok") {
						saveRow(oTable, nRow, json.id);
						
						$("#sample_2 tr").each(function () {
						    $('td', this).each(function () {
						        $(this).removeAttr("id");
						     })
						})
					} else if(json.say == "invalid-inputs"){
						$.each(json, function(key,value) {
							  if(key!="say") {
								  $("#"+key).append("<span class='help-block'>" +
								  		value+"</span>");
								  $("#"+key).addClass("has-error");
							  }
							});
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
		});
		$('#sample_2').on('click', '.edit-row', function(e) {
			e.preventDefault();
			if (actualEditingRow) {
				if (newRow) {
					oTable.fnDeleteRow(actualEditingRow);
					newRow = false;
				} else {
					restoreRow(oTable, actualEditingRow);

				}
			}
			var nRow = $(this).parents('tr')[0];
			editRow(oTable, nRow, $(this).attr("id"));
			actualEditingRow = nRow;

		});
		
		var oTable = $('#sample_2').dataTable({
			"aoColumnDefs" : [ {
				"aTargets" : [ 0 ]
			} ],
			"oLanguage" : {
				"sLengthMenu" : "Show _MENU_ Rows",
				"sSearch" : "",
				"oPaginate" : {
					"sPrevious" : "",
					"sNext" : ""
				}
			},
			"aaSorting" : [ [ 1, 'asc' ] ],
			"aLengthMenu" : [ [ 5, 10, 15, 20, -1 ], [ 5, 10, 15, 20, "All" ] // change
			// per
			// page
			// values
			// here
			],
			// set the initial value
			"iDisplayLength" : 10,
		});
		$('#sample_2_wrapper .dataTables_filter input').addClass(
				"form-control input-sm").attr("placeholder", "Search");
		// modify table search input
		$('#sample_2_wrapper .dataTables_length select').addClass(
				"m-wrap small");
		// modify table per page dropdown
		$('#sample_2_wrapper .dataTables_length select').select2();
		// initialzie select2 dropdown
		$('#sample_2_column_toggler input[type="checkbox"]').change(function() {
			/*
			 * Get the DataTables object again - this is not a recreation, just
			 * a get of the object
			 */
			var iCol = parseInt($(this).attr("data-column"));
			var bVis = oTable.fnSettings().aoColumns[iCol].bVisible;
			oTable.fnSetColumnVis(iCol, (bVis ? false : true));
		});

	};

	return {
		//main function to initiate template pages
		init : function() {
			runDataTable();
			runEditableTable();
		}
	};
}();

var TableDataForAccount = function() {
	// function to initiate DataTable
	// DataTable is a highly flexible tool, based upon the foundations of
	// progressive enhancement,
	// which will add advanced interaction controls to any HTML table
	// For more information, please visit https://datatables.net/
	var runDataTable = function() {
		var oTable = $('#sample_1').dataTable({
			"aoColumnDefs" : [ {
				"aTargets" : [ 0 ]
			} ],
			"oLanguage" : {
				"sLengthMenu" : "Show _MENU_ Rows",
				"sSearch" : "",
				"oPaginate" : {
					"sPrevious" : "",
					"sNext" : ""
				}
			},
			"aaSorting" : [ [ 1, 'asc' ] ],
			"aLengthMenu" : [ [ 5, 10, 15, 20, -1 ], [ 5, 10, 15, 20, "All" ] // change
			// per
			// page
			// values
			// here
			],
			// set the initial value
			"iDisplayLength" : 10,
		});
		$('#sample_1_wrapper .dataTables_filter input').addClass(
				"form-control input-sm").attr("placeholder", "Search");
		// modify table search input
		$('#sample_1_wrapper .dataTables_length select').addClass(
				"m-wrap small");
		// modify table per page dropdown
		$('#sample_1_wrapper .dataTables_length select').select2();
		// initialzie select2 dropdown
		$('#sample_1_column_toggler input[type="checkbox"]').change(function() {
			/*
			 * Get the DataTables object again - this is not a recreation, just
			 * a get of the object
			 */
			var iCol = parseInt($(this).attr("data-column"));
			var bVis = oTable.fnSettings().aoColumns[iCol].bVisible;
			oTable.fnSetColumnVis(iCol, (bVis ? false : true));
		});
	};

	var runEditableTable = function() {

		var newRow = false;
		var actualEditingRow = null;

		$('#sample_2').on('click','.delete-row',function(e) {
			e.preventDefault();
			if (newRow && actualEditingRow) {
				oTable.fnDeleteRow(actualEditingRow);
				newRow = false;
			}
			var nRow = $(this).parents('tr')[0];
			var id = parseInt($(this).attr("id"));
			bootbox.confirm("Are you sure to delete this row?",function(result) {
				if (result) {
					$.blockUI({
						message : '<i class="fa fa-spinner fa-spin"></i> Loading...'
					});
					$.ajax({
						url : contextURL+'/master/account/delete/'+id,
						dataType : 'json',
						success : function(json) {
							$.unblockUI();
							if (json.say == "ok") {
								oTable.fnDeleteRow(nRow);
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
			});
		});

		var oTable = $('#sample_2').dataTable({
			"aoColumnDefs" : [ {
				"aTargets" : [ 0 ]
			} ],
			"oLanguage" : {
				"sLengthMenu" : "Show _MENU_ Rows",
				"sSearch" : "",
				"oPaginate" : {
					"sPrevious" : "",
					"sNext" : ""
				}
			},
			"aaSorting" : [ [ 1, 'asc' ] ],
			"aLengthMenu" : [ [ 5, 10, 15, 20, -1 ], [ 5, 10, 15, 20, "All" ] // change
			// per
			// page
			// values
			// here
			],
			// set the initial value
			"iDisplayLength" : 10,
		});
		$('#sample_2_wrapper .dataTables_filter input').addClass(
				"form-control input-sm").attr("placeholder", "Search");
		// modify table search input
		$('#sample_2_wrapper .dataTables_length select').addClass(
				"m-wrap small");
		// modify table per page dropdown
		$('#sample_2_wrapper .dataTables_length select').select2();
		// initialzie select2 dropdown
		$('#sample_2_column_toggler input[type="checkbox"]').change(function() {
			/*
			 * Get the DataTables object again - this is not a recreation, just
			 * a get of the object
			 */
			var iCol = parseInt($(this).attr("data-column"));
			var bVis = oTable.fnSettings().aoColumns[iCol].bVisible;
			oTable.fnSetColumnVis(iCol, (bVis ? false : true));
		});

	};

	return {
		//main function to initiate template pages
		init : function() {
			runDataTable();
			runEditableTable();
		}
	};
}();

var TableDataForItemGroup = function() {

	var runDataTable = function() {
		var oTable = $('#sample_1').dataTable({
			"aoColumnDefs" : [ {
				"aTargets" : [ 0 ]
			} ],
			"oLanguage" : {
				"sLengthMenu" : "Show _MENU_ Rows",
				"sSearch" : "",
				"oPaginate" : {
					"sPrevious" : "",
					"sNext" : ""
				}
			},
			"aaSorting" : [ [ 1, 'asc' ] ],
			"aLengthMenu" : [ [ 5, 10, 15, 20, -1 ], [ 5, 10, 15, 20, "All" ] // change
			// per
			// page
			// values
			// here
			],
			// set the initial value
			"iDisplayLength" : 10,
		});
		$('#sample_1_wrapper .dataTables_filter input').addClass(
				"form-control input-sm").attr("placeholder", "Search");
		// modify table search input
		$('#sample_1_wrapper .dataTables_length select').addClass(
				"m-wrap small");
		// modify table per page dropdown
		$('#sample_1_wrapper .dataTables_length select').select2();
		// initialzie select2 dropdown
		$('#sample_1_column_toggler input[type="checkbox"]').change(function() {
			/*
			 * Get the DataTables object again - this is not a recreation, just
			 * a get of the object
			 */
			var iCol = parseInt($(this).attr("data-column"));
			var bVis = oTable.fnSettings().aoColumns[iCol].bVisible;
			oTable.fnSetColumnVis(iCol, (bVis ? false : true));
		});
	};

	var runEditableTable = function() {

		var newRow = false;
		var actualEditingRow = null;

		function restoreRow(oTable, nRow) {
			var aData = oTable.fnGetData(nRow);
			var jqTds = $('>td', nRow);

			for (var i = 0, iLen = jqTds.length; i < iLen; i++) {
				oTable.fnUpdate(aData[i], nRow, i, false);
			}

			oTable.fnDraw();
		}

		function editRow(oTable, nRow) {
			editRow(oTable, nRow, "");
		}
		
		function editRow(oTable, nRow, id) {
			var aData = oTable.fnGetData(nRow);
			var jqTds = $('>td', nRow);
			jqTds[0].setAttribute("id","description");
			jqTds[0].innerHTML = '<input type="text" class="form-control" value="'
				+ aData[0] + '">';
			
			jqTds[1].innerHTML = '<a class="save-row" id="'+id+'" href="">Save</a>';
			jqTds[2].innerHTML = '<a class="cancel-row" id="'+id+'" href="">Cancel</a>';

		}

		function saveRow(oTable, nRow) {
			saveRow(oTable, nRow, "");
		}
		
		function saveRow(oTable, nRow, id) {
			var text = $('input[type="text"]', nRow);
			
			oTable.fnUpdate(text[0].value, nRow, 0, false);
			
			oTable.fnUpdate('<a class="edit-row" href="" id="'+id+'">Edit</a>', nRow, 1,
					false);
			oTable.fnUpdate('<a class="delete-row" id="'+id+'" href="">Delete</a>', nRow,
					2, false);
			oTable.fnDraw();
			newRow = false;
			actualEditingRow = null;
		}

		$('body').on('click', '.add-row', function(e) {
			e.preventDefault();
			if (newRow == false) {
				if (actualEditingRow) {
					restoreRow(oTable, actualEditingRow);
				}
				newRow = true;
				var aiNew = oTable.fnAddData([ '', '', '' ]);
				var nRow = oTable.fnGetNodes(aiNew[0]);
				editRow(oTable, nRow);
				actualEditingRow = nRow;
				$(".container").find('#item_tag_form').remove();
			}
		});
		$('#sample_2').on('click', '.cancel-row', function(e) {

			e.preventDefault();
			if (newRow) {
				newRow = false;
				actualEditingRow = null;
				var nRow = $(this).parents('tr')[0];
				oTable.fnDeleteRow(nRow);

			} else {
				restoreRow(oTable, actualEditingRow);
				actualEditingRow = null;
			}
		});
		$('#sample_2').on('click','.delete-row',function(e) {
			e.preventDefault();
			if (newRow && actualEditingRow) {
				oTable.fnDeleteRow(actualEditingRow);
				newRow = false;
			}
			var nRow = $(this).parents('tr')[0];
			var id = parseInt($(this).attr("id"));
			bootbox.confirm("Are you sure to delete this row?",function(result) {
				if (result) {
					$.blockUI({
						message : '<i class="fa fa-spinner fa-spin"></i> Loading...'
					});
					$.ajax({
						url : contextURL+'/master/item-group/delete/'+id,
						dataType : 'json',
						success : function(json) {
							$.unblockUI();
							if (json.say == "ok") {
								oTable.fnDeleteRow(nRow);
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
			});
		});
		$('#sample_2').on('click','.save-row',function(e) {
			
			$('.has-error').each(function(i, obj) {
				$(this).removeClass("has-error");
				$(this).find("span").remove();
			});
			
			e.preventDefault();
			var nRow = $(this).parents('tr')[0];
			$.blockUI({
				message : '<i class="fa fa-spinner fa-spin"></i> Loading...'
			});
			var id = $(this).attr("id");
			var description = $('.form-control')[1].value;
			var values = { "id" : id, "description" : description};
			$.ajax({
				url : contextURL+'/master/item-group/save',
				dataType : 'json',
				data : values,
				success : function(json) {
					$.unblockUI();
					if (json.say == "ok") {
						saveRow(oTable, nRow, json.id);
						
						$("#sample_2 tr").each(function () {
						    $('td', this).each(function () {
						        $(this).removeAttr("id");
						     })
						})
					} else if(json.say == "invalid-inputs"){
						$.each(json, function(key,value) {
							  if(key!="say") {
								  $("#"+key).append("<span class='help-block'>" +
								  		value+"</span>");
								  $("#"+key).addClass("has-error");
							  }
							});
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
		});
		$('#sample_2').on('click', '.edit-row', function(e) {
			e.preventDefault();
			if (actualEditingRow) {
				if (newRow) {
					oTable.fnDeleteRow(actualEditingRow);
					newRow = false;
				} else {
					restoreRow(oTable, actualEditingRow);

				}
			}
			var nRow = $(this).parents('tr')[0];
			editRow(oTable, nRow, $(this).attr("id"));
			actualEditingRow = nRow;

		});
		
		var oTable = $('#sample_2').dataTable({
			"aoColumnDefs" : [ {
				"aTargets" : [ 0 ]
			} ],
			"oLanguage" : {
				"sLengthMenu" : "Show _MENU_ Rows",
				"sSearch" : "",
				"oPaginate" : {
					"sPrevious" : "",
					"sNext" : ""
				}
			},
			"aaSorting" : [ [ 1, 'asc' ] ],
			"aLengthMenu" : [ [ 5, 10, 15, 20, -1 ], [ 5, 10, 15, 20, "All" ] // change
			// per
			// page
			// values
			// here
			],
			// set the initial value
			"iDisplayLength" : 10,
		});
		$('#sample_2_wrapper .dataTables_filter input').addClass(
				"form-control input-sm").attr("placeholder", "Search");
		// modify table search input
		$('#sample_2_wrapper .dataTables_length select').addClass(
				"m-wrap small");
		// modify table per page dropdown
		$('#sample_2_wrapper .dataTables_length select').select2();
		// initialzie select2 dropdown
		$('#sample_2_column_toggler input[type="checkbox"]').change(function() {
			/*
			 * Get the DataTables object again - this is not a recreation, just
			 * a get of the object
			 */
			var iCol = parseInt($(this).attr("data-column"));
			var bVis = oTable.fnSettings().aoColumns[iCol].bVisible;
			oTable.fnSetColumnVis(iCol, (bVis ? false : true));
		});

	};

	return {
		//main function to initiate template pages
		init : function() {
			runDataTable();
			runEditableTable();
		}
	};
}();

var TableDataForItem = function() {
	// function to initiate DataTable
	// DataTable is a highly flexible tool, based upon the foundations of
	// progressive enhancement,
	// which will add advanced interaction controls to any HTML table
	// For more information, please visit https://datatables.net/
	var runDataTable = function() {
		var oTable = $('#sample_1').dataTable({
			"aoColumnDefs" : [ {
				"aTargets" : [ 0 ]
			} ],
			"oLanguage" : {
				"sLengthMenu" : "Show _MENU_ Rows",
				"sSearch" : "",
				"oPaginate" : {
					"sPrevious" : "",
					"sNext" : ""
				}
			},
			"aaSorting" : [ [ 1, 'asc' ] ],
			"aLengthMenu" : [ [ 5, 10, 15, 20, -1 ], [ 5, 10, 15, 20, "All" ] // change
			// per
			// page
			// values
			// here
			],
			// set the initial value
			"iDisplayLength" : 10,
		});
		$('#sample_1_wrapper .dataTables_filter input').addClass(
				"form-control input-sm").attr("placeholder", "Search");
		// modify table search input
		$('#sample_1_wrapper .dataTables_length select').addClass(
				"m-wrap small");
		// modify table per page dropdown
		$('#sample_1_wrapper .dataTables_length select').select2();
		// initialzie select2 dropdown
		$('#sample_1_column_toggler input[type="checkbox"]').change(function() {
			/*
			 * Get the DataTables object again - this is not a recreation, just
			 * a get of the object
			 */
			var iCol = parseInt($(this).attr("data-column"));
			var bVis = oTable.fnSettings().aoColumns[iCol].bVisible;
			oTable.fnSetColumnVis(iCol, (bVis ? false : true));
		});
	};

	var runEditableTable = function() {

		var newRow = false;
		var actualEditingRow = null;

		$('#sample_2').on('click','.delete-row',function(e) {
			e.preventDefault();
			if (newRow && actualEditingRow) {
				oTable.fnDeleteRow(actualEditingRow);
				newRow = false;
			}
			var nRow = $(this).parents('tr')[0];
			var id = parseInt($(this).attr("id"));
			bootbox.confirm("Are you sure to delete this row?",function(result) {
				if (result) {
					$.blockUI({
						message : '<i class="fa fa-spinner fa-spin"></i> Loading...'
					});
					$.ajax({
						url : contextURL+'/master/item/delete/'+id,
						dataType : 'json',
						success : function(json) {
							$.unblockUI();
							if (json.say == "ok") {
								oTable.fnDeleteRow(nRow);
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
			});
		});

		var oTable = $('#sample_2').dataTable({
			"aoColumnDefs" : [ {
				"aTargets" : [ 0 ]
			} ],
			"oLanguage" : {
				"sLengthMenu" : "Show _MENU_ Rows",
				"sSearch" : "",
				"oPaginate" : {
					"sPrevious" : "",
					"sNext" : ""
				}
			},
			"aaSorting" : [ [ 1, 'asc' ] ],
			"aLengthMenu" : [ [ 5, 10, 15, 20, -1 ], [ 5, 10, 15, 20, "All" ] // change
			// per
			// page
			// values
			// here
			],
			// set the initial value
			"iDisplayLength" : 10,
		});
		$('#sample_2_wrapper .dataTables_filter input').addClass(
				"form-control input-sm").attr("placeholder", "Search");
		// modify table search input
		$('#sample_2_wrapper .dataTables_length select').addClass(
				"m-wrap small");
		// modify table per page dropdown
		$('#sample_2_wrapper .dataTables_length select').select2();
		// initialzie select2 dropdown
		$('#sample_2_column_toggler input[type="checkbox"]').change(function() {
			/*
			 * Get the DataTables object again - this is not a recreation, just
			 * a get of the object
			 */
			var iCol = parseInt($(this).attr("data-column"));
			var bVis = oTable.fnSettings().aoColumns[iCol].bVisible;
			oTable.fnSetColumnVis(iCol, (bVis ? false : true));
		});

	};

	return {
		//main function to initiate template pages
		init : function() {
			runDataTable();
			runEditableTable();
		}
	};
}();

var TableDataForSaleTax = function() {
	// function to initiate DataTable
	// DataTable is a highly flexible tool, based upon the foundations of
	// progressive enhancement,
	// which will add advanced interaction controls to any HTML table
	// For more information, please visit https://datatables.net/
	var runDataTable = function() {
		var oTable = $('#sample_1').dataTable({
			"aoColumnDefs" : [ {
				"aTargets" : [ 0 ]
			} ],
			"oLanguage" : {
				"sLengthMenu" : "Show _MENU_ Rows",
				"sSearch" : "",
				"oPaginate" : {
					"sPrevious" : "",
					"sNext" : ""
				}
			},
			"aaSorting" : [ [ 1, 'asc' ] ],
			"aLengthMenu" : [ [ 5, 10, 15, 20, -1 ], [ 5, 10, 15, 20, "All" ] // change
			// per
			// page
			// values
			// here
			],
			// set the initial value
			"iDisplayLength" : 10,
		});
		$('#sample_1_wrapper .dataTables_filter input').addClass(
				"form-control input-sm").attr("placeholder", "Search");
		// modify table search input
		$('#sample_1_wrapper .dataTables_length select').addClass(
				"m-wrap small");
		// modify table per page dropdown
		$('#sample_1_wrapper .dataTables_length select').select2();
		// initialzie select2 dropdown
		$('#sample_1_column_toggler input[type="checkbox"]').change(function() {
			/*
			 * Get the DataTables object again - this is not a recreation, just
			 * a get of the object
			 */
			var iCol = parseInt($(this).attr("data-column"));
			var bVis = oTable.fnSettings().aoColumns[iCol].bVisible;
			oTable.fnSetColumnVis(iCol, (bVis ? false : true));
		});
	};

	var runEditableTable = function() {

		var newRow = false;
		var actualEditingRow = null;

		$('#sample_2').on('click','.delete-row',function(e) {
			e.preventDefault();
			if (newRow && actualEditingRow) {
				oTable.fnDeleteRow(actualEditingRow);
				newRow = false;
			}
			var nRow = $(this).parents('tr')[0];
			var id = parseInt($(this).attr("id"));
			bootbox.confirm("Are you sure to delete this row?",function(result) {
				if (result) {
					$.blockUI({
						message : '<i class="fa fa-spinner fa-spin"></i> Loading...'
					});
					$.ajax({
						url : contextURL+'/master/sale-tax/delete/'+id,
						dataType : 'json',
						success : function(json) {
							$.unblockUI();
							if (json.say == "ok") {
								oTable.fnDeleteRow(nRow);
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
			});
		});

		var oTable = $('#sample_2').dataTable({
			"aoColumnDefs" : [ {
				"aTargets" : [ 0 ]
			} ],
			"oLanguage" : {
				"sLengthMenu" : "Show _MENU_ Rows",
				"sSearch" : "",
				"oPaginate" : {
					"sPrevious" : "",
					"sNext" : ""
				}
			},
			"aaSorting" : [ [ 1, 'asc' ] ],
			"aLengthMenu" : [ [ 5, 10, 15, 20, -1 ], [ 5, 10, 15, 20, "All" ] // change
			// per
			// page
			// values
			// here
			],
			// set the initial value
			"iDisplayLength" : 10,
		});
		$('#sample_2_wrapper .dataTables_filter input').addClass(
				"form-control input-sm").attr("placeholder", "Search");
		// modify table search input
		$('#sample_2_wrapper .dataTables_length select').addClass(
				"m-wrap small");
		// modify table per page dropdown
		$('#sample_2_wrapper .dataTables_length select').select2();
		// initialzie select2 dropdown
		$('#sample_2_column_toggler input[type="checkbox"]').change(function() {
			/*
			 * Get the DataTables object again - this is not a recreation, just
			 * a get of the object
			 */
			var iCol = parseInt($(this).attr("data-column"));
			var bVis = oTable.fnSettings().aoColumns[iCol].bVisible;
			oTable.fnSetColumnVis(iCol, (bVis ? false : true));
		});

	};

	return {
		//main function to initiate template pages
		init : function() {
			runDataTable();
			runEditableTable();
		}
	};
}();

var TableDataForVat = function() {
	// function to initiate DataTable
	// DataTable is a highly flexible tool, based upon the foundations of
	// progressive enhancement,
	// which will add advanced interaction controls to any HTML table
	// For more information, please visit https://datatables.net/
	var runDataTable = function() {
		var oTable = $('#sample_1').dataTable({
			"aoColumnDefs" : [ {
				"aTargets" : [ 0 ]
			} ],
			"oLanguage" : {
				"sLengthMenu" : "Show _MENU_ Rows",
				"sSearch" : "",
				"oPaginate" : {
					"sPrevious" : "",
					"sNext" : ""
				}
			},
			"aaSorting" : [ [ 1, 'asc' ] ],
			"aLengthMenu" : [ [ 5, 10, 15, 20, -1 ], [ 5, 10, 15, 20, "All" ] // change
			// per
			// page
			// values
			// here
			],
			// set the initial value
			"iDisplayLength" : 10,
		});
		$('#sample_1_wrapper .dataTables_filter input').addClass(
				"form-control input-sm").attr("placeholder", "Search");
		// modify table search input
		$('#sample_1_wrapper .dataTables_length select').addClass(
				"m-wrap small");
		// modify table per page dropdown
		$('#sample_1_wrapper .dataTables_length select').select2();
		// initialzie select2 dropdown
		$('#sample_1_column_toggler input[type="checkbox"]').change(function() {
			/*
			 * Get the DataTables object again - this is not a recreation, just
			 * a get of the object
			 */
			var iCol = parseInt($(this).attr("data-column"));
			var bVis = oTable.fnSettings().aoColumns[iCol].bVisible;
			oTable.fnSetColumnVis(iCol, (bVis ? false : true));
		});
	};

	var runEditableTable = function() {

		var newRow = false;
		var actualEditingRow = null;

		$('#sample_2').on('click','.delete-row',function(e) {
			e.preventDefault();
			if (newRow && actualEditingRow) {
				oTable.fnDeleteRow(actualEditingRow);
				newRow = false;
			}
			var nRow = $(this).parents('tr')[0];
			var id = parseInt($(this).attr("id"));
			bootbox.confirm("Are you sure to delete this row?",function(result) {
				if (result) {
					$.blockUI({
						message : '<i class="fa fa-spinner fa-spin"></i> Loading...'
					});
					$.ajax({
						url : contextURL+'/master/vat/delete/'+id,
						dataType : 'json',
						success : function(json) {
							$.unblockUI();
							if (json.say == "ok") {
								oTable.fnDeleteRow(nRow);
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
			});
		});

		var oTable = $('#sample_2').dataTable({
			"aoColumnDefs" : [ {
				"aTargets" : [ 0 ]
			} ],
			"oLanguage" : {
				"sLengthMenu" : "Show _MENU_ Rows",
				"sSearch" : "",
				"oPaginate" : {
					"sPrevious" : "",
					"sNext" : ""
				}
			},
			"aaSorting" : [ [ 1, 'asc' ] ],
			"aLengthMenu" : [ [ 5, 10, 15, 20, -1 ], [ 5, 10, 15, 20, "All" ] // change
			// per
			// page
			// values
			// here
			],
			// set the initial value
			"iDisplayLength" : 10,
		});
		$('#sample_2_wrapper .dataTables_filter input').addClass(
				"form-control input-sm").attr("placeholder", "Search");
		// modify table search input
		$('#sample_2_wrapper .dataTables_length select').addClass(
				"m-wrap small");
		// modify table per page dropdown
		$('#sample_2_wrapper .dataTables_length select').select2();
		// initialzie select2 dropdown
		$('#sample_2_column_toggler input[type="checkbox"]').change(function() {
			/*
			 * Get the DataTables object again - this is not a recreation, just
			 * a get of the object
			 */
			var iCol = parseInt($(this).attr("data-column"));
			var bVis = oTable.fnSettings().aoColumns[iCol].bVisible;
			oTable.fnSetColumnVis(iCol, (bVis ? false : true));
		});

	};

	return {
		//main function to initiate template pages
		init : function() {
			runDataTable();
			runEditableTable();
		}
	};
}();

var TableDataForState = function() {

	var runDataTable = function() {
		var oTable = $('#sample_1').dataTable({
			"aoColumnDefs" : [ {
				"aTargets" : [ 0 ]
			} ],
			"oLanguage" : {
				"sLengthMenu" : "Show _MENU_ Rows",
				"sSearch" : "",
				"oPaginate" : {
					"sPrevious" : "",
					"sNext" : ""
				}
			},
			"aaSorting" : [ [ 1, 'asc' ] ],
			"aLengthMenu" : [ [ 5, 10, 15, 20, -1 ], [ 5, 10, 15, 20, "All" ] // change
			// per
			// page
			// values
			// here
			],
			// set the initial value
			"iDisplayLength" : 10,
		});
		$('#sample_1_wrapper .dataTables_filter input').addClass(
				"form-control input-sm").attr("placeholder", "Search");
		// modify table search input
		$('#sample_1_wrapper .dataTables_length select').addClass(
				"m-wrap small");
		// modify table per page dropdown
		$('#sample_1_wrapper .dataTables_length select').select2();
		// initialzie select2 dropdown
		$('#sample_1_column_toggler input[type="checkbox"]').change(function() {
			/*
			 * Get the DataTables object again - this is not a recreation, just
			 * a get of the object
			 */
			var iCol = parseInt($(this).attr("data-column"));
			var bVis = oTable.fnSettings().aoColumns[iCol].bVisible;
			oTable.fnSetColumnVis(iCol, (bVis ? false : true));
		});
	};

	var runEditableTable = function() {

		var newRow = false;
		var actualEditingRow = null;

		function restoreRow(oTable, nRow) {
			var aData = oTable.fnGetData(nRow);
			var jqTds = $('>td', nRow);

			for (var i = 0, iLen = jqTds.length; i < iLen; i++) {
				oTable.fnUpdate(aData[i], nRow, i, false);
			}

			oTable.fnDraw();
		}

		function editRow(oTable, nRow) {
			editRow(oTable, nRow, "");
		}
		
		function editRow(oTable, nRow, id) {
			var aData = oTable.fnGetData(nRow);
			var jqTds = $('>td', nRow);
			jqTds[0].setAttribute("id","name");
			jqTds[0].innerHTML = '<input type="text" class="form-control" value="'
				+ aData[0] + '">';
			
			jqTds[1].innerHTML = '<a class="save-row" id="'+id+'" href="">Save</a>';
			jqTds[2].innerHTML = '<a class="cancel-row" id="'+id+'" href="">Cancel</a>';

		}

		function saveRow(oTable, nRow) {
			saveRow(oTable, nRow, "");
		}
		
		function saveRow(oTable, nRow, id) {
			var text = $('input[type="text"]', nRow);
			
			oTable.fnUpdate(text[0].value, nRow, 0, false);
			
			oTable.fnUpdate('<a class="edit-row" href="" id="'+id+'">Edit</a>', nRow, 1,
					false);
			oTable.fnUpdate('<a class="delete-row" id="'+id+'" href="">Delete</a>', nRow,
					2, false);
			oTable.fnDraw();
			newRow = false;
			actualEditingRow = null;
		}

		$('body').on('click', '.add-row', function(e) {
			e.preventDefault();
			if (newRow == false) {
				if (actualEditingRow) {
					restoreRow(oTable, actualEditingRow);
				}
				newRow = true;
				var aiNew = oTable.fnAddData([ '', '', '' ]);
				var nRow = oTable.fnGetNodes(aiNew[0]);
				editRow(oTable, nRow);
				actualEditingRow = nRow;
			}
		});
		$('#sample_2').on('click', '.cancel-row', function(e) {

			e.preventDefault();
			if (newRow) {
				newRow = false;
				actualEditingRow = null;
				var nRow = $(this).parents('tr')[0];
				oTable.fnDeleteRow(nRow);

			} else {
				restoreRow(oTable, actualEditingRow);
				actualEditingRow = null;
			}
		});
		$('#sample_2').on('click','.delete-row',function(e) {
			e.preventDefault();
			if (newRow && actualEditingRow) {
				oTable.fnDeleteRow(actualEditingRow);
				newRow = false;
			}
			var nRow = $(this).parents('tr')[0];
			var id = parseInt($(this).attr("id"));
			bootbox.confirm("Are you sure to delete this row?",function(result) {
				if (result) {
					$.blockUI({
						message : '<i class="fa fa-spinner fa-spin"></i> Loading...'
					});
					$.ajax({
						url : contextURL+'/master/state/delete/'+id,
						dataType : 'json',
						success : function(json) {
							$.unblockUI();
							if (json.say == "ok") {
								oTable.fnDeleteRow(nRow);
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
			});
		});
		$('#sample_2').on('click','.save-row',function(e) {
			
			$('.has-error').each(function(i, obj) {
				$(this).removeClass("has-error");
				$(this).find("span").remove();
			});
			
			e.preventDefault();
			var nRow = $(this).parents('tr')[0];
			$.blockUI({
				message : '<i class="fa fa-spinner fa-spin"></i> Loading...'
			});
			var id = $(this).attr("id");
			var name = $('.form-control')[1].value;
			var values = { "id" : id, "name" : name};
			$.ajax({
				url : contextURL+'/master/state/save',
				dataType : 'json',
				data : values,
				success : function(json) {
					$.unblockUI();
					if (json.say == "ok") {
						saveRow(oTable, nRow, json.id);
						
						$("#sample_2 tr").each(function () {
						    $('td', this).each(function () {
						        $(this).removeAttr("id");
						     })
						})
					} else if(json.say == "invalid-inputs"){
						$.each(json, function(key,value) {
							  if(key!="say") {
								  $("#"+key).append("<span class='help-block'>" +
								  		value+"</span>");
								  $("#"+key).addClass("has-error");
							  }
							});
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
		});
		$('#sample_2').on('click', '.edit-row', function(e) {
			e.preventDefault();
			if (actualEditingRow) {
				if (newRow) {
					oTable.fnDeleteRow(actualEditingRow);
					newRow = false;
				} else {
					restoreRow(oTable, actualEditingRow);

				}
			}
			var nRow = $(this).parents('tr')[0];
			editRow(oTable, nRow, $(this).attr("id"));
			actualEditingRow = nRow;

		});
		
		var oTable = $('#sample_2').dataTable({
			"aoColumnDefs" : [ {
				"aTargets" : [ 0 ]
			} ],
			"oLanguage" : {
				"sLengthMenu" : "Show _MENU_ Rows",
				"sSearch" : "",
				"oPaginate" : {
					"sPrevious" : "",
					"sNext" : ""
				}
			},
			"aaSorting" : [ [ 1, 'asc' ] ],
			"aLengthMenu" : [ [ 5, 10, 15, 20, -1 ], [ 5, 10, 15, 20, "All" ] // change
			// per
			// page
			// values
			// here
			],
			// set the initial value
			"iDisplayLength" : 10,
		});
		$('#sample_2_wrapper .dataTables_filter input').addClass(
				"form-control input-sm").attr("placeholder", "Search");
		// modify table search input
		$('#sample_2_wrapper .dataTables_length select').addClass(
				"m-wrap small");
		// modify table per page dropdown
		$('#sample_2_wrapper .dataTables_length select').select2();
		// initialzie select2 dropdown
		$('#sample_2_column_toggler input[type="checkbox"]').change(function() {
			/*
			 * Get the DataTables object again - this is not a recreation, just
			 * a get of the object
			 */
			var iCol = parseInt($(this).attr("data-column"));
			var bVis = oTable.fnSettings().aoColumns[iCol].bVisible;
			oTable.fnSetColumnVis(iCol, (bVis ? false : true));
		});

	};

	return {
		//main function to initiate template pages
		init : function() {
			runDataTable();
			runEditableTable();
		}
	};
}();

var TableDataForCharge = function() {
	// function to initiate DataTable
	// DataTable is a highly flexible tool, based upon the foundations of
	// progressive enhancement,
	// which will add advanced interaction controls to any HTML table
	// For more information, please visit https://datatables.net/
	var runDataTable = function() {
		var oTable = $('#sample_1').dataTable({
			"aoColumnDefs" : [ {
				"aTargets" : [ 0 ]
			} ],
			"oLanguage" : {
				"sLengthMenu" : "Show _MENU_ Rows",
				"sSearch" : "",
				"oPaginate" : {
					"sPrevious" : "",
					"sNext" : ""
				}
			},
			"aaSorting" : [ [ 1, 'asc' ] ],
			"aLengthMenu" : [ [ 5, 10, 15, 20, -1 ], [ 5, 10, 15, 20, "All" ] // change
			// per
			// page
			// values
			// here
			],
			// set the initial value
			"iDisplayLength" : 10,
		});
		$('#sample_1_wrapper .dataTables_filter input').addClass(
				"form-control input-sm").attr("placeholder", "Search");
		// modify table search input
		$('#sample_1_wrapper .dataTables_length select').addClass(
				"m-wrap small");
		// modify table per page dropdown
		$('#sample_1_wrapper .dataTables_length select').select2();
		// initialzie select2 dropdown
		$('#sample_1_column_toggler input[type="checkbox"]').change(function() {
			/*
			 * Get the DataTables object again - this is not a recreation, just
			 * a get of the object
			 */
			var iCol = parseInt($(this).attr("data-column"));
			var bVis = oTable.fnSettings().aoColumns[iCol].bVisible;
			oTable.fnSetColumnVis(iCol, (bVis ? false : true));
		});
	};

	var runEditableTable = function() {

		var newRow = false;
		var actualEditingRow = null;

		$('#sample_2').on('click','.delete-row',function(e) {
			e.preventDefault();
			if (newRow && actualEditingRow) {
				oTable.fnDeleteRow(actualEditingRow);
				newRow = false;
			}
			var nRow = $(this).parents('tr')[0];
			var id = parseInt($(this).attr("id"));
			bootbox.confirm("Are you sure to delete this row?",function(result) {
				if (result) {
					$.blockUI({
						message : '<i class="fa fa-spinner fa-spin"></i> Loading...'
					});
					$.ajax({
						url : contextURL+'/master/charge/delete/'+id,
						dataType : 'json',
						success : function(json) {
							$.unblockUI();
							if (json.say == "ok") {
								oTable.fnDeleteRow(nRow);
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
			});
		});

		var oTable = $('#sample_2').dataTable({
			"aoColumnDefs" : [ {
				"aTargets" : [ 0 ]
			} ],
			"oLanguage" : {
				"sLengthMenu" : "Show _MENU_ Rows",
				"sSearch" : "",
				"oPaginate" : {
					"sPrevious" : "",
					"sNext" : ""
				}
			},
			"aaSorting" : [ [ 1, 'asc' ] ],
			"aLengthMenu" : [ [ 5, 10, 15, 20, -1 ], [ 5, 10, 15, 20, "All" ] // change
			// per
			// page
			// values
			// here
			],
			// set the initial value
			"iDisplayLength" : 10,
		});
		$('#sample_2_wrapper .dataTables_filter input').addClass(
				"form-control input-sm").attr("placeholder", "Search");
		// modify table search input
		$('#sample_2_wrapper .dataTables_length select').addClass(
				"m-wrap small");
		// modify table per page dropdown
		$('#sample_2_wrapper .dataTables_length select').select2();
		// initialzie select2 dropdown
		$('#sample_2_column_toggler input[type="checkbox"]').change(function() {
			/*
			 * Get the DataTables object again - this is not a recreation, just
			 * a get of the object
			 */
			var iCol = parseInt($(this).attr("data-column"));
			var bVis = oTable.fnSettings().aoColumns[iCol].bVisible;
			oTable.fnSetColumnVis(iCol, (bVis ? false : true));
		});

	};

	return {
		//main function to initiate template pages
		init : function() {
			runDataTable();
			runEditableTable();
		}
	};
}();

var TableDataForDaybook = function() {
	// function to initiate DataTable
	// DataTable is a highly flexible tool, based upon the foundations of
	// progressive enhancement,
	// which will add advanced interaction controls to any HTML table
	// For more information, please visit https://datatables.net/
	var runDataTable = function() {
		var oTable = $('#sample_1').dataTable({
			"aoColumnDefs" : [ {
				"aTargets" : [ 0 ]
			} ],
			"oLanguage" : {
				"sLengthMenu" : "Show _MENU_ Rows",
				"sSearch" : "",
				"oPaginate" : {
					"sPrevious" : "",
					"sNext" : ""
				}
			},
			"aaSorting" : [ [ 1, 'asc' ] ],
			"aLengthMenu" : [ [ 5, 10, 15, 20, -1 ], [ 5, 10, 15, 20, "All" ] // change
			// per
			// page
			// values
			// here
			],
			// set the initial value
			"iDisplayLength" : 10,
		});
		$('#sample_1_wrapper .dataTables_filter input').addClass(
				"form-control input-sm").attr("placeholder", "Search");
		// modify table search input
		$('#sample_1_wrapper .dataTables_length select').addClass(
				"m-wrap small");
		// modify table per page dropdown
		$('#sample_1_wrapper .dataTables_length select').select2();
		// initialzie select2 dropdown
		$('#sample_1_column_toggler input[type="checkbox"]').change(function() {
			/*
			 * Get the DataTables object again - this is not a recreation, just
			 * a get of the object
			 */
			var iCol = parseInt($(this).attr("data-column"));
			var bVis = oTable.fnSettings().aoColumns[iCol].bVisible;
			oTable.fnSetColumnVis(iCol, (bVis ? false : true));
		});
	};

	var runEditableTable = function() {

		var newRow = false;
		var actualEditingRow = null;

		function restoreRow(oTable, nRow) {
			var aData = oTable.fnGetData(nRow);
			var jqTds = $('>td', nRow);

			for (var i = 0, iLen = jqTds.length; i < iLen; i++) {
				oTable.fnUpdate(aData[i], nRow, i, false);
			}

			oTable.fnDraw();
		}

		function editRow(oTable, nRow) {
			editRow(oTable, nRow, "");
		}
		
		function editRow(oTable, nRow, id) {
			var aData = oTable.fnGetData(nRow);
			var jqTds = $('>td', nRow);
			jqTds[0].setAttribute("id","name");
			jqTds[0].innerHTML = '<input type="text" class="form-control" value="'
				+ aData[0] + '">';
			if(aData[1]=="SALE") {
				jqTds[1].innerHTML = '<select class="form-control"><option>PURCHASE</option><option selected>SALE</option></select>';
			} else {
				jqTds[1].innerHTML = '<select class="form-control"><option selected>PURCHASE</option><option>SALE</option></select>';
			}
			
			jqTds[2].innerHTML = '<a class="save-row" id="'+id+'" href="">Save</a>';
			jqTds[3].innerHTML = '<a class="cancel-row" id="'+id+'" href="">Cancel</a>';

		}

		function saveRow(oTable, nRow) {
			saveRow(oTable, nRow, "");
		}
		
		function saveRow(oTable, nRow, id) {
			var text = $('input[type="text"]', nRow);
			var select = $('select', nRow);
			
			oTable.fnUpdate(text[0].value, nRow, 0, false);
			oTable.fnUpdate(select[0].value, nRow, 1, false);
			
			oTable.fnUpdate('<a class="edit-row" href="" id="'+id+'">Edit</a>', nRow, 2,
					false);
			oTable.fnUpdate('<a class="delete-row" id="'+id+'" href="">Delete</a>', nRow,
					3, false);
			oTable.fnDraw();
			newRow = false;
			actualEditingRow = null;
		}

		$('body').on('click', '.add-row', function(e) {
			e.preventDefault();
			if (newRow == false) {
				if (actualEditingRow) {
					restoreRow(oTable, actualEditingRow);
				}
				newRow = true;
				var aiNew = oTable.fnAddData([ '', '', '', '' ]);
				var nRow = oTable.fnGetNodes(aiNew[0]);
				editRow(oTable, nRow);
				actualEditingRow = nRow;
			}
		});
		$('#sample_2').on('click', '.cancel-row', function(e) {

			e.preventDefault();
			if (newRow) {
				newRow = false;
				actualEditingRow = null;
				var nRow = $(this).parents('tr')[0];
				oTable.fnDeleteRow(nRow);

			} else {
				restoreRow(oTable, actualEditingRow);
				actualEditingRow = null;
			}
		});
		$('#sample_2').on('click','.delete-row',function(e) {
			e.preventDefault();
			if (newRow && actualEditingRow) {
				oTable.fnDeleteRow(actualEditingRow);
				newRow = false;
			}
			var nRow = $(this).parents('tr')[0];
			var id = parseInt($(this).attr("id"));
			bootbox.confirm("Are you sure to delete this row?",function(result) {
				if (result) {
					$.blockUI({
						message : '<i class="fa fa-spinner fa-spin"></i> Loading...'
					});
					$.ajax({
						url : contextURL+'/master/daybook/delete/'+id,
						dataType : 'json',
						success : function(json) {
							$.unblockUI();
							if (json.say == "ok") {
								oTable.fnDeleteRow(nRow);
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
			});
		});
		$('#sample_2').on('click','.save-row',function(e) {
			
			$('.has-error').each(function(i, obj) {
				$(this).removeClass("has-error");
				$(this).find("span").remove();
			});
			
			e.preventDefault();
			var nRow = $(this).parents('tr')[0];
			$.blockUI({
				message : '<i class="fa fa-spinner fa-spin"></i> Loading...'
			});
			var id = $(this).attr("id");
			var name = $('.form-control')[1].value;
			var type = $('.form-control')[2].value;
			var values = { "id" : id, "name" : name, "type" : type};
			$.ajax({
				url : contextURL+'/master/daybook/save',
				dataType : 'json',
				data : values,
				success : function(json) {
					$.unblockUI();
					if (json.say == "ok") {
						saveRow(oTable, nRow, json.id);
						
						$("#sample_2 tr").each(function () {
						    $('td', this).each(function () {
						        $(this).removeAttr("id");
						     })
						})
					} else if(json.say == "invalid-inputs"){
						$.each(json, function(key,value) {
							  if(key!="say") {
								  $("#"+key).append("<span class='help-block'>" +
								  		value+"</span>");
								  $("#"+key).addClass("has-error");
							  }
							});
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
		});
		$('#sample_2').on('click', '.edit-row', function(e) {
			e.preventDefault();
			if (actualEditingRow) {
				if (newRow) {
					oTable.fnDeleteRow(actualEditingRow);
					newRow = false;
				} else {
					restoreRow(oTable, actualEditingRow);

				}
			}
			var nRow = $(this).parents('tr')[0];
			editRow(oTable, nRow, $(this).attr("id"));
			actualEditingRow = nRow;

		});
		
		var oTable = $('#sample_2').dataTable({
			"aoColumnDefs" : [ {
				"aTargets" : [ 0 ]
			} ],
			"oLanguage" : {
				"sLengthMenu" : "Show _MENU_ Rows",
				"sSearch" : "",
				"oPaginate" : {
					"sPrevious" : "",
					"sNext" : ""
				}
			},
			"aaSorting" : [ [ 1, 'asc' ] ],
			"aLengthMenu" : [ [ 5, 10, 15, 20, -1 ], [ 5, 10, 15, 20, "All" ] // change
			// per
			// page
			// values
			// here
			],
			// set the initial value
			"iDisplayLength" : 10,
		});
		$('#sample_2_wrapper .dataTables_filter input').addClass(
				"form-control input-sm").attr("placeholder", "Search");
		// modify table search input
		$('#sample_2_wrapper .dataTables_length select').addClass(
				"m-wrap small");
		// modify table per page dropdown
		$('#sample_2_wrapper .dataTables_length select').select2();
		// initialzie select2 dropdown
		$('#sample_2_column_toggler input[type="checkbox"]').change(function() {
			/*
			 * Get the DataTables object again - this is not a recreation, just
			 * a get of the object
			 */
			var iCol = parseInt($(this).attr("data-column"));
			var bVis = oTable.fnSettings().aoColumns[iCol].bVisible;
			oTable.fnSetColumnVis(iCol, (bVis ? false : true));
		});

	};

	return {
		//main function to initiate template pages
		init : function() {
			runDataTable();
			runEditableTable();
		}
	};
}();


var TableDataForPayment = function() {
	// function to initiate DataTable
	// DataTable is a highly flexible tool, based upon the foundations of
	// progressive enhancement,
	// which will add advanced interaction controls to any HTML table
	// For more information, please visit https://datatables.net/
	var runDataTable = function() {
		var oTable = $('#sample_1').dataTable({
			"aoColumnDefs" : [ {
				"aTargets" : [ 0 ]
			} ],
			"oLanguage" : {
				"sLengthMenu" : "Show _MENU_ Rows",
				"sSearch" : "",
				"oPaginate" : {
					"sPrevious" : "",
					"sNext" : ""
				}
			},
			"aaSorting" : [ [ 1, 'asc' ] ],
			"aLengthMenu" : [ [ 5, 10, 15, 20, -1 ], [ 5, 10, 15, 20, "All" ] // change
			// per
			// page
			// values
			// here
			],
			// set the initial value
			"iDisplayLength" : 10,
		});
		$('#sample_1_wrapper .dataTables_filter input').addClass(
				"form-control input-sm").attr("placeholder", "Search");
		// modify table search input
		$('#sample_1_wrapper .dataTables_length select').addClass(
				"m-wrap small");
		// modify table per page dropdown
		$('#sample_1_wrapper .dataTables_length select').select2();
		// initialzie select2 dropdown
		$('#sample_1_column_toggler input[type="checkbox"]').change(function() {
			/*
			 * Get the DataTables object again - this is not a recreation, just
			 * a get of the object
			 */
			var iCol = parseInt($(this).attr("data-column"));
			var bVis = oTable.fnSettings().aoColumns[iCol].bVisible;
			oTable.fnSetColumnVis(iCol, (bVis ? false : true));
		});
	};

	var runEditableTable = function() {

		var newRow = false;
		var actualEditingRow = null;

		$('#sample_2').on('click','.delete-row',function(e) {
			e.preventDefault();
			if (newRow && actualEditingRow) {
				oTable.fnDeleteRow(actualEditingRow);
				newRow = false;
			}
			var nRow = $(this).parents('tr')[0];
			var id = parseInt($(this).attr("id"));
			bootbox.confirm("Are you sure to delete this row?",function(result) {
				if (result) {
					$.blockUI({
						message : '<i class="fa fa-spinner fa-spin"></i> Loading...'
					});
					$.ajax({
						url : contextURL+'/transaction/payment/delete/'+id,
						dataType : 'json',
						success : function(json) {
							$.unblockUI();
							if (json.say == "ok") {
								oTable.fnDeleteRow(nRow);
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
			});
		});

		var oTable = $('#sample_2').dataTable({
			"aoColumnDefs" : [ {
				"aTargets" : [ 0 ]
			} ],
			"oLanguage" : {
				"sLengthMenu" : "Show _MENU_ Rows",
				"sSearch" : "",
				"oPaginate" : {
					"sPrevious" : "",
					"sNext" : ""
				}
			},
			"aaSorting" : [ [ 1, 'asc' ] ],
			"aLengthMenu" : [ [ 5, 10, 15, 20, -1 ], [ 5, 10, 15, 20, "All" ] // change
			// per
			// page
			// values
			// here
			],
			// set the initial value
			"iDisplayLength" : 10,
		});
		$('#sample_2_wrapper .dataTables_filter input').addClass(
				"form-control input-sm").attr("placeholder", "Search");
		// modify table search input
		$('#sample_2_wrapper .dataTables_length select').addClass(
				"m-wrap small");
		// modify table per page dropdown
		$('#sample_2_wrapper .dataTables_length select').select2();
		// initialzie select2 dropdown
		$('#sample_2_column_toggler input[type="checkbox"]').change(function() {
			/*
			 * Get the DataTables object again - this is not a recreation, just
			 * a get of the object
			 */
			var iCol = parseInt($(this).attr("data-column"));
			var bVis = oTable.fnSettings().aoColumns[iCol].bVisible;
			oTable.fnSetColumnVis(iCol, (bVis ? false : true));
		});

	};

	return {
		//main function to initiate template pages
		init : function() {
			runDataTable();
			runEditableTable();
		}
	};
}();
