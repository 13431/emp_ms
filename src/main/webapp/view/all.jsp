<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>员工列表</title>
    <style>
        table, th, td {
            border: 1px solid gray;
            border-collapse: collapse;
            padding: 2px 10px;
        }
        tr:nth-child(odd) {
            background: lightyellow;
        }
    </style>
</head>
<body>

<div>

    <header>
        <h2>所有员工</h2>
    </header>

    <table>
        <tr>
            <th>员工编号</th>
            <th>员工姓名</th>
            <th>员工工资</th>
            <th>员工部门</th>
        </tr>

        <c:forEach items="${emps}" var="e">
            <tr>
                <td>${e.empno}</td>
                <td><a href="/emp?empno=${e.empno}">${e.name}</a></td>
                <td>${e.salary}</td>
                <td><a href="/dept?deptno=${e.department.deptno}">${e.department.name}</a></td>
            </tr>
        </c:forEach>
    </table>

</div>


</body>
</html>
