<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Currency List</title>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="resources/currencyList.js"></script>
</head>
<body>
	<h1>Currency List</h1>

	<div id="board">
		<button id="btnAddData">add data</button>
		<button id="btnCurrencyInit">currencyInit</button>
		<table id="grid">
			<thead>
				<tr>
					<th>ID</th>
					<th>CODE</th>
					<th>DESCRIPTION</th>
					<th>SYMBOL</th>
					<th>RATE</th>
					<td>action</td>
				</tr>
			</thead>
			<tbody>

			</tbody>
		</table>
	</div>
	<div id="dataForm" style="display: none;">
		<h1>Currency Form</h1>

		<form>
			<input type="hidden" id="f_id" />
			<table>
				<!-- <tr>
					<td>ID</td>
					<td><input type="text" id="f_id" readonly="readonly" /></td>
				</tr> -->
				<tr>
					<td>CODE</td>
					<td><input type="text" required="required" id="f_code" /></td>
				</tr>
				<tr>
					<td>DESCRIPTION</td>
					<td><input type="text" required="required" id="f_description" /></td>
				</tr>
				<tr>
					<td>SYMBOL</td>
					<td><input type="text" required="required" id="f_symbol" /></td>
				</tr>
				<tr>
					<td>RATE</td>
					<td><input type="text" required="required" id="f_rate" /></td>
				</tr>
			</table>
		</form>
		<button id="btnAdd" style="display: none;">add data</button>
		<button id="btnUpdate" style="display: none;">update data</button>
		<button id="btnCancel">cancel</button>
	</div>
</body>
</html>