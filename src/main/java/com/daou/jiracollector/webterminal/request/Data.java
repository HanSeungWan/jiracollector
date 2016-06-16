package com.daou.jiracollector.webterminal.request;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by intern on 2016-03-25.
 */
public class Data {

    private Logger logger = Logger.getLogger(getClass());
    private Parser parser = new Parser();

    public ArrayList<String> getRequestProjectVersion(HttpServletRequest request) throws IOException {

        ArrayList<String> requestList = new ArrayList<String>();

        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String totalData = "";

        if (br != null) {
            totalData = br.readLine();
        }

        logger.info(totalData);

        String result[] = parser.getJSONDataProjectVersion(totalData);

        String product = result[0];
        String version = result[1];

        requestList.add(0, version);
        requestList.add(1, product);

        logger.info("receive product=[" + product + "] receive version=[" + version + "]");

        return requestList;
    }

    public ArrayList<String> getRequestProjectVersionDate(HttpServletRequest request) throws IOException {

        ArrayList<String> requestList = new ArrayList<String>();

        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String totalData = "";

        if (br != null) {
            totalData = br.readLine();
        }

        logger.info(totalData);

        String result[] = parser.getJSONDataProjectVersionDate(totalData);

        br.close();

        String product = result[0];
        String version = result[1];
        String startDate = result[2];
        String endDate = result[3];

        logger.info("receive product=[" + product + "] receive version=[" + version + "] receive startDate=["
                + startDate + "] receive endDate=[" + endDate + "]");

        requestList.add(0, version);
        requestList.add(1, product);
        requestList.add(2, startDate);
        requestList.add(3, endDate);

        return requestList;
    }

    public ArrayList<String> getRequestProjectJiraProjectName(HttpServletRequest request) throws IOException {

        ArrayList<String> requestList = new ArrayList<String>();

        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String totalData = "";

        if (br != null) {
            totalData = br.readLine();
        }

        logger.info(totalData);

        String result[] = parser.getJSONProjectJiraProjectName(totalData);

        br.close();

        String project = result[0];
        String jiraProject = result[1];

        logger.info("receive product=[" + project + "] receive jira project=[" + jiraProject+"]");

        requestList.add(0, project);
        requestList.add(1, jiraProject);

        return requestList;
    }

    public ArrayList<String> getReqeustProject(HttpServletRequest request) throws IOException {

        ArrayList<String> requestList = new ArrayList<String>();

        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String product = "";

        if (br != null) {
            product = br.readLine();
        }

        product = product.replace("product=", "");

        logger.info("receive product=[" + product +"]" );

        requestList.add(0, product);

        return requestList;
    }

    public ArrayList<String> getRequestProjectJiraProjectManager(HttpServletRequest request) throws IOException {

        ArrayList<String> requestList = new ArrayList<String>();

        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String totalData = "";

        if (br != null) {
            totalData = br.readLine();
        }

        logger.info(totalData);

        String result[] = parser.getJSONDataProjectJiraProjectManager(totalData);

        br.close();

        String product = result[0];
        String jiraProduct = result[1];
        String userName = result[2];

        logger.info("receive product=[" + product + "] receive jiraProduct=[" + jiraProduct + "] receive userName=[" + userName + "]");

        requestList.add(0, product);
        requestList.add(1, jiraProduct);
        requestList.add(2, userName);

        return requestList;
    }


    public ArrayList<String> getRequestProjectInfo(HttpServletRequest request) throws IOException {

        ArrayList<String> requestList = new ArrayList<>();

        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String totalData = br.readLine();

        logger.info(totalData);

        String result[] = parser.getJSONDataInsertProjectInfo(totalData);

        br.close();

        String product = result[0];
        String jiraProduct = result[1];
        String userName = result[2];
        String projectId  = result[3];

        logger.info("receive product=[" + product + "] receive jiraProduct=[" + jiraProduct + "] receive userName=[" + userName + "] receive projectId =["
                + projectId  +"]");

        requestList.add(0, product);
        requestList.add(1, jiraProduct);
        requestList.add(2, userName);
        requestList.add(3, projectId );

        return requestList;
    }

    public ArrayList<String> getRequestJiraProjectNameActive(HttpServletRequest request) throws IOException {

        ArrayList<String> requestList;

        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));

        String totalData = br.readLine();

        logger.info(totalData);

        requestList = parser.getJSONDataJiraProjectActive(totalData);

        for(String projectName : requestList){

            logger.info("receive=[" + projectName +"]");
        }

        br.close();

        return requestList;
    }

    public ArrayList<String> getRequestUserName(HttpServletRequest request) throws IOException {

        ArrayList<String> requestList = new ArrayList<>();

        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));

        String totalData = br.readLine();

        logger.info(totalData);

        br.close();

        String result[] = parser.getJSONDataUserName(totalData);

        String userName = result[0];

        logger.info("receive=[" + userName + "]");


        requestList.add(0,userName);

        return requestList;
    }

    public ArrayList<String> getRequestVersionNameReleaseStatusProjectName(HttpServletRequest request) throws IOException {

        ArrayList<String> requestList = new ArrayList<>();

        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));

        String totalData = br.readLine();

        logger.info(totalData);

        br.close();

        String result[] = parser.getJSONDataVersionNameReleaseStatusProjectName(totalData);

        String versionName = result[0];
        String releaseStatus = result[1];
        String projectName = result[2];

        logger.info("receive versionName=[" + versionName + "]" + "receive releaseStauts=" + "[" + releaseStatus + "]" + "receive releaseStauts=" + "[" + projectName + "]");

        requestList.add(0,versionName);
        requestList.add(1,releaseStatus);
        requestList.add(2,projectName);

        return requestList;
    }

    public  ArrayList<String> getRequestIndicatorData(HttpServletRequest request) throws IOException {

        ArrayList<String> requestList = new ArrayList<>();

        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));

        String totalData = br.readLine();

        logger.info(totalData);

        br.close();

        String result[] = parser.getJSONDataIndicatorData(totalData);

        String jiraProjectName = result[0];
        String graphtype = result[1];
        String targetNo = result[2];
        String targetChar = result[3];
        String explanation = result[4];

        logger.info("receive jiraProjectName=[" + jiraProjectName + "]" + "receive graphtype=" + "[" + graphtype + "]" + "receive targetNo=" + "[" + targetNo + "]" + "receive targetChar=" + "[" + targetChar + "]" + "receive explanation=" + "[" + explanation + "]" );

        requestList.add(0,jiraProjectName);
        requestList.add(1,graphtype);
        requestList.add(2,targetNo);
        requestList.add(3,targetChar);
        requestList.add(4,explanation);

        return requestList;
    }

    public  ArrayList<String> getRequestDeleteIndicatorList(HttpServletRequest request) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));

        String  totalData = br.readLine();

        logger.info(totalData);

        br.close();

        ArrayList<String> result = parser.getJSONDataDeleteIndicatorList(totalData);

        String projectId = result.get(0);

        logger.info("receive projectId=[" + projectId + "]");

        for(int i = 1; i < result.size(); i++){

            logger.info("receive graphList=[" + result.get(i) + "]");
        }

        return result;
    }
}
