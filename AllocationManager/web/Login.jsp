
<html xmlns:p="http://primefaces.prime.com.tr/ui">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <title>AllocationManager - Login</title>
    </head>
    <body style="font-family: Arial, Helvetica, sans-serif;
                 color: #3a4f54;
                 background-color: #dfecf1;
                 font-size: small;display: block;">
        <center style="vertical-align: middle">
            <form name="loginForm" method="POST" 
                  action="/AllocationManager/LoginServlet"
                  style="color: #e33b06;">
                <table>
                    <thead >
                        <tr>
                            <th colspan="3" align="center">Please Login</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td align="left">Username: </td>
                            <td align="left" colspan="2"><input type="text" name="txtUserName" value="" /></td>
                        </tr>
                        <tr>
                            <td align="left">Password:</td>
                            <td align="left" colspan="2"><input type="password" name="txtPassword" value="" /></td>
                        </tr>
                        <tr>
                            <td colspan="3">
                            </td>
                        </tr>
                        <tr>
                            <td></td>
                            <td align="left"><input type="submit" value="Login" /></td>
                            <td align="left"><input type="reset" value="Clear" /></td>
                        </tr>
                    </tbody>
                </table>
            </form>
        </center>
    </body>
</html>
