package mvc;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Vector;
import javax.swing.JOptionPane;

import Exception.DigitsException;
import Exception.ElementExistsException;
import Exception.EmptyFieldsException;
import Exception.IDException;
import Exception.TimesException;
import Exception.YearException;
import finalProject.CompanyViewListenable;
import finalProject.Employee.ePreferences;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class View {
	private Vector<CompanyViewListenable> listeners;
	private BorderPane br;
	private VBox vb;

	public View(Stage theStage) {

		theStage.setTitle("ultimate company's profit regulatory system");
		this.listeners = new Vector<CompanyViewListenable>();

		br = new BorderPane();
		Label lblMenu = new Label();
		lblMenu.setText("System's Menu");
		lblMenu.setFont(new Font("Times", 30));

		Button btn1 = new Button("Add department");
		Button btn2 = new Button("Add role");
		Button btn3 = new Button("Add employee");
		Button btn4 = new Button("Set schedule for employee");
		Button btn5 = new Button("View departments");
		Button btn6 = new Button("View roles");
		Button btn7 = new Button("View employees");
		Button btn8 = new Button("View company");
		Button btn9 = new Button("View daily profits");
		Button btn10 = new Button("Change synchronization");
		Button btn11 = new Button("Exit menu");

		vb = new VBox();
		vb.setAlignment(Pos.TOP_CENTER);
		vb.setSpacing(5);
		vb.setPadding(new Insets(10));
		vb.getChildren().add(lblMenu);
		vb.getChildren().addAll(btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn10, btn11);

		// define company
		GridPane bp0 = new GridPane();
		bp0.setPadding(new Insets(10));
		Label lblWelcome = new Label("Welcome to system");
		lblWelcome.setFont(new Font("Times", 30));
		Label lblName = new Label("Enter company name");
		TextField tfName = new TextField();
		Label lblYear = new Label("Established year");
		TextField tfYear = new TextField();
		Label lblAvarage = new Label("Average profit per employee hourly");
		TextField tfAverageProfit = new TextField();

		Button btnContinue = new Button("Continue");
		Button btnExit = new Button("Exit");
		bp0.setPadding(new Insets(10));
		bp0.setHgap(10);
		bp0.setVgap(10);
		bp0.add(lblWelcome, 0, 0);
		bp0.add(lblName, 0, 2);
		bp0.add(tfName, 1, 2);
		bp0.add(lblYear, 0, 3);
		bp0.add(tfYear, 1, 3);
		bp0.add(lblAvarage, 0, 4);
		bp0.add(tfAverageProfit, 1, 4);
		bp0.add(btnExit, 0, 7);
		bp0.add(btnContinue, 1, 7);

		btnContinue.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg2) {
				try {
					if (tfName.getText().isEmpty() || tfYear.getText().isEmpty() || tfAverageProfit.getText().isEmpty()) {
						throw new EmptyFieldsException();
					}
					else{
						for (CompanyViewListenable l : listeners) {
							if(l.isNumbers(tfAverageProfit.getText())==true && l.isNumbers(tfYear.getText())==true){
								String name = tfName.getText();
								int year = Integer.parseInt(tfYear.getText());
								double averageProfit = Double.parseDouble(tfAverageProfit.getText());
								l.viewSetCompany(name, year, averageProfit);
								JOptionPane.showMessageDialog(null, "Company was added successfuly");
							}else if(Integer.parseInt(tfYear.getText())>LocalDate.now().getYear()) {
								throw new YearException();		
							}
							else {
								br.setCenter(bp0);
							}
						}
						br.setCenter(vb);
					}
				} catch (YearException| DigitsException | EmptyFieldsException e) {
					JOptionPane.showMessageDialog(null,e.getMessage());
					br.setCenter(bp0);
				}
			}
		});
		btnExit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				theStage.close();
			}
		});

		// read from file
		GridPane bp00 = new GridPane();
		bp00.setPadding(new Insets(10));
		Label lblWelcomeRead = new Label("Welcome to system");
		lblWelcomeRead.setFont(new Font("Times", 30));
		Label lblFileRead = new Label("Would you like to read from a file? ");
		lblFileRead.setFont(new Font("Times", 20));
		Button btnOKFileRead = new Button("OK");
		Button btnNOFileRead = new Button("NO");
		bp00.setPadding(new Insets(10));
		bp00.setHgap(10);
		bp00.setVgap(10);
		bp00.add(lblWelcomeRead, 0, 0);
		bp00.add(lblFileRead, 0, 2);
		bp00.add(btnOKFileRead, 0, 4);
		bp00.add(btnNOFileRead, 1, 4);

		br.setCenter(bp00);

		btnOKFileRead.setOnAction(new EventHandler<ActionEvent>() {
			@Override

			public void handle(ActionEvent arg50) {
				try {
					for (CompanyViewListenable l : listeners) {
						l.read();
						JOptionPane.showMessageDialog(null, "Last backup was loaded..");
						br.setCenter(vb);
					} 
				}catch (IOException | ClassNotFoundException e) {
					JOptionPane.showMessageDialog(null, "please save data at least once");
					br.setCenter(bp0);
				}
			}
		});

		btnNOFileRead.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				br.setCenter(bp0);
			}
		});

		// add department
		GridPane bp1 = new GridPane();
		bp1.setPadding(new Insets(10));
		Label lblDepartmentName = new Label("Enter department name");
		TextField tfDepartmentName = new TextField();
		ComboBox<String> cbSynchronize = new ComboBox<String>();
		Label lblSynchronize = new Label("Is synchronized ?");
		cbSynchronize.getItems().addAll("YES", "NO");
		ComboBox<String> cbStartTime = new ComboBox<String>();
		cbStartTime.getItems().addAll(setHoursInComboBox());
		Label lblStartTime = new Label("Start time: ");
		ComboBox<String> cbFinishTime = new ComboBox<String>();
		cbFinishTime.getItems().addAll(setHoursInComboBox());
		Label lblFinishTime = new Label("Finish time: ");
		ComboBox<String> cbDepartmentChangeable = new ComboBox<String>();
		Label lblDepartmentChangeable = new Label("Make it changeable? ");
		cbDepartmentChangeable.getItems().addAll("YES", "NO");

		Button btnAddDepartment = new Button("Add");
		Button btnCancelDepartment = new Button("Cancel");
		bp1.setPadding(new Insets(10));
		bp1.setHgap(10);
		bp1.setVgap(10);
		bp1.add(lblDepartmentName, 0, 0);
		bp1.add(tfDepartmentName, 1, 0);
		bp1.add(lblSynchronize, 0, 1);
		bp1.add(cbSynchronize, 1, 1);
		bp1.add(lblStartTime, 0, 2);
		bp1.add(cbStartTime, 1, 2);
		bp1.add(lblFinishTime, 0, 3);
		bp1.add(cbFinishTime, 1, 3);
		bp1.add(lblDepartmentChangeable, 2, 3);
		bp1.add(cbDepartmentChangeable, 3, 3);
		bp1.add(btnAddDepartment, 0, 5);
		bp1.add(btnCancelDepartment, 1, 5);

		btn1.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg) {
				tfDepartmentName.clear();
				cbStartTime.setValue(null);
				cbFinishTime.setValue(null);
				cbSynchronize.setValue(null);
				cbDepartmentChangeable.setValue(null);

				lblStartTime.setVisible(false);
				cbStartTime.setVisible(false);
				lblFinishTime.setVisible(false);
				cbFinishTime.setVisible(false);
				lblDepartmentChangeable.setVisible(false);
				cbDepartmentChangeable.setVisible(false);

				cbSynchronize.setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent e) {
						if (cbSynchronize.getValue() == ("YES")) {
							lblStartTime.setVisible(true);
							cbStartTime.setVisible(true);
							lblFinishTime.setVisible(true);
							cbFinishTime.setVisible(true);
							lblDepartmentChangeable.setVisible(true);
							cbDepartmentChangeable.setVisible(true);
							br.setCenter(bp1);
						} else {
							lblStartTime.setVisible(false);
							cbStartTime.setVisible(false);
							lblFinishTime.setVisible(false);
							cbFinishTime.setVisible(false);
							lblDepartmentChangeable.setVisible(false);
							cbDepartmentChangeable.setVisible(false);
							br.setCenter(bp1);
						}

					}
				});

				br.setCenter(bp1);
			}

		});

		btnAddDepartment.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg2) {
				try {
					if (tfDepartmentName.getText().isEmpty() || cbSynchronize.getValue() == null
							|| cbSynchronize.getValue() == "YES" && cbDepartmentChangeable.getValue() == null) {
						throw new EmptyFieldsException();
					}
					else if (cbSynchronize.getValue() == "YES" && exchangeTimeFromComboBox(
							cbStartTime.getValue()) > exchangeTimeFromComboBox(cbFinishTime.getValue())) {
						throw new TimesException();
					}
					else {
						for (CompanyViewListenable l : listeners) {
							boolean flag=false;
							if(!(l.viewDepartments()==null)) {
								for(int i=0; i<l.viewDepartments().size();i++) {
									if(l.viewDepartments().elementAt(i).equals(tfDepartmentName.getText())) {
										flag=l.viewDepartments().contains(tfDepartmentName.getText()); 
										throw new ElementExistsException();
									}
								}

							}
							String name;
							int start, finish;
							boolean changeable = false;
							if (cbSynchronize.getValue() == "YES") {
								name = tfDepartmentName.getText();
								start = exchangeTimeFromComboBox(cbStartTime.getValue());
								finish = exchangeTimeFromComboBox(cbFinishTime.getValue());
								if (cbDepartmentChangeable.getValue() == "YES") {
									changeable = true;
									l.viewAddDepartment(name, start, finish, true, true);
								} 
								else {
									l.viewAddDepartment(name, start, finish, true, false);
								}
							}
							else {
								name = tfDepartmentName.getText();
								l.viewAddDepartment(name, 0, 0, false, false);
							}
							if(!flag) {
								JOptionPane.showMessageDialog(null, "Department was added successfuly");
								br.setCenter(vb);
							}
						}
					}
				} catch (EmptyFieldsException | TimesException | ElementExistsException e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
					br.setCenter(bp1);
				}
			}
		});

		btnCancelDepartment.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				tfDepartmentName.clear();
				cbStartTime.setValue(null);
				cbFinishTime.setValue(null);
				cbSynchronize.setValue(null);
				// cancel button
				br.setCenter(vb);
			}
		});

		// add role
		GridPane bp2 = new GridPane();
		bp2.setPadding(new Insets(10));
		Label lblRoleName = new Label("Enter name of role");
		TextField tfRoleName = new TextField();
		ComboBox<String> cbDepartmentSelection = new ComboBox<String>();
		Label lblDepartmentSelection = new Label("Select department");
		ComboBox<String> cbSynchronizeRole = new ComboBox<String>();
		Label lblSynchronizeRole = new Label("Is synchronized ?");
		cbSynchronizeRole.getItems().addAll("YES", "NO");
		ComboBox<String> cbStartTimeRole = new ComboBox<String>();
		cbStartTimeRole.getItems().addAll(setHoursInComboBox());
		Label lblStartTimeRole = new Label("Start time: ");
		ComboBox<String> cbFinishTimeRole = new ComboBox<String>();
		cbFinishTimeRole.getItems().addAll(setHoursInComboBox());
		Label lblFinishTimeRole = new Label("Finish time: ");
		ComboBox<String> cbRoleChangeable = new ComboBox<String>();
		Label lblRoleChangeable = new Label("Make it changeable? ");
		cbRoleChangeable.getItems().addAll("YES", "NO");

		Button btnAddRole = new Button("Add");
		Button btnCancelRlole = new Button("Cancel");
		bp2.setPadding(new Insets(10));
		bp2.setHgap(10);
		bp2.setVgap(10);
		bp2.add(lblRoleName, 0, 0);
		bp2.add(tfRoleName, 1, 0);
		bp2.add(lblDepartmentSelection, 0, 1);
		bp2.add(cbDepartmentSelection, 1, 1);
		bp2.add(lblSynchronizeRole, 0, 2);
		bp2.add(cbSynchronizeRole, 1, 2);
		bp2.add(lblStartTimeRole, 0, 3);
		bp2.add(cbStartTimeRole, 1, 3);
		bp2.add(lblFinishTimeRole, 0, 4);
		bp2.add(cbFinishTimeRole, 1, 4);
		bp2.add(lblRoleChangeable, 2, 4);
		bp2.add(cbRoleChangeable, 3, 4);
		bp2.add(btnAddRole, 0, 6);
		bp2.add(btnCancelRlole, 1, 6);

		btn2.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg) {
				cbDepartmentSelection.setValue(null);
				cbDepartmentSelection.getItems().clear();
				tfRoleName.clear();

				cbStartTimeRole.setValue(null);
				cbFinishTimeRole.setValue(null);
				cbSynchronizeRole.setValue(null);
				cbRoleChangeable.setValue(null);

				lblStartTimeRole.setVisible(false);
				cbStartTimeRole.setVisible(false);
				lblFinishTimeRole.setVisible(false);
				cbFinishTimeRole.setVisible(false);
				lblRoleChangeable.setVisible(false);

				cbSynchronizeRole.setOnAction(new EventHandler<ActionEvent>() {

					public void handle(ActionEvent e) {
						if (cbSynchronizeRole.getValue() == ("YES")) {
							lblStartTimeRole.setVisible(true);
							cbStartTimeRole.setVisible(true);
							lblFinishTimeRole.setVisible(true);
							cbFinishTimeRole.setVisible(true);
							lblRoleChangeable.setVisible(true);
							cbRoleChangeable.setVisible(true);
							br.setCenter(bp2);
						} else {
							lblStartTimeRole.setVisible(false);
							cbStartTimeRole.setVisible(false);
							lblFinishTimeRole.setVisible(false);
							cbFinishTimeRole.setVisible(false);
							lblRoleChangeable.setVisible(false);
							cbRoleChangeable.setVisible(false);
							br.setCenter(bp2);
						}

					}
				});
				for (CompanyViewListenable l : listeners) {
					if (!(l.viewDepartments() == null)) {
						cbDepartmentSelection.getItems().addAll(l.viewDepartments());
					}
				}
				if(cbDepartmentSelection.getItems().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please add departments before adding any roles");
					br.setCenter(vb);
				}
				else
					br.setCenter(bp2);
			}
		});

		btnAddRole.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg2) {
				try {
					if (cbDepartmentSelection.getValue() == null || tfRoleName.getText().isEmpty()
							|| cbSynchronizeRole.getValue() == "YES" && cbRoleChangeable.getValue() == null) {
						JOptionPane.showMessageDialog(null, "One or more of the fields is empty");
					} 
					else if (cbSynchronizeRole.getValue() == "YES" && exchangeTimeFromComboBox(
							cbStartTimeRole.getValue()) > exchangeTimeFromComboBox(cbFinishTimeRole.getValue())) {
						JOptionPane.showMessageDialog(null, "Start time cannot be later than finish time");
					} 
					else {
						for (CompanyViewListenable l : listeners) {
							String roleName, department;
							int start, finish;
							boolean changeable = false;
							roleName = tfRoleName.getText();
							department = cbDepartmentSelection.getValue();
							if (cbSynchronizeRole.getValue() == "YES") {
								start = exchangeTimeFromComboBox(cbStartTime.getValue());
								finish = exchangeTimeFromComboBox(cbFinishTime.getValue());
								if (cbRoleChangeable.getValue() == "YES") {
									changeable = true;
									l.viewAddRole(roleName, department, start, finish, true, true);
								} 
								else {
									l.viewAddRole(roleName, department, start, finish, true, false);
								}
							} 
							else {
								l.viewAddRole(roleName, department, 0, 0, false, false);
							}
						}
						JOptionPane.showMessageDialog(null, "Role was added successfuly");
					}

				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "please fill all the fields");
				}

				br.setCenter(vb);
			}
		});

		btnCancelRlole.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				cbDepartmentSelection.setValue(null);
				// cancel button
				br.setCenter(vb);
			}
		});


		// add employee
		GridPane bp3 = new GridPane();
		bp3.setPadding(new Insets(10));
		Label lblEmployeeName = new Label("Enter name: ");
		TextField tfEmployeeName = new TextField();
		Label lblEmployeeID = new Label("ID: ");
		TextField tfEmployeeID = new TextField();
		Label lblEmployeeYear = new Label("Year of birth");
		ComboBox<Integer> cbEmployeeYear = new ComboBox<Integer>();
		cbEmployeeYear.getItems().addAll(setYearsInComboBox());
		Label lblEmployeeType = new Label("Employee type: ");
		ComboBox<String> cbEmployeeType = new ComboBox<String>();
		cbEmployeeType.getItems().addAll("Base waged", "Hourly waged", "Based + sales");
		Label lblEmployeeSales = new Label("% sales:");
		TextField tfEmployeeSales = new TextField();
		Label lblEmployeeSalaryPerHour = new Label("Salary per hour");
		TextField tfEmployeeSalaryPerHour = new TextField();
		ComboBox<String> cbEmployeeRole = new ComboBox<String>();
		Label lblEmployeeRole = new Label("Role in the company ");
		Label lblPreferationEmployee = new Label("Preferation: ");
		ComboBox<String> cbPreferationEmployee = new ComboBox<String>();
		cbPreferationEmployee.getItems().addAll("Early start", "Late start", "Default working hours", "Work from home");

		Button btnAddEmployee = new Button("Add");
		Button btnCancelEmployee = new Button("Cancel");
		bp3.setPadding(new Insets(10));
		bp3.setHgap(10);
		bp3.setVgap(10);
		bp3.add(lblEmployeeName, 0, 0);
		bp3.add(tfEmployeeName, 1, 0);
		bp3.add(lblEmployeeID, 0, 1);
		bp3.add(tfEmployeeID, 1, 1);
		bp3.add(lblEmployeeYear, 0, 2);
		bp3.add(cbEmployeeYear, 1, 2);
		bp3.add(lblEmployeeType, 0, 3);
		bp3.add(cbEmployeeType, 1, 3);
		bp3.add(lblEmployeeSalaryPerHour, 0, 4);
		bp3.add(tfEmployeeSalaryPerHour, 1, 4);
		bp3.add(lblEmployeeSales, 2, 4);
		bp3.add(tfEmployeeSales, 3, 4);
		bp3.add(lblEmployeeRole, 0, 5);
		bp3.add(cbEmployeeRole, 1, 5);
		bp3.add(lblPreferationEmployee, 0, 6);
		bp3.add(cbPreferationEmployee, 1, 6);
		bp3.add(btnAddEmployee, 0, 8);
		bp3.add(btnCancelEmployee, 1, 8);

		btn3.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg02) {
				tfEmployeeName.clear();
				tfEmployeeID.clear();
				cbEmployeeYear.setValue(null);
				tfEmployeeSalaryPerHour.clear();
				tfEmployeeSales.clear();
				cbEmployeeRole.setValue(null);
				cbPreferationEmployee.setValue(null);
				cbEmployeeType.setValue(null);
				lblEmployeeSales.setVisible(false);
				tfEmployeeSales.setVisible(false);

				cbEmployeeType.setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent e) {
						if (cbEmployeeType.getValue() == ("Based + sales")) {
							lblEmployeeSales.setVisible(true);
							tfEmployeeSales.setVisible(true);
							br.setCenter(bp3);
						} else {
							lblEmployeeSales.setVisible(false);
							tfEmployeeSales.setVisible(false);
							br.setCenter(bp3);
						}
					}
				});

				cbEmployeeRole.getItems().clear();
				try {
					for (CompanyViewListenable l : listeners) {
						if (!l.viewRoles().isEmpty()) {
							cbEmployeeRole.getItems().addAll(l.viewRoles());
							br.setCenter(bp3);
						} 
						else {
							throw new Exception("Please add roles before adding employees");
						}
					}
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Please add roles before adding employees");
					br.setCenter(vb);
				}
			}
		});

		btnAddEmployee.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg2) {
				try {
					if (tfEmployeeName.getText().isEmpty() || tfEmployeeID.getText().isEmpty()
							|| cbEmployeeYear.getValue() == null || cbEmployeeType.getValue() == null
							|| tfEmployeeSalaryPerHour.getText().isEmpty() || cbEmployeeRole.getValue() == null
							|| cbPreferationEmployee.getValue() == null
							|| cbEmployeeType.getValue() == "Based + sales" && tfEmployeeSales.getText().isEmpty()) {
						throw new EmptyFieldsException("One or more of the fields is empty");
					} else {
						for (CompanyViewListenable l : listeners) {
							if(l.setId(tfEmployeeID.getText())==true && l.isNumbers(tfEmployeeSalaryPerHour.getText())==true 
									&& l.isNumbers(tfEmployeeSales.getText())==true) {
								String name, ID, type, role;
								int year;
								double salary, salesPercent;
								name = tfEmployeeName.getText();
								ID = tfEmployeeID.getText();
								type = cbEmployeeType.getValue();
								role = cbEmployeeRole.getValue();
								year = cbEmployeeYear.getValue();
								salary = Double.parseDouble(tfEmployeeSalaryPerHour.getText());
								if (tfEmployeeSales.getText().isEmpty()) {
									tfEmployeeSales.setText("0");
								}
								salesPercent = Double.parseDouble(tfEmployeeSales.getText());

								if (cbPreferationEmployee.getValue() == "Early start") {
									l.viewAddEmployee(name, ID, year, role, ePreferences.EARLY_START, false, salary, type,
											salesPercent);
								} 
								else if (cbPreferationEmployee.getValue() == "Late start") {
									l.viewAddEmployee(name, ID, year, role, ePreferences.LATE_START, false, salary, type,
											salesPercent);
								} 
								else if (cbPreferationEmployee.getValue() == "Default working hours") {
									l.viewAddEmployee(name, ID, year, role, ePreferences.DEFAULT, false, salary, type,
											salesPercent);
								} 
								else {
									l.viewAddEmployee(name, ID, year, role, ePreferences.HOME, false, salary, type,
											salesPercent);
								}
								JOptionPane.showMessageDialog(null, "Employee was added successfuly");
								br.setCenter(vb);
							}
							else {
								br.setCenter(bp3);
							}
						}
					}
				} catch (DigitsException | IDException | EmptyFieldsException | ElementExistsException e ) {
					JOptionPane.showMessageDialog(null, e.getMessage());
					br.setCenter(bp3);
				}
			}
		});
		btnCancelEmployee.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				cbEmployeeRole.setValue(null);
				cbPreferationEmployee.setValue(null);
				cbEmployeeType.setValue(null);
				// cancel button
				br.setCenter(vb);
			}
		});

		// employees schedule
		GridPane bp4 = new GridPane();
		bp4.setPadding(new Insets(10));
		Label lblSchedule = new Label("Daily Schedule");
		lblSchedule.setFont(new Font("Times", 30));
		Label lblScheduleName = new Label("Name");
		Label lblSchedulePreference = new Label("Preference");
		Label lblScheduleStart = new Label("Start");
		Label lblScheduleFinish = new Label("Finish");
		Label lblWorkHome = new Label("Work from home ?");
		Button btnFinish = new Button("Finish");
		Button btnCancel = new Button("Cancel");
		bp4.setHgap(15);
		bp4.setVgap(15);
		bp4.add(lblSchedule, 0, 0);
		bp4.add(lblScheduleName, 0, 1);
		bp4.add(lblSchedulePreference, 1, 1);
		bp4.add(lblScheduleStart, 2, 1);
		bp4.add(lblScheduleFinish, 3, 1);
		bp4.add(lblWorkHome, 4, 1);

		Vector<Integer> startTime = new Vector<Integer>();
		Vector<Integer> finishTime = new Vector<Integer>();
		int max = 4;
		for (CompanyViewListenable l : listeners) {
			if (!l.employeesForSchedule().isEmpty())
				max = l.employeesForSchedule().size();
		}
		bp4.add(btnFinish, 0, max + 2);
		bp4.add(btnCancel, 1, max + 2);

		Vector<ComboBox<String>> detailStart = new Vector<ComboBox<String>>();
		Vector<ComboBox<String>> detailFinish = new Vector<ComboBox<String>>();
		Vector<ComboBox<String>> detailWorkHome = new Vector<ComboBox<String>>();
		Vector<Label> syncStart = new Vector<Label>();
		Vector<Label> syncFinish = new Vector<Label>();

		btn4.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg02) {
				detailStart.clear();
				detailFinish.clear();
				detailWorkHome.clear();

				for (CompanyViewListenable l : listeners) {
					if (!l.employeesForSchedule().isEmpty()) {
						for (int e = 0; e < l.employeesForSchedule().size(); e++) {
							Label lblDynamicName = new Label(l.employeesForSchedule().elementAt(e).getEmployeeName());
							Label lblDynamicPreference = new Label(
									l.employeesForSchedule().elementAt(e).showPreference());
							Label lblSyncStart = new Label("-");
							Label lblSyncFinish = new Label("-");
							ComboBox<String> cbSchedulesStart = new ComboBox<String>();
							ComboBox<String> cbSchedulesFinish = new ComboBox<String>();
							ComboBox<String> cbWorkHome = new ComboBox<String>();
							cbWorkHome.getItems().addAll("YES", "NO");

							cbSchedulesStart.getItems().addAll(setHoursInComboBox());
							cbSchedulesFinish.getItems().addAll(setHoursInComboBox());
							detailStart.add(cbSchedulesStart);
							detailFinish.add(cbSchedulesFinish);
							detailWorkHome.add(cbWorkHome);
							bp4.add(lblDynamicName, 0, e + 2);
							bp4.add(lblDynamicPreference, 1, e + 2);
							bp4.add(detailStart.elementAt(e), 2, e + 2);
							bp4.add(detailFinish.elementAt(e), 3, e + 2);
							bp4.add(detailWorkHome.elementAt(e), 4, e + 2);

							if (!l.viewSynchronizedDepartments().isEmpty()) {
								for (int j = 0; j < l.viewSynchronizedDepartments().size(); j++) {
									if (l.employeesForSchedule().elementAt(e).getMyRole().getDep()
											.getDepartmentName() == l.viewSynchronizedDepartments().elementAt(j)
											.getDepartmentName()) {

										if (l.viewSynchronizedDepartments().elementAt(j)
												.getStartTimeDepartment() < 10) {
											lblSyncStart.setText("0" + l.viewSynchronizedDepartments().elementAt(j)
													.getStartTimeDepartment() + ":00");
											detailStart.elementAt(e).setValue(lblSyncStart.getText());
										} else {
											lblSyncStart.setText(l.viewSynchronizedDepartments().elementAt(j)
													.getStartTimeDepartment() + ":00");
											detailStart.elementAt(e).setValue(lblSyncStart.getText());
										}
										if (l.viewSynchronizedDepartments().elementAt(j)
												.getFinishTimeDepartment() < 10) {
											lblSyncFinish.setText("0" + l.viewSynchronizedDepartments().elementAt(j)
													.getFinishTimeDepartment() + ":00");
											detailFinish.elementAt(e).setValue(lblSyncFinish.getText());
										} else {
											lblSyncFinish.setText(l.viewSynchronizedDepartments().elementAt(j)
													.getFinishTimeDepartment() + ":00");
											detailFinish.elementAt(e).setValue(lblSyncFinish.getText());
										}
										syncStart.add(lblSyncStart);
										syncFinish.add(lblSyncFinish);

										bp4.add(syncStart.elementAt(e), 2, e + 2);
										bp4.add(syncFinish.elementAt(e), 3, e + 2);

										cbSchedulesStart.setVisible(false);
										cbSchedulesFinish.setVisible(false);
										lblSyncFinish.setVisible(true);
										lblSyncStart.setVisible(true);

										cbWorkHome.setOnAction(new EventHandler<ActionEvent>() {
											public void handle(ActionEvent e) {
												if (cbWorkHome.getValue() == ("YES")
														&& !(lblSyncFinish.getText().equals("00:00")
																&& lblSyncStart.getText().equals("00:00"))) {
													cbSchedulesStart.setVisible(true);
													cbSchedulesFinish.setVisible(true);
													lblSyncFinish.setVisible(false);
													lblSyncStart.setVisible(false);
													br.setCenter(bp4);
												} else if (lblSyncFinish.getText().equals("00:00")
														&& lblSyncStart.getText().equals("00:00")) {
													cbSchedulesStart.setVisible(true);
													cbSchedulesFinish.setVisible(true);
													lblSyncFinish.setVisible(false);
													lblSyncStart.setVisible(false);
													br.setCenter(bp4);
												} else {
													cbSchedulesStart.setVisible(false);
													cbSchedulesFinish.setVisible(false);
													lblSyncFinish.setVisible(true);
													lblSyncStart.setVisible(true);
													br.setCenter(bp4);
												}
												br.setCenter(bp4);
											}
										});

									}
								}
							}
							cbSchedulesStart.setVisible(true);
							cbSchedulesFinish.setVisible(true);
							lblSyncFinish.setVisible(false);
							lblSyncStart.setVisible(false);

						}
						br.setCenter(bp4);
					}
					else {
						JOptionPane.showMessageDialog(null,"No employees exists for schedual define");
						br.setCenter(vb);
					}
				}
			}
		});

		btnFinish.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg2) {
				try {
					if (detailStart.isEmpty() || detailFinish.isEmpty()) {
						throw new Exception("One or more of the fields is empty");
					} else {
						for (CompanyViewListenable l : listeners) {
							for (int i = 0; i < detailFinish.size(); i++) {
								if ((detailFinish.elementAt(i).getValue() == null
										|| detailStart.elementAt(i).getValue() == null)
										&& detailWorkHome.elementAt(i).getValue() == "NO"
										|| detailWorkHome.elementAt(i).getValue() == null) {
									JOptionPane.showMessageDialog(null, "please fill all the fields");
								}
							}
							for (int j = 0; j < detailFinish.size(); j++) {
								int start, finish;
								start = exchangeTimeFromComboBox(detailStart.elementAt(j).getValue());
								finish = exchangeTimeFromComboBox(detailFinish.elementAt(j).getValue());
								if (exchangeTimeFromComboBox(detailStart.elementAt(j).getValue()) > (exchangeTimeFromComboBox(detailFinish.elementAt(j).getValue()))) {
									JOptionPane.showMessageDialog(null, "Start time cannot be later than finish time");
									br.setCenter(bp4);
								}
								else {
									if (detailWorkHome.elementAt(j).getValue() == "YES") {
										l.setTiming(j, start, finish, true);
									} else {
										l.setTiming(j, start, finish, false);
									}
									l.calcSalery();
									JOptionPane.showMessageDialog(null, "Sceduale was made successfuly");
									br.setCenter(vb);
								}
							}
						}
					}

				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "please fill all the fields");
				}
			}
		});
		btnCancel.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				detailStart.clear();
				detailFinish.clear();
				detailWorkHome.clear();
				detailFinish.clear();
				detailStart.clear();
				br.setCenter(vb);
			}
		});

		// view departments
		ScrollPane sp5 = new ScrollPane();
		sp5.setPrefSize(500, 500);
		GridPane bp5 = new GridPane();
		Label txtAllDepartments = new Label();
		Button btnBackDepartment = new Button("Back");
		bp5.setPadding(new Insets(10));
		bp5.setHgap(10);
		bp5.setVgap(10);
		bp5.add(btnBackDepartment, 0, 1);

		btn5.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg5) {
				for (CompanyViewListenable l : listeners) {
					txtAllDepartments.setText(l.printDepartment());
					txtAllDepartments.setFont(new Font("Times", 14));
					sp5.setContent(txtAllDepartments);
					bp5.add(sp5, 0, 0);
				}
				br.setCenter(bp5);
			}

		});

		btnBackDepartment.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg50) {
				// back button
				br.setCenter(vb);
				bp5.getChildren().remove(sp5);
			}

		});

		// view role
		ScrollPane sp6 = new ScrollPane();
		sp6.setPrefSize(500, 500);
		GridPane bp6 = new GridPane();
		Label txtAllRoles = new Label();
		Button btnBackRoles = new Button("Back");
		bp6.setPadding(new Insets(10));
		bp6.setHgap(10);
		bp6.setVgap(10);
		bp6.add(btnBackRoles, 0, 1);

		btn6.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg5) {
				for (CompanyViewListenable l : listeners) {
					txtAllRoles.setText(l.printRoles());
					txtAllRoles.setFont(new Font("Times", 14));
					sp6.setContent(txtAllRoles);
					bp6.add(sp6, 0, 0);
				}
				br.setCenter(bp6);
			}

		});

		btnBackRoles.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg50) {
				// back button
				br.setCenter(vb);
				bp6.getChildren().remove(sp6);
			}

		});

		// view employee
		ScrollPane sp7 = new ScrollPane();
		sp7.setPrefSize(500, 500);
		GridPane bp7 = new GridPane();
		Label txtAllEmployee = new Label();
		Button btnBackEmployee = new Button("Back");
		bp7.setPadding(new Insets(10));
		bp7.setHgap(10);
		bp7.setVgap(10);
		bp7.add(btnBackEmployee, 0, 1);

		btn7.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg5) {
				for (CompanyViewListenable l : listeners) {
					txtAllEmployee.setText(l.printEmployee());
					txtAllEmployee.setFont(new Font("Times", 14));
					sp7.setContent(txtAllEmployee);
					bp7.add(sp7, 0, 0);
				}
				br.setCenter(bp7);
			}

		});

		btnBackEmployee.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg50) {
				// back button
				br.setCenter(vb);
				bp7.getChildren().remove(sp7);
			}

		});

		// view company
		ScrollPane sp8 = new ScrollPane();
		sp8.setPrefSize(500, 500);
		GridPane bp8 = new GridPane();
		Label txtTheCompany = new Label();
		Button btnBackTheCompany = new Button("Back");
		bp8.setPadding(new Insets(10));
		bp8.setHgap(10);
		bp8.setVgap(10);
		bp8.add(btnBackTheCompany, 0, 1);

		btn8.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg5) {
				for (CompanyViewListenable l : listeners) {
					txtTheCompany.setText(l.printCompany());
					txtTheCompany.setFont(new Font("Times", 14));
					sp8.setContent(txtTheCompany);
					bp8.add(sp8, 0, 0);
				}
				br.setCenter(bp8);
			}

		});

		btnBackTheCompany.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg50) {
				// back button
				br.setCenter(vb);
				bp8.getChildren().remove(sp8);
			}

		});

		// view daily profits
		ScrollPane sp9 = new ScrollPane();
		sp9.setPrefSize(500, 500);
		GridPane bp9 = new GridPane();
		Label txtDailyProfits = new Label();
		Button btnBackDailyProfits = new Button("Back");
		bp9.setPadding(new Insets(10));
		bp9.setHgap(10);
		bp9.setVgap(10);
		bp9.add(btnBackDailyProfits, 0, 1);

		btn9.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg5) {
				for (CompanyViewListenable l : listeners) {

					txtDailyProfits.setText(l.printDailyProfits());
					txtDailyProfits.setFont(new Font("Times", 14));
					sp9.setContent(txtDailyProfits);
					bp9.add(sp9, 0, 0);
				}
				br.setCenter(bp9);
			}

		});

		btnBackDailyProfits.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg50) {
				// back button
				br.setCenter(vb);
				bp9.getChildren().remove(sp9);
			}

		});

		// change synchronization
		GridPane bp10 = new GridPane();
		bp10.setPadding(new Insets(10));
		Label lblChangesyncElement = new Label("Choose a type:");
		ComboBox<String> cbChangesyncElement = new ComboBox<String>();
		cbChangesyncElement.getItems().addAll("Department", "Role", "Employee");
		Label lblChangesyncSelection = new Label("Select specificly:");
		ComboBox<String> cbChangesyncDepartment = new ComboBox<String>();
		ComboBox<String> cbChangesyncRole = new ComboBox<String>();
		ComboBox<String> cbChangesyncEmployees = new ComboBox<String>();
		Label lblChangesyncEmployeePreferation = new Label("Preferation: ");
		ComboBox<String> cbChangesyncEmployeePreferation = new ComboBox<String>();
		cbChangesyncEmployeePreferation.getItems().addAll("Early start", "Late start", "Default working hours",
				"Work from home");
		Label lblChangesyncStart = new Label("Start time: ");
		ComboBox<String> cbChangesyncStart = new ComboBox<String>();
		cbChangesyncStart.getItems().addAll(setHoursInComboBox());
		Label lblChangesyncFinish = new Label("Finish time: ");
		ComboBox<String> cbChangesyncFinish = new ComboBox<String>();
		cbChangesyncFinish.getItems().addAll(setHoursInComboBox());

		Button btnChangesyncChange = new Button("Change");
		Button btnChangesyncCancel = new Button("Cancel");
		bp10.setPadding(new Insets(10));
		bp10.setHgap(10);
		bp10.setVgap(10);
		bp10.add(lblChangesyncElement, 0, 0);
		bp10.add(cbChangesyncElement, 1, 0);
		bp10.add(lblChangesyncSelection, 0, 1);
		bp10.add(cbChangesyncEmployees, 1, 1);
		bp10.add(lblChangesyncEmployeePreferation, 0, 2);
		bp10.add(cbChangesyncEmployeePreferation, 1, 2);
		bp10.add(cbChangesyncDepartment, 1, 1);
		bp10.add(cbChangesyncRole, 1, 1);
		bp10.add(lblChangesyncStart, 0, 2);
		bp10.add(cbChangesyncStart, 1, 2);
		bp10.add(lblChangesyncFinish, 2, 2);
		bp10.add(cbChangesyncFinish, 3, 2);
		bp10.add(btnChangesyncChange, 0, 5);
		bp10.add(btnChangesyncCancel, 1, 5);

		btn10.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg) {
				cbChangesyncElement.setValue(null);
				cbChangesyncStart.setValue(null);
				cbChangesyncFinish.setValue(null);
				cbChangesyncDepartment.setValue(null);
				cbChangesyncRole.setValue(null);
				cbChangesyncEmployeePreferation.setValue(null);
				cbChangesyncEmployees.setValue(null);
				cbChangesyncDepartment.getItems().clear();
				cbChangesyncRole.getItems().clear();
				cbChangesyncEmployees.getItems().clear();

				cbChangesyncStart.setVisible(false);
				cbChangesyncFinish.setVisible(false);
				cbChangesyncDepartment.setVisible(false);
				cbChangesyncRole.setVisible(false);
				cbChangesyncEmployees.setVisible(false);
				lblChangesyncEmployeePreferation.setVisible(false);
				cbChangesyncEmployeePreferation.setVisible(false);
				lblChangesyncStart.setVisible(false);
				lblChangesyncFinish.setVisible(false);

				for (CompanyViewListenable l : listeners) {
					if (!l.viewSyncDepartments().isEmpty()) {
						cbChangesyncDepartment.getItems().addAll(l.viewSyncDepartments());
					}
					if (!l.viewSyncRoles().isEmpty()) {
						cbChangesyncRole.getItems().addAll(l.viewSyncRoles());
					}
					if (!l.viewSyncEmployees().isEmpty()) {
						cbChangesyncEmployees.getItems().addAll(l.viewSyncEmployees());
					}
				}

				cbChangesyncElement.setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent e) {
						if (cbChangesyncElement.getValue() == ("Department")) {
							cbChangesyncStart.setVisible(true);
							cbChangesyncFinish.setVisible(true);
							cbChangesyncDepartment.setVisible(true);
							cbChangesyncRole.setVisible(false);
							cbChangesyncEmployees.setVisible(false);
							lblChangesyncEmployeePreferation.setVisible(false);
							cbChangesyncEmployeePreferation.setVisible(false);
							lblChangesyncStart.setVisible(true);
							lblChangesyncFinish.setVisible(true);
							br.setCenter(bp10);
						} else if (cbChangesyncElement.getValue() == ("Role")) {
							cbChangesyncStart.setVisible(true);
							cbChangesyncFinish.setVisible(true);
							cbChangesyncDepartment.setVisible(false);
							cbChangesyncRole.setVisible(true);
							cbChangesyncEmployees.setVisible(false);
							lblChangesyncEmployeePreferation.setVisible(false);
							cbChangesyncEmployeePreferation.setVisible(false);
							lblChangesyncStart.setVisible(true);
							lblChangesyncFinish.setVisible(true);
							br.setCenter(bp10);
						} else if (cbChangesyncElement.getValue() == ("Employee")) {
							cbChangesyncStart.setVisible(false);
							cbChangesyncFinish.setVisible(false);
							cbChangesyncDepartment.setVisible(false);
							cbChangesyncRole.setVisible(false);
							cbChangesyncEmployees.setVisible(true);
							lblChangesyncEmployeePreferation.setVisible(true);
							cbChangesyncEmployeePreferation.setVisible(true);
							lblChangesyncStart.setVisible(false);
							lblChangesyncFinish.setVisible(false);
						} else {
							cbChangesyncStart.setVisible(false);
							cbChangesyncFinish.setVisible(false);
							cbChangesyncDepartment.setVisible(false);
							cbChangesyncRole.setVisible(false);
							lblChangesyncEmployeePreferation.setVisible(false);
							cbChangesyncEmployeePreferation.setVisible(false);
							lblChangesyncStart.setVisible(false);
							lblChangesyncFinish.setVisible(false);
						}

					}
				});

				br.setCenter(bp10);
			}

		});

		btnChangesyncChange.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg2) {
				try {
					if (cbChangesyncElement.getValue() == null || (cbChangesyncStart.getValue() == null
							|| cbChangesyncFinish.getValue() == null) && (cbChangesyncElement.getValue() == ("Department") ||cbChangesyncElement.getValue() == ("Role"))
							|| cbChangesyncElement.getValue() == ("Department") && cbChangesyncDepartment.getValue() == null
							|| cbChangesyncElement.getValue() == ("Role") && cbChangesyncRole.getValue() == null
							|| cbChangesyncElement.getValue() == ("Employee")
							&& cbChangesyncEmployeePreferation.getValue() == null) {
						throw new EmptyFieldsException();
					} else if (exchangeTimeFromComboBox(cbChangesyncStart.getValue()) > exchangeTimeFromComboBox(
							cbChangesyncFinish.getValue())) {
						JOptionPane.showMessageDialog(null, "Start time cannot be later than finish time");
					} else {
						for (CompanyViewListenable l : listeners) {
							String specific;
							int start;
							int finish;
							if (cbChangesyncElement.getValue() == ("Department")) {
								start = exchangeTimeFromComboBox(cbChangesyncStart.getValue());
								finish = exchangeTimeFromComboBox(cbChangesyncFinish.getValue());
								specific = cbChangesyncDepartment.getValue();
								l.setNewTimingDepartment(start, finish, specific);
							} else if (cbChangesyncElement.getValue() == ("Role")) {
								start = exchangeTimeFromComboBox(cbChangesyncStart.getValue());
								finish = exchangeTimeFromComboBox(cbChangesyncFinish.getValue());
								specific = cbChangesyncRole.getValue();
								l.setNewTimingRole(start, finish, specific);
							} else {
								specific = cbChangesyncEmployees.getValue();
								if (cbChangesyncEmployeePreferation.getValue() == "Early start") {
									l.setNewPreferationEmployee(ePreferences.EARLY_START, specific);
								} else if (cbChangesyncEmployeePreferation.getValue() == "Late start") {
									l.setNewPreferationEmployee(ePreferences.LATE_START, specific);
								} else if (cbChangesyncEmployeePreferation.getValue() == "Default working hours") {
									l.setNewPreferationEmployee(ePreferences.DEFAULT, specific);
								} else
									l.setNewPreferationEmployee(ePreferences.HOME, specific);
							}
						}
					}
					JOptionPane.showMessageDialog(null, "Change was made successfuly");
				} catch (EmptyFieldsException e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				}
				br.setCenter(vb);
			}
		});

		btnChangesyncCancel.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				cbChangesyncElement.setValue(null);
				cbChangesyncStart.setValue(null);
				cbChangesyncFinish.setValue(null);
				cbChangesyncDepartment.setValue(null);
				cbChangesyncRole.setValue(null);
				cbChangesyncEmployeePreferation.setValue(null);
				cbChangesyncEmployees.setValue(null);
				// cancel button
				br.setCenter(vb);
			}
		});

		btn11.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				try {
					for (CompanyViewListenable l : listeners) {
						l.save();
						JOptionPane.showMessageDialog(null, "Data was save to system");
					}
				}
				catch (IOException e) {
					JOptionPane.showMessageDialog(null, "No data was saved");
				}
				theStage.close();
			}
		});

		Scene scene = new Scene(br, 700, 500);
		theStage.setScene(scene);
		theStage.show();

	}

	public void setListener(CompanyViewListenable l) {
		listeners.add(l);
	}

	public Vector<String> setHoursInComboBox() {
		Vector<String> hours = new Vector<String>();
		for (int i = 0; i < 24; i++) {
			if (i < 10)
				hours.add("0" + String.valueOf(i) + ":00");
			else
				hours.add(String.valueOf(i) + ":00");
		}
		return hours;
	}

	public int exchangeTimeFromComboBox(String time) {
		int hour = 0;
		Vector<String> hours = new Vector<String>();
		hours = setHoursInComboBox();

		for (int i = 0; i < 24; i++) {
			if (hours.elementAt(i).equals(time))
				hour = i;
		}
		return hour;
	}

	public Vector<Integer> setYearsInComboBox() {
		Vector<Integer> years = new Vector<Integer>();
		for (int i = 2004; i >= 1901; i--) {
			years.add(i);
		}
		return years;
	}

}
