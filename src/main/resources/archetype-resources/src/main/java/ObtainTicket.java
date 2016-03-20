#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import ${package}.util.LicenseSigner;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;

/**
 *
 */
@WebServlet(value = "/rpc/obtainTicket.action", name = "ObtainTicket")
public class ObtainTicket extends GenericServlet {

    @Override
    public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
        String salt = request.getParameter("salt");
        String userName = request.getParameter("userName");
        String prolongationPeriod = "607875500";
        String respXml = "<ObtainTicketResponse><message>OK</message>" +
                "<prolongationPeriod>" +
                prolongationPeriod +
                "</prolongationPeriod>" +
                "<responseCode>OK</responseCode>" +
                "<salt>" + salt + "</salt>" +
                "<ticketId>1</ticketId>" +
                "<ticketProperties>licensee=" + userName +
                "\tlicenseType=0\t" +
                "</ticketProperties></ObtainTicketResponse>";
        String signXml = LicenseSigner.sign(respXml);
        System.out.println(signXml + respXml);
        response.setContentType("text/xml");
        response.getWriter().print(signXml + respXml);
    }
}
