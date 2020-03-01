package gui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.entities.Department;
import model.services.DepartmentService;

public class DepartmentListController implements Initializable {

	@FXML
	private DepartmentService service; // dependencia

	@FXML
	private TableView<Department> tableViewDepartment;

	@FXML
	private TableColumn<Department, Integer> tableColumnId;

	@FXML
	private TableColumn<Department, String> tableColumnName;

	@FXML
	private Button btnew;

	@FXML
	public void onBtNewAction() {
		System.out.println("onBtNewAction");
	}

	private ObservableList<Department> obsList;//carregar o departamento dentro dessa lista

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}

	public void setDepartmentService(DepartmentService service) {
		this.service = service;
	}

	private void initializeNodes() {
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));// inicia o comportamento das columans
		tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));

		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewDepartment.prefHeightProperty().bind(stage.heightProperty());// faz o tableview acompanhar a janela
	}

	public void updateTableView() {//responsavel por acessar o service carregar os departamentos e jogar na minha observablelist
		if (service == null) {//proteger 
			throw new IllegalStateException("Service was null");
		}
		List<Department> list = service.findAll();//recuperar os departamentos mockado no serivce4
		obsList = FXCollections.observableArrayList(list);//carregar lista dentro do obslist
		tableViewDepartment.setItems(obsList);
	}

}
