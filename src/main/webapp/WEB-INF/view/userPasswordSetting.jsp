<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<%
		if(request.getSession().getAttribute("user") == null) {
			response.sendRedirect("/CoStudy/index.jsp");
		}
	%>
	
	<meta charset="UTF-8">
	<meta content="IE=edge,chrome=1" http-equiv="X-UA-Compatible" />
	<meta name="viewport" content="width=device-width, initial-scale=1">
	
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/semantic-ui@2.4.2/dist/semantic.min.css">
	<link rel="stylesheet" href="/CoStudy/css/authBody.css">
	<link rel="stylesheet" href="/CoStudy/css/footer.css">
	
	<title>코스터디 :: 회원가입</title>
	
	<style>
		#btnLogin {
			margin-right: 0.5em;
		}
	</style>
	
</head>

<body>
	<body id="root">
		<jsp:include page="./component/General/DashboardHeader.jsp"></jsp:include>
    	
    	<jsp:include page="./component/User/UserPasswordSettingBody.jsp"></jsp:include>
    
    	<jsp:include page="./component/General/Footer.jsp"></jsp:include>
	
		
		<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
		<script src="https://cdn.jsdelivr.net/npm/semantic-ui@2.4.2/dist/semantic.min.js"></script>
		<script src="/CoStudy/js/defaultHeader.js"></script>
		<script src="/CoStudy/js/registerBody.js"></script>
	</body>
</html>