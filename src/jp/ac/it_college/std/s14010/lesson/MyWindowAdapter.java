package jp.ac.it_college.std.s14010.lesson;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by s14010 on 15/06/30.
 */
public class MyWindowAdapter extends WindowAdapter {
    public void windowClosing(WindowEvent e) {
        System.exit(0);
    }
}