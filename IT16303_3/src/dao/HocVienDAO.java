/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import helper.jdbcHelper;
import model.hocVien;
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
public class HocVienDAO extends eduSysDAO<hocVien,Integer >{
    
    String INSERT_SQL ="INSERT INTO HocVien(MaKH,MaNH,Diem)VALUES(?,?,?)";
    String UPDATE_SQL="UPDATE HocVien SET MaKH=?,MaNH=?,Diem=? WHERE MaHV=?";
    String DELETE_SQL="DELETE FROM HocVien WHERE MaHV=?";
    String SELECT_ALL_SLQ="SELECT*FROM HocVien";
    String SELECT_BY_ID_SQL ="SELECT*FROM HocVien where MaHV=?";

    @Override
    public void insert(hocVien entity) {
        jdbcHelper.executeUpdate(INSERT_SQL,
              entity.getMaKH(),entity.getMaNH(),entity.getDiem());
    }

    @Override
    public void update(hocVien entity) {
        jdbcHelper.executeUpdate(UPDATE_SQL,
               entity.getMaKH(),entity.getMaNH(),entity.getDiem(), entity.getMaHV());
    }

    @Override
    public void delete(Integer id) {
        jdbcHelper.executeUpdate(DELETE_SQL,id);
    }

    @Override
    public List<hocVien> selectAll() {
        return this.selectBySql(SELECT_ALL_SLQ);
    }

    @Override
    public hocVien selectById(Integer id) {
        List<hocVien> list = this.selectBySql(SELECT_BY_ID_SQL, id);
        if(list.isEmpty()){
            return null;
        }
        return list.get(0);
    }

    @Override
    protected List<hocVien> selectBySql(String sql, Object... args) {
        List<hocVien> list = new ArrayList<hocVien>();
        try {
            ResultSet rs = jdbcHelper.executeQuery(sql,args);
            while(rs.next()){
                hocVien entity = new hocVien();
                entity.setMaHV(rs.getInt("MaHV"));
                entity.setMaKH(rs.getInt("MaKH"));
                entity.setMaNH(rs.getString("MaNH"));
                entity.setDiem(rs.getDouble("Diem"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public List<hocVien> selectbyKhoaHoc(int makh){
        String sql ="select * from hocvien where makh=?";
        return selectBySql(sql,makh);
    }
    
}
