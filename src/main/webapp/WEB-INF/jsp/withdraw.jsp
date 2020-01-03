<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.cdc.atm.web.model.Withdraw" %>

<html lang="en">

<head>
    <title>ATM - Withdraw Screen</title>
    <link href="styles/atm.css" rel="stylesheet" type="text/css">
</head>

<body>
<div class="error-text">${errors}</div>
<form:form method="POST" action="/withdraw" modelAttribute="<%=Withdraw.Metadata.MODEL%>">
    <table class="table-form">
        <tr>
            <td colspan="2">1. $10</td>
        </tr>
        <tr>
            <td colspan="2">2. $50</td>
        </tr>
        <tr>
            <td colspan="2">3. $100</td>
        </tr>
        <tr>
            <td colspan="2">4. Other</td>
        </tr>
        <tr>
            <td colspan="2">5. Back</td>
        </tr>
        <tr>
            <td>Please choose option[5]:</td>
            <td><form:input path="option"/></td>
        </tr>
        <tr>
            <td colspan="2"><input type="submit" value="Submit"/></td>
        </tr>
    </table>
</form:form>
</body>

</html>