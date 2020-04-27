package ESharing.Client.Views.MainAppView;

import ESharing.Client.Core.ViewHandler;
import ESharing.Client.Core.ViewModelFactory;
import ESharing.Client.Model.UserActions.LoggedUser;
import ESharing.Client.Views.ViewController;

/**
 * The controller class used to manage all functions and components from the fxml file
 * @version 1.0
 * @author Group1
 */
public class MainAppViewController extends ViewController {

    private ViewHandler viewHandler;
    private MainAppViewModel mainAppViewModel;
    private LoggedUser loggerUser;

    /**
     * Initializes controller with all components
     */
    public void init()
    {
        this.viewHandler = ViewHandler.getViewHandler();
        this.mainAppViewModel = ViewModelFactory.getViewModelFactory().getMainAppViewModel();
        this.loggerUser = LoggedUser.getLoggedUser();
        System.out.println(loggerUser.getUser().getUsername());
    }

    /**
     * Opens a user setting view
     */
    public void onSettingButton()
    {
        viewHandler.openMainSettingView();
    }

    /**
     * Logs out current user and opens a welcome view
     */
    public void onLogout() {
        LoggedUser.getLoggedUser().logoutUser();
        viewHandler.openWelcomeView();
    }
}
