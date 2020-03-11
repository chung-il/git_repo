<%@page import="member.bean.MemberDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	//데이터
	String id = request.getParameter("id");
	String pwd = request.getParameter("pwd");
	//DB
	MemberDAO memberDAO = new MemberDAO();
	String name = memberDAO.login(id,pwd);
	//화면 이동
	if(name==null){
		response.sendRedirect("loginFrom.jsp");
	}else{
		session.setAttribute("memId", id);
		session.setAttribute("memName", name);
		response.sendRedirect("../board/boardList.jsp?=pg1");
	}

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

</body>
</html>