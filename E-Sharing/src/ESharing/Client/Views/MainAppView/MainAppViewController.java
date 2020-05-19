package ESharing.Client.Views.MainAppView;

import ESharing.Client.Core.ModelFactory;
import ESharing.Client.Core.ViewHandler;
import ESharing.Client.Core.ViewModelFactory;
import ESharing.Client.Model.ChatModel.ChatModel;
import ESharing.Client.Model.UserActions.LoggedUser;
import ESharing.Client.Views.ViewController;
import ESharing.Shared.Util.Events;
import ESharing.Shared.TransferedObject.Message;
import ESharing.Shared.Util.GeneralFunctions;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;

/**
 * The controller class used to manage all functions and components from the fxml file
 * @version 1.0
 * @author Group1
 */
public class MainAppViewController extends ViewController {

    @FXML private Label messageNotification;
    @FXML private Rectangle addAdvertisementRectangle;
    @FXML private Rectangle messageRectangle;
    @FXML private Rectangle settingRectangle;
    @FXML private Pane contentPane;
    private ViewHandler viewHandler;
    private MainAppViewModel mainAppViewModel;
    private ChatModel chatModel;
    private LoggedUser loggerUser;

    /**
     * Initializes controller with all components
     */
    public void init()
    {
        this.viewHandler = ViewHandler.getViewHandler();
        this.mainAppViewModel = ViewModelFactory.getViewModelFactory().getMainAppViewModel();
        this.chatModel = ModelFactory.getModelFactory().getChatModel();
        this.loggerUser = LoggedUser.getLoggedUser();

        messageNotification.textProperty().bind(mainAppViewModel.getNotificationProperty());
        hideNavigateRectangles();
        mainAppViewModel.loadNotifications();

        mainAppViewModel.addPropertyChangeListener(Events.USER_LOGOUT.toString(), this::onAdminRemoveAccount);
    }

    /**
     * Opens a user setting view
     */
    public void onSettingButton()
    {
        viewHandler.openMainSettingView(contentPane);
        hideNavigateRectangles();
        settingRectangle.setVisible(true);
        GeneralFunctions.fadeNode("FadeIn", settingRectangle, 500);
        LoggedUser.getLoggedUser().setCurrentOpenConversation(new ArrayList<>());
    }

    /**
     * Logs out current user and opens a welcome view
     */
    public void onLogout() {
        mainAppViewModel.userLoggedOut();
        viewHandler.openWelcomeView();
    }

    public void onChatButton()
    {
        viewHandler.openChatView(contentPane);
        hideNavigateRectangles();
        messageRectangle.setVisible(true);
        GeneralFunctions.fadeNode("FadeIn", messageRectangle, 500);
    }

    private void onAdminRemoveAccount(PropertyChangeEvent propertyChangeEvent) {
        viewHandler.openWelcomeView();
    }

    private void hideNavigateRectangles() {
        messageRectangle.setVisible(false);
        settingRectangle.setVisible(false);
        addAdvertisementRectangle.setVisible(false);
    }

    public void onMinimizeAction() {
        viewHandler.minimizeWindow();
    }

    public void onCloseButtonAction() {
        mainAppViewModel.userLoggedOut();
        System.exit(0);
    }

    public void onGoToAddAdvertisement() {
        viewHandler.openAddAdvertisementView(contentPane);
        hideNavigateRectangles();
        addAdvertisementRectangle.setVisible(true);
        GeneralFunctions.fadeNode("FadeIn", addAdvertisementRectangle, 500);
    }
}
