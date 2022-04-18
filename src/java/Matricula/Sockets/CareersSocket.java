/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

/**
 *
 * @author Usuario
 */
@ServerEndpoint("/careers")
public class CareersSocket {

    @OnOpen
    public void onOpen(Session peer) throws IOException, GlobalException, NoDataException, EncodeException {
        Model model = Model.instance();
        model.getCareerSockets().add(peer);
        model.notifyCareerSockets(peer);   
    }

    @OnClose
    public void onClose(Session peer) throws GlobalException, NoDataException, IOException, EncodeException {
        Model model = Model.instance();
        model.getCareerSockets().remove(peer);
    }
    
}
