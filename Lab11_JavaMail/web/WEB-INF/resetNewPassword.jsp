<%-- 
    Document   : resetNewPassword
    Created on : Nov 26, 2021, 8:32:20 AM
    Author     : rmjba
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Reset Password</title>
    </head>
    <body>
        <h1>Enter a new password</h1>
        <form action="reset" method="post">
            <input type="password" name="newPassword" value=""><br>
            <input type="hidden" name="resetUUID" value="">
            <input type="submit" value="Submit"/>
        </form>
    </body>
</html>
