package com.kotsovskyi.action;

import com.kotsovskyi.utils.Constants;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by romankotsovskyi on 3/29/15.
 */
public class ActionFactory {

    private static ActionFactory instance = new ActionFactory();
    private Map<String, Action> actions = new HashMap<String, Action>();

    public ActionFactory() {
        actions.put(Constants.LOGIN_ACTION, new LoginAction());
    }

    public Action getAction(HttpServletRequest request) {
        Action action = actions.get(request.getParameter("action"));
        if (action == null) {
            action = new DefaultAction();
        }
        return action;
    }

    public static ActionFactory getInstance() {
        return instance;
    }
}

