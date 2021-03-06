<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.cdc.atm.web.model.Welcome" %>

<html lang="en">

<head>
    <title>ATM - Welcome Screen</title>
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
<form:form method="POST" action="/welcome" modelAttribute="<%=Welcome.Metadata.MODEL%>">
    <table class="table-form">
        <tr>
            <td>Enter Account Number:</td>
            <td><form:input path="accountNumber"/></td>
        </tr>
        <tr>
            <td>Enter PIN:</td>
            <td><form:input path="pin"/></td>
        </tr>
        <tr>
            <td colspan="2"><input type="submit" value="Submit" id="submit"/></td>
        </tr>
    </table>
</form:form>
</body>

</html>