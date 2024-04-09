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
public class DSDiemRenLuyen {

    Connection con = KetNoiCSDL.getConnection();
    PreparedStatement ps;

    public int getMax() {
        int id = 0;
        Statement st;
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery("select max(id) from dsdrl");
            while (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DSDiemRenLuyen.class.getName()).log(Level.SEVERE, null, ex);
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
                TrangChu.jTextField22.setText(String.valueOf(rs.getInt(2)));
                TrangChu.jTextField23.setText(String.valueOf(rs.getInt(3)));
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Sinh Viên Hoặc Học Kỳ Không Tồn Tại");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DSDiemRenLuyen.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public boolean getTen(String id) {
        try {
            ps = con.prepareStatement("select * from sinhvien where id = ?");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                TrangChu.jTextField37.setText(rs.getString(2));
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Sinh Viên Không Tồn Tại");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DKMonHoc.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    
    //Kiểm tra điểm đã tồn tại
    public boolean isDiemExist(String id) {
        try {
            ps = con.prepareStatement("select * from dsdrl where id = ?");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DSDiemRenLuyen.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean isSidHocKyNoExist(int sid, int hockyNo) {
        try {
            ps = con.prepareStatement("select * from dsdrl where msv = ? and hocky = ?");
            ps.setInt(1, sid);
            ps.setInt(2, hockyNo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DSDiemRenLuyen.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    //Hiển thị dữ liệu vào bảng
    public void getScoreValue(JTable table, String searchValue) {
        String sql = "select * from dsdrl where concat(id,msv,hoten,hocky) like ? order by id desc";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, "%" + searchValue + "%");
            ResultSet rs = ps.executeQuery();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            Object[] row;
            while (rs.next()) {
                row = new Object[6];
                row[0] = rs.getString(1);
                row[1] = rs.getString(2);
                row[2] = rs.getString(3);
                row[3] = rs.getString(4);
                row[4] = rs.getString(5);
                row[5] = rs.getString(6);
                model.addRow(row);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DSDiemRenLuyen.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Nhập dữ liệu vào bảng điểm môn học
    public void insert(int id, int sid, String hoten, int hocky, String drl, String xeploaidrl) {
        String sql = "insert into dsdrl values(?,?,?,?,?,?)";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setInt(2, sid);
            ps.setString(3, hoten);
            ps.setInt(4, hocky);
            ps.setString(5, drl);
            ps.setString(6, xeploaidrl);

            if (ps.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "Thêm Điểm Rèn Luyện Học Thành Công");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DSDiemRenLuyen.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
        //Cập nhật bang diem
    public void update(String id, String drl, String xeploaidrl) {
        String sql = "update dsdrl set drl=?,xeploaidrl=? where id=?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, drl);
            ps.setString(2, xeploaidrl);
            ps.setString(3, id);
            if (ps.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "Cập Nhật Thành Công");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DSDiem.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
