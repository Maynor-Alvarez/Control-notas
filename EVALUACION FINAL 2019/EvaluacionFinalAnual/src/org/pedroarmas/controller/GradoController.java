/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pedroarmas.controller;

import java.awt.Button;
import java.awt.TextField;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.JOptionPane;
import org.pedroarmas.bean.Grado;
import org.pedroarmas.db.Conexion;
import org.pedroarmas.report.GenerarReporte;
import org.pedroarmas.sistema.Principal;

/**
 *
 * @author programacion
 */
public class GradoController implements Initializable {
private enum operaciones {AGREGAR, GUARDAR,NUEVO, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO};    
private Principal escenarioPrincipal; 
private operaciones tipoDeOperacion = operaciones.NINGUNO;
private ObservableList <Grado> listaGrado;

@FXML private TextField txtSeccion;
@FXML private TextField txtModalidad;
@FXML private TextField txtJornada;
@FXML private TextField txtGrado;
@FXML private TableView tblGrados;
@FXML private TableColumn colSeccion;
@FXML private TableColumn colModalidad;
@FXML private TableColumn colJornada;
@FXML private TableColumn colGrado;
@FXML private TableColumn colCodigoGrado;
@FXML private Button btnNuevo;
@FXML private Button btnEliminar;
@FXML private Button btnEditar;
@FXML private Button btnReporte;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
    
    public void cargarDatos(){
        tblGrados.setItems(getGrados());
        colCodigoGrado.setCellValueFactory(new PropertyValueFactory<Grado, Integer>("codigoGrado"));
        colGrado.setCellValueFactory(new PropertyValueFactory<Grado, String>("grado"));
        colSeccion.setCellValueFactory(new PropertyValueFactory<Grado, String>("Seccion"));
        colModalidad.setCellValueFactory(new PropertyValueFactory<Grado, String> ("modalidad"));
        colJornada.setCellValueFactory(new PropertyValueFactory<Grado, String> ("jornada"));
      
    }
    
    public ObservableList <Grado>getGrados(){
        ArrayList <Grado> lista = new ArrayList <Grado>();
        try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_listarGrados}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Grado(resultado.getInt("codigoGrado"),
                                    resultado.getString("grado"),
                                    resultado.getString("seccion"),
                                    resultado.getString("modalidad"),
                                    resultado.getString("jornada")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaGrado = FXCollections.observableList(lista);
    }
    
    public void seleccionarElementos(){
        txtGrado.setText(((Grado)tblGrados.getSelectionModel().getSelectedItem()).getGrado());
        txtSeccion.setText(((Grado)tblGrados.getSelectionModel().getSelectedItem()).getSeccion());
        txtModalidad.setText(((Grado)tblGrados.getSelectionModel().getSelectedItem()).getModalidad());
        txtJornada.setText(((Grado)tblGrados.getSelectionModel().getSelectedItem()).getJornada());
    }
    
    public void editar(){
        switch(tipoDeOperacion){
            case NINGUNO:
                if(tblGrados.getSelectionModel().getSelectedItem() != null){
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnNuevo.setDisable(true);
                    btnEliminar.setDisable(true);
                    activarControles();
                    tipoDeOperacion =operaciones.AGREGAR;
                }else {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar un elemento");
                }
                break;
            case ACTUALIZAR:
                actualizar();
                btnEditar.setText("Editar");
                btnReporte.setText("Reporte");
                btnNuevo.setDisable(false);
                btnEliminar.setDisable(false);
                tipoDeOperacion = operaciones.NINGUNO;
                cargarDatos();
                break;
        }
    }
    
