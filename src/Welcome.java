
/**
 *
 * @author Dev
 */
import java.awt.*;
import java.awt.event.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.border.*;
import java.io.*;
import java.util.*;
import javax.swing.text.*;
import bddict.*;
import java.applet.Applet;
import java.applet.AudioClip;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URL;

public class Welcome extends JFrame implements WindowListener, MouseListener, ActionListener {

  //private int   maxrange = 10, count;
    private JFrame preloaderFrame;
    public static DebugJFrame debugWin;
    SettingsJFrame settingsWin;
  //private static JPanel txt=new JPanel();
  //private static JLabel vtxt[] = new JLabel[500];
  //private JFrame preloaderFrame;
    private Container preloaderContainer;
    private GridBagLayout preloaderLayout;
    private GridBagConstraints preloaderConstraints;
    private JLayeredPane layeredPane = new JLayeredPane();
    private JLabel picJLabel;
    private JLabel userpicJLabel;
    private JLabel shadeJLabel;
    private JLabel swName,  swVersion; //, refreshRate;
    //private int tableWidth = 800,  tableHeight = 400;
    //private JComboBox viewJCombo;
    //private String[] comboItem = { " None ", " 10 sec ", " 20 sec ", " 30 sec ", " 60 sec " };
    //private int[] comboSql = { 0, 10, 20, 30, 60};
    //private JButton viewButton;
    private JLabel copyright;
    //private static JTextArea msgTA;
    JTextPane txtpane;
    private JScrollPane jsp;
    //private static String cmd=new String();
    private Color fontcolor = new Color(87, 121, 148, 255); //blue news short (224, 227, 233, 255)
    private Color fontcolor2 = new Color(87, 121, 148, 255); // news details(light b 247, 248, 249)
    private Color fontcolor3 = new Color(63, 86, 105, 255); // news title.....( blue left 170, 178, 191, 255)
    private Color fontcolor4 = new Color(30, 30, 30, 255); //user profile
    private Color fontcolor5 = new Color(163, 171, 186, 255); //deep blue press news
    private Color fontcolor6 = new Color(63, 86, 105, 255); //white over news
    private Color fontcolor7 = new Color(30, 30, 30, 255); //title news(45, 61, 74, 255)
    private Color textAreaColor = new Color(255, 255, 255, 255); //bg text area
    private Color borderColor = new Color(40, 40, 40, 255);   //border line color
    private Color shadecolor = new Color(30, 30, 30, 80);
    //private Point origin = new Point(195, 70);
    private int x,  y;//, cn2;
    MenuBar menuBar;
    private int frameWidth;
    private int frameHeight;
    //public static RefreshThread RT;
    public static Welcome win;
    // public static long globalSleepTime;
    private int ans;
    private JOptionPane helpWindow = new JOptionPane();
    private javax.swing.JButton aboutjButton;
    private javax.swing.JToggleButton dictjButton;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JButton openjButton;
    private javax.swing.JToggleButton pausejButton;
    private javax.swing.JButton playjButton;
    private javax.swing.JButton savejButton;
    private javax.swing.JButton settingsjButton;
    private javax.swing.JButton stopjButton;
    private javax.swing.JButton plusjButton;
    private javax.swing.JButton minusjButton;
    String line;
    static String dir;
    File d, f;
    int fontsize = 20;
    boolean tagdict;
    public static bddict.Hash hash;
    JMenuItem jpopmenuItem;
    int startindex, endindex;
    public static PlaySound ps;
    public static boolean tagdebug,  tagjmf;
    JFileChooser chooser2;
    File d2, f2;

