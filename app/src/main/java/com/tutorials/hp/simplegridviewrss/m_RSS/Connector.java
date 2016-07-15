package com.tutorials.hp.simplegridviewrss.m_RSS;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Oclemy on 6/10/2016 for ProgrammingWizards Channel and http://www.camposha.com.
 */
public class Connector {

    public static Object connect(String urlAddress)
    {
        try {
            URL url=new URL(urlAddress);
            HttpURLConnection con= (HttpURLConnection) url.openConnection();

            //CONNECTION PROPERTIES
            con.setRequestMethod("GET");
            con.setConnectTimeout(15000);
            con.setReadTimeout(15000);
            con.setDoInput(true);

            return con;

        } catch (MalformedURLException e) {
            e.printStackTrace();
            return ErrorTracker.WRONG_URL_FORMAT;

        } catch (IOException e) {
            e.printStackTrace();
            return ErrorTracker.CONNECTION_ERROR;
        }
    }


}
