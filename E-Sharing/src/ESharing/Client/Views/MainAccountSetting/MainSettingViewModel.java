package ESharing.Client.Views.MainAccountSetting;

import ESharing.Client.Core.ModelFactory;
import ESharing.Client.Model.UserAccount.UserSettingModel;
import ESharing.Shared.TransferedObject.User;

/**
 * The class in a view model layer contains all functions which are used in the signUp view.
 * @version 1.0
 * @author Group1
 */
public class MainSettingViewModel {

    private UserSettingModel userSettingModel;

    /**
     * A constructor initializes model layer for a user features and all fields
     */
    public MainSettingViewModel()
    {
        this.userSettingModel = ModelFactory.getModelFactory().getUserSettingModel();
    }

    /**
     * Sends the remove current logged user request to the view model
     * @param loggedUser the current logged user
     */
    public void removeAccount(User loggedUser) {
            userSettingModel.removeAccount(loggedUser);
    }

}