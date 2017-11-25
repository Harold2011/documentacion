/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package achievements;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author unicuces
 */
public class Achievements extends javax.swing.JInternalFrame {

    DefaultTableModel model;
    connection.Connection cone;
    DefaultListModel buscar;
    String idT;

    /**
     * Creates new form Archievements
     */
    public Achievements() {
        initComponents();
        cone = new connection.Connection();
        id();
        cargar("");
        mostrarPeriodo();
        mostrarMateria();
        btnUpdate.setEnabled(false);

        buscar = new DefaultListModel();
    }

    void cargar(String valor) {
        String[] titulos = {"idlogro", "Nombre", "Descripcion", "Materia", "Periodo"};
        String[] registros = new String[5];
        String sql = "select idLogro,descripcion,log.Nombre as Nombre,mat.Nombre as materias,per.Nombre as periodo\n"
                + "from logro as log \n"
                + " INNER JOIN materia as mat on log.Materia_id=mat.idMateria\n"
                + " INNER JOIN periodo as per on log.Periodo_id=per.idperiodo";

        model = new DefaultTableModel(null, titulos);

        //conectar cc=new conectar();
        //Connection cn = cc.conexion();
        try {

            ResultSet rs = cone.consultDB(sql);

            while (rs.next()) {
                registros[0] = rs.getString("idLogro");
                registros[1] = rs.getString("Nombre");
                registros[2] = rs.getString("descripcion");
                registros[3] = rs.getString("materias");
                registros[4] = rs.getString("periodo");
                model.addRow(registros);
            }

            tbAchievements.setModel(model);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }

    }

