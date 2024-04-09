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
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author OTOMI
 */
public class DiemCaNhan {

    Connection con = KetNoiCSDL.getConnection();
    PreparedStatement ps;

    //Kiểm tra điểm đã tồn tại
    public boolean isDiemExist(int sid) {
        try {
            ps = con.prepareStatement("select * from dsdiem where msv = ?");
            ps.setInt(1, sid);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DiemCaNhan.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    //Hiển thị dữ liệu vào bảng
    public void getScoreValue(JTable table, int sid) {
        String sql = "select * from dsdiem where msv = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, sid);
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
            Logger.getLogger(DiemCaNhan.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public double getDiemTB(int sid) {
        double diemTB = 0.0;
        Statement st;
        
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery("select avg(tbhocky) from dsdiem where msv = "+sid+"");
            if (rs.next()) {
                diemTB = rs.getDouble(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DiemCaNhan.class.getName()).log(Level.SEVERE, null, ex);
        }
        return diemTB;
    }
}
