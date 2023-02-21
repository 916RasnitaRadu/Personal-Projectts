package view.gui;

import controller.Controller;
import exceptions.InterpreterException;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import model.adts.MyDictionary;
import model.adts.MyList;
import model.statements.IStatement;
import model.types.Type;
import repository.IRepository;
import repository.Repository;
import view.Examples;

import java.util.List;
import java.util.ResourceBundle;
import java.net.URL;
public class SelectWindowController implements Initializable {
    @FXML
    private Button selectButton;

    @FXML
    private ListView<IStatement> selectItemListView;

    private MainWindowController mainWindowController;

    public MainWindowController getMainWindowController() {
        return mainWindowController;
    }

    public void setMainWindowController(MainWindowController mainWindowController) {
        this.mainWindowController = mainWindowController;
    }

    @FXML
    private IStatement selectExample(ActionEvent actionEvent)
    {
        return selectItemListView.getItems().get(selectItemListView.getSelectionModel().getSelectedIndex());
    }

    private void displayExamples()
    {
        List<IStatement> examples = Examples.initExamples();
        selectItemListView.setItems(FXCollections.observableArrayList(examples));
        selectItemListView.getSelectionModel().select(0);

        selectButton.setOnAction(actionEvent -> {
            int index = selectItemListView.getSelectionModel().getSelectedIndex();
            IStatement selectedProgram = selectItemListView.getItems().get(index);

            IRepository repository = new Repository("log" + index + ".txt");
            Controller controller = new Controller(repository);
            controller.add(selectedProgram);
            try {
                selectedProgram.typeCheck(new MyDictionary<String, Type>());
                mainWindowController.setController(controller);
            }
            catch (InterpreterException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
                alert.show();
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        displayExamples();
    }
}
