package com.tutorials.hp.simplegridviewrss.m_RSS;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.GridView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

/**
 * Created by Oclemy on 6/10/2016 for ProgrammingWizards Channel and http://www.camposha.com.
 */
public class Downloader extends AsyncTask<Void,Void,Object> {

    Context c;
    String urlAddress;
    GridView gv;

    ProgressDialog pd;

    public Downloader(Context c, String urlAddress, GridView gv) {
        this.c = c;
        this.urlAddress = urlAddress;
        this.gv = gv;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd=new ProgressDialog(c);
        pd.setTitle("Fetch data");
        pd.setMessage("Fetching Data...Please wait");
        pd.show();

    }

    @Override
    protected Object doInBackground(Void... params) {
        return this.downloadData();
    }

    @Override
    protected void onPostExecute(Object data) {
        super.onPostExecute(data);
        pd.dismiss();
        if(data.toString().startsWith("Error"))
        {
            Toast.makeText(c,data.toString(),Toast.LENGTH_SHORT).show();
        }else {
            //CALL PARSER CLASS
            new RSSParser(c, (InputStream) data,gv).execute();
        }
    }

    private Object downloadData()
    {
        Object connection=Connector.connect(urlAddress);
        if(connection.toString().startsWith("Error"))
        {
            return connection.toString();
        }

        try
        {
            HttpURLConnection con= (HttpURLConnection) connection;
            InputStream is=new BufferedInputStream(con.getInputStream());

            return is;

        } catch (IOException e) {
            e.printStackTrace();
            return ErrorTracker.IO_EROR;
        }
    }
}
















