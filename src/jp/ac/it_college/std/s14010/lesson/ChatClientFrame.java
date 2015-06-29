package jp.ac.it_college.std.s14010.lesson;

import jp.ac.it_college.std.s14010.lesson.MyWindowAdapter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by s14010 on 15/06/30.
 */
class ChatClientFrame extends JFrame implements ActionListener, ScrollPaneConstants {

    String server = "172.14.40.10"; // 接続サーバ名
    JButton b_talk,b_disconnect; // 送信ボタン,`切断ボタン
    JTextArea display; // チャット表示用
    JTextField input; // チャットメッセージ入力欄
    JScrollPane scroll; // displayスクロール用
    Socket sock = null;
    PrintWriter sout;
    BufferedReader sin;

    public ChatClientFrame() {
        Container contentPane = getContentPane();
        setSize(300,300);
        addWindowListener(new MyWindowAdapter());
        contentPane.setLayout(new FlowLayout());

        display = new JTextArea("", 10, 25);
        scroll = new JScrollPane(display, VERTICAL_SCROLLBAR_ALWAYS, HORIZONTAL_SCROLLBAR_NEVER);
        contentPane.add(scroll);
        display.setEditable(false);

        input = new JTextField("", 25);
        contentPane.add(input);
        input.addActionListener(this);

        b_talk = new JButton("送信");
        contentPane.add(b_talk);
        b_talk.addActionListener(this);

        b_disconnect = new JButton("切断");
        contentPane.add(b_disconnect);
        b_disconnect.addActionListener(this);
    }

    public void actionPerformed(ActionEvent ae) {
        if (sock != null && sock.isConnected()) {
            if (ae.getSource() == b_disconnect) { // 切断ボタンが押されたら
                System.exit(0);

            }
            // それ以外(入力欄で改行 or 送信ボタン)
            System.out.println(input.getText());
            sout.println(input.getText());
            input.setText(""); // 入力欄を空にする
            sout.flush();
        } else { // サーバに接続されていない
            append_display("サーバーに接続されてません。\n");
        }
    }

    public void connect() {
        try {
            InetAddress addr = InetAddress.getByName(server);
            sock = new Socket("typosone.jp", 5228);
            System.out.println("接続しました。");
            sout = new PrintWriter(new OutputStreamWriter(sock.getOutputStream()));
            sin = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            char[] t = new char[256];
            sout.flush();
            while(sock != null && sock.isConnected()) {
                String sain = sin.readLine(); // サーバからのメッセージを受信
                append_display(sain + "\n"); // 表示に追加する
                System.out.println(sain);
            }
        }
        catch (UnknownHostException e) {
            System.err.println(e);
        }
        catch (IOException e) {
            System.err.println(e);
            sock = null;
        }
    }

    public void append_display(String mess) {
        // メッセージを表示内容に追加
        display.append(mess);
        // 表示位置を内容の一番下(最新)にもってくる
        display.setCaretPosition(display.getText().length());
    }
}
