/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller.staff.order;

import dao.staffOrderDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;
import model.Manager;
import model.OrderInfo;

/**
 *
 * @author ADMIN
 */
public class OrderController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet OrderController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet OrderController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String action = request.getServletPath();

        try {
            switch (action) {
                case "/listAdminOrders":
                    listAllOrders(request, response);
                    break;
                case "/updateOrderStatus":
                    updateOrderStatus(request, response);
                    break;
                case "/viewOrderDetails":
                    viewOrderDetails(request, response);
                    break;
                case "/searchByPhone":
                    searchOrdersByPhone(request, response);
                    break;
                case "/deleteOrder":
                    deleteOrder(request, response);
                    break;
                default:
                    listAllOrders(request, response);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    // Hiển thị danh sách tất cả đơn hàng
    private void listAllOrders(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String statusFilter = request.getParameter("status");

        List<OrderInfo> orders;
        staffOrderDAO odersDAO = new staffOrderDAO();

        orders = odersDAO.selectAllOrder(); // Lấy tất cả đơn hàng

        request.setAttribute("listOrders", orders);
        request.getRequestDispatcher("admin_orders.jsp").forward(request, response);
    }

    // Cập nhật trạng thái đơn hàng
    private void updateOrderStatus(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int orderId = Integer.parseInt(request.getParameter("id"));
        int newStatus = 2;

        staffOrderDAO odersDAO = new staffOrderDAO();
        boolean isUpdated = odersDAO.updateOrderStatus(orderId, newStatus);

        if (isUpdated) {
            response.sendRedirect("listAdminOrders?message=Status updated successfully");
        } else {
            response.sendRedirect("listAdminOrders?error=Failed to update status");
        }
    }

    // Hiển thị chi tiết đơn hàng
    private void viewOrderDetails(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int orderID = Integer.parseInt(request.getParameter("id"));
        staffOrderDAO ordersDAO = new staffOrderDAO();
        OrderInfo order = ordersDAO.selectOrder(orderID);

        if (order != null) {
            request.setAttribute("OrderInfo", order);
            request.getRequestDispatcher("admin_view_orders.jsp").forward(request, response);
        } else {
            response.sendRedirect("listAdminOrders?error=Order not found");
        }
    }

    // Tìm kiếm theo số điện thoại
    private void searchOrdersByPhone(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String phoneSearch = request.getParameter("phone");
        staffOrderDAO ordersDAO = new staffOrderDAO();
        List<OrderInfo> orders = ordersDAO.searchByPhone(phoneSearch);

        request.setAttribute("listOrders", orders);
        request.getRequestDispatcher("admin_orders.jsp").forward(request, response);
    }
 
    // Xóa order
    private void deleteOrder(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        int orderID = Integer.parseInt(request.getParameter("id"));
        staffOrderDAO ordersDAO = new staffOrderDAO();
        boolean isDeleted = ordersDAO.deleteOrder(orderID);

        if (isDeleted) {
            response.sendRedirect("listAdminOrders?message=Order deleted successfully");
        } else {
            response.sendRedirect("listAdminOrders?error=Order deletion failed");
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
