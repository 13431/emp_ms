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
        List<Employee> employees = null;

        String ename = req.getParameter("ename");

        String ename2 = req.getParameter("ename2");
        String lowsal = req.getParameter("lowsal");
        String hisal = req.getParameter("hisal");

        if(ename != null && !ename.isEmpty()) {
            employees = new EmpDAO().queryByEname(ename);
        } else if ((ename2 != null && !ename2.isEmpty())
                || (lowsal != null && !lowsal.isEmpty())
                || (hisal != null && !hisal.isEmpty())) {
            // employees = new EmpDAO().queryByConditions(ename2, lowsal, hisal);
            employees = new EmpDAO().criteriaByConditions(ename2, lowsal, hisal);
        }
        else {
            employees = new EmpDAO().getAll();
        }

        req.setAttribute("emps", employees);
        req.getRequestDispatcher("/view/all.jsp").forward(req, resp);
    }
}
