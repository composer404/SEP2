package ESharing.Client.Views.SignInView;

import ESharing.Client.Core.ModelFactory;
import ESharing.Client.Model.UserAccount.UserAccountModel;
import ESharing.Shared.TransferedObject.User;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * The class in a view model layer contains all functions which are used in the signIn view.
 * @version 1.0
 * @author Group1
 */
public class SignInViewModel {

    private StringProperty usernameProperty;
    private StringProperty passwordProperty;
    private StringProperty warningProperty;


    private UserAccountModel userAccountModel;
    private User loginUser;

    /**
     * A constructor initializes model layer for a user features and all fields
     */
    public SignInViewModel() {
        this.userAccountModel = ModelFactory.getModelFactory().getUserAccountModel();
        usernameProperty = new SimpleStringProperty();
        passwordProperty = new SimpleStringProperty();
        warningProperty= new SimpleStringProperty();
    }

    /**
     * Sends a login request to a model layer with values from a gui
     */
    public boolean onLoginButton() {
        loginUser = userAccountModel.onLoginRequest(usernameProperty.get(), passwordProperty.get());
        if(loginUser != null) return true;
        else return false;
    }

    /**
     * Verifies all fields used in the login process
     * @return the boolean value with the result of the verification
     */
    public boolean textFieldsVerification()
    {
        boolean verification = true;
        if((passwordProperty.get() == null && usernameProperty.get() == null) || (usernameProperty.get().equals("") && passwordProperty.get().equals(""))) {
            warningProperty.set("Fields are empty!");
            verification = false;
        }
        else if((passwordProperty.get() == null && usernameProperty.get().equals("")) || (usernameProperty.get() == null && passwordProperty.get().equals(""))) {
            warningProperty.set("Fields are empty!");
            verification = false;
        }
        else if(usernameProperty.get() == null || usernameProperty.get().equals("")) {
            warningProperty.set("The username field can not be empty!");
            verification = false;
        }
        else if(passwordProperty.get() == null || passwordProperty.get().equals("")) {
            warningProperty.set("The password field can not be empty!");
            verification = false;
        }
        else if(!onLoginButton()) {
            warningProperty.set("The user does not exist");
            verification = false;
        }
        return verification;
    }

    public User getLoginUser() {
        return loginUser;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the value used in the username text field
     */
    public StringProperty getUsernameProperty() {
        return usernameProperty;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the value used in the password text field
     */
    public StringProperty getPasswordProperty() {
        return passwordProperty;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the value used in the warning notification
     */
    public StringProperty getWarningProperty() {
        return warningProperty;
    }

}
