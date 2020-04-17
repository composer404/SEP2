package ESharing.Server.Persistance;

import ESharing.Shared.TransferedObject.Address;
import ESharing.Shared.TransferedObject.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO
{

  private static UserDAOImpl instance;
  private AddressDAO addressDAO;

  //ALTER TABLE user ADD CONSTRAINT unique_username UNIQUE (username);
  private UserDAOImpl(AddressDAO addressDAO)
  {
    try
    {
      DriverManager.registerDriver(new org.postgresql.Driver());
      this.addressDAO = addressDAO;
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
  }

  public static synchronized UserDAOImpl getInstance(AddressDAO addressDAO)
  {
    if (instance == null)
    {
      instance = new UserDAOImpl(addressDAO);
    }
    return instance;
  }

  private Connection getConnection() throws SQLException
  {
    return DriverManager.getConnection(
        "jdbc:postgresql://localhost:5432/sep2",
        "postgres", "password");
  }

  @Override public boolean create(User user)
  {
    System.out.println("Trying to establish connection");
    try (Connection connection = getConnection())
    {
      System.out.println("Connection established");
      PreparedStatement statement = connection.prepareStatement(
          "INSERT INTO user_account (username,password,phoneNumber,address_id) VALUES (?,?,?,?) "
              + "ON CONFLICT ON CONSTRAINT unique_username DO NOTHING;");
      statement.setString(1, user.getUsername());
      statement.setString(2, user.getPassword());
      statement.setString(3, user.getPhoneNumber());
      statement.setInt(4, addressDAO.create(user.getAddress()));
      int affectedRows = statement.executeUpdate();
      System.out.println("Affected rows by create user: " + affectedRows);

      if (affectedRows == 1)
      {
//        Statement statementForLastValue = connection.createStatement();
//        ResultSet resultSet = statementForLastValue.executeQuery("SELECT last_value FROM user_account_user_id_seq;");
//        if (resultSet.next())
//        {
//          user.setUser_id(resultSet.getInt("last_value"));
//        }
        return true;
      }
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    return false;
  }

  @Override public User readById(int user_id)
  {
    try(Connection connection = getConnection())
    {
      PreparedStatement statement = connection.prepareStatement("SELECT * FROM user_account NATURAL JOIN address WHERE user_id= ?;");
      statement.setInt(1, user_id);
      ResultSet resultSet = statement.executeQuery();
      if (resultSet.next())
      {
        String username= resultSet.getString("username");
        String password= resultSet.getString("password");
        String phoneNumber = resultSet.getString(" phoneNumber");
        int address_id = resultSet.getInt("address_id");
        String street = resultSet.getString("street");
        String postcode = resultSet.getString("postcode");
        String number = resultSet.getString("number");
        String city = resultSet.getString("city");
        Address address = new Address(street, number,city,postcode);
        address.setAddress_id(address_id);
        User user = new User(username,password,phoneNumber,address);
        user.setUser_id(user_id);
        return user;
      }
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    return null;
  }

  @Override public User readByUserNameAndPassword(String usernameRequest, String passwordRequest)
  {
    try (Connection connection = getConnection())
    {
      PreparedStatement statement = connection.prepareStatement(
          "SELECT * FROM user_account NATURAL JOIN address WHERE username = ? AND password = ?;");
      statement.setString(1, usernameRequest);
      statement.setString(2, passwordRequest);
      ResultSet resultSet = statement.executeQuery();
      if (resultSet.next())
      {
        int user_id = resultSet.getInt("user_id");
        String username= resultSet.getString("username");
        String password= resultSet.getString("password");
        String phoneNumber = resultSet.getString("phoneNumber");
        int address_id = resultSet.getInt("address_id");
        String street = resultSet.getString("street");
        String postcode =resultSet.getString("postcode");
        String number = resultSet.getString("number");
        String city = resultSet.getString("city");
        Address address = new Address(street,number,city,postcode);
        address.setAddress_id(address_id);
        User user = new User(username,password,phoneNumber,address);
        user.setUser_id(user_id);
        return user;
      }
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    return null;
  }

  @Override public boolean update(User user)
  {
    try (Connection connection = getConnection())
    {
      PreparedStatement statement = connection.prepareStatement(
          "UPDATE user_account SET  username = ?, password = ?, phoneNumber=? , address =? WHERE user_id=?;");
      statement.setString(1, user.getUsername());
      statement.setString(2, user.getPassword());
      statement.setString(3, user.getPhoneNumber());
      statement.setInt(4, addressDAO.create(user.getAddress()));
      statement.setInt(5, user.getUser_id());
      int affectedRows = statement.executeUpdate();

      if (affectedRows == 1)
        return true;
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    return false;
  }

  @Override public boolean delete(User user)
  {
    try (Connection connection = getConnection())
    {
      PreparedStatement statement = connection.prepareStatement("DELETE FROM user_account WHERE user_id = ?;");
      statement.setInt(1, user.getUser_id());
      int noUsersLivingAtAddress = getNoUsersLivingAtAddress(user.getAddress().getAddress_id());
      int affectedRows = statement.executeUpdate();
      if (noUsersLivingAtAddress == 1)
        addressDAO.delete(user.getAddress().getAddress_id());

      if (affectedRows == 1)
        return true;
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    return false;
  }

  private int getNoUsersLivingAtAddress (int address_id)
  {
    try (Connection connection = getConnection())
    {
      Statement statement = connection.createStatement();
      ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) AS count FROM user_account WHERE address_id = " + address_id + ";");

      if (resultSet.next())
        return resultSet.getInt("count");
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    return 0;
  }
}
