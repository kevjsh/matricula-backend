package Matricula.Logic;

import Matricula.Excepciones.GlobalException;
import Matricula.Excepciones.NoDataException;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.websocket.EncodeException;
import javax.websocket.Session;

import Matricula.Data.*;
import java.util.ArrayList;

public class Model {

    private static Model uniqueInstance;

    //Connection to database
    private final UsersService users;
    private final CareersService careers;
    private final CiclesService cicles;
    private final CoursesService courses;
    private final GroupsService groups;

    // Socket clients
    private static Set<Session> userSockets;
    private static Set<Session> careerSockets;
    private static Set<Session> cicleSockets;
    private static Set<Session> courseSockets;
    private static Set<Session> groupSockets;

    public static Model instance() throws GlobalException, NoDataException, IOException, EncodeException {
        if (uniqueInstance == null) {
            uniqueInstance = new Model();
        }
        return uniqueInstance;
    }

    private Model() throws GlobalException, NoDataException, IOException, EncodeException {

        //Connection to database
        users = new UsersService();
        careers = new CareersService();
        cicles = new CiclesService();
        courses = new CoursesService();
        groups = new GroupsService();

        // Sockets
        userSockets = Collections.synchronizedSet(new HashSet<Session>());
        careerSockets = Collections.synchronizedSet(new HashSet<Session>());
        cicleSockets = Collections.synchronizedSet(new HashSet<Session>());
        courseSockets = Collections.synchronizedSet(new HashSet<Session>());
        groupSockets = Collections.synchronizedSet(new HashSet<Session>());

    }

    /* Users */
    public Set<Session> getUserSockets() {
        return userSockets;
    }

    public void notifyUserSockets(Session socket) throws IOException, EncodeException, GlobalException, NoDataException {

        ArrayList<User> usersList = users.FindUser(null, null, 0, 1);

        if (socket != null) {
            socket.getBasicRemote().sendObject(new Gson().toJson(usersList));

        } else {
            // Notify all sockets
            for (Session s : userSockets) {
                s.getBasicRemote().sendObject(new Gson().toJson(usersList));
            }
        }
    }

    public User login(User user) throws GlobalException, NoDataException {
        User u = users.FindUser(user.getPersonId(), null, 0, 0).get(0);

        if (u != null && u.getPassword().equals(user.getPassword())) {
            return u;
        }

        return null;
    }

    public void saveUser(User user) throws GlobalException, NoDataException {
        users.saveUser(user);
    }

    public void deleteUser(int id) throws GlobalException, NoDataException {
        users.DeleteUser(id);
    }

    /* ************************************************************************** */
 /* Careers */
    public Set<Session> getCareerSockets() {
        return careerSockets;
    }

    public void notifyCareerSockets(Session socket) throws IOException, EncodeException, GlobalException, NoDataException {

        ArrayList<Career> careersList = careers.FindCareer(null, null, 1);

        if (socket != null) {
            socket.getBasicRemote().sendObject(new Gson().toJson(careersList));

        } else {
            // Notify all sockets
            for (Session s : careerSockets) {
                s.getBasicRemote().sendObject(new Gson().toJson(careersList));
            }
        }
    }

    public void saveCareer(Career career) throws GlobalException, NoDataException {
        careers.saveCareer(career);
    }

    public void deleteCareer(int id) throws GlobalException, NoDataException {
        careers.DeleteCareer(id);
    }

    /* ************************************************************************** */

    /* Cicles */
    public Set<Session> getCicleSockets() {
        return cicleSockets;
    }

    public void notifyCicleSockets(Session socket) throws IOException, EncodeException, GlobalException, NoDataException {

        ArrayList<Cicle> ciclesList = cicles.FindCicle(0, 1);

        if (socket != null) {
            socket.getBasicRemote().sendObject(new Gson().toJson(ciclesList));

        } else {
            // Notify all sockets
            for (Session s : cicleSockets) {
                s.getBasicRemote().sendObject(new Gson().toJson(ciclesList));
            }

        }
    }

    public void saveCicle(Cicle cicle) throws GlobalException, NoDataException {
        cicles.saveCicle(cicle);
    }

    public void deleteCicle(int id) throws GlobalException, NoDataException {
        cicles.DeleteCicle(id);
    }

    /* ************************************************************************** */

    /* Courses */
    public Set<Session> getCoursesSockets() {
        return courseSockets;
    }

    public void notifyCoursesSockets(Session socket) throws IOException, EncodeException, GlobalException, NoDataException {

        ArrayList<Course> coursesList = courses.FindCourse(0, "", "", 0, 1);

        if (socket != null) {
            socket.getBasicRemote().sendObject(new Gson().toJson(coursesList));

        } else {
            // Notify all sockets
            for (Session s : courseSockets) {
                s.getBasicRemote().sendObject(new Gson().toJson(coursesList));
            }

        }
    }

    public void addCourse(Course course) throws GlobalException, NoDataException {
        courses.AddCourse(course);
    }

    public void updateCourse(Course course) throws GlobalException, NoDataException {
        courses.UpdateCourse(course);
    }

    public void deleteCourse(int id) throws GlobalException, NoDataException {
        courses.DeleteCourse(id);
    }
    
    /* ************************************************************************** */

    /* Groups */
    public Set<Session> getGroupSockets() {
        return groupSockets;
    }

    public void notifyGroupSockets(Session socket) throws IOException, EncodeException, GlobalException, NoDataException {

        ArrayList<Group> groupsList = groups.FindGroup(0,1);

        if (socket != null) {
            socket.getBasicRemote().sendObject(new Gson().toJson(groupsList));

        } else {
            // Notify all sockets
            for (Session s : groupSockets) {
                s.getBasicRemote().sendObject(new Gson().toJson(groupsList));
            }
        }
    }
    
    public void saveGroup(Group group) throws GlobalException, NoDataException {
        groups.SaveGroup(group);
    }

    public void deleteGroup(int id) throws GlobalException, NoDataException {
        groups.DeleteGroup(id);
    }

}
