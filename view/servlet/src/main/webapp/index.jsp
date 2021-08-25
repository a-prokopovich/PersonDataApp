<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<head>
    <meta charset="UTF-8">
    <title>Person Data</title>
</head>
<body>
    <h2 class="addInf">Get Person data by URL</h2>
    <form action="data" method="POST" autocomplete="off">
        <p>Enter URL: <input type="text" name="url"/></p>
        <input type="submit" value="SEND"/>
    </form>
    <br>
    <div> <if ${id != null}>
        <h3>Person details: </h3>
        <p><b>&emsp;ID</b>: ${id}</p>
        <p><b>&emsp;Full name</b>: ${fullName}</p>
        <p><b>&emsp;Phone</b>: ${phone}</p>
        <p><b>&emsp;Email</b>: ${email}</p>
    </if> </div>
    <br>
    <div> <p>${message}</p> </div>
</body>
</html>