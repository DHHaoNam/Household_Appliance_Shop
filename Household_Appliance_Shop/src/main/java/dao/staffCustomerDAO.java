/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Address;
import model.Customer;
import java.sql.*;

/**
 *
 * @author ADMIN
 */
public class staffCustomerDAO extends DAO {

    public List<Customer> selectAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM Customer";  // Bỏ join Address
        try ( Connection connection = getConnection();  PreparedStatement ps = connection.prepareStatement(sql);  ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Customer customer = new Customer();
                customer.setCustomerID(rs.getInt("customerID"));
                customer.setFullName(rs.getString("fullName"));
                customer.setEmail(rs.getString("email"));
                customer.setPhone(rs.getString("phone"));
                customer.setUserName(rs.getString("userName"));
                customer.setPassword(rs.getString("password"));
                // Chuyển đổi java.sql.Date thành LocalDate (nếu không null)
                java.sql.Date sqlDate = rs.getDate("registrationDate");
                if (sqlDate != null) {
                    customer.setRegistrationDate(sqlDate.toLocalDate());
                }
                // Lấy cột status dạng boolean
                customer.setStatus(rs.getBoolean("status"));

                customers.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

    // 2. Tìm kiếm khách hàng theo số điện thoại
    public List<Customer> searchByPhone(String phone) {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM Customer WHERE phone LIKE ?";
        try ( Connection connection = getConnection();  PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, "%" + phone + "%");
            try ( ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Customer customer = new Customer();
                    customer.setCustomerID(rs.getInt("customerID"));
                    customer.setFullName(rs.getString("fullName"));
                    customer.setEmail(rs.getString("email"));
                    customer.setPhone(rs.getString("phone"));
                    customer.setUserName(rs.getString("userName"));
                    customer.setPassword(rs.getString("password"));
                    // Chuyển đổi java.sql.Date thành LocalDate (nếu không null)
                    java.sql.Date sqlDate = rs.getDate("registrationDate");
                    if (sqlDate != null) {
                        customer.setRegistrationDate(sqlDate.toLocalDate());
                    }
                    customer.setStatus(rs.getBoolean("status"));

                    customers.add(customer);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

    // 3. Cập nhật trạng thái khách hàng (true/false)
    public boolean updateCustomerStatus(int customerID, boolean newStatus) {
        String sql = "UPDATE Customer SET status = ? WHERE customerID = ?";
        try ( Connection connection = getConnection();  PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setBoolean(1, newStatus);
            ps.setInt(2, customerID);
            int row = ps.executeUpdate();
            return row > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 4. Lấy thông tin chi tiết 1 khách hàng
    public Customer selectCustomer(int customerID) {
        String sql = "SELECT * FROM Customer WHERE customerID = ?";
        try ( Connection connection = getConnection();  PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, customerID);
            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Customer c = new Customer();
                    c.setCustomerID(rs.getInt("customerID"));
                    c.setFullName(rs.getString("fullName"));
                    c.setEmail(rs.getString("email"));
                    c.setPhone(rs.getString("phone"));
                    c.setUserName(rs.getString("userName"));
                    c.setPassword(rs.getString("password"));
                    // Chuyển đổi java.sql.Date thành LocalDate (nếu không null)
                    java.sql.Date sqlDate = rs.getDate("registrationDate");
                    if (sqlDate != null) {
                        c.setRegistrationDate(sqlDate.toLocalDate());
                    }
                    c.setStatus(rs.getBoolean("status"));
                    return c;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // (Tuỳ chọn) Xoá khách hàng
    public boolean deleteCustomer(int customerID) {
        String sql = "DELETE FROM Customer WHERE customerID = ?";
        try ( Connection connection = getConnection();  PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, customerID);
            int row = ps.executeUpdate();
            return row > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
