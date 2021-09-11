$(function() {
	findAll();
	$('#btnCurrencyInit').click(fnCurrencyInit);
	$('#btnAddData').click(fnAddData);
	$('#btnCancel').click(fnCancel);
	$('#btnAdd').click(fnAdd);
	$('#btnUpdate').click(fnUpdate);
});

function findAll() {
	$.ajax({
		url: "currency",
		cache: false,
		type: "GET",
		success: function(result, textStatus, request) {
			//console.log('result:' + JSON.stringify(result));
			var tbody = $('#grid tbody').empty();
			for (var i = 0; i < result.length; i++) {
				var row = result[i];
				//console.log('row:' + JSON.stringify(row));
				var tr = $("<tr>").attr("id", 'tr_' + row.id);
				tr.append($("<td>").text(row.id));
				tr.append($("<td>").text(row.code))
					.append($("<td>").text(row.description)).append($("<td>").text(row.symbol)).append($("<td>").text(row.rate));
				tr.append($("<td>")
					.append($("<button>").text('edit').click(fnModify))
					.append($("<button>").text('delete').click(fnRemove)));
				tbody.append(tr);
			}
		},
		error: function(xhr, ajaxOptions, thrownError) {
		}
	});
}
function fnRemove() {
	var tr = $(this).parent().parent();
	var tr_id = tr.attr("id");
	console.log('tr_id:' + tr_id);
	var id = tr_id.substr(3);
	$.ajax({
		url: "currency/" + id,
		cache: false,
		type: "DELETE",
		success: function(result, textStatus, request) {
			if (result)
				//findAll();
				tr.remove();
			else
				alert('remove failed!');
		},
		error: function(xhr, ajaxOptions, thrownError) {
		}
	});
}
function fnModify() {
	var tr = $(this).parent().parent();
	var tr_id = tr.attr("id");
	console.log('tr_id:' + tr_id);
	var id = tr_id.substr(3);
	$.ajax({
		url: "currency/" + id,
		cache: false,
		type: "GET",
		success: function(result, textStatus, request) {
			//console.log('result:' + JSON.stringify(result));
			if (result) {
				for (var key in result)
					$('#f_' + key).val(result[key]);
				$('#btnAdd').hide();
				$('#btnUpdate').show();
				$('#dataForm').show();
			} else {
				alert('modify failed!');
			}
		},
		error: function(xhr, ajaxOptions, thrownError) {
		}
	});
}
function fnAddData() {
	$('form')[0].reset();
	$('#btnAdd').show();
	$('#btnUpdate').hide();
	$('#dataForm').show();
}
function fnCancel() {
	$('#dataForm').hide();
	$('form')[0].reset();
}
function formParam() {
	var param = {};
	$('form input').each(
		function(index) {
			_this =
				$(this);
			var id = _this.attr('id');
			if (id.indexOf('f_') >= 0) {
				var key = id.substr(2);
				param[key] = _this.val();
			}
		}
	);
	return param;
}
function validationNumber(e, num) {
	var regu = /^[0-9] \.?[0-9]*$/;
	if (e.value != "") {
		if (!regu.test(e.value)) {
			alert("請輸入正確的數字");
			e.value = e.value.substring(0, e.value.length - 1);
			e.focus();
		} else {
			if (num == 0) {
				if (e.value.indexOf('.') > -1) {
					e.value = e.value.substring(0, e.value.length - 1);
					e.focus();
				}
			}
			if (e.value.indexOf('.') > -1) {
				if (e.value.split('.')[1].length > num) {
					e.value = e.value.substring(0, e.value.length - 1);
					e.focus();
				}
			}
		}
	}
}
function fnAdd() {
	var param = formParam();

	//	//非負浮點數(正浮點數 + 0)
	//	
	//	if (!r.test(param.rate)) {
	//		erm = 'RATE please type float(#.##)';
	//		alert(erm);
	//		return;
	//	}
	//	if (/^\d{1,n}\.\d{1,n}$/.test(str) alert("只能輸入浮點型數據");

	//var r=^\d+(\.\d+)?$/;

	$.ajax({
		url: "currency",
		cache: false,
		type: "POST",
		data: param,
		success: function(result, textStatus, request) {
			if (result) {
				findAll();
				fnCancel();
			} else {
				alert('add failed!');
			}
		},
		error: function(xhr, ajaxOptions, thrownError) {
		}
	});
}
function fnUpdate() {
	var param = formParam();

	//	//非負浮點數(正浮點數 + 0)
	//	
	//	if (!r.test(param.rate)) {
	//		erm = 'RATE please type float(#.##)';
	//		alert(erm);
	//		return;
	//	}
	//	if (/^\d{1,n}\.\d{1,n}$/.test(str) alert("只能輸入浮點型數據");

	//var r=^\d+(\.\d+)?$/;

	$.ajax({
		url: "currency/" + param.id,
		cache: false,
		type: "PUT",
		data: param,
		success: function(result, textStatus, request) {
			if (result) {
				findAll();
				fnCancel();
			} else {
				alert('add failed!');
			}
		},
		error: function(xhr, ajaxOptions, thrownError) {
		}
	});
}
function fnCurrencyInit() {
	$.ajax({
		url: "currencyInit",
		cache: false,
		type: "POST",
		success: function(result, textStatus, request) {
			if (result)
				findAll();
			else
				alert('currencyInit failed!');
		},
		error: function(xhr, ajaxOptions, thrownError) {
		}
	});
}