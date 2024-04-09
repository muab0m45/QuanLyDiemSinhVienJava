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
public class DKMonHoc {

    Connection con = KetNoiCSDL.getConnection();
    PreparedStatement ps;

    public int getMax() {
        int id = 0;
        Statement st;
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery("select max(id) from dkmonhoc");
            while (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DKMonHoc.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id + 1;
    }

    public boolean getId(String id) {
        try {
            ps = con.prepareStatement("select * from sinhvien where id = ?");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                TrangChu.jTextField11.setText(String.valueOf(rs.getString(1)));
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Sinh Viên Không Tồn Tại");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DKMonHoc.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public int countHocKy(String id) {
        int total = 0;
        try {
            ps = con.prepareStatement("select count(*) as 'total' from dkmonhoc where msv = ?");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                total = rs.getInt(1);
            }
            if (total == 10) {
                JOptionPane.showMessageDialog(null, "Sinh Viên Đã Hoàn Thành Chương Trình");
                return -1;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DKMonHoc.class.getName()).log(Level.SEVERE, null, ex);
        }
        return total;
    }

    //Kiểm tra sinh viên đã học kỳ này hay chưa
    public boolean isHocKyExist(int sid, int hockyNo) {
        try {
            ps = con.prepareStatement("select * from dkmonhoc where msv = ? and hocky = ?");
            ps.setInt(1, sid);
            ps.setInt(2, hockyNo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DKMonHoc.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    //Kiểm tra sinh viên đã đăng ký môn học này chưa
    public boolean isMonHocExist(int sid, String monhocNo, String monhoc) {
        String sql = "select * from dkmonhoc where msv = ? and " +monhocNo+ " = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, sid);
            ps.setString(2, monhoc);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DKMonHoc.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    //Nhập dữ liệu vào bảng đăng ký môn học
    public void insert(int id, int sid, int hocky, String monhoc1, String monhoc2, String monhoc3, String monhoc4, String monhoc5, String monhoc6) {
        String sql = "insert into dkmonhoc values(?,?,?,?,?,?,?,?,?)";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setInt(2, sid);
            ps.setInt(3, hocky);
            ps.setString(4, monhoc1);
            ps.setString(5, monhoc2);
            ps.setString(6, monhoc3);
            ps.setString(7, monhoc4);
            ps.setString(8, monhoc5);
            ps.setString(9, monhoc6);

            if (ps.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "Thêm Môn Học Thành Công");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DKMonHoc.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    //Hiển thị dữ liệu vào bảng
    public void getCourseValue(JTable table, String searchValue) {
        String sql = "select * from dkmonhoc where concat(id,msv,hocky,monhoc1,monhoc2,monhoc3,monhoc4,monhoc5,monhoc6) like ? order by id desc";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, "%" + searchValue + "%");
            ResultSet rs = ps.executeQuery();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            Object[] row;
            while (rs.next()) {
                row = new Object[9];
                row[0] = rs.getString(1);
                row[1] = rs.getString(2);
                row[2] = rs.getString(3);
                row[3] = rs.getString(4);
                row[4] = rs.getString(5);
                row[5] = rs.getString(6);
                row[6] = rs.getString(7);
                row[7] = rs.getString(8);
                row[8] = rs.getString(9);
                model.addRow(row);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DKMonHoc.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
