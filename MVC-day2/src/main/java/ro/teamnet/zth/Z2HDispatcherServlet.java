package ro.teamnet.zth;

import org.codehaus.jackson.map.ObjectMapper;
import ro.teamnet.zth.fmk.MethodAttributes;
import ro.teamnet.zth.fmk.domain.HttpMethod;
import ro.teamnet.zth.utils.BeanDeserializator;
import ro.teamnet.zth.utils.ControllerScanner;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

public class Z2HDispatcherServlet extends HttpServlet {

    private ControllerScanner controllerScanner;

    @Override
    public void init() throws ServletException {
        controllerScanner = new ControllerScanner("ro.teamnet.zth.appl.controller");
        controllerScanner.scan();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        dispatchReply(req, resp, HttpMethod.GET);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        dispatchReply(req, resp, HttpMethod.POST);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        dispatchReply(req, resp, HttpMethod.DELETE);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        dispatchReply(req, resp, HttpMethod.PUT);
    }

    private void dispatchReply(HttpServletRequest req, HttpServletResponse resp, HttpMethod methodType) {
        try {
            Object resultToDisplay = dispatch(req, methodType);
            reply(resp, resultToDisplay);
        } catch (Exception e) {
            try {
                sendExceptionError(e, resp);
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }

    private void sendExceptionError(Exception e, HttpServletResponse resp) throws IOException {
        resp.getWriter().print(e.getMessage());
        resp.getWriter().print(controllerScanner.getAllowedMethods());
    }

    private void reply(HttpServletResponse resp, Object resultToDisplay) {
        // TODO serialize the output(resultToDisplay = controllerinstance.invokeMethod(parameters))
        // TODO into JSON using ObjectMapper (jackson)
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            final String responseAsString = objectMapper.writeValueAsString(resultToDisplay);
            resp.getWriter().print(responseAsString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Object dispatch(HttpServletRequest req, HttpMethod methodType) {
        // TODO to invoke the controller method for the current request and return the controller output
        String pathInfo = req.getPathInfo();
        MethodAttributes attributes = null;

        if (!pathInfo.startsWith("/employees") && !pathInfo.startsWith("/departments") &&
                !pathInfo.startsWith("/jobs") && !pathInfo.startsWith("/locations") )
            throw new RuntimeException("URL-ul nu contine \"/employees\" sau \"/departments\" sau " +
                    "\"/jobs\" sau \"/locations\"");

        attributes = controllerScanner.getMetaData(pathInfo,methodType);

        if (attributes != null) {
            try {
                Object entityClass = attributes.getControllerClass().newInstance();
                Method returnMethod = attributes.getMethod();
                //BeanDeserializator deserializator = new BeanDeserializator();
                return returnMethod.invoke(entityClass,BeanDeserializator.getMethodParams(returnMethod, req).toArray());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }


}
