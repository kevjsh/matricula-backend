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


@ServerEndpoint("/users")
public class UsersSocket {

    @OnOpen
    public void onOpen(Session peer) throws IOException, GlobalException, NoDataException, EncodeException {
        Model model = Model.instance();
        model.getUserSockets().add(peer);
        model.notifyUserSockets(peer);   
    }

    @OnClose
    public void onClose(Session peer) throws GlobalException, NoDataException, IOException, EncodeException {
        Model model = Model.instance();
        model.getUserSockets().remove(peer);
    }
    
}
