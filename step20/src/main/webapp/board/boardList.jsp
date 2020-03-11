<%@page import="board.bean.BoardDTO"%>
<%@page import="java.util.List"%>
<%@page import="board.dao.BoardDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
#currentpaging {color: blue; text-decoration: underline;}
#paging {color: red; text-decoration: none;}

#subjectA:link {color: black; text-decoration: none;}
#subjectA:visited {color: black; text-decoration: none;}
#subjectA:active {border: black; text-decoration:  none;}
#subjectA:hover {border:  black; text-decoration: underline;}
</style>
<script type="text/javascript">
	function isLogin(seq) {
		 if(session.getAttribute("memId") == null) {
			alert("먼저 로그인 하세요.");
		}else{
			location.href="../board/boardView.do?seq=" + seq
					+"&pg=" + ${pg}; 
		}
	}
</script>
</head>
<body>
<table border="1">
	<tr bgcolor="#ffffcc" align="center">
		<td width="70">글번호</td>
		<td width="200">제목</td>
		<td width="100">작성자</td>
		<td width="100">작성일</td>
		<td width="70">조회수</td>
	</tr>
