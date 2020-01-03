<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.cdc.atm.web.model.FundTransfer" %>

<html lang="en">

<head>
    <title>ATM - Fund Transfer Screen 1</title>
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
<form:form method="POST" action="/fundTransfer" modelAttribute="<%=FundTransfer.Metadata.MODEL%>">
    <input id="page" name="page" type="hidden" value="<%=FundTransfer.Page.FUND_TRANSFER_PAGE_1.toString()%>"/>
    <table class="table-form">
        <tr>
            <td>Please enter destination account and press enter to continue or press enter to go back to Transaction:</td>
            <td><form:input path="accountNumber"/></td>
        </tr>
        <tr>
            <td colspan="2"><input type="submit" value="Submit" id="submit"/></td>
        </tr>
    </table>
</form:form>
</body>

</html>