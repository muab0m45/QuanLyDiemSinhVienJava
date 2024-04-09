/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sinhvien;

import csdl.KetNoiCSDL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author OTOMI
 */
public class DSDiem {

    Connection con = KetNoiCSDL.getConnection();
    PreparedStatement ps;

    public int getMax() {
        int id = 0;
        Statement st;
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery("select max(id) from dsdiem");
            while (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DSDiem.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id + 1;
    }

    public boolean getThongTin(int sid, int hockyNo) {
        try {
            ps = con.prepareStatement("select * from dkmonhoc where msv = ? and hocky = ?");
            ps.setInt(1, sid);
            ps.setInt(2, hockyNo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                TrangChu.jTextField14.setText(String.valueOf(rs.getInt(2)));
                TrangChu.jTextField16.setText(String.valueOf(rs.getInt(3)));
                TrangChu.jTextCourse1.setText(rs.getString(4));
                TrangChu.jTextCourse2.setText(rs.getString(5));
                TrangChu.jTextCourse3.setText(rs.getString(6));
                TrangChu.jTextCourse4.setText(rs.getString(7));
                TrangChu.jTextCourse5.setText(rs.getString(8));
                TrangChu.jTextCourse6.setText(rs.getString(9));
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Sinh Viên Hoặc Học Kỳ Không Tồn Tại");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DSDiem.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    //Kiểm tra điểm đã tồn tại
    public boolean isDiemExist(String id) {
        try {
            ps = con.prepareStatement("select * from dsdiem where id = ?");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DSDiem.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    //Kiểm tra MSV hoặc học kỳ đã tồn tại
    public boolean isSidHocKyNoExist(int sid, int hockyNo) {
        try {
            ps = con.prepareStatement("select * from dsdiem where msv = ? and hocky = ?");
            ps.setInt(1, sid);
            ps.setInt(2, hockyNo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DSDiem.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    //Hiển thị dữ liệu vào bảng
    public void getScoreValue(JTable table, String searchValue) {
        String sql = "select * from dsdiem where concat(id,msv,hocky) like ? order by id desc";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, "%" + searchValue + "%");
            ResultSet rs = ps.executeQuery();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            Object[] row;
            while (rs.next()) {
                row = new Object[16];
                row[0] = rs.getString(1);
                row[1] = rs.getString(2);
                row[2] = rs.getString(3);
                row[3] = rs.getString(4);
                row[4] = rs.getDouble(5);
                row[5] = rs.getString(6);
                row[6] = rs.getDouble(7);
                row[7] = rs.getString(8);
                row[8] = rs.getDouble(9);
                row[9] = rs.getString(10);
                row[10] = rs.getDouble(11);
                row[11] = rs.getString(12);
                row[12] = rs.getDouble(13);
                row[13] = rs.getString(14);
                row[14] = rs.getDouble(15);
                row[15] = rs.getDouble(16);
                model.addRow(row);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DSDiem.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Nhập dữ liệu vào bảng điểm môn học
    public void insert(int id, int sid, int hocky, String monhoc1, String monhoc2, String monhoc3, String monhoc4,
            String monhoc5, String monhoc6, double diem1, double diem2, double diem3, double diem4, double diem5, double diem6, double tbhocky) {
        String sql = "insert into dsdiem values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setInt(2, sid);
            ps.setInt(3, hocky);
            ps.setString(4, monhoc1);
            ps.setDouble(5, diem1);
            ps.setString(6, monhoc2);
            ps.setDouble(7, diem2);
            ps.setString(8, monhoc3);
            ps.setDouble(9, diem3);
            ps.setString(10, monhoc4);
            ps.setDouble(11, diem4);
            ps.setString(12, monhoc5);
            ps.setDouble(13, diem5);
            ps.setString(14, monhoc6);
            ps.setDouble(15, diem6);
            ps.setDouble(16, tbhocky);

            if (ps.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "Thêm Điểm Học Thành Công");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DSDiem.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    //Cập nhật bang diem
    public void update(String id, double diem1, double diem2, double diem3, double diem4, double diem5, double diem6, double tbhocky) {
        String sql = "update dsdiem set diem1=?,diem2=?,diem3=?,diem4=?,diem5=?,diem6=?,tbhocky=? where id=?";
        try {
            ps = con.prepareStatement(sql);
            ps.setDouble(1, diem1);
            ps.setDouble(2, diem2);
            ps.setDouble(3, diem3);
            ps.setDouble(4, diem4);
            ps.setDouble(5, diem5);
            ps.setDouble(6, diem6);
            ps.setDouble(7, tbhocky);
            ps.setString(8, id);
            if (ps.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "Cập Nhật Thành Công");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DSDiem.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
