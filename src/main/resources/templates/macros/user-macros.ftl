<#macro userMacros user>
    <#if user.active>
        <p><strong>Id:</strong> ${user.id} </p>
        <p><strong>First name:</strong> ${user.firstName} </p>
        <p><strong>Last name:</strong> ${user.lastName} </p>
        <p><strong>Age:</strong> ${user.age} </p>
        <p><strong>Email:</strong> ${user.email} </p>
        <p><strong>Created:</strong> ${user.created} </p>
        <p><strong>Changed:</strong> ${user.updated} </p>
        <p><strong>Active:</strong> ${user.active} </p>
    <#else>
        User is not active!
    </#if>
</#macro>


<#macro around value1 value2>
    <h1>${value1}</h1>
    <#nested>
    <h1>${value2}</h1>
</#macro>
