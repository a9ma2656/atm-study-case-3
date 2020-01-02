<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.cdc.atm.web.model.FundTransfer" %>

<html lang="en">

<head>
    <title>ATM - Fund Transfer Screen 1</title>
</head>

<body>
<div style="color: darkred">${errors}</div>
<form:form method="POST" action="/fundTransfer" modelAttribute="<%=FundTransfer.Metadata.MODEL%>">
    <input id="page" name="page" type="hidden" value="1"/>
    <table>
        <tr>
            <td colspan="2" height="50">&nbsp;</td>
        </tr>
        <tr>
            <td>Please enter destination account and press enter to continue or
                press enter to go back to Transaction:
            </td>
            <td><form:input path="accountNumber"/></td>
        </tr>
        <tr>
            <td><input type="submit" value="Submit"/></td>
        </tr>
    </table>
</form:form>
</body>

</html>