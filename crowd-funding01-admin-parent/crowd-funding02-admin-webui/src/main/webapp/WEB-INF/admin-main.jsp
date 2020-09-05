<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<!DOCTYPE html>
<html>
<%--提取公共head--%>
<%@include file="include-head.jsp"%>
<body>

	<%@include file="include-nav.jsp"%>
	<div class="container-fluid">
		<div class="row">
			<%@include file="include-sidebar.jsp"%>
			<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
				<h1 class="page-header">控制面板</h1>
				<div class="row placeholders">

					<sec:authorize access="hasRole('火影')">
						<div class="col-xs-6 col-sm-3 placeholder">
							<img data-src="holder.js/200x200/auto/sky" class="img-responsive"
								alt="Generic placeholder thumbnail">
							<h4>Label</h4>
							<span class="text-muted">Something else</span>
						</div>
					</sec:authorize>
					<sec:authorize access="hasRole('土影')">
						<div class="col-xs-6 col-sm-3 placeholder">
							<img data-src="holder.js/200x200/auto/vine"
								class="img-responsive" alt="Generic placeholder thumbnail">
							<h4>Label</h4>
							<span class="text-muted">Something else</span>
						</div>
					</sec:authorize>
					<sec:authorize access="hasRole('水影')">
						<div class="col-xs-6 col-sm-3 placeholder">
							<img data-src="holder.js/200x200/auto/sky" class="img-responsive"
								alt="Generic placeholder thumbnail">
							<h4>Label</h4>
							<span class="text-muted">Something else</span>
						</div>
					</sec:authorize>
					<sec:authorize access="hasRole('雷影')">
						<div class="col-xs-6 col-sm-3 placeholder">
							<img data-src="holder.js/200x200/auto/vine"
								class="img-responsive" alt="Generic placeholder thumbnail">
							<h4>Label</h4>
							<span class="text-muted">Something else</span>
						</div>
					</sec:authorize>
					<sec:authorize access="hasRole('风影')">
						<div class="col-xs-6 col-sm-3 placeholder">
							<img data-src="holder.js/200x200/auto/vine"
								class="img-responsive" alt="Generic placeholder thumbnail">
							<h4>Label</h4>
							<span class="text-muted">Something else</span>
						</div>
					</sec:authorize>
				</div>
			</div>
		</div>
	</div>
</body>
</html>


