<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>部门信息</title>
</head>
<body>

<div>

    <section>
        <header><h2>部门信息</h2></header>
        <ul>
            <li>部门编号： ${dept.deptno}</li>
            <li>部门名称： ${dept.name}</li>
            <li>部门地址： ${dept.location}</li>
        </ul>
    </section>

    <section>
        <header><h2>部门员工信息</h2></header>
        <table>
            <tr>
                <th>员工编号</th>
                <th>员工姓名</th>
                <th>员工工资</th>
            </tr>

            <c:forEach items="${dept.employees}" var="e">
                <tr>
                    <td>${e.empno}</td>
                    <td><a href="/emp?empno=${e.empno}">${e.name}</a></td>
                    <td>${e.salary}</td>
                </tr>
            </c:forEach>
        </table>
    </section>

</div>

</body>
</html>
