<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="/css/common.css">
<script type="text/javascript" src="/js/lib/jquery-3.7.1.js"></script>
</head>
<body>

    <div id="body-wrapper">
        <div id="header">
            Header 영역입니다.
            <jsp:include page="../member/membermenu.jsp" />
        </div>
        
        <jsp:include page="./menu.jsp" />
        
        <div id="content-wrapper">
