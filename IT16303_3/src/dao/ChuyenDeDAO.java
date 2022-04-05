/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import model.chuyenDe;
import helper.jdbcHelper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author admin
 */
public class ChuyenDeDAO extends eduSysDAO<chuyenDe,String >{
    
    final String INSERT_SQL ="INSERT INTO ChuyenDe (MaCD, TenCD, HocPhi, ThoiLuong, Hinh, MoTa) VALUES (?, ?, ?, ?, ?, ?)";
    final String UPDATE_SQL="UPDATE ChuyenDe SET TenCD=?, HocPhi=?, ThoiLuong=?, Hinh=?, MoTa=? WHERE MaCD=?";
    final String DELETE_SQL="DELETE FROM ChuyenDe WHERE MaCD=?";
    final String SELECT_ALL_SLQ="SELECT*FROM ChuyenDe";
    final String SELECT_BY_ID_SQL ="SELECT * FROM ChuyenDe where MaCD=?";

    @Override
    public void insert(chuyenDe entity) {
       
            jdbcHelper.executeUpdate(INSERT_SQL,
                    entity.getMaCD(),entity.getTenCD(),entity.getHocPhi(),entity.getThoiLuong(),entity.getHinh(),entity.getMoTa());
        
    }

    @Override
    public void update(chuyenDe entity) {
        
            jdbcHelper.executeUpdate(UPDATE_SQL,
                    entity.getTenCD(),entity.getHocPhi(),entity.getThoiLuong(),entity.getHinh(),entity.getMoTa(),entity.getMaCD());
        
    }
    @Override
    public void delete(String id) {
        
            jdbcHelper.executeUpdate(DELETE_SQL,id);
       
    }
    @Override
    public List<chuyenDe> selectAll() {
        return this.selectBySql(SELECT_ALL_SLQ);
    }

    @Override
    public chuyenDe selectById(String id) {
        List<chuyenDe>list = this.selectBySql(SELECT_BY_ID_SQL, id);
        if(list.isEmpty()){
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<chuyenDe> selectBySql(String sql, Object... args) {
        List<chuyenDe> list = new ArrayList<chuyenDe>();
        try {
            ResultSet rs = jdbcHelper.executeQuery(sql,args);
            while(rs.next()){
                chuyenDe entity = new chuyenDe();
                entity.setMaCD(rs.getString("MaCD"));
                entity.setTenCD(rs.getString("TenCD"));
                entity.setHocPhi(rs.getDouble("HocPhi"));
                entity.setThoiLuong(rs.getInt("ThoiLuong"));
                entity.setHinh(rs.getString("Hinh"));
                entity.setMoTa(rs.getString("MoTa"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
   
}
