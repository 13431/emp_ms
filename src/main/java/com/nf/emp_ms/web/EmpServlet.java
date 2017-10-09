package com.nf.emp_ms.web;

import com.nf.emp_ms.dao.EmpDAO;
import com.nf.emp_ms.entity.Employee;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/emp")
public class EmpServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long empno = Long.parseLong(req.getParameter("empno"));
        Employee employee = new EmpDAO().getById(empno);
        req.setAttribute("emp", employee);
        req.getRequestDispatcher("/view/emp.jsp").forward(req, resp);
    }
}
