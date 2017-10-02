<%
String name = (String) request.getAttribute("name");
String email = (String) request.getAttribute("email");
%>
<div class="container">
    <div class="row">
        <div class="col-lg-12">
            <h3>Microsoft Auth</h3>

            <p>Login Successful</p>

            <div>
                <p>Name: <%=name%> </p>
                <p>Email: <%=email%> </p>
            </div>
            <!--
            <p>AuthCode</p>
            <p>${authCode}"</p>

            <p>ID Token</p>
            <p>${idToken}</p>

            <p>Access Token</p>
            <p>${accessToken}</p>
            -->
        </div>
    </div>
</div>