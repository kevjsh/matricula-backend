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

@ServerEndpoint("/groups")
public class GroupsSocket {

   @OnOpen
    public void onOpen(Session peer) throws IOException, GlobalException, NoDataException, EncodeException {
        Model model = Model.instance();
        model.getGroupSockets().add(peer);
        model.notifyGroupSockets(peer);   
    }

    @OnClose
    public void onClose(Session peer) throws GlobalException, NoDataException, IOException, EncodeException {
        Model model = Model.instance();
        model.getGroupSockets().remove(peer);
    }
    
}
