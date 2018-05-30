package com.example.bojun.test;


import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.net.Socket;
import java.util.LinkedList;

import android.app.Activity;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class Chatting_Exercise extends Activity {
    TextView showText;
    Button Button_send;
    EditText editText_massage;
    Handler msghandler;

    SocketClient client;
    ReceiveThread receive;
    SendThread send;
    Socket socket;

    PipedInputStream sendstream = null;
    PipedOutputStream receivestream = null;

    LinkedList<SocketClient> threadList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting_exercise);

        showText = (TextView) findViewById(R.id.showText_TextView);
        editText_massage = (EditText) findViewById(R.id.editText_message);
        Button_send = (Button) findViewById(R.id.Button_send);
        threadList = new LinkedList<SocketClient>();

        client = new SocketClient("210.115.229.176",
                "8990");
        threadList.add(client);
        client.start();
        // ReceiveThread를통해서 받은 메세지를 Handler로 MainThread에서 처리(외부Thread에서는 UI변경이불가)
        msghandler = new Handler() {
            @Override
            public void handleMessage(Message hdmsg) {
                if (hdmsg.what == 1111) {
                    showText.append(hdmsg.obj.toString() + "\n");
                }
            }
        };
        //전송 버튼 클릭 이벤트
        Button_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                //SendThread 시작
                if (editText_massage.getText().toString() != null) {
                    send = new SendThread(socket);
                    send.start();

                    //시작후 edittext 초기화
                    editText_massage.setText("");
                }
            }
        });
    }

    class SocketClient extends Thread {
        boolean threadAlive;
        String ip;
        String port;
        String mac;

        //InputStream inputStream = null;
        OutputStream outputStream = null;
        BufferedReader br = null;

        private DataOutputStream output = null;

        public SocketClient(String ip, String port) {
            threadAlive = true;
            this.ip = ip;
            this.port = port;
        }

        @Override
        public void run() {

            try {
                // 연결후 바로 ReceiveThread 시작
                socket = new Socket(ip, Integer.parseInt(port));
                //inputStream = socket.getInputStream();
                output = new DataOutputStream(socket.getOutputStream());
                receive = new ReceiveThread(socket);
                receive.start();

                //mac주소를 받아오기위해 설정
                WifiManager mng = (WifiManager) getSystemService(WIFI_SERVICE);
                WifiInfo info = mng.getConnectionInfo();
                mac = info.getMacAddress();

                //mac 전송
                output.writeUTF(mac);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    class ReceiveThread extends Thread {
        private Socket socket = null;
        DataInputStream input;

        public ReceiveThread(Socket socket) {
            this.socket = socket;
            try {
                input = new DataInputStream(socket.getInputStream());
            } catch (Exception e) {
            }
        }

        // 메세지 수신후 Handler로 전달
        public void run() {
            try {
                while (input != null) {

                    String msg = input.readUTF();
                    if (msg != null) {
                        Log.d(ACTIVITY_SERVICE, "test");

                        Message hdmsg = msghandler.obtainMessage();
                        hdmsg.what = 1111;
                        hdmsg.obj = msg;
                        msghandler.sendMessage(hdmsg);
                        Log.d(ACTIVITY_SERVICE, hdmsg.obj.toString());
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    class SendThread extends Thread {
        private Socket socket;
        String sendmsg = editText_massage.getText().toString();
        DataOutputStream output;

        public SendThread(Socket socket) {
            this.socket = socket;
            try {
                output = new DataOutputStream(socket.getOutputStream());
            } catch (Exception e) {
            }
        }

        public void run() {

            try {
                // 메세지 전송부 (누군지 식별하기위한 방법으로 mac를 사용)
                Log.d(ACTIVITY_SERVICE, "11111");
                int mac = 0;
                WifiManager mng = (WifiManager) getSystemService(WIFI_SERVICE);
                WifiInfo info = mng.getConnectionInfo();
                mac = info.getIpAddress();

                if (output != null) {
                    if (sendmsg != null) {
                        output.writeUTF(mac + "  :  " + sendmsg);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NullPointerException npe) {
                npe.printStackTrace();

            }
        }
    }
}