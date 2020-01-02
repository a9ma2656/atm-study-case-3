<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.cdc.atm.web.model.FundTransfer" %>

<html lang="en">

<head>
    <title>ATM - Fund Transfer Screen 2</title>
</head>

<body>
<div style="color: darkred">${errors}</div>
<form:form method="POST" action="/fundTransfer" modelAttribute="<%=FundTransfer.Metadata.MODEL%>">
    <input id="page" name="page" type="hidden" value="2"/>
    <form:hidden path="accountNumber"/>
    <table>
        <tr>
            <td colspan="2" height="50">&nbsp;</td>
        </tr>
        <tr>
            <td>Please enter transfer amount and
                press enter to continue or
                press enter to go back to Transaction:
            </td>
            <td><form:input path="transferAmount"/></td>
        </tr>
        <tr>
            <td colspan="2">Press enter to continue or </td>
        </tr>
        <tr>
            <td><input type="submit" value="Submit"/></td>
        </tr>
    </table>
</form:form>
</body>

</html>