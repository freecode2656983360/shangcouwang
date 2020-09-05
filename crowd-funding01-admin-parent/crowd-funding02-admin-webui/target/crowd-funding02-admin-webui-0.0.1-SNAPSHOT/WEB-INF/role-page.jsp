<%--
  Created by IntelliJ IDEA.
  User: NGX
  Date: 2020/5/3
  Time: 18:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<html>
<%@include file="include-head.jsp"%>
<link rel="stylesheet" href="css/pagination.css" />
<link rel="stylesheet" href="ztree/zTreeStyle.css" />
<script type="text/javascript" src="ztree/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript" src="jquery/jquery.pagination.js"></script>
<script type="text/javascript" src="layer/layer.js"></script>
<script type="text/javascript" src="crowdjs/my-role.js"></script>
<script type="text/javascript">
	$(function() {
		// 1. 为分页操作准备初始化数据
		window.pageNum = 1;
		window.pageSize = 5;
		window.keyword = "";

		// 2. 调用分页函数，显示分页效果
		generatePage();

		// 给查询按钮绑定单击函数
		$("#searchBtn").click(function() {
			// 获取关键词赋值给全局变量
			window.keyword = $("#keywordInput").val();
			// 调用分页函数刷新页面
			generatePage();
		});

		// 新增按钮
		$("#showAddModalBtn").click(function() {
			$("#addModal").modal("show");
		});

		// 给新增模态框的按钮绑定单击响应函数
		$("#savaRoleBtn").click(function() {
			// 先获取用户的输入
			var roleName = $("#roleName").val();
			// 发送ajax请求
			$.ajax({
				"url" : "role/add.json",
				"type" : "post",
				"data" : {
					"name" : roleName
				},
				"dataType" : "json",
				"success" : function(response) {
					var result = response.result;
					if (result == 'success') {
						layer.msg("操作成功");
						// 页码定位到最后一页
						window.pageNum = 999999;
						// 重新加载分页数据
						generatePage();
					}
					if (result == "failed") {
						layer.msg("操作失败" + result.message);
					}
				},
				"error" : function(response) {
					layer.msg(response.status + " " + response.statusText);

				}
			});
			// 关闭模态框
			$("#addModal").modal("hide");
			// 清理模态框
			$("#roleName").val("");

		});

		// 编辑的按钮都是动态生成的，要找到动态的所附着的静态标签
		$("#rolePageBody").on("click", ".pencilBtn", function() {
			// 打开模态框
			$("#editModal").modal("show");
			// 获取当前行的名称
			var roleName = $(this).parent().prev().text();
			// 获取当前角色的id
			window.roleId = this.id;

			// 使用roleName设置文本框的值
			$("#editRoleName").val(roleName);

		});

		$("#updateRoleBtn").click(function() {
			var roleName = $("#editRoleName").val();

			$.ajax({
				"url" : "role/update.json",
				"type" : "post",
				"data" : {
					"id" : window.roleId,
					"name" : roleName
				},
				"success" : function(response) {
					var result = response.result;

					if (result == 'success') {
						layer.msg("操作成功");
						// 刷新页面
						generatePage();
					}
					if (result == "failed") {
						layer.msg("操作失败" + result.message);
					}
				},
				"error" : function(response) {
					layer.msg(response.status + " " + response.statusText);
				}
			});
			// 关闭模态框
			$("#editModal").modal("hide");
			// 清理模态框
			$("#editRoleName").val("");
		});

		// 点击删除按钮执行删除
		$("#deleteRoleBtn").click(function() {
			var requestBody = JSON.stringify(window.roleIdArray);
			$.ajax({
				"url" : "role/delete.json",
				"type" : "post",
				"data" : requestBody,
				"contentType" : "application/json;charset=UTF-8",
				"dataType" : "json",
				"success" : function(response) {
					var result = response.result;

					if (result == 'success') {
						layer.msg("操作成功");
						// 刷新页面
						generatePage();
					}
					if (result == "failed") {
						layer.msg("操作失败" + result.message);
					}
				},
				"error" : function(response) {
					layer.msg(response.status + " " + response.statusText);
				}
			});
			$("#confirmModal").modal("hide");
		});

		// 单条删除
		$("#rolePageBody").on("click", ".deleteBtn", function() {
			var roleName = $(this).parent().prev().text();
			var roleArray = [ {
				roleId : this.id,
				roleName : roleName
			} ];
			showConfirmModal(roleArray);
		});

		// 给总删除按钮绑定函数
		$("#sumBox").click(function() {
			// 获取当前复选框的属性
			var currentStatus = this.checked;
			$(".itemBox").prop("checked", currentStatus);
		});

		//批量删除
		$("#showDeleteModalBtn").click(function() {
			var roleArray = [];

			$(".itemBox:checked").each(function() {
				var roleId = this.id;
				var roleName = $(this).parent().next().text();
				roleArray.push({
					"roleId" : roleId,
					"roleName" : roleName
				});

			});
			//检查roleArray的长度
			if (roleArray.length == 0) {
				layer.msg("请至少选择一个执行删除");
				return;
			}
			showConfirmModal(roleArray);
		});
		// 13.给分配权限按钮绑定单击响应函数
		$("#rolePageBody").on("click", ".checkBtn", function() {
			// 把当前角色id存入全局变量
			window.roleId = this.id;
			// 打开模态框
			$("#assignModal").modal("show");
			// 在模态框中装载树 Auth 的形结构数据
			fillAuthTree();
		});
		// 14.给分配权限模态框中的“分配”按钮绑定单击响应函数
		$("#assignBtn").click(function() {
			// ①收集树形结构的各个节点中被勾选的节点
			// [1]声明一个专门的数组存放 id
			var authIdArray = [];
			// [2]获取 zTreeObj 对象
			var zTreeObj = $.fn.zTree.getZTreeObj("authTreeDemo");
			// [3]获取全部被勾选的节点
			var checkedNodes = zTreeObj.getCheckedNodes();
			// [4]遍历 checkedNodes
			for (var i = 0; i < checkedNodes.length; i++) {
				var checkedNode = checkedNodes[i];
				var authId = checkedNode.id;
				authIdArray.push(authId);
			}
			// ②发送请求执行分配
			var requestBody = {
				"authIdArray" : authIdArray,
				// 为了服务器端 handler 方法能够统一使用 List<Integer>方式接收数据，roleId 也存入数组
				"roleId" : [ window.roleId ]
			}
			requestBody = JSON.stringify(requestBody);
			$.ajax({
				"url" : "assign/do/role/assign/auth.json",
				"type" : "post",
				"data" : requestBody,
				"contentType" : "application/json;charset=UTF-8",
				"dataType" : "json",
				"success" : function(response) {
					var result = response.result;
					if (result == "success") {
						layer.msg("操作成功！");
					}
					if (result == "failed") {
						layer.msg("操作失败！" + response.message);
					}
				},
				"error" : function(response) {
					layer.msg(response.status + " " + response.statusText);
				}
			});
			$("#assignModal").modal("hide");
		});
	});
