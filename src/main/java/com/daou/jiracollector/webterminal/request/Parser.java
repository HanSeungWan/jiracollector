package com.daou.jiracollector.webterminal.request;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Parser {

    /**
     * 웹에서 전송된 JSON 데이터 파싱 메서드
     *
     * @param data JSON 데이터
     * @return product, version 정보를 가지는 배열
     */
    String[] getJSONDataProjectVersion(String data) {

        String delimiter = "&";
        String result[] = new String[2];
        StringTokenizer st = new StringTokenizer(data, delimiter);

        String product = st.nextToken();
        String version = st.nextToken();

        product = product.replace("product=", "");
        version = version.replace("version=", "");

        result[0] = product;
        result[1] = version;

        return result;
    }

    String[] getJSONProjectJiraProjectName(String data) {

        String delimiter = "&";
        String result[] = new String[2];
        StringTokenizer st = new StringTokenizer(data, delimiter);

        String project = st.nextToken();
        String jiraProject = st.nextToken();

        project = project.replace("product=", "");
        jiraProject = jiraProject.replace("jiraProduct=", "");

        result[0] = project;
        result[1] = jiraProject;

        return result;
    }

    String[] getJSONDataProjectVersionDate(String data) {

        String delimiter = "&";
        String result[] = new String[4];
        StringTokenizer st = new StringTokenizer(data, delimiter);

        String product = st.nextToken();
        String version = st.nextToken();
        String startDate = st.nextToken();
        String endDate = st.nextToken();

        product = product.replace("product=", "");
        version = version.replace("version=", "");
        startDate = startDate.replace("startDate=", "");
        endDate = endDate.replace("endDate=", "");

        result[0] = product;
        result[1] = version;
        result[2] = startDate;
        result[3] = endDate;

        return result;
    }

    String[] getJSONDataInsertProjectInfo(String data) throws UnsupportedEncodingException {

        String delimiter = "&";
        String result[] = new String[4];
        StringTokenizer st = new StringTokenizer(data, delimiter);

        String projectId = st.nextToken();
        String product = st.nextToken();
        String jiraProduct = st.nextToken();
        String userName = st.nextToken();

        projectId = projectId.replace("projectId=", "");
        product = product.replace("product=", "");
        jiraProduct = jiraProduct.replace("jiraProduct=", "");
        userName = userName.replace("managerName=", "");

        result[0] = product;
        result[1] = jiraProduct;
        result[2] = URLDecoder.decode(userName, "UTF-8");
        result[3] = projectId;

        return result;
    }

    String[] getJSONDataProjectJiraProjectManager(String data) throws UnsupportedEncodingException {

        String delimiter = "&";
        String result[] = new String[4];
        StringTokenizer st = new StringTokenizer(data, delimiter);

        String product = st.nextToken();
        String jiraProduct = st.nextToken();
        String userName = st.nextToken();

        product = product.replace("product=", "");
        jiraProduct = jiraProduct.replace("jiraProduct=", "");
        userName = userName.replace("managerName=", "");

        result[0] = product;
        result[1] = jiraProduct;
        result[2] = URLDecoder.decode(userName, "UTF-8");

        return result;
    }

    public ArrayList<String> getJSONDataJiraProjectActive(String data) {

        String delimiter = "&";
        ArrayList<String> result = new ArrayList<>();

        StringTokenizer st = new StringTokenizer(data, delimiter);

        while (st.hasMoreTokens()) {
            String jiraProejctName = st.nextToken();
            jiraProejctName = jiraProejctName.substring("checkArray%5B%5D=".length(), jiraProejctName.length());

            result.add(jiraProejctName);
        }

        return result;
    }

    String[] getJSONDataUserName(String data) {

        String delimiter = "&";
        String result[] = new String[1];
        StringTokenizer st = new StringTokenizer(data,delimiter);

        String userName = st.nextToken();

        userName = userName.replace("userName=","");

        result[0] = userName;

        return result;
    }


    String[] getJSONDataVersionNameReleaseStatusProjectName(String data) {

        String delimiter = "&";
        String result[] = new String[3];
        StringTokenizer st = new StringTokenizer(data,delimiter);

        String versionName = st.nextToken();
        String releaseStatus = st.nextToken();
        String projectName = st.nextToken();

        versionName = versionName.replace("versionName=","");
        releaseStatus = releaseStatus.replace("releaseStatus=","");
        projectName = projectName.replace("projectName=","");

        result[0] = versionName;
        result[1] = releaseStatus;
        result[2] = projectName;

        return result;
    }

    String[] getJSONDataIndicatorData(String data) throws UnsupportedEncodingException {

        String delimiter = "&";
        String result[] = new String[5];
        StringTokenizer st = new StringTokenizer(data,delimiter);

        String jiraProjectName = st.nextToken();
        String graphtype = st.nextToken();
        String targetNo = st.nextToken();
        String targetChar = st.nextToken();
        String explanation = st.nextToken();


        jiraProjectName = jiraProjectName.replace("jiraProjectName=","");
        graphtype = graphtype.replace("graphtype=","");
        targetNo = targetNo.replace("targetNo=","");
        targetChar = targetChar.replace("targetChar=","");
        explanation = explanation.replace("explanation=","");

        result[0] = jiraProjectName;
        result[1] = URLDecoder.decode(graphtype, "UTF-8");
        result[2] = targetNo;
        result[3] = URLDecoder.decode(targetChar, "UTF-8");
        result[4] = URLDecoder.decode(explanation, "UTF-8");

        return result;
    }

    public ArrayList<String> getJSONDataDeleteIndicatorList(String data) throws UnsupportedEncodingException {

        String delimiter = "&";
        ArrayList<String> result = new ArrayList<>();

        StringTokenizer st = new StringTokenizer(data, delimiter);

        String projectId = st.nextToken();

        projectId = projectId.replace("jiraproject=","");
        result.add(0,projectId);

        int count = 1;

        while (st.hasMoreTokens()) {
            String graptype = st.nextToken();
            graptype = URLDecoder.decode(graptype, "UTF-8");
            graptype = graptype.substring("checkArray[]=".length(), graptype.length());
            result.add(count, graptype);

            count++;
        }

        return result;
    }
}
