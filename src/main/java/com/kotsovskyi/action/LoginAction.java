package com.kotsovskyi.action;

import com.kotsovskyi.server.Client;
import com.kotsovskyi.server.ThreadEchoHandler;
import com.kotsovskyi.service.UserService;
import com.kotsovskyi.utils.ClientsDirectory;
import com.kotsovskyi.utils.Constants;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginAction implements Action {

    private UserService userService;

    public LoginAction() {
        userService = new UserService();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter(Constants.REQUEST_PARAMETER_LOGIN);
        String password = request.getParameter(Constants.REQUEST_PARAMETER_PASSWORD);
        String user;

        if (login != null && password != null) {
            user = userService.getUser(login, password);

            if (user != null) {
                Client client = new Client(user);
                ThreadEchoHandler.addApplicationForBattle(client);
                ClientsDirectory.addClient(user, client);
                new Thread(client).start();

                return "/jsp/battlefields.jsp";
            }
        }
        return "/jsp/index.jsp";
    }
}