</script>


<body>
	<%@include file="include-nav.jsp"%>
	<%@include file="include-sidebar.jsp"%>

	<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title">
					<i class="glyphicon glyphicon-th"></i> 数据列表
				</h3>
			</div>
			<div class="panel-body">
				<form class="form-inline" role="form" style="float: left;">
					<div class="form-group has-feedback">
						<div class="input-group">
							<div class="input-group-addon">查询条件</div>
							<input id="keywordInput" class="form-control has-success"
								type="text" placeholder="请输入查询条件">
						</div>
					</div>
					<button id="searchBtn" type="button" class="btn btn-warning">
						<i class="glyphicon glyphicon-search"></i> 查询
					</button>
				</form>
				<button type="button" id="showDeleteModalBtn" class="btn btn-danger"
					style="float: right; margin-left: 10px;">
					<i class=" glyphicon glyphicon-remove"></i> 删除
				</button>
				<button type="button" id="showAddModalBtn" class="btn btn-primary"
					style="float: right;">
					<i class="glyphicon glyphicon-plus"></i> 新增
				</button>
				<br>
				<hr style="clear: both;">
				<div class="table-responsive">
					<table class="table  table-bordered">
						<thead>
							<tr>
								<th width="30">#</th>
								<th width="30"><input id="sumBox" type="checkbox"></th>
								<th>名称</th>
								<th width="100">操作</th>
							</tr>
						</thead>
						<tbody id="rolePageBody">
						<tfoot>
							<tr>
								<td colspan="6" align="center">
									<div id="Pagination" class="pagination">
										<!-- 这里显示分页 -->
									</div>
								</td>
							</tr>
						</tfoot>

					</table>
				</div>
			</div>
		</div>
	</div>
	<%@include file="modal-role-add.jsp"%>
	<%@include file="modal-role-edit.jsp"%>
	<%@include file="modal-role-confirm.jsp"%>
	<%@include file="modal-role-assign-auth.jsp"%>
</body>
</html>
