package com.kotsovskyi.servlet;

import com.kotsovskyi.action.TakeShotAction;
import com.kotsovskyi.action.TakeShotHelper;
import org.json.JSONException;
import org.json.simple.parser.ParseException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TakeShotServlet extends HttpServlet {
    private TakeShotAction takeShotAction = new TakeShotAction();
    private TakeShotHelper takeShotHelper = new TakeShotHelper();

    @Override
    public void init() throws ServletException {
        super.init();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page = null;
        try {
            page = takeShotAction.execute(request, response);
            try {
                takeShotHelper.execute(request,response);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
        try {
            dispatcher.forward(request, response);
        } catch (NullPointerException e) {
        } catch (IllegalStateException e) {
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
