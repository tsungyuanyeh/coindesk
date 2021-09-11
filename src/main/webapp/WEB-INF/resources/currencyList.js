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

	var valid = /^(?:-?\d+|-?\d{1,3}(?:,\d{3})+)?(?:\.\d+)?$/.test(param.rate);
	if (valid)
		return param;
	var erm = 'RATE please type float(#.##)';
	alert(erm);
	return;
}
function fnAdd() {
	var param = formParam();
	if (param)
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
	if (param)
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