<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ page import="org.github.snambi.bbs.microsoft.beans.Event" %>

<%
Event[] events = (Event[]) request.getAttribute("meetings");
System.out.println("events = " + events.length);
%>

<div class="container">
    <div class="row">
        <div class="col-lg-12">
            <h3>Microsoft Outlook</h3>

            <B>Meetings</B>
            <table class="table">
                <thead>
                <tr>
                    <th><span class="glyphicon glyphicon-envelope"></span></th>
                    <th>Organizer</th>
                    <th>Subject</th>
                    <th>Start</th>
                    <th>End</th>
                </tr>
                </thead>
                <tbody>
                <%for( Event m : events){%>
                <tr class="info">
                    <td>
                        <span class="glyphicon glyphicon-envelope"></span>
                    </td>
                    <td>
                        <%=m.getOrganizer().getEmailAddress().getName()%>
                    </td>
                    <td>
                        <%=m.getSubject()%>
                    </td>
                    <td>
                        <%=m.getStart().getDateTime()%>
                    </td>
                    <td>
                        <%=m.getEnd().getDateTime()%>
                    </td>
                </tr>
                <%}%>
                </tbody>
            </table>
        </div>
    </div>
</div>