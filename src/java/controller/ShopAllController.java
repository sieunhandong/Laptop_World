/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.BrandDAO;
import dal.ProductDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Vector;
import model.Brand;
import model.Product;


public class ShopAllController extends HttpServlet {


    protected void processRequest(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
      
        
        ProductDAO paginDao = new ProductDAO();
        BrandDAO brand = new BrandDAO();
        String indexPage = req.getParameter("index");
        if (indexPage == null) {
            indexPage = "1";
        }
       
        int index = Integer.parseInt(indexPage);
        if (index == 0) {
            index = 1;
        }
        
        int total = paginDao.getTotalAllProduct();
        int endPage = total/6;
        if(endPage%6!=0){
            endPage++;
            
        }
        
        if (index >= endPage) {
            index = endPage;              
        }
        
        req.setAttribute("endPage", endPage);
        
//        Vector<Product> list = paginDao.getProductsByPage(index);
//        req.setAttribute("listProduct", list);
        
        req.setAttribute("indexNow", index);
        
       Vector<Brand> listca = brand.getAll();
        req.setAttribute("listBrand", listca);
        req.setAttribute("none2", "none");
        req.setAttribute("none3", "none");
        req.getRequestDispatcher("Content.jsp").forward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
