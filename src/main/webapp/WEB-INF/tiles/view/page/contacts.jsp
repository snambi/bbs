<%@ page import="org.github.snambi.bbs.microsoft.Contact" %>
<%@ page import="org.github.snambi.bbs.microsoft.EmailAddress" %>

<%
Boolean isLoggedIn = (Boolean) request.getAttribute("isLoggedIn");
Contact[] contacts = (Contact[]) request.getAttribute("contacts");

System.out.println("loggedin = "+ isLoggedIn );
System.out.println("contacts = " + contacts.length);
%>


<div class="container">
    <div class="row">
        <div class="col-lg-12">
            <h3>Microsoft Auth</h3>

            <B>Contacts</B>
            <table class="table">
                <thead>
                <tr>
                    <th><span class="glyphicon glyphicon-envelope"></span></th>
                    <th>Nambi</th>
                    <th>Company</th>
                    <th>Email</th>
                </tr>
                </thead>
                <tbody>
                <%for( Contact m : contacts){%>
                <tr class="info">
                    <td>
                        <span class="glyphicon glyphicon-envelope"></span>
                    </td>
                    <td>
                        <%=m.getGivenName()%>
                    </td>
                    <td>
                        <%=m.getSurname()%>
                    </td>
                    <td>
                        <ul>
                            <% for( EmailAddress e : m.getEmailAddresses()){ %>
                            <li><%=e.getAddress()%></li>
                            <%}%>
                        </ul>
                    </td>
                </tr>
                <%}%>
                </tbody>
            </table>
        </div>
    </div>
</div>