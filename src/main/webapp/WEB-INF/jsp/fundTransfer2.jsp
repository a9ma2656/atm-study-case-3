<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.cdc.atm.web.model.FundTransfer" %>

<html lang="en">

<head>
    <title>ATM - Fund Transfer Screen 2</title>
    <link href="styles/atm.css" rel="stylesheet" type="text/css">
</head>

<body>
<div class="error-text">${errors}</div>
<form:form method="POST" action="/fundTransfer" modelAttribute="<%=FundTransfer.Metadata.MODEL%>">
    <input id="page" name="page" type="hidden" value="<%=FundTransfer.Page.FUND_TRANSFER_PAGE_2.toString()%>"/>
    <form:hidden path="accountNumber"/>
    <table class="table-form">
        <tr>
            <td>Please enter transfer amount and press enter to continue or press enter to go back to Transaction:</td>
            <td><form:input path="transferAmount"/></td>
        </tr>
        <tr>
            <td colspan="2"><input type="submit" value="Submit"/></td>
        </tr>
    </table>
</form:form>
</body>

</html>