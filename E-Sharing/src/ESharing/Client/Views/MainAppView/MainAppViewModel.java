package ESharing.Client.Views.MainAppView;

import ESharing.Client.Core.ModelFactory;
import ESharing.Client.Model.AppModel.AppOverviewModel;

/**
 * The class in a view model layer contains all functions which are used in the signUp view.
 * @version 1.0
 * @author Group1
 */
public class MainAppViewModel{

    private AppOverviewModel model;

    /**
     * A constructor initializes model layer for a user features and all fields
     */
    public MainAppViewModel()
    {
        this.model = ModelFactory.getModelFactory().getAppOverviewModel();
    }


}
