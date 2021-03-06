package Polypro;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;
import dao.KhoaHocDAO;
import dao.thongKeDAO;
import model.khoaHoc;
import dao.thongKeDAO;
import helper.Auth;
import helper.MgsBox;

/**
 *
 * @author DELL-PC
 */
public class thongKejDialog extends javax.swing.JFrame {

    /**
     * Creates new form thongKejDialog
     */
    public thongKejDialog() {
        initComponents();
        init();
        
    }

    void setselectTab(int index) {
        tabs.setSelectedIndex(index);
    }

    thongKejDialog(int index) {
        initComponents();
        tabs.setSelectedIndex(index);
        init();
        
    }
    void init(){
        fillComboBoxkhoaHoc();
        fillTableBangDiem();
        fillTableNguoiHoc();
        fillTablekhoaHoc();
        fillComboBoxNam();
        tabs.getSelectedIndex();
        
    }
    thongKeDAO dao =new thongKeDAO();
    KhoaHocDAO khdao = new KhoaHocDAO();
    void fillComboBoxkhoaHoc() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboKhoaHoc3.getModel(); //kết nối cbo với model
        model.removeAllElements(); //xóa tất cả item
        List<khoaHoc> list = khdao.selectAll();
        for (khoaHoc kh : list) {
            model.addElement(kh);
        }
        
    }
    void fillComboBoxNam() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboNam3.getModel();
        model.removeAllElements();
        List<Integer> list = dao.getNamKhaiGiang();
        for(Integer year: list){
            model.addElement(year);
        }
    }

    //xóa bảng điểm, điền dữ liệu vào bảng điểm theo MaKH
    void fillTableBangDiem() {
        DefaultTableModel model = (DefaultTableModel) tblBangDiem3.getModel();
        model.setRowCount(0);     
         khoaHoc kh = (khoaHoc) cboKhoaHoc3.getSelectedItem();
        List<Object[]> list = dao.getBangDiem(kh.getMaKH()); //lấy về 1 <Object[]> list theo MaKH
        for (Object[] row : list) {
            model.addRow(row);
        }
    }

    //xóa bảng người học, đièm dữ liệu vào bảng người học
    void fillTableNguoiHoc() {
        DefaultTableModel model = (DefaultTableModel) tblNguoiHoc3.getModel();
        model.setRowCount(0);
        List<Object[]> list = dao.getNguoiHoc();
        for (Object[] row : list) {
            model.addRow(row);
        }
    }

    //xóa bảng tổng hợp điểm, điền dữ liệu vào bảng tổng hợp điểm
    void fillTablekhoaHoc() {
        DefaultTableModel model = (DefaultTableModel) tonghopdiem.getModel();
        model.setRowCount(0);
        List<Object[]> list = dao.getDiemTheoChuyenDe();
        for (Object[] row : list) {
            model.addRow(row);
        }
    }

    //xóa bảng doanh thu, điền dữ liệu vào bảng doanh thu theo năm tương ứng
    void fillTableDoanhThu() {
        DefaultTableModel model = (DefaultTableModel) tblDoanhThu3.getModel();
        model.setRowCount(0);
        int nam = Integer.parseInt(cboNam3.getSelectedItem().toString());
        List<Object[]> list = dao.getDoanhThu(nam);
        for (Object[] row : list) {
            model.addRow(row);
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

        jLabel20 = new javax.swing.JLabel();
        tabs = new javax.swing.JTabbedPane();
        pnlNguoiHoc3 = new javax.swing.JPanel();
        jScrollPane13 = new javax.swing.JScrollPane();
        tblNguoiHoc3 = new javax.swing.JTable();
        pnlBangDiem3 = new javax.swing.JPanel();
        jScrollPane14 = new javax.swing.JScrollPane();
        tblBangDiem3 = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        cboKhoaHoc3 = new javax.swing.JComboBox<>();
        pnlKhoaHoc3 = new javax.swing.JPanel();
        jScrollPane15 = new javax.swing.JScrollPane();
        tonghopdiem = new javax.swing.JTable();
        pnlDoanhThu3 = new javax.swing.JPanel();
        pnlTruongPhong3 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        cboNam3 = new javax.swing.JComboBox<>();
        jScrollPane16 = new javax.swing.JScrollPane();
        tblDoanhThu3 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(0, 0, 204));
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setText("TỔNG HỢP THỐNG KÊ");

        tabs.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabsMouseClicked(evt);
            }
        });

        pnlNguoiHoc3.setLayout(new java.awt.BorderLayout());

        tblNguoiHoc3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "NĂM", "SỐ NGƯỜI HỌC", "ĐẦU TIÊN", "SAU CÙNG"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblNguoiHoc3.setRowHeight(25);
        jScrollPane13.setViewportView(tblNguoiHoc3);

        pnlNguoiHoc3.add(jScrollPane13, java.awt.BorderLayout.CENTER);

        tabs.addTab("NGƯỜI HỌC", new javax.swing.ImageIcon(getClass().getResource("/icon/User group.png")), pnlNguoiHoc3, "NGƯỜI HỌC"); // NOI18N

        pnlBangDiem3.setLayout(new java.awt.BorderLayout());

        tblBangDiem3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "MÃ NH", "HỌ VÀ TÊN", "ĐIỂM", "XẾP LOẠI"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblBangDiem3.setRowHeight(25);
        jScrollPane14.setViewportView(tblBangDiem3);

        pnlBangDiem3.add(jScrollPane14, java.awt.BorderLayout.CENTER);

        jPanel7.setLayout(new java.awt.BorderLayout());

        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Card file.png"))); // NOI18N
        jLabel16.setText("KHÓA HỌC: ");
        jPanel7.add(jLabel16, java.awt.BorderLayout.LINE_START);

        cboKhoaHoc3.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboKhoaHoc3ItemStateChanged(evt);
            }
        });
        cboKhoaHoc3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboKhoaHoc3ActionPerformed(evt);
            }
        });
        cboKhoaHoc3.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                cboKhoaHoc3PropertyChange(evt);
            }
        });
        jPanel7.add(cboKhoaHoc3, java.awt.BorderLayout.CENTER);

        pnlBangDiem3.add(jPanel7, java.awt.BorderLayout.PAGE_START);

        tabs.addTab("BẢNG ĐIỂM", new javax.swing.ImageIcon(getClass().getResource("/icon/Numbered list.png")), pnlBangDiem3, "BẢNG ĐIỂM"); // NOI18N

        pnlKhoaHoc3.setLayout(new java.awt.BorderLayout());

        tonghopdiem.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "CHUYÊN ĐỀ", "TỔNG SỐ HV", "CAO NHẤT", "THẤP NHẤT", "ĐIỂM TRUNG BÌNH"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tonghopdiem.setRowHeight(25);
        jScrollPane15.setViewportView(tonghopdiem);

        pnlKhoaHoc3.add(jScrollPane15, java.awt.BorderLayout.CENTER);

        tabs.addTab("TỔNG HỢP ĐIỂM", new javax.swing.ImageIcon(getClass().getResource("/icon/Clien list.png")), pnlKhoaHoc3, "TỔNG HỢP ĐIỂM"); // NOI18N

        pnlDoanhThu3.setLayout(new java.awt.CardLayout());

        pnlTruongPhong3.setLayout(new java.awt.BorderLayout());

        jPanel8.setLayout(new java.awt.BorderLayout());

        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Date.png"))); // NOI18N
        jLabel17.setText("NĂM: ");
        jPanel8.add(jLabel17, java.awt.BorderLayout.LINE_START);

        cboNam3.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboNam3ItemStateChanged(evt);
            }
        });
        cboNam3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboNam3ActionPerformed(evt);
            }
        });
        cboNam3.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                cboNam3PropertyChange(evt);
            }
        });
        jPanel8.add(cboNam3, java.awt.BorderLayout.CENTER);

        pnlTruongPhong3.add(jPanel8, java.awt.BorderLayout.PAGE_START);

        tblDoanhThu3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "CHUYÊN ĐỀ", "SỐ KHÓA", "SỐ HV", "DOANH THU", "HP CAO NHẤT", "HP THẤP NHẤT", "HP TRUNG BÌNH"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblDoanhThu3.setRowHeight(25);
        jScrollPane16.setViewportView(tblDoanhThu3);

        pnlTruongPhong3.add(jScrollPane16, java.awt.BorderLayout.CENTER);

        pnlDoanhThu3.add(pnlTruongPhong3, "card1");

        tabs.addTab("DOANH THU", new javax.swing.ImageIcon(getClass().getResource("/icon/Coins.png")), pnlDoanhThu3, "DOANH THU"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, 689, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(tabs, javax.swing.GroupLayout.PREFERRED_SIZE, 677, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(404, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(78, 78, 78)
                    .addComponent(tabs, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(78, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cboKhoaHoc3ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboKhoaHoc3ItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_cboKhoaHoc3ItemStateChanged

    private void cboKhoaHoc3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboKhoaHoc3ActionPerformed
        // TODO add your handling code here:
        fillTableBangDiem();
    }//GEN-LAST:event_cboKhoaHoc3ActionPerformed

    private void cboKhoaHoc3PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_cboKhoaHoc3PropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_cboKhoaHoc3PropertyChange

    private void cboNam3ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboNam3ItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_cboNam3ItemStateChanged

    private void cboNam3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboNam3ActionPerformed
        // TODO add your handling code here:
        if (Auth.isManager()) {
          fillTableDoanhThu();  
        }
        
    }//GEN-LAST:event_cboNam3ActionPerformed

    private void cboNam3PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_cboNam3PropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_cboNam3PropertyChange

    private void tabsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabsMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tabsMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(thongKejDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(thongKejDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(thongKejDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(thongKejDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new thongKejDialog().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cboKhoaHoc3;
    private javax.swing.JComboBox<String> cboNam3;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JScrollPane jScrollPane16;
    private javax.swing.JPanel pnlBangDiem3;
    private javax.swing.JPanel pnlDoanhThu3;
    private javax.swing.JPanel pnlKhoaHoc3;
    private javax.swing.JPanel pnlNguoiHoc3;
    private javax.swing.JPanel pnlTruongPhong3;
    private javax.swing.JTabbedPane tabs;
    private javax.swing.JTable tblBangDiem3;
    private javax.swing.JTable tblDoanhThu3;
    private javax.swing.JTable tblNguoiHoc3;
    private javax.swing.JTable tonghopdiem;
    // End of variables declaration//GEN-END:variables


    

    
}