    public Welcome() throws FileNotFoundException, IOException {

        tagdebug = false;
        tagjmf = false;
        tagdict = true;
      //hash = new Hash();
        hash = Preloader.frmabout.hash;
        ps = Preloader.frmabout.ps;


        chooser2 = new JFileChooser();
        chooser2.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser2.setCurrentDirectory(new java.io.File(""));
        chooser2.setDialogTitle("Select output file...");

        try {


            JFrame.setDefaultLookAndFeelDecorated(true);
            preloaderFrame = new JFrame("Subachan v 2.0 Beta");
            //preloaderFrame.setUndecorated(true);
            preloaderFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            preloaderLayout = new GridBagLayout();
            preloaderConstraints = new GridBagConstraints();
            preloaderContainer = getContentPane();
            preloaderContainer.setLayout(preloaderLayout);

            layeredPane.setPreferredSize(new Dimension(680, 390));
            layeredPane.setBorder(BorderFactory.createTitledBorder(""));
          //Icon bug = new ImageIcon("Data\\welcome.jpg", "Process Manager");
          //Icon bug = new ImageIcon(Welcome.class.getResource("Data/" + "welcome.jpg"), "Process Manager");
            picJLabel = new JLabel(new javax.swing.ImageIcon(getClass().getResource("/Data/welcome.jpg")));
            picJLabel.setVerticalAlignment(JLabel.TOP);
            picJLabel.setHorizontalAlignment(JLabel.CENTER);
            picJLabel.setOpaque(true);
            picJLabel.setBackground(Color.black);
            picJLabel.setForeground(Color.black);
          //picJLabel.setBorder(BorderFactory.createLineBorder(Color.black));
            picJLabel.setBounds(0, 0, 680, 390);
            layeredPane.add(picJLabel, new Integer(0));
            shadeJLabel = new JLabel();
            shadeJLabel.setVerticalAlignment(JLabel.TOP);
            shadeJLabel.setHorizontalAlignment(JLabel.CENTER);
            shadeJLabel.setOpaque(true);
            shadeJLabel.setBackground(shadecolor);
            shadeJLabel.setForeground(Color.black);
          //shadeJLabel.setBorder(BorderFactory.createLineBorder(Color.black));
            shadeJLabel.setBounds(46, 106, 130, 145); //(46, 88, 130, 163);
            layeredPane.add(shadeJLabel, new Integer(1));
          //Icon userpic = new ImageIcon(Welcome.class.getResource("Data/" + "logo.jpg"), "Process Manager");
          //Icon userpic = new ImageIcon("Data\\dev.jpg");
            userpicJLabel = new JLabel(new javax.swing.ImageIcon(getClass().getResource("/Data/logo130145.png")));
          //downlaodPic(userpicJLabel);
            userpicJLabel.setSize(130, 145); //SwingConstants.LEFT
          //userpicJLabel.set
            userpicJLabel.setVerticalAlignment(JLabel.TOP);
            userpicJLabel.setHorizontalAlignment(JLabel.CENTER);
            userpicJLabel.setOpaque(true);
            userpicJLabel.setBackground(Color.WHITE);
            userpicJLabel.setForeground(Color.black);
          //picJLabel.setBorder(BorderFactory.createLineBorder(Color.black));
            userpicJLabel.setBounds(40, 100, 130, 145);
            layeredPane.add(userpicJLabel, new Integer(2));
            Font nameFont = new java.awt.Font("Arial", 3, 22);
            swName = new JLabel("Subachan" + " " + "");
          //swName = new JLabel("Devojyoti" + " " + "Aich");
            swName.setVerticalAlignment(JLabel.TOP);
            swName.setHorizontalAlignment(JLabel.LEFT);
          //swName.setOpaque(true);
          //swName.setBackground(Color.black);
            swName.setForeground(fontcolor4);
          //swName.setBorder(BorderFactory.createLineBorder(Color.black));
            swName.setBounds(60, 265, 160, 22);
            swName.setFont(nameFont);
            layeredPane.add(swName, new Integer(2));
            Font nameFont2 = new java.awt.Font("Arial", 3, 12);
            swVersion = new JLabel("version 1.0");
            swVersion.setVerticalAlignment(JLabel.TOP);
            swVersion.setHorizontalAlignment(JLabel.LEFT);
          //swVersion.setOpaque(true);
          //swVersion.setBackground(Color.black);
            swVersion.setForeground(fontcolor3);
          //swVersion.setBorder(BorderFactory.createLineBorder(Color.black));
            swVersion.setBounds(100, 285, 70, 20);
            swVersion.setFont(nameFont2);
            layeredPane.add(swVersion, new Integer(2));

            /**********************************************/
            jToolBar1 = new javax.swing.JToolBar();
            openjButton = new javax.swing.JButton();
            savejButton = new javax.swing.JButton();
            playjButton = new javax.swing.JButton();
            pausejButton = new javax.swing.JToggleButton();
            stopjButton = new javax.swing.JButton();
            dictjButton = new javax.swing.JToggleButton();
            aboutjButton = new javax.swing.JButton();
            settingsjButton = new javax.swing.JButton();
            plusjButton = new javax.swing.JButton();
            minusjButton = new javax.swing.JButton();

            //jToolBar1.setRollover(true);
            jToolBar1.setFloatable(false);
            openjButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/toolbar/folder32.png"))); // NOI18N
            openjButton.setToolTipText("Open");
            openjButton.setFocusable(false);
            openjButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
            openjButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

             openjButton.addActionListener(new java.awt.event.ActionListener() {

                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    openjButtonActionPerformed(evt);
                }
            });
            jToolBar1.add(openjButton);
            savejButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/toolbar/filesave32.png"))); // NOI18N
            savejButton.setToolTipText("Save");
            savejButton.setFocusable(false);
            savejButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
            savejButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
            savejButton.addActionListener(new java.awt.event.ActionListener() {

                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    savejButtonActionPerformed(evt);
                }
            });
            jToolBar1.add(savejButton);


            playjButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/toolbar/play32.png"))); // NOI18N
            playjButton.setToolTipText("Play");
            playjButton.setFocusable(false);
            playjButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
            playjButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
            playjButton.addActionListener(new java.awt.event.ActionListener() {

                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    playjButtonActionPerformed(evt);
                }
            });
            jToolBar1.add(playjButton);



            pausejButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/toolbar/pause32.png"))); // NOI18N
            pausejButton.setToolTipText("Pause");
            pausejButton.setFocusable(false);
            pausejButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
            pausejButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
            pausejButton.addActionListener(new java.awt.event.ActionListener() {

                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    pausejButtonActionPerformed(evt);
                }
            });
            jToolBar1.add(pausejButton);

            stopjButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/toolbar/stop32.png"))); // NOI18N
            stopjButton.setToolTipText("Stop");
            stopjButton.setFocusable(false);
            stopjButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
            stopjButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
            stopjButton.addActionListener(new java.awt.event.ActionListener() {

                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    stopjButtonActionPerformed(evt);
                }
            });
            jToolBar1.add(stopjButton);




            plusjButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/toolbar/plus32.png"))); // NOI18N
            plusjButton.setToolTipText("Increase font size");
            plusjButton.setFocusable(false);
            plusjButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
            plusjButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
            plusjButton.addActionListener(new java.awt.event.ActionListener() {

                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    plusjButtonActionPerformed(evt);
                }
            });
            jToolBar1.add(plusjButton);




            minusjButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/toolbar/minus32.png"))); // NOI18N
            minusjButton.setToolTipText("Reduce font size");
            minusjButton.setFocusable(false);
            minusjButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
            minusjButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
            minusjButton.addActionListener(new java.awt.event.ActionListener() {

                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    minusjButtonActionPerformed(evt);
                }
            });
            jToolBar1.add(minusjButton);




            dictjButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/toolbar/folder_home32.png"))); // NOI18N
            dictjButton.setToolTipText("Dictionary");
            dictjButton.setFocusable(false);
            dictjButton.setSelected(tagdict);
            dictjButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
            dictjButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
            dictjButton.addActionListener(new java.awt.event.ActionListener() {

                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    dictjButtonActionPerformed(evt);
                }
            });
            jToolBar1.add(dictjButton);


            aboutjButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/toolbar/gohome32.png"))); // NOI18N
            aboutjButton.setToolTipText("About");
            aboutjButton.setFocusable(false);
            aboutjButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
            aboutjButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
            aboutjButton.addActionListener(new java.awt.event.ActionListener() {

                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    aboutjButtonActionPerformed(evt);
                }
            });
            jToolBar1.add(aboutjButton);

            settingsjButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/toolbar/settings32.png"))); // NOI18N
            settingsjButton.setToolTipText("Settings");
            settingsjButton.setFocusable(false);
            settingsjButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
            settingsjButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
            settingsjButton.addActionListener(new java.awt.event.ActionListener() {

                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    settingsjButtonActionPerformed(evt);
                }
            });
            jToolBar1.add(settingsjButton);



            jToolBar1.setBounds(232, 320, 480, 40);
            layeredPane.add(jToolBar1, new Integer(3));
            /*********************************************/

