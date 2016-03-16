package com.kotsovskyi.action;

import org.json.JSONException;
import org.json.simple.parser.ParseException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface Action {
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException, JSONException;
}
