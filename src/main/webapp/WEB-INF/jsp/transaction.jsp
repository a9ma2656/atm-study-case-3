<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.cdc.atm.web.model.Transaction" %>

<html lang="en">

<head>
    <title>ATM - Transaction Screen</title>
</head>

<body>
<div style="color: darkred">${errors}</div>
<form:form method="POST" action="/transaction" modelAttribute="<%=Transaction.Metadata.MODEL%>">
    <table>
        <tr>
            <td colspan="2">1. Withdraw</td>
        </tr>
        <tr>
            <td colspan="2">2. Fund Transfer</td>
        </tr>
        <tr>
            <td colspan="2">3. Exit</td>
        </tr>
        <tr>
            <td>Please choose option[3]:</td>
            <td><form:input path="option"/></td>
        </tr>
        <tr>
            <td><input type="submit" value="Submit"/></td>
        </tr>
    </table>
</form:form>
</body>

</html>