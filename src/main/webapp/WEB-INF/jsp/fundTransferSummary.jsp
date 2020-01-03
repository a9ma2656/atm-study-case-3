<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.cdc.atm.web.model.FundTransferSummary" %>

<html lang="en">

<head>
    <title>ATM - Fund Transfer Summary Screen</title>
    <link href="styles/atm.css" rel="stylesheet" type="text/css">
</head>

<body>
<div class="error-text">${errors}</div>
<form:form method="POST" action="/fundTransferSummary" modelAttribute="<%=FundTransferSummary.Metadata.MODEL%>">
    <table class="table-form">
        <tr>
            <td colspan="3">Fund Transfer Summary</td>
        </tr>
        <tr>
            <td>Destination Account</td>
            <td>:</td>
            <td><form:input path="accountNumber" readonly="true"
                            style="border: none; background-color: transparent;"/></td>
        </tr>
        <tr>
            <td>Transfer Amount</td>
            <td>:</td>
            <td>$<form:input path="transferAmount" readonly="true"
                             style="border: none; background-color: transparent;"/></td>
        </tr>
        <tr>
            <td>Reference Number</td>
            <td>:</td>
            <td><form:input path="referenceNumber" readonly="true"
                            style="border: none; background-color: transparent;"/></td>
        </tr>
        <tr>
            <td>Balance</td>
            <td>:</td>
            <td>$<form:input path="balance" readonly="true" style="border: none; background-color: transparent;"/></td>
        </tr>
        <tr>
            <td colspan="3">&nbsp;</td>
        </tr>
        <tr>
            <td colspan="3">1. Transaction</td>
        </tr>
        <tr>
            <td colspan="3">2. Exit</td>
        </tr>
        <tr>
            <td>Choose option[2]:</td>
            <td colspan="2"><form:input path="option"/></td>
        </tr>
        <tr>
            <td colspan="3"><input type="submit" value="Submit"/></td>
        </tr>
    </table>
</form:form>
</body>

</html>