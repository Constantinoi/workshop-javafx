package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
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
	public void onBtNewAction(ActionEvent event) {
		Stage parentStage = Utils.currentsStage(event);//passa a referencia para o Stage atual e cria a janela
		Department obj = new Department();
		createDialogForm(obj, "/gui/DepartmentForm.fxml", parentStage);
	}

	private ObservableList<Department> obsList;// carregar o departamento dentro dessa lista

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

	public void updateTableView() {// responsavel por acessar o service carregar os departamentos e jogar na minha
									// observablelist
		if (service == null) {// proteger
			throw new IllegalStateException("Service was null");
		}
		List<Department> list = service.findAll();// recuperar os departamentos mockado no serivce4
		obsList = FXCollections.observableArrayList(list);// carregar lista dentro do obslist
		tableViewDepartment.setItems(obsList);
	}

	private void createDialogForm(Department obj, String absoluteName, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();//chama um painel (carregar a view)
			
/* Quando carregar uma janela de modal a frente de uma janela existente
 *  e necessario instaciar um novo stage(um palco na frente do outro)*/
			
			DepartmentFormController controller = loader.getController();//pégar o controlador do formulario
			controller.setDepartment(obj);//injetar o departamento
			controller.updateFormData();//carrega os dados do obj
			
			
			Stage dialogStage = new Stage();// cria uma nova stage
			dialogStage.setTitle("Criar um departamento");//titulo
			dialogStage.setScene(new Scene(pane));//quem sera a cena do Stage criar uma nova cena
			dialogStage.setResizable(false);// redimencionamento da janela
			dialogStage.initOwner(parentStage);//quem e o Stage pai dessa janela
			dialogStage.initModality(Modality.WINDOW_MODAL);//janela travada enquanto nao fecha nao acessar a janeal anterios
			dialogStage.showAndWait();
		} catch (IOException e) {
			Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
		} finally {
			// TODO: handle finally clause
		}
		
	}

}
