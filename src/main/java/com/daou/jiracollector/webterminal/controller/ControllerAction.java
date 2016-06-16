package com.daou.jiracollector.webterminal.controller;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 이 클래스는 properties를 설정하여 .do를 통해  javascript와 java를 연결해주는 클래스입니다.
 * 이 클래스는 사용자의 요청을 분석하여 request와 response의 역활을 수행합니다.
 */

public class ControllerAction extends HttpServlet {

    private Map commandMap = new HashMap();

    public void init(ServletConfig config) throws ServletException {

        //loadProperties("com/daou/jiracollector/properties/Command");

        loadProperties("/Command");
    }

    // properties 설정
    private void loadProperties(String path) {

        ResourceBundle rbHome;

        rbHome = ResourceBundle.getBundle(path);

        Enumeration<String> actionEnumHome = rbHome.getKeys();

        while (actionEnumHome.hasMoreElements()) {
            String command = actionEnumHome.nextElement();

            String className = rbHome.getString(command);

            try {

                Class commandClass = Class.forName(className);

                Object commandInstance = commandClass.newInstance();

                commandMap.put(command, commandInstance);

            } catch (ClassNotFoundException e) {

                // throw new ServletException(e);

            } catch (InstantiationException | IllegalAccessException e) {

                e.printStackTrace();

            }

        }

    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        requestPro(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        requestPro(request, response);

    }

    // 사용자의 요청을 분석해서 해당 작업을 처리
    private void requestPro(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        CommandAction com;

        try {

            String command = request.getRequestURI();

            if (command.indexOf(request.getContextPath()) == 0) {

                command = command.substring(request.getContextPath().length());

            }

            com = (CommandAction) commandMap.get(command);

            if (com == null) {

                System.out.println("not found : " + command);

                return;

            }

            com.requestPro(request, response);

        } catch (Throwable e) {

            //e.printStackTrace();

        }
    }

}