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
import model.OrderInfo;

/**
 *
 * @author ADMIN
 */
public class staffOrderDAO extends DAO {

    private Connection conn;
    private static final String SELECT_ORDERINFO_BY_ID = "SELECT * FROM OrderInfo WHERE orderID = ?";
    private static final String SELECT_ALL_ORDERINFO = "SELECT * FROM OrderInfo";
    private static final String DELETE_ORDERINFO_SQL = "DELETE FROM OrderInfo WHERE orderID = ?";
    private static final String SEARCH_ORDER_BY_PHONE = "SELECT o.* FROM OrderInfo o JOIN Customer c ON o.customerID = c.customerID WHERE c.phone = ?";
    private static final String UPDATE_ORDERINFO_SQL = "UPDATE OrderInfo SET customerID = ?, deliveryOptionID = ?, managerID = ?, paymentMethodID = ?, totalPrice = ?, deliveryAddress = ? WHERE orderID = ?";

    public OrderInfo selectOrder(int orderID) {
        OrderInfo order = null;
        try ( Connection connection = getConnection();  PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ORDERINFO_BY_ID)) {
            preparedStatement.setInt(1, orderID);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                order = new OrderInfo(
                        rs.getInt("orderID"),
                        rs.getInt("customerID"),
                        rs.getInt("orderStatus"),
                        rs.getDate("orderDate"),
                        rs.getInt("deliveryOptionID"),
                        rs.getInt("managerID"),
                        rs.getInt("paymentMethodID"),
                        rs.getBigDecimal("totalPrice"),
                        rs.getString("deliveryAddress")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return order;
    }

    public List<OrderInfo> selectAllOrder() {
        List<OrderInfo> order = new ArrayList<>();
        try (
                 Connection connection = getConnection();  PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_ORDERINFO)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                order.add(new OrderInfo(
                        rs.getInt("orderID"),
                        rs.getInt("customerID"),
                        rs.getInt("orderStatus"),
                        rs.getDate("orderDate"),
                        rs.getInt("deliveryOptionID"),
                        rs.getInt("managerID"),
                        rs.getInt("paymentMethodID"),
                        rs.getBigDecimal("totalPrice"),
                        rs.getString("deliveryAddress")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return order;
    }

    /**
     * Tìm đơn hàng theo ID
     *
     * @param order
     * @return
     * @throws java.sql.SQLException
     */
    public boolean updateOrder(OrderInfo order) throws SQLException {
        boolean rowUpdated;
        try ( Connection connection = getConnection();  PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ORDERINFO_SQL)) {
            preparedStatement.setInt(1, order.getOrderID());
            preparedStatement.setInt(2, order.getCustomerID());
            preparedStatement.setInt(3, order.getOrderStatus());
            preparedStatement.setDate(4, order.getOrderDate());
            preparedStatement.setInt(5, order.getDeliveryOptionID());
            preparedStatement.setInt(6, order.getManagerID());
            preparedStatement.setInt(6, order.getPaymentMethodID());
            preparedStatement.setBigDecimal(7, order.getTotalPrice());
            preparedStatement.setString(8, order.getDeliveryAddress());
            rowUpdated = preparedStatement.executeUpdate() > 0;
        }
        return rowUpdated;
    }

    public boolean deleteOrder(int orderID) throws SQLException {
        boolean rowDeleted;
        try ( Connection connection = getConnection();  PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ORDERINFO_SQL)) {
            preparedStatement.setInt(1, orderID);
            rowDeleted = preparedStatement.executeUpdate() > 0;
        }
        return rowDeleted;
    }

    /**
     * Tìm kiếm đơn hàng qua số điện thoại khách hàng (giả sử bảng Customer có
     * phone, bạn JOIN hoặc thay đổi logic tuỳ cấu trúc)
     *
     * @param phone
     * @return
     */
    public List<OrderInfo> searchByPhone(String phone) {
        List<OrderInfo> list = new ArrayList<>();
        // Ví dụ: SELECT * FROM OrderInfo o JOIN Customer c ON o.customerID = c.customerID WHERE c.phone = ?
        String sql = "SELECT o.* FROM OrderInfo o JOIN Customer c ON o.customerID = c.customerID WHERE c.phone = ?";
        try ( Connection conn = getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, phone);
            try ( ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    OrderInfo order = new OrderInfo();
                    order.setOrderID(rs.getInt("orderID"));
                    order.setCustomerID(rs.getInt("customerID"));
                    order.setOrderStatus(rs.getInt("orderStatus"));
                    order.setOrderDate(rs.getDate("orderDate"));
                    order.setDeliveryOptionID(rs.getInt("deliveryOptionID"));
                    order.setManagerID(rs.getInt("managerID"));
                    order.setPaymentMethodID(rs.getInt("paymentMethodID"));
                    order.setTotalPrice(rs.getBigDecimal("totalPrice"));
                    order.setDeliveryAddress(rs.getString("deliveryAddress"));

                    list.add(order);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Cập nhật thông tin đơn hàng (không bao gồm trạng thái)
     *
     * @param phone
     * @return
     */
    public List<OrderInfo> searchOrderByPhone(int phone) {
        List<OrderInfo> list = new ArrayList<>();
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SEARCH_ORDER_BY_PHONE);
            preparedStatement.setString(1, "%" + phone + "%");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                list.add(new OrderInfo(
                        rs.getInt("orderID"),
                        rs.getInt("customerID"),
                        rs.getInt("orderStatus"),
                        rs.getDate("orderDate"),
                        rs.getInt("deliveryOptionID"),
                        rs.getInt("managerID"),
                        rs.getInt("paymentMethodID"),
                        rs.getBigDecimal("totalPrice"),
                        rs.getString("deliveryAddress")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Cập nhật trạng thái đơn hàng
     *
     * @param orderID
     * @param newStatus
     * @return
     */
    public boolean updateOrderStatus(int orderID, int newStatus) {
        String sql = "UPDATE OrderInfo SET orderStatus = ? WHERE orderID = ?";
        try ( Connection connection = getConnection();  PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, newStatus);
            ps.setInt(2, orderID);
            int row = ps.executeUpdate();
            return row > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
