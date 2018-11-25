import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Preloader extends JFrame {
    private JFrame preloaderFrame;
    private Container preloaderContainer;
    private GridBagLayout preloaderLayout;
    private GridBagConstraints preloaderConstraints;
    private JLabel picJLabel = new JLabel();
    public static JLabel statusJLabel = new JLabel();
    private int picWidth,  picHeight,  x,  y;
    public static bddict.Hash hash;
    public static ListSoundFile lsf;
    public static PlaySound ps;
    public static Preloader frmabout;
    private JLayeredPane layeredPane = new JLayeredPane();
    private Color fontcolororg = new Color(255, 197, 110, 255);
    private Color fontcolorgray = new Color(124, 121, 118, 255);
    long time1, time2;
    public Preloader() {
        //JFrame.setDefaultLookAndFeelDecorated(true);
        preloaderFrame = new JFrame("Subachan");
        preloaderFrame.setUndecorated(true);
        preloaderFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        preloaderLayout = new GridBagLayout();
        preloaderConstraints = new GridBagConstraints();
        preloaderContainer = getContentPane();
        preloaderContainer.setLayout(preloaderLayout);
      
        layeredPane.setPreferredSize(new Dimension(435, 262));

        Icon bug = new ImageIcon(getClass().getResource("/Data/preloader.png"), "Loading...");
        picJLabel = new JLabel(bug);
        Font picJLabelfont = new java.awt.Font("Arial", 1, 12);

        picJLabel.setVerticalAlignment(JLabel.TOP);
        picJLabel.setHorizontalAlignment(JLabel.LEFT);
        //picJLabel.setBackground(Color.black);
        //picJLabel.setForeground(fontcolororg);
        //picJLabel.setBorder(BorderFactory.createLineBorder(Color.black));
        picJLabel.setBounds(0, 0, 435, 262); //X=232
        picJLabel.setFont(picJLabelfont);
        layeredPane.add(picJLabel, new Integer(1));
        statusJLabel = new JLabel("Loading....");
        Font statusJLabelfont = new java.awt.Font("Arial", 1, 12);

        statusJLabel.setVerticalAlignment(JLabel.TOP);
        statusJLabel.setHorizontalAlignment(JLabel.LEFT);
        //statusJLabel.setBackground(Color.black);
        statusJLabel.setForeground(fontcolorgray);
        //statusJLabel.setBorder(BorderFactory.createLineBorder(Color.black));
        statusJLabel.setBounds(210, 225, 200, 40); //X=232
        statusJLabel.setFont(picJLabelfont);
        layeredPane.add(statusJLabel, new Integer(2));        
        
        
        addComponent(layeredPane, 0, 0, 1, 1, 0, 0, GridBagConstraints.NONE);
        preloaderFrame.add(preloaderContainer);


        picHeight = bug.getIconHeight();
        picWidth = bug.getIconWidth();

        Toolkit k = this.getToolkit();
        Dimension dim = k.getScreenSize();
        x = (int) (dim.width / 2) - (int) (picWidth / 2);
        y = (int) (dim.height / 2) - (int) (picHeight / 2);
        preloaderFrame.setBounds(x, y, picWidth, picHeight);
        preloaderFrame.setVisible(true);

    }
    
    public void loadall(){
        Thread t = new Thread();
        t.start();
        try {
            time1 = System.currentTimeMillis();
            lsf = new ListSoundFile();
            lsf.listprocess(1);
            hash = new bddict.Hash();
            ps = new PlaySound();
            MergeTest mt = new MergeTest();
            try {
                mt.dataInsertion();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            mt.initialize();
            t.sleep(200);
            time2 = System.currentTimeMillis();
            System.out.println("class:preloadder method:loadall time to initialize soundfile and di Time : "+(time2-time1)+" ms");
        } catch (InterruptedException ex) {
        }

        if (t.isAlive() == false) {
            try {
                preloaderFrame.dispose();
                new Welcome();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Preloader.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Preloader.class.getName()).log(Level.SEVERE, null, ex);
            }

        }



    }
    
    public void statusUpdate(String s){
        statusJLabel.setText("Loading: "+s+"....");
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
    public static String line = "";
    public static void main(String[] args) {
        frmabout = new Preloader();
        frmabout.loadall(); 
        // System.out.println("mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm");
//        PlaySound ps = new PlaySound();
        
//         try{
//            File outFile;
//            String line="";
//            ServerSocket ss=new ServerSocket(2055);
//            //ServerSocket ss1=new ServerSocket(1200);
//                while(true){
//                    Socket s=ss.accept();
//                    //Socket s1=ss1.accept();
//                    System.out.println("Client Accepted");
//                    BufferedReader br=new BufferedReader(new InputStreamReader(s.getInputStream(), "UTF8"));
//                    line=br.readLine();
//                    System.out.println(line);
//                    if (line.trim()!=null)
//                    {
//                        try{ps.stopthesound();}catch(Exception e){System.err.println("Error stoping: "+e);}
//                        try{ps.playthesound(line.trim());}catch(Exception e){System.err.println("Error playing: "+e);}
//
//                    }
//                   // if(line!="stop")
//                    /*
//                    if(line.equals("STOP"))
//                    {
//                        ps.stopthesound();
//                        System.out.println("Match Stop");
//                        //ps.playthesound(line);
//                        //ps.savethesound();
//                    }
//                    else if (line!=null)
//                    {
//                        ps.stopthesound();
//                        ps.playthesound(line.trim());
//                    }
//                     *
//                     */
//
//                    s.close();
//                }
//            }catch(Exception e){System.out.println("ffffffffff"+e);}

    }
}

