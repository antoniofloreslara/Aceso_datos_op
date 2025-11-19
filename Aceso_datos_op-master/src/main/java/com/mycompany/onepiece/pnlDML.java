/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.onepiece;

import java.sql.*;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author MEDAC
 */
public class pnlDML extends javax.swing.JPanel {

    /**
     * Creates new form pnlDML
     */
    public pnlDML() {
        initComponents();
        cargarTabla();
    }

    private void insertar() {
    Connection con = null;
    try {
        con = BBDD.getConexion();
        if (con == null) {
            JOptionPane.showMessageDialog(this, "ERROR: No hay conexión a la base de datos.");
            return;
        }

        String idPersonaje = JOptionPane.showInputDialog("ID del Personaje:");
        String nombre = JOptionPane.showInputDialog("Nombre del personaje:");
        String reinoOrigen = JOptionPane.showInputDialog("Reino de origen:");
        String tipoPersonaje = JOptionPane.showInputDialog("Tipo de personaje:");

        String sql = "INSERT INTO Personaje (id_personaje, nombre, reino_origen, tipo_personaje) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, Integer.parseInt(idPersonaje));
        ps.setString(2, nombre);
        ps.setString(3, reinoOrigen);
        ps.setString(4, tipoPersonaje);

        ps.executeUpdate(); // Aquí salta el trigger si hay conflicto

        JOptionPane.showMessageDialog(this, "Registro insertado correctamente.");
        cargarTabla();

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "ERROR al insertar: " + e.getMessage());
        try { if (con != null) con.rollback(); } catch (SQLException ex) {}
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "ERROR: " + e.getMessage());
    }
  }

    private void actualizar() {
        try {
            Connection con = BBDD.getConexion();
            if (con == null) {
                JOptionPane.showMessageDialog(this, "ERROR: No hay conexión a la base de datos.");
                return;
            }

            String idPersonaje = JOptionPane.showInputDialog("ID del Personaje a modificar:");
            String nuevoNombre = JOptionPane.showInputDialog("Nuevo nombre:");
            String nuevoReino = JOptionPane.showInputDialog("Nuevo reino de origen:");
            String nuevoTipo = JOptionPane.showInputDialog("Nuevo tipo de personaje:");

            String sql = "UPDATE Personaje SET nombre=?, reino_origen=?, tipo_personaje=? WHERE id_personaje=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, nuevoNombre);
            ps.setString(2, nuevoReino);
            ps.setString(3, nuevoTipo);
            ps.setInt(4, Integer.parseInt(idPersonaje));

            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Registro actualizado correctamente.");
            cargarTabla();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "ERROR al actualizar: " + e.getMessage());
        }
    }

    private void eliminar() {
        try {
            Connection con = BBDD.getConexion();
            if (con == null) {
                JOptionPane.showMessageDialog(this, "ERROR: No hay conexión a la base de datos.");
                return;
            }

            String idPersonaje = JOptionPane.showInputDialog("ID del Personaje a eliminar:");

            String sql = "DELETE FROM Personaje WHERE id_personaje=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(idPersonaje));

            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Registro eliminado correctamente.");
            cargarTabla();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "ERROR al eliminar: " + e.getMessage());
        }
    }

    private void cargarTabla() {
        try {
            Connection con = BBDD.getConexion();
            if (con == null) {
                JOptionPane.showMessageDialog(this, "ERROR: No hay conexión a la base de datos.");
                return;
            }

            String sql = "SELECT * FROM Personaje";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            DefaultTableModel modelo = new DefaultTableModel(
                    new String[]{"ID Personaje", "Nombre", "Reino de origen", "Tipo"}, 0
            );

            while (rs.next()) {
                modelo.addRow(new Object[]{
                    rs.getInt("id_personaje"),
                    rs.getString("nombre"),
                    rs.getString("reino_origen"),
                    rs.getString("tipo_personaje")
                });
            }

            jTable1.setModel(modelo);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "ERROR al cargar tabla: " + e.getMessage());
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
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        btnDelete = new javax.swing.JButton();
        btnInsert = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        btnPirata = new javax.swing.JButton();
        btnMarina = new javax.swing.JButton();

        jPanel1.setLayout(new java.awt.GridBagLayout());

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Nombre", "Rango", "Personaje_ID", "Arma_ID"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel1.add(jScrollPane1, gridBagConstraints);

        jPanel2.setLayout(new java.awt.GridBagLayout());

        btnDelete.setText("DELETE");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        jPanel2.add(btnDelete, gridBagConstraints);

        btnInsert.setText("INSERT");
        btnInsert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInsertActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanel2.add(btnInsert, gridBagConstraints);

        btnUpdate.setText("UPDATE");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        jPanel2.add(btnUpdate, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jPanel1.add(jPanel2, gridBagConstraints);

        jPanel3.setLayout(new java.awt.GridBagLayout());

        btnPirata.setText("PIRATAS");
        btnPirata.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPirataActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        jPanel3.add(btnPirata, gridBagConstraints);

        btnMarina.setText("MARINA");
        btnMarina.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMarinaActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        jPanel3.add(btnMarina, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        jPanel1.add(jPanel3, gridBagConstraints);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnInsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertActionPerformed
        insertar();
    }//GEN-LAST:event_btnInsertActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        actualizar();
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        eliminar();
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnPirataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPirataActionPerformed
        Piratas pt = new Piratas();
        pt.setVisible(true);
    }//GEN-LAST:event_btnPirataActionPerformed

    private void btnMarinaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMarinaActionPerformed
        Marina mr = new Marina();
        mr.setVisible(true);
    }//GEN-LAST:event_btnMarinaActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnInsert;
    private javax.swing.JButton btnMarina;
    private javax.swing.JButton btnPirata;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