    public void mostrarPeriodo() {
        try {
            String sql = "SELECT idperiodo FROM periodo";
            connection.Connection cone2 = new connection.Connection();
            ResultSet rs = cone2.consultDB(sql);
            while (rs.next()) {
                cboPeriod.addItem(rs.getString("idperiodo"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Achievements.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void mostrarMateria() {
        try {
            String mysql = "SELECT idMateria FROM materia";
            connection.Connection cone2 = new connection.Connection();
            ResultSet rs = cone2.consultDB(mysql);
            while (rs.next()) {
                cdoSubjects.addItem(rs.getString("idMateria"));
            }

        } catch (SQLException ex) {
            Logger.getLogger(Achievements.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void cargarListas() {
        try {
            String sql = "select idLogro,Nombre from logro";
            connection.Connection cone2 = new connection.Connection();
            ResultSet rs = cone2.consultDB(sql);
            while (rs.next()) {
                buscar.addElement(rs.getString("idLogro") + "-" + rs.getString("Nombre"));

            }
        } catch (SQLException ex) {
            Logger.getLogger(Achievements.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void selectUpdate() {
        int selectRow;

        selectRow = tbAchievements.getSelectedRow();
        if (selectRow == -1) {
            JOptionPane.showMessageDialog(null, "no ha seleccionado ninguna fila");
        } else {
            try {

                idT = (String) model.getValueAt(tbAchievements.getSelectedRow(), 0);
                ResultSet rs = cone.consultDB("select * from logro where idLogro = " + idT);

                if (rs.next()) {

                    txtName.setText(rs.getString("Nombre"));
                    txtDescription.setText(rs.getString("descripcion"));
                    cdoSubjects.setSelectedItem(rs.getString("Materia_id"));
                    cboPeriod.setSelectedItem(rs.getString("Periodo_id"));
                    spiPercentage.setValue(rs.getString("porsentaje"));
                    

                } else {
                    JOptionPane.showMessageDialog(rootPane, "El codigo no existe");
                }
            } catch (SQLException ex) {
                System.out.println(ex);
            }
        }
    }

    public void updateTable() {
        String idS = txtId.getText();
        String name = txtName.getText();
        String descr = txtDescription.getText();
        String idA = (String) cdoSubjects.getSelectedItem();
        String idB = (String) cboPeriod.getSelectedItem();
         

        Integer.parseInt(idS);

        cone.consultDB("UPDATE logro SET Nombre='" + name + "', descripcion=" + idA
                + " WHERE idMateria=" + idS);
        JOptionPane.showMessageDialog(null, "Actualización Exitosa");

    }

    public void insert() {
        int Porcentaje, Activo;
        String materias = (String) cdoSubjects.getSelectedItem();
        String periodo = (String) cboPeriod.getSelectedItem();
        String name, area, tipo = "",id;
        id=txtId.getText();
        name = txtName.getText();
        area = txtDescription.getText();
        Activo = 1;
        Porcentaje = (int) spiPercentage.getValue();
        if (rbInstitutional.isSelected()) {
            tipo = "INS";

        } else if (rbAcademic.isSelected()) {
            tipo = "ACA";
        }

        cone.modifyDB("INSERT INTO logro VALUES ("+id+",'" + name + "','" + area + "',1," + materias + "," + periodo + "," + Porcentaje + ",'" + tipo + "')");
        JOptionPane.showMessageDialog(null, "registro exitoso");

    }
    
     public void id() {
        try {

            String fila;

            //cuadro.setText("CODIGO\tNOMBRE\tGENERO\tEDAD\tCURSO\n");
            ResultSet rs = cone.consultDB("SELECT MAX(idLogro) FROM logro");
            if (rs.next()) {
                int num = rs.getInt("MAX(idLogro)") + 1;
                if (num == 0) {
                    txtId.setText(1 + "");
                } else {
                    txtId.setText(num + "");
                }

            }

        } catch (SQLException ex) {
            System.out.println(ex);
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        Actualizar = new javax.swing.JMenuItem();
        Inactivar = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        cdoSubjects = new javax.swing.JComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDescription = new javax.swing.JTextArea();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        cboPeriod = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        rbAcademic = new javax.swing.JRadioButton();
        jLabel9 = new javax.swing.JLabel();
        rbInstitutional = new javax.swing.JRadioButton();
        jLabel10 = new javax.swing.JLabel();
        spiPercentage = new javax.swing.JSpinner();
        jLabel11 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txtId = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbAchievements = new javax.swing.JTable();
        btnInsert = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();

        Actualizar.setText("Actualizar");
        Actualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ActualizarActionPerformed(evt);
            }
        });
        jPopupMenu1.add(Actualizar);

        Inactivar.setText("Inactivar");
        jPopupMenu1.add(Inactivar);

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("LOGROS");

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel1.setText("Nombre");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel2.setText("ID_Materia");

        cdoSubjects.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cdoSubjectsActionPerformed(evt);
            }
        });

        txtDescription.setColumns(20);
        txtDescription.setRows(5);
        jScrollPane1.setViewportView(txtDescription);

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel3.setText("Descripcion");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel4.setText("Nombre de materia");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel5.setText("jLabel5");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel6.setText("ID_Periodo");

        cboPeriod.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboPeriodItemStateChanged(evt);
            }
        });
        cboPeriod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboPeriodActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel7.setText("Nombre del periodo");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel8.setText("jLabel8");

        buttonGroup1.add(rbAcademic);
        rbAcademic.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        rbAcademic.setText("Academico");
        rbAcademic.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbAcademicActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel9.setText("Tipo de logro");

        buttonGroup1.add(rbInstitutional);
        rbInstitutional.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        rbInstitutional.setText("Institucional");
        rbInstitutional.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbInstitutionalActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel10.setText("Porcentaje");

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel11.setText("%");

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel13.setText("Id Materia:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)
                            .addComponent(jLabel9))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(cdoSubjects, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel4))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(cboPeriod, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel7))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(rbAcademic)
                                .addGap(2, 2, 2)
                                .addComponent(rbInstitutional)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(16, 16, 16)
                                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jLabel10)
                                .addGap(33, 33, 33)
                                .addComponent(spiPercentage, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(4, 4, 4)
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(9, 9, 9)
                                        .addComponent(jLabel1))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addContainerGap()
                                        .addComponent(jLabel13)))
                                .addGap(26, 26, 26)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel13)
                            .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(14, 14, 14)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(jLabel2)
                                .addGap(22, 22, 22)
                                .addComponent(jLabel6)
                                .addGap(20, 20, 20)
                                .addComponent(jLabel9))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cdoSubjects, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(1, 1, 1)
                                        .addComponent(jLabel4)))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cboPeriod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(1, 1, 1)
                                        .addComponent(jLabel7)))
                                .addGap(13, 13, 13)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(rbAcademic)
                                    .addComponent(rbInstitutional))))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addComponent(jLabel10))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(spiPercentage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(62, 62, 62)
                        .addComponent(jLabel5)
                        .addGap(22, 22, 22)
                        .addComponent(jLabel8))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        tbAchievements.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbAchievements.setComponentPopupMenu(jPopupMenu1);
        jScrollPane2.setViewportView(tbAchievements);

        btnInsert.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        btnInsert.setText("REGISTRAR");
        btnInsert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInsertActionPerformed(evt);
            }
        });

        btnUpdate.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        btnUpdate.setText("ACTUALIZAR");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        jLabel12.setText("Buscar:");

        txtSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchActionPerformed(evt);
            }
        });
        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addComponent(btnInsert, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel12)
                                .addGap(14, 14, 14)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 542, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(25, 25, 25)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnInsert)
                    .addComponent(btnUpdate))
                .addGap(19, 19, 19)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnInsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertActionPerformed
        insert();
        cargar("");
    }//GEN-LAST:event_btnInsertActionPerformed

    private void cboPeriodItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboPeriodItemStateChanged
        String period = (String) cboPeriod.getSelectedItem();
        String sql = "SELECT Nombre,idperiodo FROM periodo WHERE idperiodo = " + period;
        ResultSet rs = cone.consultDB(sql);

        try {
            while (rs.next()) {
                jLabel8.setText(rs.getString("Nombre"));
            }

        } catch (Exception e) {
            System.out.println("Error" + e);
        }
    }//GEN-LAST:event_cboPeriodItemStateChanged

    private void cboPeriodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboPeriodActionPerformed

    }//GEN-LAST:event_cboPeriodActionPerformed

    private void cdoSubjectsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cdoSubjectsActionPerformed
        String mate = (String) cdoSubjects.getSelectedItem();
        String sql = "SELECT Nombre FROM materia WHERE idMateria = " + mate;
        ResultSet rs = cone.consultDB(sql);

        try {
            while (rs.next()) {
                jLabel5.setText(rs.getString("Nombre"));
            }

        } catch (Exception e) {
            System.out.println("Error" + e);
        }
    }//GEN-LAST:event_cdoSubjectsActionPerformed

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyReleased
        cargar(txtSearch.getText());
    }//GEN-LAST:event_txtSearchKeyReleased

    private void rbInstitutionalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbInstitutionalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rbInstitutionalActionPerformed

    private void rbAcademicActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbAcademicActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rbAcademicActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchActionPerformed

    private void ActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ActualizarActionPerformed
        // TODO add your handling code here:
        selectUpdate();
        btnUpdate.setEnabled(true);
    }//GEN-LAST:event_ActualizarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem Actualizar;
    private javax.swing.JMenuItem Inactivar;
    private javax.swing.JButton btnInsert;
    private javax.swing.JButton btnUpdate;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox cboPeriod;
    private javax.swing.JComboBox cdoSubjects;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JRadioButton rbAcademic;
    private javax.swing.JRadioButton rbInstitutional;
    private javax.swing.JSpinner spiPercentage;
    private javax.swing.JTable tbAchievements;
    private javax.swing.JTextArea txtDescription;
    private javax.swing.JLabel txtId;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
