/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Product;


public class ProductDAO extends DBContext {
    
   public int getTotalAllProduct() {
       Connection conn = null;
       PreparedStatement ps = null;
       ResultSet rs = null;
    String query = "SELECT COUNT(*) AS total FROM product";
    int total = 0;
    try {
        conn = new DBContext().connection;
        ps = conn.prepareStatement(query);
        rs = ps.executeQuery();
        if (rs.next()) {
            total = rs.getInt("total");
        }
    } catch (SQLException e) {
        Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, "Error getting total number of products", e);
    } finally {
        try {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, "Error closing resources", ex);
        }
    }
    return total;
}


    public Vector<Product> getAll() {
        PreparedStatement stm = null;
        ResultSet rs = null;
        Vector<Product> products = new Vector<>();
        String sql = "select * from [product]";
        try {
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id"), quantity = rs.getInt("quantity"), brand_id = rs.getInt("brand_id");
                String name = rs.getString("name"), description = rs.getString("description"), image_url = rs.getString("image_url");
                double price = rs.getDouble("price");
                Date release_date = rs.getDate("release_date");

                products.add(new Product(id, quantity, brand_id, name, description, image_url, price, release_date));
            }
            return products;

        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class
                    .getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stm.close();
                rs.close();
                connection.close();

            } catch (SQLException ex) {
                Logger.getLogger(ProductDAO.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public int insertProduct(Product p) {
        PreparedStatement stm = null;
        ResultSet rs = null;
        int generatedId = -1;

        String sql = "INSERT INTO [dbo].[product]\n"
                + "           ([name]\n"
                + "           ,[price]\n"
                + "           ,[quantity]\n"
                + "           ,[description]\n"
                + "           ,[image_url]\n"
                + "           ,[brand_id]\n"
                + "           ,[release_date])\n"
                + "     VALUES\n"
                + "           (?, ?, ?, ?, ?, ?, ?)";
        try {
            stm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stm.setString(1, p.getName());
            stm.setDouble(2, p.getPrice());
            stm.setInt(3, p.getQuantity());
            stm.setString(4, p.getDescription());
            stm.setString(5, p.getImage_url());
            stm.setInt(6, p.getBrand_id());
            stm.setDate(7, p.getRelease_date());
            stm.executeUpdate();

            //get generatedId
            rs = stm.getGeneratedKeys();
            if (rs.next()) {
                generatedId = rs.getInt(1);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class
                    .getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stm.close();
                rs.close();
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(ProductDAO.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
        return generatedId;
    }

    public void updateProduct(Product p, int pid) {
        PreparedStatement stm = null;

        String sql = "UPDATE [dbo].[product]\n"
                + "   SET [name] = ?\n"
                + "      ,[price] = ?\n"
                + "      ,[quantity] = ?\n"
                + "      ,[release_date] = ?\n"
                + " WHERE id = ?";
        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, p.getName());
            stm.setDouble(2, p.getPrice());
            stm.setInt(3, p.getQuantity());
            stm.setDate(4, p.getRelease_date());
            stm.setInt(5, pid);
            stm.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class
                    .getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stm.close();
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(ProductDAO.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public int deletetProduct(int id) {
        int n = 0;
        PreparedStatement stm = null;
        ResultSet rs = getData("select * from [dbo].[order_detail] where product_id = " + id);

        String sql = "DELETE FROM [dbo].[product]\n"
                + "      WHERE id = ?";
        try {
            if (!rs.next()) {
                stm = connection.prepareStatement(sql);
                stm.setInt(1, id);
                
                n = stm.executeUpdate();
            }
            
        } catch (SQLException ex) {
            n = -1;
            Logger.getLogger(ProductDAO.class
                    .getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                rs.close();
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(ProductDAO.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
        return n;
    }

    public Product getProductsById(int productId) {
        PreparedStatement stm = null;
        ResultSet rs = null;
        Product product = null;
        String sql = "select * from [product] where id = ?";
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, productId);
            rs = stm.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id"), quantity = rs.getInt("quantity"), brand_id = rs.getInt("brand_id");
                String name = rs.getString("name"), description = rs.getString("description"), image_url = rs.getString("image_url");
                double price = rs.getDouble("price");
                Date release_date = rs.getDate("release_date");

                product = new Product(id, quantity, brand_id, name, description, image_url, price, release_date);
            }
            return product;

        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class
                    .getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stm.close();
                rs.close();
                connection.close();

            } catch (SQLException ex) {
                Logger.getLogger(ProductDAO.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }
    
    
    

    //search by name
    public Vector<Product> getProductsByKeywords(String s) {
        PreparedStatement stm = null;
        ResultSet rs = null;
        Vector<Product> products = new Vector<>();
        String sql = "select * from [product] where name like ?";
        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, "%" + s + "%");
            rs = stm.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id"), quantity = rs.getInt("quantity"), brand_id = rs.getInt("brand_id");
                String name = rs.getString("name"), description = rs.getString("description"), image_url = rs.getString("image_url");
                double price = rs.getDouble("price");
                Date release_date = rs.getDate("release_date");

                products.add(new Product(id, quantity, brand_id, name, description, image_url, price, release_date));
            }
            return products;

        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class
                    .getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stm.close();
                rs.close();
                connection.close();

            } catch (SQLException ex) {
                Logger.getLogger(ProductDAO.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }
    
    
//    //chia trang
//    public Vector<Product> getProductsByPage(int index) {
//    PreparedStatement stm = null;
//    ResultSet rs = null;
//    Connection conn = null; 
//    Vector<Product> list = new Vector<>();
//    
//    String sql = "select * from Product order by id offset ? rows fetch next 6 rows only";
//    try {
//        conn = new DBContext().connection;
//        
//        stm = connection.prepareStatement(sql);
//        stm.setInt(1, (index - 1) * 6);
//        rs = stm.executeQuery();
//        while (rs.next()) { 
//            list.add(new Product(rs.getInt(1)),
//                    rs.getInt(2),
//            rs.getInt(3),
//            rs.getString(4),
//            rs.getString(5),
//            rs.getString(6),
//            rs.getDouble(7),
//            rs.getDate(8));
//        }
//        return list;
//    } catch (SQLException ex) {
//    }
//    return list;
//}


    public Vector<Product> sortProducts(Vector<Product> products, String sortBy) {
        if (sortBy.equals("priceLowHigh")) {
            products.sort(Comparator.comparing(Product::getPrice));
        }

        if (sortBy.equals("priceHighLow")) {
            products.sort(Comparator.comparing(Product::getPrice, Comparator.reverseOrder()));
        }

        if (sortBy.equals("latest")) {
            products.sort(Comparator.comparing(Product::getRelease_date, Comparator.reverseOrder()));
        }
        return products;
    }

    public Vector<Product> filterByPrice(String filter, Vector<Product> products) {
        Vector<Product> productsAfterFilter = new Vector<>();

        switch (filter) {
            case "price-500-750":
                productsAfterFilter = filterPrice(500, 750, products);
                break;
            case "price-750-1000":
                productsAfterFilter = filterPrice(750, 1000, products);
                break;
            case "price-1000-1500":
                productsAfterFilter = filterPrice(1000, 1500, products);
                break;
            case "price-1500up":
                productsAfterFilter = filterPrice(1500, Double.MAX_VALUE, products);
                break;
            default:
                return products;
        }

        return productsAfterFilter;
    }

    public Vector<Product> filterByBrand(String filter, Vector<Product> products) {
        Vector<Product> productsAfterFilter = new Vector<>();

        if (filter.equals("brand-all")) {
            return products;
        }
        String[] filterSplits = filter.split("[-]");
        int brandId = Integer.parseInt(filterSplits[1]);
        productsAfterFilter = filterBrand(brandId, products);

        return productsAfterFilter;
    }

    private Vector<Product> filterPrice(double min, double max, Vector<Product> products) {
        Vector<Product> productsAfterFilter = new Vector<>();

        for (Product product : products) {
            if (product.getPrice() < max && product.getPrice() > min) {
                productsAfterFilter.add(product);
            }
        }
        return productsAfterFilter;
    }

    private Vector<Product> filterBrand(int id, Vector<Product> products) {

        Vector<Product> productsAfterFilter = new Vector<>();
        for (Product product : products) {
            if (product.getBrand_id() == id) {
                productsAfterFilter.add(product);
            }
        }
        return productsAfterFilter;
    }

}
