/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import helper.jdbcHelper;
import model.khoaHoc;
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
public class KhoaHocDAO extends eduSysDAO<khoaHoc, Integer>{
    
    String INSERT_SQL ="INSERT INTO KhoaHoc(MaCD,HocPhi,ThoiLuong,NgayKG,GhiChu,MaNV,NgayTao)VALUES(?,?,?,?,?,?,?)";
    String UPDATE_SQL="UPDATE KhoaHoc SET MaCD=?,HocPhi=?,ThoiLuong=?,NgauKG=?,GhiChu=?,MaNV=?,NgayTao=? WHERE MaKH=?";
    String DELETE_SQL="DELETE FROM KhoaHoc WHERE MaKH=?";
    String SELECT_ALL_SLQ="SELECT*FROM KhoaHoc";
    String SELECT_BY_ID_SQL ="SELECT*FROM KhoaHoc where MaKH=?";
    
    @Override
    public void insert(khoaHoc entity) {
        jdbcHelper.executeUpdate(INSERT_SQL,
                entity.getMaKH(),entity.getMaCD(),entity.getHocPhi(),entity.getThoiLuong(),entity.getNgayKG(),entity.getGhiChu(),entity.getMaNV(),entity.getNgayTao());
    }

    @Override
    public void update(khoaHoc entity) {
        jdbcHelper.executeUpdate(UPDATE_SQL,
                entity.getMaKH(),entity.getMaCD(),entity.getHocPhi(),entity.getThoiLuong(),entity.getNgayKG(),entity.getGhiChu(),entity.getMaNV(),entity.getNgayTao());
    }

    @Override
    public void delete(Integer id) {
        jdbcHelper.executeUpdate(DELETE_SQL,id);
    }

    @Override
    public List<khoaHoc> selectAll() {
        return this.selectBySql(SELECT_ALL_SLQ);
    }

    @Override
    public khoaHoc selectById(Integer id) {
        List<khoaHoc> list = this.selectBySql(SELECT_BY_ID_SQL, id);
        if(list.isEmpty()){
            return null;
        }
        return list.get(0);
    }

    @Override
    protected List<khoaHoc> selectBySql(String sql, Object... args) {
        List<khoaHoc> list = new ArrayList<khoaHoc>();
        try {
            ResultSet rs = jdbcHelper.executeQuery(sql,args);
            while(rs.next()){
                khoaHoc entity = new khoaHoc();
                entity.setMaKH(rs.getInt("MaKH"));
                entity.setMaCD(rs.getString("MaCD"));
                entity.setHocPhi(rs.getDouble("HocPhi"));
                entity.setThoiLuong(rs.getInt("ThoiLuong"));
                entity.setNgayKG(rs.getDate("NgayKG"));
                entity.setGhiChu(rs.getString("GhiChu"));
                entity.setMaNV(rs.getString("MaNV"));
                entity.setNgayTao(rs.getDate("NgayTao"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public List<khoaHoc> selectByChuyenDe(String macd){
        String sql ="select * from khoahoc  where macd=?";
        return selectBySql(sql,macd);
    }
    
}
