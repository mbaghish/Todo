package src;
import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class PlaceholderTextField extends JTextField implements FocusListener {

    private static final long serialVersionUID = 1L;
    private String placeholder;
    private int xOffset = 60; // Adjust the horizontal offset to shift the text to the right
    private int yOffset = 3; // Adjust the vertical offset to shift the text to down
    public PlaceholderTextField(String placeholder) {
        this.placeholder = placeholder;
        addFocusListener(this);
    }

    @Override
    public void focusGained(FocusEvent e) {
        if (getText().isEmpty()) {
            repaint();
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        if (getText().isEmpty()) {
            repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (getText().isEmpty() && !isFocusOwner()) {
            FontMetrics fm = g.getFontMetrics();
            int x = (getWidth() - fm.stringWidth(placeholder))/2 - xOffset;
            int y = getHeight() - fm.getDescent() - yOffset ; // Adjust the vertical position slightly
            g.setColor(Color.GRAY);
            g.drawString(placeholder, x, y);
        }
    }
}
