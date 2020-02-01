<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<HTML>
 <HEAD>
  <TITLE>Equibase-Data API-Error</TITLE>
  <meta charset="utf-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <link rel="stylesheet" href="/data-api/css/foundation.css" />
  <link rel="stylesheet" href="/data-api/css/common.css">
 </HEAD>
 <BODY>
	
	<jsp:include page="header.jsp"></jsp:include>
	<div class="marginTop50 text-center error-page">${error}</div>	
	
	<jsp:include page="footer.jsp"></jsp:include>
	
	<script type="text/javascript" src="/data-api/js/prototype.js"></script>
	<script type="text/javascript" src="/data-api/js/jquery-1.11.3.min.js"></script>
	<script type="text/javascript" src="/data-api/js/foundation.min.js"></script>
	<script type="text/javascript" src="/data-api/js/controller.js"></script>
	<script type="text/javascript" src="/data-api/js/common.js"></script>
	<script>
	$(document).foundation();
	</script>

	
 </BODY>
</HTML>
