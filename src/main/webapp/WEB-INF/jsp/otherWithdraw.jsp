<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.cdc.atm.web.model.OtherWithdraw" %>

<html lang="en">

<head>
    <title>ATM - Other Withdraw Screen</title>
</head>

<body>
<div style="color: darkred">${errors}</div>
<form:form method="POST" action="/otherWithdraw" modelAttribute="<%=OtherWithdraw.Metadata.MODEL%>">
    <table>
        <tr>
            <td colspan="2" height="50">&nbsp;</td>
        </tr>
        <tr>
            <td colspan="2">Other Withdraw</td>
        </tr>
        <tr>
            <td>Enter amount to withdraw:</td>
            <td><form:input path="withdraw"/></td>
        </tr>
        <tr>
            <td><input type="submit" value="Submit"/></td>
        </tr>
    </table>
</form:form>
</body>

</html>