/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import model.*;

/**
 *
 * @author ADMIN
 */
public class DAO extends DBContext {

    PreparedStatement ps = null;
    ResultSet rs = null;

    public List<Product> getProducts() {
        String sql = "SELECT * FROM Product WHERE Status = 1";
        List<Product> list = new ArrayList<>();
        try {
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Product(rs.getInt(1), rs.getString(2), rs.getDouble(3), rs.getString(4), rs.getBoolean(5), rs.getInt(6)));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }

    public Product getProducts(int productId) {
        String sql = "SELECT * FROM Product WHERE ProductID = ?";
        List<Product> list = new ArrayList<>();
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, productId);
            rs = ps.executeQuery();
            while (rs.next()) {
                return new Product(rs.getInt(1), rs.getString(2), rs.getDouble(3), rs.getString(4), rs.getBoolean(5), rs.getInt(6));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public List<Product> getAllProducts() {
        String sql = "SELECT * FROM Product";
        List<Product> list = new ArrayList<>();
        try {
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Product(rs.getInt(1), rs.getString(2), rs.getDouble(3), rs.getString(4), rs.getBoolean(5), rs.getInt(6)));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }

    public List<Product> getProducts(String categoryId) {
        String sql = "SELECT * FROM Product WHERE Status = 1 AND CategoryID = ?";
        List<Product> list = new ArrayList<>();
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, categoryId);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Product(rs.getInt(1), rs.getString(2), rs.getDouble(3), rs.getString(4), rs.getBoolean(5), rs.getInt(6)));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }

    public void addProduct(String name, double price, String description, String status, String categoryId) {
        String sql = "INSERT INTO [dbo].[Product] ([Name] ,[Price] ,[Description] ,[Status] ,[CategoryID]) VALUES (? ,? ,? ,? ,?)";
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, name);
            ps.setDouble(2, price);
            ps.setString(3, description);
            ps.setString(4, status);
            ps.setString(5, categoryId);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void editProduct(String name, double price, String description, String status, String categoryId, String productid) {
        String sql = "UPDATE [dbo].[Product] SET [Name] = ? ,[Price] = ? ,[Description] = ? ,"
                + "[Status] = ? ,[CategoryID] = ? WHERE ProductID = ?";
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, name);
            ps.setDouble(2, price);
            ps.setString(3, description);
            ps.setString(4, status);
            ps.setString(5, categoryId);
            ps.setString(6, productid);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void deleteProduct(String productId) {
        String sql = "DELETE FROM [dbo].[Product] WHERE ProductID = ?";
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, productId);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void changeStatusCustomer(String accountId, String status) {
        String sql = "UPDATE [dbo].[Account] SET [Status] = ? WHERE AccountID = ?";
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, status);
            ps.setString(2, accountId);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void deleteCustomer(String accountId) {
        String sql = "DELETE FROM [dbo].[Account] WHERE AccountID = ?";
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, accountId);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void addProductToCart(String productid, String accountId, int quantity, double totalPrice) {
        String sql = "INSERT INTO [dbo].[Cart] ([ProductID] ,[AccountID] ,[Quantity] ,[Total]) VALUES (? ,? ,? ,?)";
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, productid);
            ps.setString(2, accountId);
            ps.setInt(3, quantity);
            ps.setDouble(4, totalPrice);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void addProductToCart(String productid, String accountId) {
        String sql = "UPDATE [dbo].[Cart] SET [Quantity] = [Quantity] + 1 WHERE [ProductID] = ? AND [AccountID]= ?";
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, productid);
            ps.setString(2, accountId);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void updateCart(String productid, String accountId) {
        String sql = "UPDATE [dbo].[Cart] SET [Total] = (SELECT Quantity FROM Cart WHERE [ProductID] = ? AND [AccountID]= ?) "
                + "* (SELECT Price FROM Product WHERE ProductID = ?) WHERE [ProductID] = ? AND [AccountID]= ?";
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, productid);
            ps.setString(2, accountId);
            ps.setString(3, productid);
            ps.setString(4, productid);
            ps.setString(5, accountId);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void updateCart(int quantity, int cartId) {
        String sql = "UPDATE [dbo].[Cart] SET [Quantity] = ? WHERE ID = ?";
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, quantity);
            ps.setInt(2, cartId);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public boolean checkExistProductInCart(String productid, String accountId) {
        String sql = "SELECT * FROM [dbo].[Cart] WHERE ProductID = ? AND AccountID = ?";
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, productid);
            ps.setString(2, accountId);
            rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    public List<Cart> getCartProducts(int accountId) {
        List<Cart> list = new ArrayList<>();
        String sql = "SELECT Cart.ID, Product.Name, Cart.Quantity, Product.Price, Cart.Total, Cart.ProductID FROM Cart JOIN Product ON Cart.ProductID = Product.ProductID WHERE Cart.AccountID = ?";
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, accountId);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Cart(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getDouble(4), rs.getDouble(5), rs.getInt(6)));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }

    public void removeProductFromCart(int cartId) {
        String sql = "DELETE FROM [dbo].[Cart] WHERE ID = ?";
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, cartId);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void removeProductFromCart(String accountId) {
        String sql = "DELETE FROM [dbo].[Cart]\n"
                + "      WHERE AccountID = ?";
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, accountId);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public String getTotalCartPrice(int accountId) {
        String sql = "SELECT SUM(Total) FROM Cart WHERE AccountID = ?";
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, accountId);
            rs = ps.executeQuery();
            while (rs.next()) {
                Locale locale = new Locale("vi", "VN");
                NumberFormat formatter = NumberFormat.getCurrencyInstance(locale);
                return formatter.format(rs.getDouble(1));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return "Can't caculate total";
    }

    public static void main(String[] args) {
        DAO dao = new DAO();
        System.out.println(dao.getTotalCartPrice(1));
    }
}
