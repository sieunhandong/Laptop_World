/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dal.BrandDAO;
import dal.ProductDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Vector;
import model.Brand;
import model.Product;


public class CustomerController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        HttpSession session = req.getSession();
        String service = req.getParameter("service");
        Vector<Brand> brands = (new BrandDAO()).getAll();
        req.setAttribute("allBrands", brands);

        if (session.getAttribute("numberProductsInCart") == null) {
            session.setAttribute("numberProductsInCart", 0);
        }
        
        if (service == null) {
            service = "listAllProducts";
        }

        if (service.equals("listAllProducts")) {
            Vector<Product> products = (new ProductDAO()).getAll();
            req.setAttribute("allProducts", products);

            req.getRequestDispatcher("index.jsp").forward(req, resp);
        }

        if (service.equals("searchByKeywords")) {
            String keywords = req.getParameter("keywords");
            String sortBy = req.getParameter("sortBy");
            String filterByPrice = req.getParameter("filterByPrice");
            String filterByBrand = req.getParameter("filterByBrand");

            if (filterByPrice == null) {
                filterByPrice = "price-all";
            }

            if (filterByBrand == null) {
                filterByBrand = "brand-all";
            }

            Vector<Product> productsAfterFilterByKeywords = (new ProductDAO()).getProductsByKeywords(keywords);
            Vector<Product> productsAfterFilterByPrice;
            Vector<Product> productsAfterFilterByBrand;

            req.setAttribute("keywords", keywords);
            //filter by price
            productsAfterFilterByPrice = (new ProductDAO()).filterByPrice(filterByPrice, productsAfterFilterByKeywords);
            req.setAttribute("filterByPrice", filterByPrice);

            //filter by brand
            productsAfterFilterByBrand = (new ProductDAO()).filterByBrand(filterByBrand, productsAfterFilterByPrice);
            req.setAttribute("filterByBrand", filterByBrand);

            if (sortBy == null || sortBy.equals("unsorted")) {
                req.setAttribute("allProducts", productsAfterFilterByBrand);
                req.setAttribute("sortBy", "unsorted");

            } else if (sortBy.equals("priceLowHigh") || sortBy.equals("priceHighLow") || sortBy.equals("latest")) {
                Vector<Product> productsAfterFilter = (new ProductDAO()).sortProducts(productsAfterFilterByBrand, sortBy);

                req.setAttribute("allProducts", productsAfterFilter);
                req.setAttribute("sortBy", sortBy);
            }
            req.getRequestDispatcher("index.jsp").forward(req, resp);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("index.jsp");
    }

}
