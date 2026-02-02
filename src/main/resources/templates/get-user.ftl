<#import "macros/user-macros.ftl" as um>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>User info</title>
</head>
<body>
<h1>User information:</h1>
<@um.around value1="Before user macros" value2="After user macros">
    <@um.userMacros user/>
</@um.around>
</body>
</html>