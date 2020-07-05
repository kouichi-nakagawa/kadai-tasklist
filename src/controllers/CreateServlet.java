package controllers;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.todo;
import utils.DBUtil;
import validators.todoValidator;

/**
 * Servlet implementation class CreateServlet
 */
@WebServlet("/create")
public class CreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String _token = (String)request.getParameter("_token");
        if(_token != null && _token.equals(request.getSession().getId())) {
            EntityManager em = DBUtil.createEntityManager();

            todo td = new todo();

            String content = request.getParameter("content");
            td.setContent(content);

            Timestamp currentTime = new Timestamp(System.currentTimeMillis());
            td.setCreated_at(currentTime);
            td.setUpdated_at(currentTime);

            List<String> errors = todoValidator.validate(td);
            if(errors.size() > 0) {
                em.close();

                request.setAttribute("_token",request.getSession().getId());
                request.setAttribute("todo",td);
                request.setAttribute("errors",errors);

                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/tasks/edit.jsp");
                rd.forward(request, response);
            } else {

            em.getTransaction().begin();
            em.persist(td);
            em.getTransaction().commit();
            request.getSession().setAttribute("flush", "登録が完了しました");
            em.close();

            request.getSession().removeAttribute("tasks_id");

            response.sendRedirect(request.getContextPath() + "/index");
        }
     }
  }
}
