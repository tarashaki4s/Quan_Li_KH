/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import helper.jdbcHelper;
import model.nhanVien;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author HP
 */
public class NhanVienDAO extends eduSysDAO<nhanVien, String>{
    String INSERT_SQL ="INSERT INTO NhanVien(MaNV,MatKhau,HoTen,VaiTro,Gioitinh)VALUES(?,?,?,?,?)";
    String UPDATE_SQL="UPDATE NhanVien SET MatKhau=?,HoTen=?,VaiTro=?,Gioitinh=? WHERE MaNV=?";
    String DELETE_SQL="DELETE FROM NhanVIen WHERE MANV=?";
    String SELECT_ALL_SLQ="SELECT*FROM NhanVien";
    String SELECT_BY_ID_SQL ="SELECT*FROM NhanVien where MaNV=?";
    
    
    @Override
    public void insert(nhanVien entity) {
        try {
            jdbcHelper.executeUpdate(INSERT_SQL,
                    entity.getMaNV(),entity.getMatKhau(),entity.getHoTen(),entity.isVaiTro(),entity.getGioitinh());
        } catch (Exception ex) {
            Logger.getLogger(NhanVienDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(nhanVien entity) {
        try {
            jdbcHelper.executeUpdate(UPDATE_SQL,
                    entity.getMatKhau(),entity.getHoTen(),entity.isVaiTro(),entity.getGioitinh(),entity.getMaNV());
        } catch (Exception ex) {
            Logger.getLogger(NhanVienDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(String id) {
        try {
            jdbcHelper.executeUpdate(DELETE_SQL,id);
        } catch (Exception ex) {
            Logger.getLogger(NhanVienDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<nhanVien> selectAll() {
        return this.selectBySql(SELECT_ALL_SLQ);
    }

    @Override
    public nhanVien selectById(String id) {
       List<nhanVien> list=this.selectBySql(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    protected List<nhanVien> selectBySql(String sql, Object... args) {
        List<nhanVien> list = new ArrayList<nhanVien>();
        try {
            ResultSet rs = jdbcHelper.executeQuery(sql,args);
            while(rs.next()){
                nhanVien entity = new nhanVien();
                entity.setMaNV(rs.getString("MaNV"));
                entity.setHoTen(rs.getString("HoTen"));
                entity.setMatKhau(rs.getString("MatKhau"));
                entity.setVaiTro(rs.getBoolean("VaiTro"));
                entity.setGioitinh(rs.getString("Gioitinh"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
}
