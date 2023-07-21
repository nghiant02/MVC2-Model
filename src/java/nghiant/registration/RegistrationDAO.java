/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nghiant.registration;

import nghiant.util.DBHelper;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author localboss
 */
public class RegistrationDAO implements Serializable {

//    public boolean checkLogin(String username, String password)
//        throws SQLException, NamingException { //chi tao 2 tham so vi trong table chi co 2 tham so
    public RegistrationDTO checkLogin(String username, String password)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        RegistrationDTO result = null;
        try {
            //1. Connect DB   
            con = DBHelper.makeConnection();
            if (con != null) {
                //2. Write SQL command
                String sql = "Select fullname, role "
                        + "From Registration "
                        + "Where username = ? And password = ?";
                //3. Create Statement Object
                stm = con.prepareStatement(sql);
                stm.setString(1, username);
                stm.setString(2, password);
                //4. Excute Statement Object to get result 
                rs = stm.executeQuery();//o day kh truyen tham so boi vi o dong 36 da thuc hien du 4 buoc r
                //5. Process result
                if (rs.next()) { //code tu dong 42 la code tu database set value cho model
                    String fullName = rs.getString("fullname");
                    boolean role = rs.getBoolean("role");

                    result = new RegistrationDTO(username, password, fullName, role);
                }
            }//end connection has existed
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return result;
    }

    private List<RegistrationDTO> accountList;

    public List<RegistrationDTO> getAccountList() {
        return accountList;
    }

    public void searchLastName(String keyword) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            //1. Connect DB
            con = DBHelper.makeConnection();
            if (con != null) {
                //2. Write SQL command
                String sql = "Select username, password, fullname, role "
                        + "From Registration "
                        + "Where fullname Like ?";
                //3. Create Statement Object
                stm = con.prepareStatement(sql);
                stm.setString(1, "%" + keyword + "%");
                //4. Excute Statement Object to get result
                rs = stm.executeQuery();
                //5. Process result
                while (rs.next()) {
                    String username = rs.getString("username");
                    String password = rs.getString("password");
                    String fullName = rs.getString("fullname");
                    boolean role = rs.getBoolean("role");

                    RegistrationDTO dto = new RegistrationDTO(
                            username, password, fullName, role);
                    if (this.accountList == null) {
                        this.accountList = new ArrayList<>();
                    }//end account List has NOT existed
                    this.accountList.add(dto);
                }//end result set has not reached EOF
            } // end connection has existed
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }

    }

    public boolean deleteAccount(String pk)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        boolean result = false;
        try {
            //1. Connect DB   
            con = DBHelper.makeConnection();
            if (con != null) {
                //2. Write SQL command
                String sql = "Delete From Registration "
                        + "Where username = ?";
                //3. Create Statement Object
                stm = con.prepareStatement(sql);
                stm.setString(1, pk);
                //4. Excute Statement Object to get result 
                int effectRows = stm.executeUpdate();
                //5. Process result
                if (effectRows > 0) {
                    result = true;
                }
            }//end connection has existed
        } finally {

            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return result;
    }

    public boolean updateAccount(String username, String password, boolean role)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;

        try {
            //1. Connect DB   
            con = DBHelper.makeConnection();
            if (con != null) {
                //2. Write SQL command
                String sql = "Update Registration "
                        + "Set password = ?, role = ? "
                        + "Where username = ?";
                //3. Create Statement Object
                stm = con.prepareStatement(sql);
                stm.setString(1, password);
                stm.setBoolean(2, role);
                stm.setString(3, username);
                //4. Excute Statement Object to get result 
                int effectRows = stm.executeUpdate();
                //5. Process result
                if (effectRows > 0) {
                    return true;
                }
            }//end connection has existed
        } finally {

            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return false;
    }
   public boolean createAccount(RegistrationDTO dto)
        throws SQLException, NamingException {
            Connection con = null;
            PreparedStatement stm = null;
            boolean result = false;
            try {
                //1. Connect DB
                con = DBHelper.makeConnection();
                if (con != null) { // check connection
                    //2. Write SQL command
                    String sql = "Insert into users("
                            + "username, password, fullname, role"
                            + ") Values("
                            + "?, ?, ?, ?"
                            + ")";
                    //3. Create Statement Object
                    stm = con.prepareStatement(sql);
                    stm.setString(1, dto.getUsername());
                    stm.setString(2, dto.getPassword());
                    stm.setString(3, dto.getFullName());
                    stm.setBoolean(4, dto.isRole());
                    //4. Execute Statement Object to get result
                    int effectRows = stm.executeUpdate();
                    //5. Process result
                    if (effectRows > 0) {
                        result = true;
                    }
                }//end connection has existed
            } finally {
                if (stm != null) {
                    stm.close();
                }
                if (con != null) {
                    con.close();
                }
            }
            return result;
        }
}
