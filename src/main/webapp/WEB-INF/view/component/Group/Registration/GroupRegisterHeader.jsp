<%@page import="java.net.URLEncoder"%>
<%@page import="cst.dto.BoardDTO"%>
<%@page import="cst.dto.UserDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html >

<%
	String groupName = null;

	if(request.getParameter("groupname") != null) {
		groupName = request.getParameter("groupname");
		
		
	}
%>

<head>
<title>BbsBody</title>
</head>
	<body>
		<div class="ui  container">
				<br />
				<br />
				<br />
				<br />
				<br />
				
				<div class="ui secondary pointing menu">
					<a class="item" href="/CoStudy/group/view.do?groupname=<%= URLEncoder.encode(groupName, "UTF-8") %>">
		 				그룹 정보
					</a>
		  		
			  		<a class="item" href="/CoStudy/group/bbs/list.do?groupname=<%= URLEncoder.encode(groupName, "UTF-8") %>">
			    		그룹 게시판
			  		</a>
			  		
			  		<a class="item" href="/CoStudy/group/modify.do?groupname=<%= URLEncoder.encode(groupName, "UTF-8") %>">
			    		그룹 정보 수정
			  		</a>
			  		
			  		<a class="item active" href="/CoStudy/group/accept.do?groupname=<%= URLEncoder.encode(groupName, "UTF-8") %>">
			    		가입 승인
			  		</a>
				</div>
		</div>
    </body>
		
