package Matricula.Services;

import Matricula.Excepciones.GlobalException;
import Matricula.Excepciones.NoDataException;
import Matricula.Logic.Model;
import Matricula.Logic.User;
import com.google.gson.Gson;
import java.io.IOException;
import javax.websocket.EncodeException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/users")
public class UsersApi {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveUser(User user) throws GlobalException, NoDataException, IOException, EncodeException {

        try {

            Model model = Model.instance();
            model.saveUser(user);
            model.notifyUserSockets(null);

            return Response.ok().build();

        } catch (Exception e) {
            return Response.status(404).entity(new Gson().toJson("Error insertando el usuario")).build();
        }
    }

    @DELETE
    @Path("{id}")
    public void delete(@PathParam("id") int id) throws GlobalException, NoDataException, IOException, EncodeException {
        Model model = Model.instance();
        model.deleteUser(id);
        model.notifyUserSockets(null);
    }
}
