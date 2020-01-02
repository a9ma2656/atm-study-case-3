<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.cdc.atm.web.model.FundTransfer" %>

<html lang="en">

<head>
    <title>ATM - Fund Transfer Screen 4</title>
</head>

<body>
<div style="color: darkred">${errors}</div>
<form:form method="POST" action="/fundTransfer" modelAttribute="<%=FundTransfer.Metadata.MODEL%>">
    <input id="page" name="page" type="hidden" value="4"/>
    <form:hidden path="accountNumber"/>
    <form:hidden path="transferAmount"/>
    <form:hidden path="referenceNumber"/>
    <table>
        <tr>
            <td colspan="3" height="50">&nbsp;</td>
        </tr>
        <tr>
            <td>Destination Account</td>
            <td>:</td>
            <td><form:label path="accountNumber"/></td>
        </tr>
        <tr>
            <td>Transfer Amount</td>
            <td>:</td>
            <td>$<form:label path="transferAmount"/></td>
        </tr>
        <tr>
            <td>Reference Number</td>
            <td>:</td>
            <td><form:label path="referenceNumber"/></td>
        </tr>
    </table>
    <table>
        <tr>
            <td colspan="2" height="50">&nbsp;</td>
        </tr>
        <tr>
            <td colspan="2">1. Confirm Trx</td>
        </tr>
        <tr>
            <td colspan="2">2. Cancel Trx</td>
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