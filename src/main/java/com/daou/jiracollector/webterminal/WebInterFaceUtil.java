package com.daou.jiracollector.webterminal;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by intern on 2016-03-11.
 */
public class WebInterFaceUtil {

    private Logger logger = Logger.getLogger(getClass());

    public void jsonMapper(HttpServletResponse response, String json) {

        ObjectMapper mapper = new ObjectMapper();

        mapper.reader();

        response.setContentType("application/json");

        try {
            mapper.writeValue(response.getOutputStream(), json);
        } catch (Exception e) {
            logger.info("OK");
        }
    }
}
