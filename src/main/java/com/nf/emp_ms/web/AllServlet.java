package com.nf.emp_ms.web;

import com.nf.emp_ms.dao.EmpDAO;
import com.nf.emp_ms.entity.Employee;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@WebServlet("/all")
public class AllServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Employee> employees = new EmpDAO().getAll();
        req.setAttribute("emps", employees);
        req.getRequestDispatcher("/view/all.jsp").forward(req, resp);
    }
}
