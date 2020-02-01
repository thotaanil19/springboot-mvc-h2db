<%@page import="com.springboot.api.util.Util" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<div class="row">
	<div class="large-12 medium-12 columns header">
		<div class="logo-text small-12">
			<img src="/data-api/images/e-logo.png">
			Springboot API
		</div>
		
			<%
				boolean isUserLoggedIn = Util.isUserAuthenticated();
				if(isUserLoggedIn) {
					%><a class="right login-text logout-pg" id="logout" href="/data-api/login/logout" base_access_limit="<spring:eval expression="@dataSalesApiProperty.getCustomerBaseAccessLimit()"/>" login_role="<%=Util.getLoggedUserRole()%>" login_id="<%=Util.getLoggedUserId()%>">Logout</a><%
				} else { 
					%><div class="right login-text"><a href="login/formlogin">Login</a></div><%
				}
			%>
	
	</div>
</div>
<script type="text/javascript" src="/data-api/js/jquery-1.11.3.min.js"></script>
  <script>
	 if(window.location.href.indexOf("login") >= 0){
		if($('.login-text').hasClass('logout-pg')){
			var userRole = $('.login-text').attr('login_role');
			if(userRole == "CUSTOMER"){
				location.href = "/data-api/customer/dashboard"
			}else{
				location.href = "/data-api/admin/dashboard"
			}
		}
	}
 </script>