package view.gui;

import controller.Controller;
import exceptions.InterpreterException;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.adts.IStack;
import model.programState.ProgramState;
import model.statements.IStatement;
import model.values.Value;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class MainWindowController implements Initializable {
    private Controller controller;
    @FXML
    private Label programStatesCount;
    @FXML
    private Button execButton;
    @FXML
    private ListView<String> executionStackView;
    @FXML
    private TableView<Map.Entry<String, Value>> symbolTableView;
    @FXML
    private TableColumn<Map.Entry<String, Value>, String> symbolTableNames;
    @FXML
    private TableColumn<Map.Entry<String, Value>, String> symbolTableValues;
    @FXML
    private ListView<String> outputView;
    @FXML
    private ListView<String> fileTableView;
    @FXML
    private TableView<Map.Entry<Integer, Value>> heapTableView;
    @FXML
    private TableColumn<Map.Entry<Integer,Value>,Integer> heapTableAddresses;
    @FXML
    private TableColumn<Map.Entry<Integer,Value>, String> heapTableValues;
    @FXML
    private ListView<Integer> programIdentifiersView;

    public Controller getController() {
        return controller;
    }
    public void setController(Controller controller) {
        this.controller = controller;
        populateProgramStatesCount();
        populateProgramIdentifiersView();
        execButton.setDisable(false);
    }

    private ProgramState getSelectedProgramState()
    {
        if (programIdentifiersView.getSelectionModel().getSelectedIndex() == -1) return null;

        Integer id = programIdentifiersView.getSelectionModel().getSelectedItem();
        return controller.getRepository().getProgramWithId(id);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        this.controller = null;
        heapTableAddresses.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getKey()).asObject());
        heapTableValues.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getValue() + " "));

        symbolTableNames.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getKey() + " "));
        symbolTableNames.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getValue() + " "));

        programIdentifiersView.setOnMouseClicked(mouseEvent -> changeProgramStateHandler(getSelectedProgramState()));
        execButton.setDisable(true);
    }

    private void changeProgramStateHandler(ProgramState programState) {
        if (programState == null) return;
        populateProgramStatesCount();
        populateProgramIdentifiersView();
        populateHeapTable(programState);
        populateFileTableView(programState);
        populateOutputView(programState);
        populateExecutionStack(programState);
        populateSymbolTableView(programState);
    }

    public void oneStepHandler(ActionEvent actionEvent)
    {
        if (controller == null)
        {
            Alert error = new Alert(Alert.AlertType.ERROR, "No program selected!");
            error.show();
            execButton.setDisable(true);
            return;
        }
        ProgramState programState = getSelectedProgramState();
        if (programState != null && programState.isCompleted())
        {
            Alert error = new Alert(Alert.AlertType.ERROR, "Nothing left to execute!");
            error.show();
            return;
        }
        try {
            List<ProgramState> prgList = controller.getRepository().getProgramList();
            controller.oneStepForAllPrograms(prgList);
            changeProgramStateHandler(programState);
            if (controller.getRepository().getProgramList().size() == 0) execButton.setDisable(true);
        }
        catch (Exception e)
        {
            Alert error = new Alert(Alert.AlertType.ERROR, e.getMessage());
            error.show();
            execButton.setDisable(true);
        }
    }
    private void populateProgramStatesCount() {
        programStatesCount.setText("The number of Program States: " + controller.getRepository().getProgramList().size());
    }

    private void populateProgramIdentifiersView()
    {
        List<Integer> idArray = controller.getRepository().getProgramList().stream().map(ProgramState::getId).collect(Collectors.toList());
        programIdentifiersView.setItems(FXCollections.observableList(idArray));
        programIdentifiersView.refresh();
    }

    private void populateExecutionStack(ProgramState programState)
    {
        IStack<IStatement> stack = programState.getExecutionStack();
        List<String> stackOutput = new ArrayList<>();
        for (IStatement statement : stack.get_stack())
        {
            stackOutput.add(statement.toString());
        }
        Collections.reverse(stackOutput);
        executionStackView.setItems(FXCollections.observableArrayList(stackOutput));
    }

    private void populateSymbolTableView(ProgramState programState)
    {
        symbolTableView.setItems(FXCollections.observableList(new ArrayList<>(programState.getSymbolTable().get_content().entrySet())));
        symbolTableView.refresh();
    }

    private void populateOutputView(ProgramState programState)
    {
        outputView.setItems(FXCollections.observableArrayList(programState.getOutput().getList()));
    }

    private void populateFileTableView(ProgramState programState)
    {
        fileTableView.setItems(FXCollections.observableArrayList(programState.getFileTable().get_key_set()));
    }

    private void populateHeapTable(ProgramState programState)
    {
        heapTableView.setItems(FXCollections.observableList(new ArrayList<>(programState.getHeap().getContent().entrySet())));
        heapTableView.refresh();
    }

}
