package Matricula.Services;

import Matricula.Excepciones.GlobalException;
import Matricula.Excepciones.NoDataException;
import Matricula.Logic.Career;
import Matricula.Logic.Model;
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


@Path("/careers")
public class CareersApi {
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveCareer(Career career) throws GlobalException, NoDataException, IOException, EncodeException {
        
        try {
            Model model = Model.instance();
            model.saveCareer(career);
            model.notifyCareerSockets(null);
            
            return Response.ok().build();
            
        } catch (Exception e) {
            return Response.status(404).entity(new Gson().toJson("Error insertando el curso")).build();
        }
    }
    
    @DELETE
    @Path("{id}")
    public void delete(@PathParam("id") int id) throws GlobalException, NoDataException, IOException, EncodeException{
        Model model = Model.instance();
        model.deleteCareer(id);
        model.notifyCareerSockets(null);
    }
}
