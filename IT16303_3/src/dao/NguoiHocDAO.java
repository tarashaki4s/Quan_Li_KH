/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import helper.MgsBox;
import helper.XDate;
import helper.jdbcHelper;
import model.nguoiHoc;
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
public class NguoiHocDAO extends eduSysDAO<nguoiHoc, String>{
    
    String INSERT_SQL ="INSERT INTO NguoiHoc(MaNH,HoTen,NgaySinh,GioiTinh,DienThoai,Email,GhiChu,MaNV,NgayDK)VALUES(?,?,?,?,?,?,?,?,?)";
    String UPDATE_SQL="UPDATE NguoiHoc SET HoTen=?,NgaySinh=?,GioiTinh=?,DienThoai=?,Email=?,GhiChu=?,MaNV=?,NgayDK=? WHERE MaNH=?";
    String DELETE_SQL="DELETE FROM NguoiHoc WHERE MaNH=?";
    String SELECT_ALL_SLQ="SELECT*FROM NguoiHoc";
    String SELECT_BY_ID_SQL ="SELECT*FROM NguoiHoc where MaNH=?";

    @Override
    public void insert(nguoiHoc entity) {
        jdbcHelper.executeUpdate(INSERT_SQL,
                entity.getMaNH(),entity.getHoTen(),entity.getNgaySinh(),entity.isGioiTinh(),entity.getDienThoai(),entity.getEmail(),entity.getGhiChu(),
                entity.getMaNV(),entity.getNgayDK());
    }

    @Override
    public void update(nguoiHoc entity) {
       jdbcHelper.executeUpdate(UPDATE_SQL,
               entity.getHoTen(),entity.getNgaySinh(),entity.isGioiTinh(),entity.getDienThoai(),entity.getEmail(),entity.getGhiChu(),entity.getMaNV(),
               entity.getNgayDK(),entity.getMaNH());
    }

    @Override
    public void delete(String id) {
        jdbcHelper.executeUpdate(DELETE_SQL,id);
    }

    @Override
    public List<nguoiHoc> selectAll() {
        return this.selectBySql(SELECT_ALL_SLQ);
    }

    @Override
    public nguoiHoc selectById(String id) {
        List<nguoiHoc> list = this.selectBySql(SELECT_BY_ID_SQL, id);
        if(list.isEmpty()){
            return null;
        }
        return list.get(0);
    }

    @Override
    protected List<nguoiHoc> selectBySql(String sql, Object... args) {
        List<nguoiHoc> list = new ArrayList<nguoiHoc>();
        try {
            ResultSet rs = jdbcHelper.executeQuery(sql,args);
            while(rs.next()){
                nguoiHoc entity = new nguoiHoc();
                entity.setMaNH(rs.getString("MaNH"));
                entity.setHoTen(rs.getString("HoTen")); 
                entity.setNgaySinh(rs.getDate("NgaySinh"));
                entity.setGioiTinh(rs.getBoolean("GioiTinh"));
                entity.setDienThoai(rs.getString("DienThoai"));
                entity.setEmail(rs.getString("Email"));
                entity.setGhiChu(rs.getString("GhiChu"));
                entity.setMaNV(rs.getString("MaNV"));
                entity.setNgayDK(rs.getDate("NgayDK"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public List<nguoiHoc> selectByKeyword(String keyword) {
        String sql="SELECT * FROM NguoiHoc WHERE HoTen LIKE ?";
        return selectBySql(sql, "%"+keyword+"%");
    }
    public List<nguoiHoc> selectNotrhCourse (int makh, String keyword){
        String sql="Select * from Nguoihoc where hoten Like ? and NGuoihoc.Manh not  in (Select manh from hocvien where Makh=?)";
        return selectBySql(sql,"%"+keyword+"%",makh);
    }
    
}
