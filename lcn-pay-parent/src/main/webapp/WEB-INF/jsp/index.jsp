<%--
  Created by IntelliJ IDEA.
  User: dustin
  Date: 2018/3/21
  Time: 21:08
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<html>
<head>
    <link rel="stylesheet" href="/bootstrap/css/bootstrap.css">
    <link rel="stylesheet" href="/bootstrap/css/bootstrap-theme.css">

</head>
<body>


<nav class="navbar navbar-default " role="navigation">
    <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                    data-target="#bs-example-navbar-collapse-1">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
        </div>

    </div>
</nav>

<h3>商品列表</h3>

<style>
    h3 {
        text-align: center;
        color: orange;
    }

</style>


<table class="table">
    <tr>
        <td>产品编号</td>
        <td>产品名称</td>
        <td>产品价格</td>
        <td>操作</td>
    </tr>
    <c:forEach items="${pList }" var="p">
        <tr>
            <td>${p.id }</td>
            <td>${p.name }</td>
            <td>${p.price }</td>
            <td>
                <a class="btn btn-warning"
                   href="<%=request.getContextPath() %>/goConfirm?productId=${p.id }">购买</a>
            </td>
        </tr>
    </c:forEach>
</table>


<nav class="navbar navbar-default navbar-fixed-bottom" role="navigation">
    <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->


        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1" style="background: cornsilk;">

        </div>
    </div>
</nav>


<script src="../../js/jquery-3.2.1.js"></script>
<script src="../../bootstrap/js/bootstrap.js"></script>

<script type="text/javascript">


</script>


</body>
</html>
