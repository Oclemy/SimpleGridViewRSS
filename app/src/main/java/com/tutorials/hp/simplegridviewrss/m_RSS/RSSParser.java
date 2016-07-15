package com.tutorials.hp.simplegridviewrss.m_RSS;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Oclemy on 6/10/2016 for ProgrammingWizards Channel and http://www.camposha.com.
 */
public class RSSParser extends AsyncTask<Void,Void,Boolean> {

    Context c;
    InputStream is;
    GridView gv;

    ProgressDialog pd;
    ArrayList<String> headlines=new ArrayList<>();

    public RSSParser(Context c, InputStream is, GridView gv) {
        this.c = c;
        this.is = is;
        this.gv = gv;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd=new ProgressDialog(c);
        pd.setTitle("Parse Data");
        pd.setMessage("Parsing...Please wait");
        pd.show();
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        return this.parseRSS();
    }

    @Override
    protected void onPostExecute(Boolean isParsed) {
        super.onPostExecute(isParsed);
        pd.dismiss();
        if(isParsed)
        {
            //BIND DATA
            gv.setAdapter(new ArrayAdapter<String>(c,android.R.layout.simple_list_item_1,headlines));

        }else {
            Toast.makeText(c,"Unable To Parse",Toast.LENGTH_SHORT).show();
        }
    }

    private Boolean parseRSS()
    {
        try
        {
            XmlPullParserFactory factory=XmlPullParserFactory.newInstance();
            XmlPullParser parser=factory.newPullParser();

            //SET STREAM
            parser.setInput(is,null);
            String headline=null;

            int event=parser.getEventType();
            Boolean isWebsiteTiltle=true;

            do {
                String name=parser.getName();

                switch (event)
                {
                    case XmlPullParser.START_TAG:
                        break;

                    case XmlPullParser.TEXT:
                        headline=parser.getText();
                        break;

                    case XmlPullParser.END_TAG:

                        if(isWebsiteTiltle)
                        {
                            isWebsiteTiltle=false;
                        }else if(name.equals("title"))
                       {
                           headlines.add(headline);
                       }
                        break;
                }

                event=parser.next();

            }
            while (event != XmlPullParser.END_DOCUMENT);


            return true;

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }
}










