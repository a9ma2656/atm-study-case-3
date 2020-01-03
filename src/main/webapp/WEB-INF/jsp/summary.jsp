<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.cdc.atm.web.model.Summary" %>

<html lang="en">

<head>
    <title>ATM - Summary Screen</title>
    <link href="styles/atm.css" rel="stylesheet" type="text/css">
</head>

<body>
<div class="error-text">${errors}</div>
<form:form method="POST" action="/summary" modelAttribute="<%=Summary.Metadata.MODEL%>">
    <table>
        <tr>
            <td colspan="3" height="50">&nbsp;</td>
        </tr>
        <tr>
            <td colspan="3">Summary</td>
        </tr>
        <tr>
            <td>Date</td>
            <td>:</td>
            <td><form:input path="date" readonly="true" style="border: none; background-color: transparent;"/></td>
        </tr>
        <tr>
            <td>Withdraw</td>
            <td>:</td>
            <td>$<form:input path="withdraw" readonly="true" style="border: none; background-color: transparent;"/></td>
        </tr>
        <tr>
            <td>Balance</td>
            <td>:</td>
            <td>$<form:input path="balance" readonly="true" style="border: none; background-color: transparent;"/></td>
        </tr>
    </table>
    <table>
        <tr>
            <td colspan="2" height="50">&nbsp;</td>
        </tr>
        <tr>
            <td colspan="2">1. Transaction</td>
        </tr>
        <tr>
            <td colspan="2">2. Exit</td>
        </tr>
        <tr>
            <td>Choose option[2]:</td>
            <td><form:input path="option"/></td>
        </tr>
        <tr>
            <td><input type="submit" value="Submit"/></td>
        </tr>
    </table>
</form:form>
</body>

</html>