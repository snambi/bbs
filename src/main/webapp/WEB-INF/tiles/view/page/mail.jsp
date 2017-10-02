<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ page import="org.github.snambi.bbs.microsoft.beans.Message" %>

<%
Boolean isLoggedIn = (Boolean) request.getAttribute("isLoggedIn");
Message[] messages = (Message[]) request.getAttribute("messages");

System.out.println("loggedin = "+ isLoggedIn );
System.out.println("messages = " + messages.length);
%>

<div class=""container>
    <div class="row">
        <div class="col-lg-12">
            <h3>Microsoft Auth</h3>

            <B>Inbox</B>
            <table class="table">
                <thead>
                <tr>
                    <th><span class="glyphicon glyphicon-envelope"></span></th>
                    <th>From</th>
                    <th>Subject</th>
                    <th>Received</th>
                    <th>Preview</th>
                </tr>
                </thead>
                <tbody>
                <%for( Message m : messages){%>
                <tr class="info">
                    <td>
                        <span class="glyphicon glyphicon-envelope"></span>
                    </td>
                    <td>
                        <%=m.getFrom().getEmailAddress().getName()%>
                    </td>
                    <td>
                        <%=m.getSubject()%>
                    </td>
                    <td>
                        <%=m.getReceivedDateTime()%>
                    </td>
                    <td>
                        <%=m.getBodyPreview()%>
                    </td>
                </tr>
                <%}%>
                </tbody>
            </table>
        </div>
    </div>
</div>