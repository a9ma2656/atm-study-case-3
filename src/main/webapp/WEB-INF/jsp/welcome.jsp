<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.cdc.atm.web.model.Account" %>

<html lang="en">

<head>
    <title>ATM - Welcome Screen</title>
</head>

<body>
<div style="color: darkred">${errors}</div>
<form:form method="POST" action="/welcome" modelAttribute="<%=Account.Metadata.MODEL%>">
    <table>
        <tr>
            <td>Enter Account Number:</td>
            <td><form:input path="accountNumber"/></td>
        </tr>
        <tr>
            <td>Enter PIN:</td>
            <td><form:input path="pin"/></td>
        </tr>
        <tr>
            <td><input type="submit" value="Submit"/></td>
        </tr>
    </table>
</form:form>
</body>

</html>