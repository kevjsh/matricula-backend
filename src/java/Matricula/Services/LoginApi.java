package Matricula.Services;

import Matricula.Excepciones.GlobalException;
import Matricula.Excepciones.NoDataException;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.websocket.EncodeException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import Matricula.Logic.Model;
import Matricula.Logic.User;
import com.google.gson.Gson;
import javax.servlet.http.HttpSession;
import javax.ws.rs.DELETE;
import javax.ws.rs.core.Response;

@Path("/login")
public class LoginApi {

    @Context
    HttpServletRequest request;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(User user) throws GlobalException, NoDataException, IOException, EncodeException {

        User logged = Model.instance().login(user);

        if (logged != null) {

            // To manage sessions
            logged.setPassword("");
            request.getSession(true).setAttribute("User", logged);

            return Response.ok(new Gson().toJson(logged), MediaType.APPLICATION_JSON).build();
            
        }
        
        return Response.status(404).entity(new Gson().toJson("No se encontró el usuario")).build();
        
    }

    @DELETE
    public void logout() {
        HttpSession session = request.getSession(true);
        session.removeAttribute("User");
        session.invalidate();
    }

}
