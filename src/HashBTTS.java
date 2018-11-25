
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;


public class HashBTTS{

    public String word,word1;
    public int length;
    public Hashtable ht;
   
    public HashBTTS() {
        init_hash();
    }


    public void init_hash() {

    //    File file = new File("dict.txt");
        ht = new Hashtable();
        StringBuffer buffer = new StringBuffer();
        char c[]=new char[100];
        char temp[]=new char[100];
        int len,j,i;
        try {
//            FileInputStream fis = new FileInputStream(file);
//            InputStreamReader isr = new InputStreamReader(fis,
//                    "UTF8");
//            Reader in = new BufferedReader(isr);
             ClassLoader CLDR = this.getClass().getClassLoader();
            InputStream inputStream = CLDR.getResourceAsStream("dict.txt");
            InputStreamReader isr = new InputStreamReader(inputStream,"UTF8");
            Reader in = new BufferedReader(isr);
            int ch;
            //System.out.printf(locale, "ans=%s", "  12কথা12");
            //System.out.println(">"+buffer.length());

            while ((ch = in.read()) > -1) {
                if (ch != 65279) {
                    buffer.append((char) ch);
                //System.out.println(">>"+ch);
                }
                if (ch == 10) {
                    //buffer.trimToSize(); 
                    word = buffer.toString();///need to remove new line
                    word.trim();
                    len=word.length();
                    word.getChars(0, len, c, 0);
                    
                    
                    for(i=0,j=0;i<len;i++,j++){
                       if(c[i]>=0x981  && c[i]<=0x9DF){
                           temp[j]=c[i];
                       }
                       else if(c[i]==' '){
                           temp[j]='\0';
                           word=String.copyValueOf(temp, 0, j);
                           j=-1;
                       }
                       else if(c[i]=='$'){
                           temp[j]='\0';
                           word1=String.copyValueOf(temp, 0, j);
                           j=-1; 
                       }
                    }
                    ht.put(word, word1);
                    length = word.length();
                    //System.out.println("len1"+length);
                    length = word1.length();
                    //System.out.println("len2"+length); 

                    buffer = new StringBuffer();

                }

            }
            in.close();

        } catch (IOException e) {
            e.printStackTrace();
        //return null;
        }

        System.out.println("File reading complete(dict.txt)");
        Preloader.frmabout.statusUpdate("dict.txt");

//        Vector v = new Vector(ht.keySet());
//        Collections.sort(v, Collections.reverseOrder());
//        StringBuffer buff = new StringBuffer();
//        String value;
//        // Display (sorted) hashtable.
//        for (Enumeration e = v.elements(); e.hasMoreElements();) {
//            String key = (String) e.nextElement();
//            System.out.println(key + ":" + ht.get(key));
//            value=(String) ht.get(key);
//            buff.append(key);
//            buff.append(" ");
//            buff.append(value);
//            buff.append('\n');
//
//            System.out.println();
//        }

//        word = buff.toString();
        //jTextArea1.setText(word);
    }
    
    
    public String Search(String key){
        String value="$";
        value=(String) ht.get(key);
        
       
        return value;
    }

             
}

