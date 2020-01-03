<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.cdc.atm.web.model.TransactionHistory" %>

<html lang="en">

<head>
    <title>ATM - Transaction History Screen</title>
    <link href="styles/atm.css" rel="stylesheet" type="text/css">
    <script src="scripts/jquery.js" lang="javascript"></script>
    <script src="scripts/atm.js" lang="javascript"></script>
    <script type="text/javascript">
        $(document).ready(function () {
            setFocusById(["submit"]);
        });
    </script>
</head>

<body>
<div class="error-text">${errors}</div>
<form:form method="POST" action="/transactionHistory" modelAttribute="<%=TransactionHistory.Metadata.MODEL%>">
    <table class="table-form">
        <tr>
            <td colspan="2">1. Last 10 transactions</td>
        </tr>
        <tr>
            <td colspan="2">2. Today transactions</td>
        </tr>
        <tr>
            <td colspan="2">3. To Transaction screen</td>
        </tr>
        <tr>
            <td>Please choose option[3]:</td>
            <td><form:input path="option"/></td>
        </tr>
        <tr>
            <td colspan="2"><input type="submit" value="Run" id="submit"/></td>
        </tr>
    </table>
</form:form>
</body>

</html>