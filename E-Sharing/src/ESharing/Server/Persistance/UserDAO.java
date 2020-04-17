package ESharing.Server.Persistance;

import ESharing.Shared.TransferedObject.Address;
import ESharing.Shared.TransferedObject.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDAO
{
  boolean create(User user);
  User readById(int user_id);
  User readByUserNameAndPassword(String searchString, String password);
  boolean update(User user);
  boolean delete(User user);

}