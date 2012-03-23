import com.bleedingwolf.ratpack.RatpackServlet;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.handler.ContextHandler;
import org.mortbay.jetty.servlet.ServletHandler;
import org.mortbay.jetty.servlet.ServletHolder;

public class Main {
    
    private static final String APP_SCRIPT_FILENAME_PARAM_NAME = "app-script-filename";
    
    public static void main(String[] args) throws Exception {
        final String webappDirLocation = "src/main/webapp/";
        final String appScriptFilename = "app.groovy";

        //The port that we should run on can be set into an environment variable
        //Look for that variable and default to 8080 if it isn't there.
        String webPort = System.getenv("PORT");
        if(webPort == null || webPort.isEmpty()) {
            webPort = "8080";
        }

        ServletHolder servletHolder = new ServletHolder(new RatpackServlet());
        servletHolder.setInitParameter(APP_SCRIPT_FILENAME_PARAM_NAME, appScriptFilename);

        ServletHandler servletHandler = new ServletHandler();
        servletHandler.addServletWithMapping(servletHolder, "/*");

        ContextHandler contextHandler = new ContextHandler("/");
        contextHandler.addHandler(servletHandler);
        contextHandler.setResourceBase(webappDirLocation);

        Server server = new Server(Integer.valueOf(webPort));
        server.setHandler(contextHandler);

        server.start();
        server.join();
    }

}
