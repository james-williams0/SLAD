package slad;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import static java.awt.Font.PLAIN;

public class DrawableArray extends ArrayList<Booking> implements Drawable {
    Font font = new Font("Monospaced", PLAIN, 16);
    public void draw(Graphics g, JPanel c){
        int i = 100; //  Default x and y coordinates, width and height values, and a tracker for what node is currently being drawn.
        int j = 50;
        int k = 1600;
        int l = 110;
        c.setBackground(Color.GRAY);
        if(size() > 0) {
            for(Booking booking : this) {
                g.setFont(font);    //  colours to other nodes, then goes to the next node to get ready for all to be drawn.
                g.setColor(Color.WHITE);
                g.fillRect(i, j, k, l);
                g.setColor(Color.BLACK);
                g.drawRect(i, j, k, l);
                g.drawString("Name: " + booking.getName(), i + 20, j + 15);
                j += 18;
                g.drawString("Date: " + booking.getDate(), i + 20, j + 15);
                j += 18;
                g.drawString("Time: " + booking.getTime(), i + 20, j + 15);
                j += 18;
                g.drawString("Number of people: " + booking.getNumOfPeople(), i + 20, j + 15);
                j += 18;
                g.drawString("Table number: " + booking.getTable(), i + 20, j + 15);
                j += 18;
                g.drawString("Details: " + booking.getDetails(), i + 20, j + 15);
                j += 18;
            }
        }
    }
}
