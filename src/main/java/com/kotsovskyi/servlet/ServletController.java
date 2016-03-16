package com.kotsovskyi.servlet;

import com.kotsovskyi.action.*;
import com.kotsovskyi.server.Server;
import org.json.JSONException;
import org.json.simple.parser.ParseException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ServletController extends HttpServlet {

    private ActionFactory actionFactory = ActionFactory.getInstance();
    private StartGameAction startGameAction = new StartGameAction();
    private StartGameHelper startGameHelper = new StartGameHelper();

    public ServletController() {
        System.out.println("online: " + Server.getInstance().numberOfOnline + " players");
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String page = null;
        String login;
        try {
            login = request.getParameter("login");
            if(login != null) {
                HttpSession httpSession = request.getSession(true);
                httpSession.setAttribute("login", login);
            }

            Action action = actionFactory.getAction(request);
            page = action.execute(request, response);
            startGameAction.execute(request, response);
            startGameHelper.execute(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
            request.setAttribute("messageError", "Servlet exception");
        } catch (IOException e) {
            e.printStackTrace();
            request.setAttribute("messageError", "Input or output error");
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
        try {
            dispatcher.forward(request, response);
        } catch (NullPointerException e) {
            //System.out.println("lol");
        } catch (IllegalStateException e) {
            //System.out.println("lol lol");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
