package Matricula.Sockets;

import Matricula.Excepciones.GlobalException;
import Matricula.Excepciones.NoDataException;
import Matricula.Logic.Model;
import java.io.IOException;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/enrollments")
public class EnrollmentsSocket {

   @OnOpen
    public void onOpen(Session peer) throws IOException, GlobalException, NoDataException, EncodeException {
        Model model = Model.instance();
        model.getEnrollmentSockets().add(peer);
        model.notifyEnrollmentSockets(peer);   
    }

    @OnClose
    public void onClose(Session peer) throws GlobalException, NoDataException, IOException, EncodeException {
        Model model = Model.instance();
        model.getEnrollmentSockets().remove(peer);
    }
    
}
