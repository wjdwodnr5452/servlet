<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="hello.servlet.domain.member.Member" %>
<%@ page import="hello.servlet.domain.member.MemberRepository" %>
<%--
  Created by IntelliJ IDEA.
  User: wjdwo
  Date: 2024-02-17
  Time: 오후 10:46
  To change this template use File | Settings | File Templates.
--%>
<%
    // request, response 사용 가능
    MemberRepository memberRepository = MemberRepository.getInstance();

    String username = request.getParameter("username");
    // request.getParameter는 항상 문자이다.
    int age = Integer.parseInt(request.getParameter("age"));

    Member member = new Member(username, age);
    memberRepository.save(member);
%>
<html>
<head>
    <title>Title</title>
</head>
<body>
성공
<ul>
    <li>id=<%=member.getId()%></li>
    <li>username=<%=member.getUsername()%></li>
    <li>age=<%=member.getAge()%></li>
</ul>
<a href="/index.html">메인</a>

</body>
</html>
