package javafx.controllers.car;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.database.Connetor;
import javafx.entity.Orders;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ListOrdercontroller implements Initializable {
    @FXML
    public TableView<Orders> tbview;
  @FXML
  public TableColumn<Orders, String> tcFullName;
  @FXML
  public TableColumn<Orders, String> tcDOB;

//@FXML
//public TableColumn<Orders, String> tcAddress;
  @FXML
  public TableColumn<Orders, String> tcCar;
  @FXML
  public TableColumn<Orders, String> tcDays;
  @FXML
  public TableColumn<Orders, String> tcPrice;



            private List<Orders> FetchOrdersFromDatabase() throws SQLException, ClassNotFoundException {
                List<Orders> orderslist = new ArrayList<Orders>();
                Connetor connector = Connetor.getInstance();
                Connection connection = connector.getConn();
                Statement st = connection.createStatement();
                ResultSet rs = st.executeQuery("select * from orderslist");
                while (rs.next()) {
                    // Get each field fromt the result set
                    String FullName = rs.getString(1);
                    LocalDate DOB = rs.getDate(2).toLocalDate();
//                    String Address = rs.getString(3);
                    String Car = rs.getString(4);
                    String Days = rs.getString(5);
                    String Price = rs.getString(6);
                   Orders orders=new Orders(FullName,DOB,Car,Days,Price);
                    orderslist.add(orders);
                }

                return orderslist;
            }

//    @Override
//    public void initialize(URL location, ResourceBundle resources){
//                    this.tcFullName.setCellValueFactory(new PropertyValueFactory("FullName"));
//                    this.tcDOB.setCellValueFactory(new PropertyValueFactory("DOB"));
//                    this.tcAddress.setCellValueFactory(new PropertyValueFactory("Address"));
//                    this.tcCar.setCellValueFactory(new PropertyValueFactory("Car"));
//                    this.tcDays.setCellValueFactory(new PropertyValueFactory("Days"));
//                    this.tcPrice.setCellValueFactory(new PropertyValueFactory("Price"));
//
//
//    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tcFullName.setCellValueFactory(new PropertyValueFactory<Orders, String>("FullName"));
        tcDOB.setCellValueFactory(new PropertyValueFactory<Orders, String>("DOB"));
//       tcAddress.setCellValueFactory(new PropertyValueFactory<Orders, String>("address"));
       tcCar.setCellValueFactory(new PropertyValueFactory<Orders, String>("Car"));
        tcDays.setCellValueFactory(new PropertyValueFactory<Orders, String>("Days"));
        tcPrice.setCellValueFactory(new PropertyValueFactory<Orders, String>("Price"));
      List<Orders>  ordersList=null;
      try{
          ordersList=FetchOrdersFromDatabase();

      } catch (SQLException e) {
          throw new RuntimeException(e);
      } catch (ClassNotFoundException e) {
          throw new RuntimeException(e);
      }
        ObservableList<Orders> data= FXCollections.observableArrayList(ordersList);
       tbview.setItems(data);

    }

    public void closetodonelist(ActionEvent actionEvent) {
        Stage parentStage = (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();
        parentStage.close();
    }
}