//            msgTA = new JTextArea();
//            msgTA.setLineWrap(true);
//            msgTA.setWrapStyleWord(true);
//            msgTA.setForeground(fontcolor2);
//            msgTA.setFont(new java.awt.Font("Vrinda", 1, 18));    
//            msgTA.setBackground(textAreaColor);
//            msgTA.setEditable(false);
            txtpane = new JTextPane();
            txtpane.setFont(new java.awt.Font("Shonar Bangla", 1, fontsize));
            txtpane.addMouseListener(this);
            txtpane.addKeyListener(new java.awt.event.KeyAdapter() {

                public void keyTyped(java.awt.event.KeyEvent evt) {
                    txtpaneKeyTyped(evt);
                }
            });
            //jsp = new JScrollPane(txtpane, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
            jsp = new JScrollPane(txtpane, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
            //msgTA.setLineWrap(false);
            //msgTA.setWrapStyleWord(false);
             jsp.setOpaque(false);
            Border loweredetched;
            Border blackline;
            loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
            blackline = BorderFactory.createLineBorder(borderColor);
            TitledBorder title;
            title = BorderFactory.createTitledBorder(loweredetched, "");
            title.setTitleJustification(TitledBorder.LEFT);
            Font titleFont = new java.awt.Font("Tahoma", 3, 18);
            title.setTitleFont(titleFont);
            jsp.setBorder(title);
            jsp.setBounds(232, 15, 440, 300);
            layeredPane.add(jsp, new Integer(2));
            Font copyrightfont = new java.awt.Font("Arial", 1, 12);
            copyright = new JLabel("A project on Bangla Language Processing of CSE, SUST.");
            copyright.setVerticalAlignment(JLabel.TOP);
            copyright.setHorizontalAlignment(JLabel.LEFT);
            //copyright.setBackground(Color.black);
            copyright.setForeground(fontcolor3);
            //copyright.setBorder(BorderFactory.createLineBorder(Color.black));
            copyright.setBounds(236, 370, 410, 20); //X=232
            copyright.setFont(copyrightfont);
            layeredPane.add(copyright, new Integer(2));
            preloaderFrame.addWindowListener(this);
            addComponent(layeredPane, 0, 0, 1, 1, 0, 0, GridBagConstraints.NONE);
            preloaderFrame.add(preloaderContainer);
            preloaderFrame.pack();
            frameWidth = preloaderFrame.getWidth();
            frameHeight = preloaderFrame.getHeight();
            Toolkit k = this.getToolkit();
            Dimension dim = k.getScreenSize();
            x = (int) (dim.width / 2) - (int) (frameWidth / 2);
            y = (int) (dim.height / 2) - (int) (frameHeight / 2);
            //x=y=150;
            preloaderFrame.setBounds(x, y, frameWidth, frameHeight);
            preloaderFrame.setVisible(true);
            //preloaderFrame.setAlwaysOnTop(true);
            preloaderFrame.setResizable(false);
        } catch (Exception ex) {
            Logger.getLogger(Welcome.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void addComponent(Component component,
            int row, int column, int width, int height, int weightx, int weighty, int fill) {
        preloaderConstraints.fill = fill;
        preloaderConstraints.gridx = column;
        preloaderConstraints.gridy = row;

        preloaderConstraints.gridwidth = width;
        preloaderConstraints.gridheight = height;

        preloaderConstraints.weightx = weightx;
        preloaderConstraints.weighty = weighty;

        preloaderConstraints.weightx = weightx;
        preloaderConstraints.weighty = weighty;
        // set mLoginConstraints and add component
        preloaderLayout.setConstraints(component, preloaderConstraints);
        preloaderContainer.add(component);
    }

    public static void append(JTextPane p, Color c, String s) { // better implementation--uses StyleContext
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = (AttributeSet) sc.addAttribute(SimpleAttributeSet.EMPTY,
                StyleConstants.Foreground, c);

        AttributeSet aset2 = (AttributeSet) sc.addAttribute(SimpleAttributeSet.EMPTY,
                StyleConstants.Foreground, Color.black);

        int len = p.getDocument().getLength(); // same value as getText().length();
        p.setCaretPosition(len);  // place caret at the end (with no selection)
        p.setCharacterAttributes(aset, false);
        p.replaceSelection(s); // there is no selection, so inserts at caret



        p.setCharacterAttributes(aset2, false);
    }

    private void checkForPopup(MouseEvent e) {
        if (e.isPopupTrigger()) {
            final JPopupMenu popup = new JPopupMenu();
            popup.setFont(new java.awt.Font("Shonar Bangla", 1, 18));
            String laststr = txtpane.getText();
            findposs();
            laststr = laststr.substring(startindex, endindex);
            hash.distance(laststr);

            for (int cn11 = 0; cn11 < hash.totaltoken && hash.rank_val[cn11] < 2 && cn11 < 10; cn11++) {
                jpopmenuItem = new JMenuItem();
                jpopmenuItem.setText(hash.rank_str[cn11]);
                jpopmenuItem.setFont(new java.awt.Font("Shonar Bangla", 1, fontsize));
                jpopmenuItem.addActionListener(this);
                popup.add(jpopmenuItem);
            }
            if (hash.totaltoken == 0) {
                jpopmenuItem = new JMenuItem();
                jpopmenuItem.setText("ডিকশেনারি বহির্ভূত শব্দ");
                jpopmenuItem.setFont(new java.awt.Font("Vrinda", 1, fontsize));
                //jpopmenuItem.addActionListener(this);
                popup.add(jpopmenuItem);
            }

            popup.show(e.getComponent(), e.getX(), e.getY() + 50);
        }
    }

    public void findposs() {

        String laststr = txtpane.getText();
        startindex = 0;
        endindex = laststr.length();
        for (int cn11 = txtpane.getCaretPosition() - 1; cn11 >= 0; cn11--) {
            if (laststr.charAt(cn11) == ' ' || laststr.charAt(cn11) == '?' || laststr.charAt(cn11) == '!' || laststr.charAt(cn11) == '।' || laststr.charAt(cn11) == '.') {
                startindex = cn11 + 1;
                break;
            }
        }

        for (int cn11 = txtpane.getCaretPosition(); cn11 < laststr.length(); cn11++) {
            if (laststr.charAt(cn11) == ' ' || laststr.charAt(cn11) == '?' || laststr.charAt(cn11) == '!' || laststr.charAt(cn11) == '।' || laststr.charAt(cn11) == '.') {
                endindex = cn11;
                break;
            }
        }

        System.out.println("rc>" + startindex + "--" + endindex);

    }

    public void mousePressed(MouseEvent e) {
        if (tagdict == true) {
            checkForPopup(e);
        }
    }

    public void mouseReleased(MouseEvent e) {
        if (tagdict == true) {
            checkForPopup(e);
        }
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    private void txtpaneKeyTyped(KeyEvent evt) {

        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset2 = (AttributeSet) sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, Color.black);
        txtpane.setCharacterAttributes(aset2, false);

        if (tagdict == true && (evt.getKeyChar() == ' ' || evt.getKeyChar() == '?' || evt.getKeyChar() == '!' || evt.getKeyChar() == '।' || evt.getKeyChar() == '.')) {
            int carloc = txtpane.getCaretPosition();
            txtpane.addNotify();
            String laststr = txtpane.getText();
//            if(txtpane.getCaretPosition()<laststr.length()){
//                txtpane.addNotify();
//            }
            String allstr = laststr;
            findposs();
            laststr = laststr.substring(startindex, carloc);
            System.out.println("type>" + startindex + "-" + carloc + "-" + endindex);
            if (hash.isInDict(laststr) == -1) {

                //findposs();
                //System.out.println(startindex + "--" + endindex);
                //allstr = allstr.substring(0, startindex) + source.getText() + allstr.substring(endindex);
                //txtpane.setText(allstr);
                txtpane.setText("");
                append(txtpane, Color.black, allstr.substring(0, startindex));
                append(txtpane, Color.red, laststr);
                append(txtpane, Color.black, allstr.substring(carloc));
                append(txtpane, Color.green, "");
                txtpane.setCaretPosition(carloc);
            }
        }

    }

    public static void main(String args[]) throws FileNotFoundException, IOException {
        Welcome win = new Welcome();
    }

    private void openjButtonActionPerformed(ActionEvent evt) {
        JFileChooser chooser = new JFileChooser();
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            d = chooser.getCurrentDirectory();
            f = chooser.getSelectedFile();
            dir = String.valueOf(f);
            line = "";
            line = readInput();
            txtpane.setText(line);
        }
    }

    private void savejButtonActionPerformed(ActionEvent evt) {
        String text = txtpane.getText().trim();
        String dir2 = "Subachan";
        if (text.length() == 0) {
            return;
        }
        findandsavetoloc(text);

        System.out.println(">>save called.");
    }

    private void playjButtonActionPerformed(ActionEvent evt) {
        //ps.playthesound(txtpane.getText().trim());
        //sayIt1(txtpane.getText().trim());
        ps.playthesound(txtpane.getText().trim());
        //ps.savethesound();
        System.out.println(">>play called.");
        pausejButton.setSelected(false);

    }

    private void pausejButtonActionPerformed(ActionEvent evt) {
        if (pausejButton.isSelected()) {
            ps.pausethesound();
            System.out.println(">>pause called.");
        } else {
            ps.resumethesound();
            System.out.println(">>resume called.");
        }
    }

    private void stopjButtonActionPerformed(ActionEvent evt) {
        ps.stopthesound();
        pausejButton.setSelected(false);
        System.out.println(">>stop called.");
    }

    private void plusjButtonActionPerformed(ActionEvent evt) {
        fontsize++;
        if (fontsize > 30) {
            fontsize = 30;
        }
        txtpane.setFont(new java.awt.Font("Vrinda", 1, fontsize));
    }

    private void minusjButtonActionPerformed(ActionEvent evt) {
        fontsize--;
        if (fontsize < 16) {
            fontsize = 16;
        }

        txtpane.setFont(new java.awt.Font("Vrinda", 1, fontsize));
    }

    private void dictjButtonActionPerformed(ActionEvent evt) {
        if (dictjButton.isSelected()) {
            tagdict = true;
            System.out.println("True");
        } else {
            tagdict = false;
            System.out.println("False");
        }
    }

    private void aboutjButtonActionPerformed(ActionEvent evt) {
        String[] cmd = new String[5];
        cmd[0] = "cmd.exe";
        cmd[1] = "/C";
        cmd[2] = "rundll32";
        cmd[3] = "url.dll,FileProtocolHandler";
        cmd[4] = "http://www.sust-subachan.com/";
        try {
            Process process = Runtime.getRuntime().exec(cmd);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void settingsjButtonActionPerformed(ActionEvent evt) {
        settingsWin = new SettingsJFrame();
    //settingsWin.setVisible(true);
    }

    @Override
    public void windowOpened(WindowEvent e) {
    }

    @Override
    public void windowClosing(WindowEvent e) {
        System.out.println("CLOSING");
        ans = 1;
        //UIManager.put("helpWindow.questionSound", "Data/Error.wav");
        ans = helpWindow.showConfirmDialog(preloaderFrame, "Are you sure to exit?", "Exit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null);
        System.out.println("ans " + ans);
        if (ans == 0) {
            System.exit(0);
        }
    }

    @Override
    public void windowClosed(WindowEvent e) {
    }

    @Override
    public void windowIconified(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    @Override
    public void windowActivated(WindowEvent e) {
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }

    private void findandsavetoloc(String text) {
        String dir2="Subachan.txt";
        if (chooser2.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {

            d2 = chooser2.getCurrentDirectory();
            f2 = chooser2.getSelectedFile();
            dir2 = String.valueOf(f2);

            if (dir2.charAt(dir2.length() - 1) == '\\') {
                dir2 = dir2.substring(0, dir2.length() - 1);
            }

            if (dir2.endsWith(".txt") || dir2.endsWith(".TXT")) {
                ;
            } else {
                dir2 = dir2 + ".txt";
            }
            
            /*************************************/
            writeoutput(text, dir2);
            //ps.savethesound();


        }


    }

    static String readInput() {
        StringBuffer buffer = new StringBuffer();
        try {
            FileInputStream fis = new FileInputStream(dir);
            InputStreamReader isr = new InputStreamReader(fis, "UTF8");
            Reader in = new BufferedReader(isr);
            int ch;

            while ((ch = in.read()) > -1) {
                if (ch != 65279) {
                    buffer.append((char) ch);
                //System.out.println(">>"+ch);
                }
            }
            in.close();
            return buffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    void writeoutput(String text, String dir2) {

        StringBuffer buffer2 = new StringBuffer();
        try {
            FileOutputStream fis2 = new FileOutputStream(dir2, true);
            OutputStreamWriter isr2 = new OutputStreamWriter(fis2, "UTF8");
            Writer out = new BufferedWriter(isr2);
            int ch;
            //System.out.printf(locale, "ans=%s", "  12কথা12");

            out.write(text);
            out.write("\r\n");



            out.close();

        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    public void actionPerformed(ActionEvent e) {
        JMenuItem source = (JMenuItem) (e.getSource());
        System.out.println("acP>" + source.toString());

        //if (e.getSource() == jpopmenuItem) {
        String allstr = txtpane.getText();
        int carloc = txtpane.getCaretPosition();
        findposs();
        System.out.println(startindex + "--" + endindex);
        allstr = allstr.substring(0, startindex) + source.getText() + allstr.substring(endindex);
        //txtpane.setText(allstr);

        txtpane.setText("");
        append(txtpane, Color.black, allstr);
        //append(txtpane, Color.red, laststr);        

        txtpane.setCaretPosition(startindex + source.getText().length() + 1);

        //append(txtpane, Color.red, source.getText());
        System.out.println("acP>complete");
    //}        
    }
}



