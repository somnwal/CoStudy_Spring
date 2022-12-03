<%@page import="java.net.URLDecoder"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="java.util.ArrayList"%>
<%@page import="cst.dto.BoardDTO"%>
<%@page import="cst.dto.UserDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html >

<%
	BoardDTO board = null;
	UserDTO user = null;

	String groupName = request.getParameter("groupname");
	ArrayList<String> registered = null;
	
	if(session.getAttribute("user") != null) {
		user = (UserDTO) session.getAttribute("user");
	}
	
	if(request.getAttribute("registered") != null) {
		registered = (ArrayList<String>) request.getAttribute("registered");
	}
%>

<head>
<title>DashboardBody</title>
</head>
	<body>
           	<div id="groupRegistered">
           		<br />
              	
              	<h3 class="ui horizontal divider header">
					<i class="book icon"></i> 가입한 그룹
              	</h3>
              	
              	<div class="ui red segments">

           		<%
           			if(registered != null) {
           				if(registered.size() != 0) {
           					for(String group : registered) {
           						String name = URLDecoder.decode(group, "UTF-8");
           		%>
	           				<div class="ui black segment">
		            			<div class="ui right aligned grid">
	  								<div class="left aligned eleven wide column">
	    								<pre><i class="user icon"> <%= name %></i></pre>
									</div>
									
									<div class="five wide column"">
    										<a class="ui blue button" href="/CoStudy/group/view.do?groupname=<%= URLEncoder.encode(group, "UTF-8") %>">바로가기</a>
	  								</div>
		  						</div>
		            		</div>
           		<%
           					}
           				} else {
           		%>
           		
           				<div class="ui black segment">
		            			<div class="ui center aligned grid">
	  								<div class="column">
	  									가입한 그룹이 없습니다.
	  								</div>
		  						</div>
		            	</div>
           		
           		<%
           				}
           			} else {
          			%>
          					<div class="ui black segment">
		            			<div class="ui center aligned grid">
	  								<div class="column">
	  									가입한 그룹이 없습니다.
	  								</div>
		  						</div>
		            	</div>
          			<%
           			}
          			%>
           	</div>
    	</div>
    </body>
		
