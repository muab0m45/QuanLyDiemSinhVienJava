/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sinhvien;

import csdl.KetNoiCSDL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author OTOMI
 */
public class SinhVien {

    Connection con = KetNoiCSDL.getConnection();
    PreparedStatement ps;

    //Nhập dữ liệu vào bảng sinh viên
    public void insert(String id, String hoten, String ngaysinh, String gioitinh, String quequan, String sodienthoai, String lop, String nienkhoa, String ghichu, String imagePath) {
        String sql = "insert into sinhvien values(?,?,?,?,?,?,?,?,?,?)";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, id);
            ps.setString(2, hoten);
            ps.setString(3, ngaysinh);
            ps.setString(4, gioitinh);
            ps.setString(5, quequan);
            ps.setString(6, sodienthoai);
            ps.setString(7, lop);
            ps.setString(8, nienkhoa);
            ps.setString(9, ghichu);
            ps.setString(10, imagePath);

            if (ps.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "Thêm Sinh Viên Mới Thành Công!");
            }
        } catch (SQLException ex) {
            Logger.getLogger(SinhVien.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    //Kiểm tra MSV đã tồn tại
    public boolean isMSVExist(String id) {
        try {
            ps = con.prepareStatement("select * from sinhvien where id = ?");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(SinhVien.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    //Hiển thị dữ liệu vào bảng
    public void getStudentValue(JTable table, String searchValue) {
        String sql = "select * from sinhvien where concat(id,hoten,quequan,sodienthoai,lop) like ? order by id desc";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, "%" + searchValue + "%");
            ResultSet rs = ps.executeQuery();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            Object[] row;
            while (rs.next()) {
                row = new Object[10];
                row[0] = rs.getString(1);
                row[1] = rs.getString(2);
                row[2] = rs.getString(3);
                row[3] = rs.getString(4);
                row[4] = rs.getString(5);
                row[5] = rs.getString(6);
                row[6] = rs.getString(7);
                row[7] = rs.getString(8);
                row[8] = rs.getString(9);
                row[9] = rs.getString(10);
                model.addRow(row);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SinhVien.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Cập nhật thông tin sinh viên
    public void update(String id, String hoten, String ngaysinh, String gioitinh, String quequan, String sodienthoai, String lop, String nienkhoa, String ghichu, String imagePath) {
        String sql = "update sinhvien set hoten=?,ngaysinh=?,gioitinh=?,quequan=?,sodienthoai=?,lop=?,nienkhoa=?,ghichu=?,image_path=? where id=?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, hoten);
            ps.setString(2, ngaysinh);
            ps.setString(3, gioitinh);
            ps.setString(4, quequan);
            ps.setString(5, sodienthoai);
            ps.setString(6, lop);
            ps.setString(7, nienkhoa);
            ps.setString(8, ghichu);
            ps.setString(9, imagePath);
            ps.setString(10, id);
            if (ps.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "Cập Nhật Thành Công");
            }
        } catch (SQLException ex) {
            Logger.getLogger(SinhVien.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    //Xóa dữ liệu sinh viên
    public void delete(String id) {
        int yesNo = JOptionPane.showConfirmDialog(null, "Các Dữ Liệu Liên Quan Cũng Bị Xóa", "Cảnh Báo", JOptionPane.OK_CANCEL_OPTION, 0);
        if (yesNo == JOptionPane.OK_OPTION) {
            try {
                ps = con.prepareStatement("delete from sinhvien where id = ?");
                ps.setString(1, id);
                if (ps.executeUpdate() > 0) {
                    JOptionPane.showMessageDialog(null, "Xóa Thành Công");
                }
            } catch (SQLException ex) {
                Logger.getLogger(SinhVien.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