    public void actualizar(){
        try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_EditarGrado(?,?,?,?,?)}");
            Grado registro = (Grado)tblGrados.getSelectionModel().getSelectedItem();
            registro.setGrado(txtGrado.getText());
            registro.setSeccion(txtSeccion.getText());
            registro.setModalidad(txtModalidad.getText());
            registro.setJornada(txtJornada.getText());
            procedimiento.setInt(1, registro.getCodigoGrado());
            procedimiento.setString(2, registro.getGrado());
            procedimiento.setString(3, registro.getSeccion());
            procedimiento.setString(4, registro.getModalidad());
            procedimiento.setString(5, registro.getJornada());
            procedimiento.execute();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void eliminar(){
        switch(tipoDeOperacion){
            case GUARDAR:
                desactivarControles();
                limpiarControles();
                btnNuevo.setText("Nuevo");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                tipoDeOperacion = operaciones.NUEVO;
                break;
            default:
                if(tblGrados.getSelectionModel().getSelectedItem() != null){
                    int respuesta = JOptionPane.showConfirmDialog(null, "¿Está seguro de eliminar el registro?", "Eliminar Grado", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if(respuesta == JOptionPane.YES_OPTION)
                        try{
                    PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_EliminarMedicos(?)}");
                    procedimiento.setInt(1, ((Grado)tblGrados.getSelectionModel().getSelectedItem()).getCodigoGrado());
                    procedimiento.execute();
                    listaGrado.remove(tblGrados.getSelectionModel().getSelectedIndex());
                    limpiarControles();
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                }else{
                    JOptionPane.showMessageDialog(null, "Debe seleccionar un elemento");
                }
        }
    }
    
    public void nuevo(){
        switch(tipoDeOperacion){
            case NINGUNO:
                activarControles();
                btnNuevo.setText("Guardar");
                btnEliminar.setText("Cancelar");
                btnEditar.setDisable(true);
                btnReporte.setDisable(true);
                tipoDeOperacion = operaciones.GUARDAR;
                break;
            case GUARDAR:
                guardar();
                desactivarControles();
                limpiarControles();
                btnNuevo.setText("Nuevo");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                tipoDeOperacion = operaciones.NINGUNO;
                cargarDatos();
        }
    }
    
    public void guardar(){
        Grado registro = new Grado();
        registro.setGrado(txtGrado.getText());
        registro.setSeccion(txtSeccion.getText());
        registro.setModalidad(txtModalidad.getText());
        registro.setJornada(txtJornada.getText());
        try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_NuevoGrado(?,?,?,?)}");
            procedimiento.setString(1, registro.getGrado());
            procedimiento.setString(2, registro.getSeccion());
            procedimiento.setString(3, registro.getModalidad());
            procedimiento.setString(4, registro.getJornada());
            procedimiento.execute();
            listaGrado.add(registro);
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
    public void desactivarControles(){
        txtSeccion.setEditable(false);
        txtModalidad.setEditable(false);
        txtJornada.setEditable(false);
        txtGrado.setEditable(false);
    }
    
    public void activarControles(){
        txtSeccion.setEditable(true);
        txtModalidad.setEditable(true);
        txtJornada.setEditable(true);
        txtGrado.setEditable(true);
    }
    
    public void limpiarControles(){
        txtSeccion.setText("");
        txtModalidad.setText("");
        txtJornada.setText("");
        txtGrado.setText("");
    }
    
    public void generarReporte(){
        switch(tipoDeOperacion){
            case NINGUNO:
                imprimirReporte();
                limpiarControles();                
                break;
            case ACTUALIZAR:
                btnEditar.setText("Editar");
                btnReporte.setText("Reporte");
                tipoDeOperacion = operaciones.NINGUNO;
                break;
        }
    }

    public void imprimirReporte(){
        if (tblGrados.getSelectionModel().getSelectedItem()  != null){
            int codigoGrado = ((Grado) tblGrados.getSelectionModel().getSelectedItem()).getCodigoGrado();
            Map parametros = new HashMap();
            parametros.put("p_codigoGrado", codigoGrado);
            GenerarReporte.mostrarReporte("reporteGrado.jasper", "Reporte de Grado", parametros);
        }else{
                JOptionPane.showMessageDialog(null, "Debe seleccionar un registro" );
                }
    }
    
    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
   
    
}
