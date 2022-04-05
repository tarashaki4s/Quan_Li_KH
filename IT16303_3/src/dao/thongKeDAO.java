/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import helper.jdbcHelper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Sieu Nhan Bay
 */
public class thongKeDAO {

    /*
    thống kê số người học của trung tâm theo từng năm
    return 1 <Object[]> list : Năm - số lượng - ngày người đầu tiên đk - ngày người cc đk
     */
    public List<Object[]> getNguoiHoc() {
        List<Object[]> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                String sql = "{call [dbo].[sp_LuongNguoiHoc]}";
                rs = jdbcHelper.executeQuery(sql);
                while (rs.next()) {
                    Object[] model = {
                        rs.getInt("Nam"),
                        rs.getInt("SoLuong"),
                        rs.getDate("DauTien"),
                        rs.getDate("CuoiCung")
                    };
                    list.add(model);

                }
            } finally {
                rs.getStatement().getConnection().close();

            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);

        }
        return list;

    }

    /*
    bảng điểm của các học viên trong khóa học
    @param makh mã khóa học
    @return <Object[]> list : mã NH - họ và tên - điểm - xếp loại
     */
    public List<Object[]> getBangDiem(Integer makh) {
        List<Object[]> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                String sql = "{call sp_BangDiem (?)}";
                rs = jdbcHelper.executeQuery(sql, makh);
                while (rs.next()) {
                    Object[] model = {
                        rs.getString("MaNH"),
                        rs.getString("HoTen"),
                        rs.getDouble("Diem"),
                        xeploai(rs.getDouble("Diem"))
                    };
                    list.add(model);
                }}
             finally {
                rs.getStatement().getConnection().close();
            }
    }
         catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
        return list;
    
    }

    /*
    tổng hợp điểm của theo từng chuyên đề
    @return <Object[]> list : tên chuyên đề - số HV - điểm thấp nhất - điểm cao nhất - điểm trung bình
     */
    public List<Object[]> getDiemTheoChuyenDe() {
        List<Object[]> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                String sql = "{call [dbo].[sp_DiemChuyenDe]}";
                rs = jdbcHelper.executeQuery(sql);
                while (rs.next()) {
                    Object[] model = {
                        rs.getString("ChuyenDe"),
                        rs.getInt("SoHV"),
                        rs.getDouble("ThapNhat"),
                        rs.getDouble("CaoNhat"),
                        rs.getDouble("TrungBinh")
                    };
                    list.add(model);
                }
            } finally {
                rs.getStatement().getConnection().close();
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return list;
    }

    /*
    tổng hợp doanh thu từng chuyên đề (theo từng năm)
    @param int nam
    @return <Object[]> list : tên chuyên đề - số KH - số HV - doanh thu - HP cao nhất - HP thấp nhất - HP trung bình
     */
    public List<Object[]> getDoanhThu(int nam) {
        List<Object[]> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                String sql = "{call [dbo].[sp_DoanhThu](?)}";
                rs = jdbcHelper.executeQuery(sql, nam);
                while (rs.next()) {
                    Object[] model = {
                        rs.getString("ChuyenDe"),
                        rs.getInt("SoKH"),
                        rs.getInt("SoHV"),
                        rs.getDouble("DoanhThu"),
                        rs.getDouble("ThapNhat"),
                        rs.getDouble("CaoNhat"),
                        rs.getDouble("TrungBinh")
                    };
                    list.add(model);
                }
                System.out.println(nam);
            } finally {
                rs.getStatement().getConnection().close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public List<Integer> getNamKhaiGiang() {
        List<Integer> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = jdbcHelper.executeQuery("select distinct year(NgayKG) as nam from KhoaHoc order by year(NgayKG) desc");
                while (rs.next()) {
                    int nam = rs.getInt(1);
                    list.add(nam);
                }
            }finally{
                rs.getStatement().getConnection().close();
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return list;
    }

    private Object xeploai(double diem) {
        if (diem<5) {
            return "Chưa đạt";
        }
        else if (diem <6.5) {
            return "Trung bình";
        }
        else if (diem <7.5) {
            return "Khá";
        }
        else if (diem <9) {
            return "giỏi";
        }
        else{
            return "Xuất xắc";
        }
    }
}
