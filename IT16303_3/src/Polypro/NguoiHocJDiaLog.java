package Polypro;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import helper.Auth;
import dao.NguoiHocDAO;
import helper.MgsBox;
import helper.XDate;
import helper.XImage;
import static java.awt.Color.blue;
import static java.awt.Color.pink;
import static java.awt.Color.white;
import java.util.List;
import static javax.print.attribute.Size2DSyntax.MM;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import model.nhanVien;
import model.nguoiHoc;

/**
 *
 * @author Sieu Nhan Bay
 */
public class NguoiHocJDiaLog extends javax.swing.JDialog {

    /**
     * Creates new form dangNhapJDialog
     */
    public NguoiHocJDiaLog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();  
        init();
    }
   NguoiHocDAO dao =new NguoiHocDAO();
    void init(){
        this.fillTable();
        this.ClearForm();
        this.row=-1;
        setLocationRelativeTo(null);
    }
    private int row;
    void fillTable(){
        DefaultTableModel model = (DefaultTableModel) tblGridView2.getModel();
        model.setRowCount(0);   //đưa số dòng bảng về 0 (xóa bảng)
        try {
            //tìm người học theo keywork
            //nếu ko có keywork thì sẽ là tất cả người học
            String keyword = txtTimKiem2.getText();
            List<nguoiHoc> list = dao.selectByKeyword(keyword);
            //đưa list tìm được lên bảng
            for (nguoiHoc nh : list) {
                Object[] row = {
                    nh.getMaNH(),
                    nh.getHoTen(),
                    nh.isGioiTinh() ? "Nam" : "Nữ",
                    XDate.toString(nh.getNgaySinh()),
                    nh.getDienThoai(),
                    nh.getEmail(),
                    nh.getMaNV(),
                    XDate.toString(nh.getNgayDK())
                };
                model.addRow(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    void updateStatus(){
        boolean edit=(this.row>=0);
        boolean first=(this.row==0);
        boolean last=(this.row==tblGridView2.getRowCount()-1);
        //Trạng Thái Form
        txtMaNH2.setEnabled(!edit);
        btnInsert2.setEnabled(!edit);
        btnUpdate2.setEnabled(edit);
        btnDelete2.setEnabled(edit);
        System.out.println(this.row);
        //Trạng thái diều hướng
        btnFirst2.setEnabled(edit&&!first);
        btnPrev2.setEnabled(edit&&!first);
        btnNext2.setEnabled(edit&&!last);
        btnLast2.setEnabled(edit&&!last);
    }
void setForm(nguoiHoc model) {
        txtMaNH2.setText(model.getMaNH());
        txtHoTen2.setText(model.getHoTen());
        if (model.isGioiTinh()==true) {
        rdonam.setSelected(true);
        }
        else{
            rdonu.setSelected(true);
        }
         
        txtngaysinh2.setText(XDate.toString(model.getNgaySinh()));
        txtDienThoai2.setText(model.getDienThoai());
        txtEmail2.setText(model.getEmail());
        txtGhiChu2.setText(model.getGhiChu());
    }

    //lấy thông trên form  cho vào đối tượng nguoiHoc
    //return nguoiHoc
    nguoiHoc getForm() {
        nguoiHoc model = new nguoiHoc();
        model.setMaNH(txtMaNH2.getText());
        model.setHoTen(txtHoTen2.getText());
        model.setGioiTinh(rdonam.isSelected());
        model.setNgaySinh(XDate.toDate(txtngaysinh2.getText()));
        model.setDienThoai(txtDienThoai2.getText());
        model.setEmail(txtEmail2.getText());
        model.setGhiChu(txtGhiChu2.getText());
        model.setMaNV(Auth.USER.getMaNV());
        model.setNgayDK(XDate.now());     //ngày đăng kí luôn là ngày hôm nay dù có sửa trên form
        return model;
    }
    void ClearForm(){
        nguoiHoc cd =new nguoiHoc();
        this.setForm(cd);
        this.row=-1;
        this.updateStatus();
    }
    void edit(){
        String manh =(String) tblGridView2.getValueAt(this.row,0);
        nguoiHoc nh =dao.selectById(manh);
        this.setForm(nh);
        tabs2.setSelectedIndex(0);
        this.updateStatus();
    }
    void insert(){
        nguoiHoc nh =getForm();
                    try {
                dao.insert(nh);
                this.fillTable();
                this.ClearForm();
                MgsBox.alert(this,"Thêm Thành CÔng!");
            } catch (Exception e) {
                MgsBox.alert(this,"Lỗi truy vấn");
                e.printStackTrace();
            }
        
    }
    void update(){
         nguoiHoc nh =getForm();
            try {
                dao.update(nh);
                this.fillTable();
                MgsBox.alert(this,"Sửa Thành CÔng!");
            } catch (Exception e) {
                MgsBox.alert(this,"Lỗi truy vấn");
                e.printStackTrace();
            }
        
    }
    void delete() {
        if (Auth.isManager()) {
            MgsBox.alert(this, "Trưởng Phòng mới được xóa !");
        } else {
            String Manv = txtMaNH2.getText();
            if (Manv.equals(Auth.USER.getMaNV())) {
                MgsBox.alert(this, "Bạn không thể xóa chính bạn !");
            } else if (MgsBox.confirm(this, "Ban muốn xóa Nhân viên này")) {
                try {
                    dao.delete(Manv);
                    this.fillTable();
                    this.ClearForm();
                    MgsBox.alert(this, "Xóa Thành Công!");
                } catch (Exception e) {
                    MgsBox.alert(this, "Xóa Thất bại!");
                }
            }
        }
    }
    void timKiem(){
        this.fillTable();
        this.ClearForm();
        updateStatus();
    }
    void fist(){
        this.row=0;
        this.edit();
    }
    void last(){
        if(this.row<tblGridView2.getRowCount()-1){
            this.row=tblGridView2.getRowCount()-1;
        this.edit();
        }
    }
    void prev(){
        if (this.row>0) {
            this.row--;
            this.edit();
        }
    }
    void next(){
       this.row++;
       this.edit();
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
        buttonGroup2 = new javax.swing.ButtonGroup();
        jPanel2 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        tabs2 = new javax.swing.JTabbedPane();
        pnlEdit2 = new javax.swing.JPanel();
        txtMaNH2 = new javax.swing.JTextField();
        txtHoTen2 = new javax.swing.JTextField();
        txtEmail2 = new javax.swing.JTextField();
        txtDienThoai2 = new javax.swing.JTextField();
        jScrollPane5 = new javax.swing.JScrollPane();
        txtGhiChu2 = new javax.swing.JTextArea();
        btnInsert2 = new javax.swing.JButton();
        btnUpdate2 = new javax.swing.JButton();
        btnDelete2 = new javax.swing.JButton();
        btnClear2 = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        btnFirst2 = new javax.swing.JButton();
        btnPrev2 = new javax.swing.JButton();
        btnNext2 = new javax.swing.JButton();
        btnLast2 = new javax.swing.JButton();
        rdonu = new javax.swing.JRadioButton();
        rdonam = new javax.swing.JRadioButton();
        txtngaysinh2 = new javax.swing.JTextField();
        pnlList2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        txtTimKiem2 = new javax.swing.JTextField();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblGridView2 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("ĐĂNG NHẬP HỆ THỐNG");
        setAlwaysOnTop(true);
        setBackground(new java.awt.Color(255, 255, 255));
        setResizable(false);
        setSize(new java.awt.Dimension(431, 199));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new java.awt.BorderLayout());
        getContentPane().add(jPanel2, java.awt.BorderLayout.LINE_START);

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(0, 0, 204));
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("QUẢN LÝ NGƯỜI HỌC");
        getContentPane().add(jLabel18, java.awt.BorderLayout.PAGE_START);

        txtMaNH2.setName("Mã người học"); // NOI18N
        txtMaNH2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaNH2ActionPerformed(evt);
            }
        });

        txtHoTen2.setName("Họ và tên"); // NOI18N

        txtEmail2.setName("Địa chỉ email"); // NOI18N

        txtDienThoai2.setName("Điện thoại"); // NOI18N

        txtGhiChu2.setColumns(20);
        txtGhiChu2.setRows(5);
        jScrollPane5.setViewportView(txtGhiChu2);

        btnInsert2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnInsert2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Add.png"))); // NOI18N
        btnInsert2.setText("Thêm");
        btnInsert2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInsert2ActionPerformed(evt);
            }
        });

        btnUpdate2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnUpdate2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Notes.png"))); // NOI18N
        btnUpdate2.setText("Sửa");
        btnUpdate2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdate2ActionPerformed(evt);
            }
        });

        btnDelete2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnDelete2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Delete.png"))); // NOI18N
        btnDelete2.setText("Xóa");
        btnDelete2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDelete2ActionPerformed(evt);
            }
        });

        btnClear2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnClear2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Unordered list.png"))); // NOI18N
        btnClear2.setText("Mới");
        btnClear2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClear2ActionPerformed(evt);
            }
        });

        jLabel19.setText("Ghi chú");

        jLabel20.setText("Điện thoại");

        jLabel21.setText("Địa chỉ Email");

        jLabel22.setText("Ngày sinh (dd/MM/yyyy)");

        jLabel23.setText("Giới tính");

        jLabel24.setText("Họ và tên");

        jLabel25.setText("Mã người học");

        btnFirst2.setBackground(new java.awt.Color(51, 51, 255));
        btnFirst2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/dau.png"))); // NOI18N
        btnFirst2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirst2ActionPerformed(evt);
            }
        });

        btnPrev2.setBackground(new java.awt.Color(51, 51, 255));
        btnPrev2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/lui.png"))); // NOI18N
        btnPrev2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrev2ActionPerformed(evt);
            }
        });

        btnNext2.setBackground(new java.awt.Color(255, 153, 0));
        btnNext2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/tien.png"))); // NOI18N
        btnNext2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNext2ActionPerformed(evt);
            }
        });

        btnLast2.setBackground(new java.awt.Color(255, 153, 0));
        btnLast2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/cuoi.png"))); // NOI18N
        btnLast2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLast2ActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdonu);
        rdonu.setText("Nữ");

        buttonGroup1.add(rdonam);
        rdonam.setText("Nam");

        javax.swing.GroupLayout pnlEdit2Layout = new javax.swing.GroupLayout(pnlEdit2);
        pnlEdit2.setLayout(pnlEdit2Layout);
        pnlEdit2Layout.setHorizontalGroup(
            pnlEdit2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEdit2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlEdit2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5)
                    .addComponent(txtMaNH2)
                    .addComponent(txtHoTen2)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlEdit2Layout.createSequentialGroup()
                        .addGroup(pnlEdit2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtDienThoai2)
                            .addGroup(pnlEdit2Layout.createSequentialGroup()
                                .addGroup(pnlEdit2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel25)
                                    .addComponent(jLabel24)
                                    .addComponent(jLabel23)
                                    .addComponent(jLabel20)
                                    .addComponent(jLabel19)
                                    .addGroup(pnlEdit2Layout.createSequentialGroup()
                                        .addComponent(rdonu)
                                        .addGap(38, 38, 38)
                                        .addComponent(rdonam)))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(18, 18, 18)
                        .addGroup(pnlEdit2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel22)
                            .addComponent(txtEmail2, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
                            .addComponent(jLabel21)
                            .addComponent(txtngaysinh2)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlEdit2Layout.createSequentialGroup()
                        .addComponent(btnInsert2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnUpdate2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnDelete2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnClear2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 118, Short.MAX_VALUE)
                        .addComponent(btnFirst2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPrev2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnNext2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLast2)))
                .addContainerGap())
        );
        pnlEdit2Layout.setVerticalGroup(
            pnlEdit2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEdit2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel25)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtMaNH2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel24)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtHoTen2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlEdit2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(jLabel22))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlEdit2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdonu)
                    .addComponent(rdonam)
                    .addComponent(txtngaysinh2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlEdit2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(jLabel21))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlEdit2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDienThoai2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtEmail2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel19)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlEdit2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnInsert2)
                    .addComponent(btnUpdate2)
                    .addComponent(btnDelete2)
                    .addComponent(btnClear2)
                    .addComponent(btnFirst2)
                    .addComponent(btnPrev2)
                    .addComponent(btnNext2)
                    .addComponent(btnLast2))
                .addGap(26, 26, 26))
        );

        tabs2.addTab("CẬP NHẬT", new javax.swing.ImageIcon(getClass().getResource("/icon/Edit.png")), pnlEdit2, "Cập nhật"); // NOI18N

        pnlList2.setLayout(new java.awt.BorderLayout());

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("TÌM KIẾM"));
        jPanel4.setLayout(new java.awt.BorderLayout());

        txtTimKiem2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimKiem2KeyReleased(evt);
            }
        });
        jPanel4.add(txtTimKiem2, java.awt.BorderLayout.CENTER);

        pnlList2.add(jPanel4, java.awt.BorderLayout.PAGE_START);

        tblGridView2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "MÃ NH", "HỌ VÀ TÊN", "GIỚI TÍNH", "NGÀY SINH", "ĐIỆN THOẠI", "EMAIL", "MÃ NV", "NGÀY ĐK"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblGridView2.setRowHeight(25);
        tblGridView2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblGridView2MouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(tblGridView2);

        pnlList2.add(jScrollPane6, java.awt.BorderLayout.CENTER);

        tabs2.addTab("DANH SÁCH", new javax.swing.ImageIcon(getClass().getResource("/icon/List.png")), pnlList2, "Danh sách"); // NOI18N

        getContentPane().add(tabs2, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnInsert2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsert2ActionPerformed
        // TODO add your handling code here:
        insert();
    }//GEN-LAST:event_btnInsert2ActionPerformed

    private void btnUpdate2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdate2ActionPerformed
        // TODO add your handling code here:
        update();
    }//GEN-LAST:event_btnUpdate2ActionPerformed

    private void btnDelete2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDelete2ActionPerformed
        // TODO add your handling code here:
        delete();
    }//GEN-LAST:event_btnDelete2ActionPerformed

    private void btnClear2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClear2ActionPerformed
        // TODO add your handling code here:
        ClearForm();
        this.row=-1;
        updateStatus();
    }//GEN-LAST:event_btnClear2ActionPerformed

    private void btnFirst2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirst2ActionPerformed
        // TODO add your handling code here:
        this.fist();
    }//GEN-LAST:event_btnFirst2ActionPerformed

    private void btnPrev2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrev2ActionPerformed
        // TODO add your handling code here:
        this.prev();
    }//GEN-LAST:event_btnPrev2ActionPerformed

    private void btnNext2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNext2ActionPerformed
        // TODO add your handling code here:
        this.next();
    }//GEN-LAST:event_btnNext2ActionPerformed

    private void btnLast2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLast2ActionPerformed
        // TODO add your handling code here:
        this.last();
    }//GEN-LAST:event_btnLast2ActionPerformed

    private void tblGridView2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblGridView2MouseClicked
        // TODO add your handling code here:
         if (evt.getClickCount()==2) {
            this.row=tblGridView2.getSelectedRow();
            this.edit();
        }
    }//GEN-LAST:event_tblGridView2MouseClicked

    private void txtMaNH2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaNH2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaNH2ActionPerformed

    private void txtTimKiem2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiem2KeyReleased
        // TODO add your handling code here:
      this.fillTable();
        this.ClearForm();
        this.row=-1;  
    }//GEN-LAST:event_txtTimKiem2KeyReleased

    
   
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
            java.util.logging.Logger.getLogger(NguoiHocJDiaLog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NguoiHocJDiaLog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NguoiHocJDiaLog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NguoiHocJDiaLog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                NguoiHocJDiaLog dialog = new NguoiHocJDiaLog(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClear2;
    private javax.swing.JButton btnDelete2;
    private javax.swing.JButton btnFirst2;
    private javax.swing.JButton btnInsert2;
    private javax.swing.JButton btnLast2;
    private javax.swing.JButton btnNext2;
    private javax.swing.JButton btnPrev2;
    private javax.swing.JButton btnUpdate2;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JPanel pnlEdit2;
    private javax.swing.JPanel pnlList2;
    private javax.swing.JRadioButton rdonam;
    private javax.swing.JRadioButton rdonu;
    private javax.swing.JTabbedPane tabs2;
    private javax.swing.JTable tblGridView2;
    private javax.swing.JTextField txtDienThoai2;
    private javax.swing.JTextField txtEmail2;
    private javax.swing.JTextArea txtGhiChu2;
    private javax.swing.JTextField txtHoTen2;
    private javax.swing.JTextField txtMaNH2;
    private javax.swing.JTextField txtTimKiem2;
    private javax.swing.JTextField txtngaysinh2;
    // End of variables declaration//GEN-END:variables
}
