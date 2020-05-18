package ESharing.Server.Networking;

import ESharing.Server.Core.ServerModelFactory;
import ESharing.Server.Model.user.ServerModel;
import ESharing.Shared.Networking.user.RMIClient;
import ESharing.Shared.Networking.user.RMIServer;
import ESharing.Shared.Util.Events;
import ESharing.Shared.TransferedObject.User;
import javafx.scene.image.Image;

import java.beans.PropertyChangeListener;
import java.io.*;
import java.nio.file.Files;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

/**
 * The class handles all requests which are received from clients
 * @version 1.0
 * @author Group 1
 */
public class ServerHandler implements RMIServer
{
  private ServerModel serverModel;
  private PropertyChangeListener listenForNewUser;
  private PropertyChangeListener listenForUserRemoved;
  private PropertyChangeListener listenForUserUpdated;
  private PropertyChangeListener listenForAvatarUpdated;

  /**
   * A constructor initializes fields and starts the internet connection
   * @throws RemoteException if the method invocation fails
   */
  public ServerHandler() throws RemoteException
  {
    UnicastRemoteObject.exportObject(this, 0);
    this.serverModel = ServerModelFactory.getInstance().getServerModel();
  }

  @Override
  public boolean addUser(User user)
  {
    return serverModel.addNewUser(user);
  }

  @Override
  public boolean removeUser(User user)
  {
    return serverModel.removeUser(user);
  }

  @Override
  public boolean editUser(User user)
  {
    return serverModel.editUser(user);
  }

  @Override
  public User loginUser(String username, String password)
  {
    return serverModel.loginUser(username, password);
  }

  @Override
  public void registerAdministratorCallback(RMIClient client) {
    listenForNewUser = evt -> {
      try {
        client.newUserReceived((User) evt.getNewValue());
      } catch (Exception e) {
        e.printStackTrace();
      }

    };
    serverModel.addPropertyChangeListener(Events.NEW_USER_CREATED.toString(), listenForNewUser);
  }

  @Override
  public void registerGeneralCallback(RMIClient client){
    listenForUserRemoved = evt -> {
      try {
        client.userRemoved((User) evt.getNewValue());
      } catch (RemoteException e) {
        e.printStackTrace();
      }
    };

    listenForUserUpdated = evt -> {
      try {
        client.userUpdated((User) evt.getNewValue());
      } catch (RemoteException e) {
        e.printStackTrace();
      }
    };

    listenForAvatarUpdated = evt -> {
      try {
        client.avatarUpdated((byte[]) evt.getNewValue());
      } catch (RemoteException e) {
        e.printStackTrace();
      }
    };

    serverModel.addPropertyChangeListener(Events.USER_REMOVED.toString(), listenForUserRemoved);
    serverModel.addPropertyChangeListener(Events.USER_UPDATED.toString(), listenForUserUpdated);
    serverModel.addPropertyChangeListener(Events.UPDATE_AVATAR.toString(), listenForAvatarUpdated);
  }

  @Override
  public List<User> getAllUsers(){
    return serverModel.getAllUsers();
  }

  @Override
  public void unRegisterUserAsAListener() {

    serverModel.removePropertyChangeListener(Events.USER_REMOVED.toString(), listenForUserRemoved);
    serverModel.removePropertyChangeListener(Events.USER_UPDATED.toString(), listenForUserUpdated);
    serverModel.removePropertyChangeListener(Events.NEW_USER_CREATED.toString(), listenForNewUser);
    serverModel.removePropertyChangeListener(Events.UPDATE_AVATAR.toString(), listenForAvatarUpdated);

    serverModel.listeners();
  }

  @Override
  public void changeUserAvatar(byte[] avatarImage, int userId){
    serverModel.changeUserAvatar(avatarImage, userId);
  }
}
