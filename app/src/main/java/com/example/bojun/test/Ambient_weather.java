package com.example.bojun.test;

import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;


public class Ambient_weather extends Activity {
    ArrayList<String> list;
    TextView textview, textview2;
    TextView day, time, temp, weather, rain;
    TableLayout tl;
    Document doc = null;
    LinearLayout layout = null;
    TableRow tr;
    ListView list_v;
    ArrayAdapter<String> adapter;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ambient_weather);

        //textview = (TextView) findViewById(R.id.textView1);
        textview2 = (TextView) findViewById(R.id.textView2);
        //tl = (TableLayout)findViewById(R.id.table);
        list_v = (ListView)findViewById(R.id.listv);

    }

    public void onClick(View view) {

        GetXMLTask task = new GetXMLTask();
        task.execute("http://www.kma.go.kr/wid/queryDFSRSS.jsp?zone=4211067500");

    }

    private class GetXMLTask extends AsyncTask<String, Void, Document> {

        @Override
        protected Document doInBackground(String... urls) {
            URL url;
            try {
                url = new URL(urls[0]);
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder(); //XML문서 빌더 객체를 생성
                doc = db.parse(new InputSource(url.openStream())); //XML문서를 파싱한다.
                doc.getDocumentElement().normalize();

            } catch (Exception e) {
                Toast.makeText(getBaseContext(), "Parsing Error", Toast.LENGTH_SHORT).show();
            }
            return doc;
        }

        @Override
        protected void onPostExecute(Document doc) {
            list = new ArrayList<String>();
            String currentstr = "";
            //data태그가 있는 노드를 찾아서 리스트 형태로 만들어서 반환
            NodeList nodeList = doc.getElementsByTagName("data");
            //data 태그를 가지는 노드를 찾음, 계층적인 노드 구조를 반환
            NodeList current = doc.getElementsByTagName("pubDate");
            currentstr = "업데이트 : " +"\n"+ current.item(0).getChildNodes().item(0).getNodeValue();
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i); //data엘리먼트 노드
                Element fstElmnt = (Element) node;
                NodeList dayList = fstElmnt.getElementsByTagName("day");
                NodeList hourList = fstElmnt.getElementsByTagName("hour");
                NodeList nameList = fstElmnt.getElementsByTagName("temp");
                NodeList websiteList = fstElmnt.getElementsByTagName("wfKor");
                NodeList popList = fstElmnt.getElementsByTagName("pop");
                if (dayList.item(0).getChildNodes().item(0).getNodeValue().equals("0"))
                    list.add("오늘 "+hourList.item(0).getChildNodes().item(0).getNodeValue() + "시의 날씨 정보");
                else if (dayList.item(0).getChildNodes().item(0).getNodeValue().equals("1"))
                    list.add("내일 "+hourList.item(0).getChildNodes().item(0).getNodeValue() + "시의 날씨 정보");
                else
                    break;

                list.add("온도 : " + nameList.item(0).getChildNodes().item(0).getNodeValue()+
                        "/ 날씨 : " + websiteList.item(0).getChildNodes().item(0).getNodeValue()+
                        "/ 강수 확률 : " + popList.item(0).getChildNodes().item(0).getNodeValue()+"%"
                );
            }
                adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_expandable_list_item_1,list);
                list_v.setAdapter(adapter);
            textview2.setText(currentstr);
            super.onPostExecute(doc);
        }
    }//end inner class - GetXMLTask
}
