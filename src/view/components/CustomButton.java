package view.components;

import javax.swing.JButton;
import javax.swing.SwingConstants;

import java.awt.Font;
import java.awt.event.MouseAdapter;

public class CustomButton extends JButton{

    public CustomButton(String text, int x, int y, int width, int height, MouseAdapter mouseAdapter){
        super(text);
        this.init(text, x, y, width, height, mouseAdapter);
    }

    public CustomButton(String text, int x, int y, MouseAdapter mouseAdapter){
        super(text);
        this.init(text, x, y, 89, 23, mouseAdapter);
    }

    private void init(String text, int x, int y, int width, int height, MouseAdapter mouseAdapter){
        this.setBounds(x,y,width,height);
        this.setFont(new Font("Times New Roman", Font.PLAIN, 11));
        this.setVerticalAlignment(SwingConstants.BOTTOM);
        this.addMouseListener(mouseAdapter);
    }
}