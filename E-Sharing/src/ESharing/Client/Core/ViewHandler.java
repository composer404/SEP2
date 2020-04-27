package ESharing.Client.Core;

import ESharing.Client.Views.ViewController;
import ESharing.Client.Views.ViewControllerFactory;
import ESharing.Shared.Util.Views;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * The class responsible for managing views
 * @version 1.0
 * @author Group1
 */
public class ViewHandler {

    private Stage currentStage;
    private Scene currentScene;
    private String css;
    private ViewController viewController;

    private double xOffset;
    private double yOffset;

    private static ViewHandler viewHandler;

    /**
     * A constructor initializes fields
     */
    private ViewHandler()
    {
        xOffset = 0;
        yOffset = 0;
        css = this.getClass().getResource("../../Addition/Styles/Styles.css").toExternalForm();
    }

    /**
     * Returns ViewHandler object if it exists, otherwise creates new object
     * @return the ViewHandler object
     */
    public static ViewHandler getViewHandler()
    {
        if(viewHandler == null)
            viewHandler = new ViewHandler();
        return viewHandler;
    }

    /**
     * Sets main stage and opens default view
     */
    public void start()
    {
        currentStage = new Stage();
        if(currentStage.getScene() == null) currentStage.initStyle(StageStyle.TRANSPARENT);
        openWelcomeView();
    }

    /**
     * Opens welcome view
     */
    public void openWelcomeView()
    {
        viewController = ViewControllerFactory.getViewController(Views.WELCOME_VIEW);
        showView(viewController,null);
    }

    /**
     * Loads signIn view to existing pane in the welcome view
     * @param existingPane the existing pane where signIn view will be loaded
     */
    public void openSignInView(Pane existingPane)
    {
        viewController = ViewControllerFactory.getViewController(Views.SIGN_IN_VIEW);
        showView(viewController, existingPane);
    }

    /**
     * Loads signUp view to existing pane in the welcome view
     * @param existingPane the existing pane where signUp view will be loaded
     */
    public void openSignUpView(Pane existingPane)
    {
        viewController = ViewControllerFactory.getViewController(Views.SIGN_UP_VIEW);
        showView(viewController, existingPane);
    }

    /**
     * Opens the the main user setting view
     */
    public void openMainSettingView()
    {
        viewController = ViewControllerFactory.getViewController(Views.MAIN_USER_SETTING_VIEW);
        showView(viewController, null);
    }

    /**
     * Opens the main application view
     */
    public void openMainAppView()
    {
        viewController = ViewControllerFactory.getViewController(Views.MAIN_APP_VIEW);
        showView(viewController, null);
    }

    /**
     * Opens a view with user information settings
     * @param existingPane the already loaded pane where the view will be displayed
     */
    public void openUserInfoSettingView(Pane existingPane)
    {
        viewController = ViewControllerFactory.getViewController(Views.USER_INFO_SETTING_VIEW);
        showView(viewController, existingPane);
    }

    /**
     * Opens a view with user address settings
     * @param existingPane the already loaded pane where the view will be displayed
     */
    public void openUserAddressSettingView(Pane existingPane)
    {
        viewController = ViewControllerFactory.getViewController(Views.USER_ADDRESS_SETTING_VIEW);
        showView(viewController, existingPane);
    }

    /**
     * Shows views in a current scene or already loaded pane, using the ViewController class
     * @param controller the ViewController class which inheritances all view controllers
     * @param existingPane the already loaded pane where a new view can be loaded
     */
    private void showView(ViewController controller, Pane existingPane)
    {
        if(existingPane == null) {
                moveWindowEvents(controller.getRoot());
                if (currentScene == null)  currentScene = new Scene(controller.getRoot());
                currentScene.setRoot(controller.getRoot());
                currentScene.setFill(Color.TRANSPARENT);
                currentScene.getStylesheets().add(css);
                if (currentStage == null) currentStage = new Stage();
                currentStage.setScene(currentScene);
                currentStage.show();
        }
        else {
                existingPane.getChildren().clear();
                existingPane.getChildren().addAll(controller.getRoot());
                System.out.println("Root : " + controller.getRoot());
        }
    }

    /**
     * Adds options to click and drag the main stage
     * @param root the parent which will be owner of click and drag actions
     */
    private void moveWindowEvents(Parent root)
    {
        root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                currentStage.setX(event.getScreenX() - xOffset);
                currentStage.setY(event.getScreenY() - yOffset);
            }
        });
    }
}
