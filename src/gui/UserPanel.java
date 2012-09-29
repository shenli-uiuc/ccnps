package ccnps.gui;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;

public class UserPanel extends JPanel{
    public static final int HEIGHT = 500;
    public static final int WIDTH = 200;
    public static final int TEXTAREA_WIDTH = 180;
    public static final int TEXTAREA_HEIGHT = 180;
    public static final int LABEL_HEIGHT = 15;
    public static final int CONTROL_LABEL_WIDTH = 50;
    public static final int CONTROL_TEXT_WIDTH = 90;
    public static final int CONTROL_TEXT_HEIGHT = 15;
    public static final int BUTTON_WIDTH = 30;
    public static final int BUTTON_HEIGHT = 15;
    public static final int V_SPACE = 5;
    public static final int H_SPACE = 5;

    private JScrollPane _lsJScroll = null;
    private JScrollPane _hsJScroll = null;

    private JTextArea _lsJTextArea = null;
    private JTextArea _hsJTextArea = null;
    
    private Label _lsLabel = null;
    private Label _hsLabel = null;

    private TextField _nameField = null;
    private TextField _subField = null;

    private Label _nameLabel = null;
    private Label _subLabel = null;

    private JButton _nameButton = null;
    private JButton _subButton = null;

    private JScrollPane _subStatPanel = null;
    private JTable _subStatTable = null;
    private DefaultTableModel _tableModel = null;
    private String [][] _subStatData = null;

    public UserPanel(){
        initGUI();
    }

    public void initGUI(){
        this.setLayout(null);
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));

        //init JTextAreas
        _lsJTextArea = new JTextArea(TEXTAREA_WIDTH, TEXTAREA_HEIGHT);
        _hsJTextArea = new JTextArea(TEXTAREA_WIDTH, TEXTAREA_HEIGHT);

        _lsJTextArea.setLineWrap(true);
        _hsJTextArea.setLineWrap(true);

        _lsLabel = new Label("Light Server Response:");
        _hsLabel = new Label("Heavy Server Response:");

        _lsLabel.setBounds(H_SPACE, V_SPACE + 0 * (LABEL_HEIGHT + TEXTAREA_HEIGHT + V_SPACE), TEXTAREA_WIDTH, LABEL_HEIGHT);
        _hsLabel.setBounds(H_SPACE, V_SPACE + 1 * (LABEL_HEIGHT + TEXTAREA_HEIGHT + V_SPACE), TEXTAREA_WIDTH, LABEL_HEIGHT);

        _lsJScroll = new JScrollPane(_lsJTextArea);
        _hsJScroll = new JScrollPane(_hsJTextArea);

        _lsJScroll.setBounds(H_SPACE,
                V_SPACE + LABEL_HEIGHT + 0 * (LABEL_HEIGHT + TEXTAREA_HEIGHT + V_SPACE), 
                TEXTAREA_WIDTH, TEXTAREA_HEIGHT);
        _hsJScroll.setBounds(H_SPACE,
                V_SPACE + LABEL_HEIGHT + 1 * (LABEL_HEIGHT + TEXTAREA_HEIGHT + V_SPACE)
                , TEXTAREA_WIDTH, TEXTAREA_HEIGHT);

        this.add(_lsJScroll);
        this.add(_hsJScroll);
        this.add(_lsLabel);
        this.add(_hsLabel);

        //init control items
        _nameLabel = new Label("Name");
        _subLabel = new Label("Follow");

        _nameField = new TextField();
        _subField = new TextField();

        _nameButton = new JButton("S");
        _subButton = new JButton("F");

        _nameLabel.setBounds(H_SPACE, V_SPACE + 2 * (LABEL_HEIGHT + TEXTAREA_HEIGHT + V_SPACE) + 0 * (V_SPACE + LABEL_HEIGHT), 
                CONTROL_LABEL_WIDTH, LABEL_HEIGHT);
        _subLabel.setBounds(H_SPACE, V_SPACE +  2 * (LABEL_HEIGHT + TEXTAREA_HEIGHT + V_SPACE) + 1 * (V_SPACE + LABEL_HEIGHT),
                CONTROL_LABEL_WIDTH, LABEL_HEIGHT);

        _nameField.setBounds(H_SPACE + CONTROL_LABEL_WIDTH, 
                V_SPACE + 2 * (LABEL_HEIGHT + TEXTAREA_HEIGHT + V_SPACE) + 0 * (V_SPACE + LABEL_HEIGHT),
                CONTROL_TEXT_WIDTH, CONTROL_TEXT_HEIGHT);
        _subField.setBounds(H_SPACE + CONTROL_LABEL_WIDTH,
                V_SPACE + 2 * (LABEL_HEIGHT + TEXTAREA_HEIGHT + V_SPACE) + 1 * (V_SPACE + LABEL_HEIGHT),
                CONTROL_TEXT_WIDTH, CONTROL_TEXT_HEIGHT);

        _nameButton.setBounds(H_SPACE + CONTROL_LABEL_WIDTH + CONTROL_TEXT_WIDTH,
                V_SPACE + 2 * (LABEL_HEIGHT + TEXTAREA_HEIGHT + V_SPACE) + 0 * (V_SPACE + LABEL_HEIGHT),
                BUTTON_WIDTH, BUTTON_HEIGHT);
        _subButton.setBounds(H_SPACE + CONTROL_LABEL_WIDTH + CONTROL_TEXT_WIDTH,
                V_SPACE + 2 * (LABEL_HEIGHT + TEXTAREA_HEIGHT + V_SPACE) + 1 * (V_SPACE + LABEL_HEIGHT),
                BUTTON_WIDTH, BUTTON_HEIGHT);

        //init following table

        this.add(_nameLabel);
        this.add(_subLabel);
        this.add(_nameField);
        this.add(_subField);
        this.add(_nameButton);
        this.add(_subButton);

        this.repaint();
    }

    public static void main(String args[]){
        JFrame jf = new JFrame();
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        UserPanel up = new UserPanel();
        jf.add(up);
        jf.setSize(up.WIDTH, up.HEIGHT);
        jf.setResizable(false);
        jf.setVisible(true); 
    }
}
