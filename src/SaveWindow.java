

import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import java.awt.image.*;


public class SaveWindow extends JFrame implements ActionListener{
	JLabel status = new JLabel(" ");
	JButton cancel = new JButton("Cancel");
	JLabel progress = new JLabel();
	JLabel lab = new JLabel("Progress in Save:");
	BufferedImage Img;
	Graphics2D g2;
	int w;
    public SaveWindow() {
    	try {
	    	//set the size of the frame and Set at the center of the Window
	        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	        setSize(300, 150);	        
	        Dimension frameSize = this.getSize();
	        if (frameSize.height > screenSize.height) {frameSize.height = screenSize.height;}
	        if (frameSize.width > screenSize.width)     {frameSize.width = screenSize.width;}
	        setLocation( (screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
	        //end of centering
	        setTitle("BTTS Save Progress");	           
	        Img = new BufferedImage(200, 10, BufferedImage.TYPE_INT_RGB);
	        g2 = Img.createGraphics();
	        g2.setColor(new Color(0,0,255));	        
	        setGUI();        
	    }catch (Exception exception){}
    }
    public void setGUI(){
    	setIconImage((new ImageIcon("images/sust.gif")).getImage());
    	setResizable(false);
    	this.setLayout(new GridBagLayout());
    	this.add(lab, new GridBagConstraints(0, 0, 2, 1, 0.0, 0.0
                , GridBagConstraints.WEST, GridBagConstraints.NONE,
                new Insets(5, 5, 5, 0), 0, 0));
        this.add(progress, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
                , GridBagConstraints.WEST, GridBagConstraints.NONE,
                new Insets(5, 5, 5, 0), 0, 0));
        this.add(cancel, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
                , GridBagConstraints.WEST, GridBagConstraints.NONE,
                new Insets(5, 5, 5, 0), 0, 0));
    	this.add(status, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
                , GridBagConstraints.WEST, GridBagConstraints.NONE,
                new Insets(5, 5, 5, 0), 0, 0));
        cancel.addActionListener(this);
        progress.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED)));
        status.setSize(250,20);
        lab.setFont(new Font("",0,13));
        status.setFont(new Font("Vrinda", 0, 13));
		w=0;
        Draw();
    }
    public void setMessage(String s, double k){    	
    	w=(int)(k*200);
    	status.setText(s+" ");
    	Draw();
    }
    public void Draw() {  
        g2.fillRect(0,0,w,10);
        progress.setIcon(new ImageIcon(Img));        
    }
    public void actionPerformed (ActionEvent e){
    	this.dispose();
    	Phonetization.saveMode=false;
    }
    public static void main(String[] args){
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}catch (Exception exc) {}
    	SaveWindow btts = new SaveWindow();
    	btts.setDefaultCloseOperation(btts.DO_NOTHING_ON_CLOSE);    	
    	btts.setVisible(true);
	}
}