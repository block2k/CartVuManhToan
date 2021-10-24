/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dal.DAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.*;

/**
 *
 * @author ADMIN
 */
public class ProductController extends HttpServlet {

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
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ProductController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ProductController at " + request.getContextPath() + "</h1>");
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
        String action = request.getParameter("action") == null ? "" : request.getParameter("action");
        switch (action) {
            case "Cart":
                loadCart(request, response);
                break;
            default:
                String categoryId = request.getParameter("category");

                DAO dao = new DAO();

                List<Product> list = new ArrayList<>();

                if (categoryId == null || categoryId.equals("")) {
                    list = dao.getProducts();
                } else {
                    list = dao.getProducts(categoryId);
                }

                request.setAttribute("listP", list);
                request.getRequestDispatcher("index.jsp").forward(request, response);
                break;
        }
    }

    protected void loadCart(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        DAO dao = new DAO();
        List<Cart> list = dao.getCartProducts(1);

        request.setAttribute("listC", list);
        request.getRequestDispatcher("index.jsp").forward(request, response);
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
        PrintWriter out = response.getWriter();
        String action = request.getParameter("action");
        DAO dao = new DAO();
        switch (action) {
            case "AddToCart":
                addToCart(request, response);
                break;
            case "RemoveProductFromCart":
                removeFromCart(request, response);
                break;
            case "updateQuantity":
                List<Cart> list = dao.getCartProducts(1);
                for (Cart cart : list) {
                    String value = request.getParameter(cart.genId());
                    dao.updateCart(Integer.parseInt(value), cart.getId()); // update số lượng
                    dao.updateCart(String.valueOf(cart.getProductId()), String.valueOf(1)); //tính lại tiền (lấy số lượng nhân với giá ban đầu)
                }
                String url = request.getHeader("referer");
                response.sendRedirect(url);
                break;
            default:
                response.sendRedirect("Product");
                break;
        }
    }

    protected void removeFromCart(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        DAO dao = new DAO();
        String cartId = request.getParameter("cartId");
        if (cartId != null) {
            dao.removeProductFromCart(Integer.parseInt(cartId));
        } else {
            dao.removeProductFromCart(String.valueOf(1));
        }
        response.sendRedirect("Product?action=Cart");
    }

    protected void addToCart(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String productId = request.getParameter("productId");
        int quantity = 1;
        String price = request.getParameter("productPrice");
        DAO dao = new DAO();
        // nếu sản phẩm chưa tồn tại trong giỏ hàng
        if (!dao.checkExistProductInCart(productId, String.valueOf(1))) {
            // add sản phẩm vào giỏ hàng
            dao.addProductToCart(productId, String.valueOf(1), quantity, Double.parseDouble(price));
        } else {
            // nếu sản phẩm đã tồn tại trong giỏ hàng thì cập nhật số lượng
            dao.addProductToCart(productId, String.valueOf(1));
            dao.updateCart(productId, String.valueOf(1));
        }
        String url = request.getHeader("referer");
        response.sendRedirect(url);
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
