package scratch.webapp.jee.controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * @author Karl Bennett
 */
@Path("scratch")
public class ScratchController {

    @GET
    @Produces("text/plain")
    public String handle() {
        return "scratched";
    }
}
