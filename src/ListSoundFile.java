
import java.io.*;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.Hashtable;
/**
 *
 * @author Dev
 */
public class ListSoundFile {

    String[] filename;
    String[] filenamebd;
    File d, f;
    static String dir;
    int flaginit;
    Hashtable hsh = new Hashtable();
    

    public void listprocess(int t) {
        dir="Sound\\";
        File alldir = new File(dir);
        FilenameFilter filter = new FilenameFilter() {
            public boolean accept(File file1, String name) {
                return name.contains("."); //name.indexOf(".");
            }
        };
        /* this comment added to test commit */

        File[] children = alldir.listFiles(filter);
        System.out.println("Total sound file>"+children.length);
        Preloader.frmabout.statusUpdate("Total "+children.length+" diphone");
        
        filename = new String[children.length + 10];
        filenamebd = new String[children.length + 10];

        
        flaginit = children.length;

        if (children == null) {
            System.out.println("\n> Files not found\n");
        } else {
            for (int ln1 = 0; ln1 < children.length; ln1++) {
                filename[ln1] = children[ln1].getName();
            }

            int n = children.length;
            int ptr;
            String temp;
            for (int cn1 = 0; cn1 < n - 1; cn1++) {
                ptr = 0;
                for (; ptr < n - cn1 - 1;) //???
                {
                    if ((filename[ptr].compareTo(filename[ptr + 1])) > 0) //if(data[ptr]>data[ptr+1])
                    {
                        temp = filename[ptr];
                        filename[ptr] = filename[ptr + 1];
                        filename[ptr + 1] = temp;
                    }
                    ptr = ptr + 1;
                }
            }


            String uni = "আমি";
            String spc = "";
            for (int ln1 = 0; ln1 < children.length; ln1++) {
                // System.out.println(">" + filename[ln1]);
                uni = findunivalue(ln1);
                //spc = findspc(ln1);
                filenamebd[ln1] = uni;
                hsh.put(filenamebd[ln1], filename[ln1]);
            //listModel.insertElementAt(filename[ln1] + spc + "  <" + uni + ">", ln1);
            }

        }

    }

    private String findunivalue(int ln) {
        String uni2 = "";
        String tm;
        int flagb = 0;
        int digitno;

        if (filename[ln].indexOf("7b") != -1) {
            tm = filename[ln].replaceFirst("7b", "07b");

            digitno = tm.length() - 4;
            if (digitno == 3) {
                uni2 = uni2.concat(hextoletter(tm.substring(0, 3)));
            } else if (digitno == 6) {
                uni2 = uni2.concat(hextoletter(tm.substring(0, 3)));
                uni2 = uni2.concat(hextoletter(tm.substring(3, 6)));
            } else if (digitno == 9) {
                uni2 = uni2.concat(hextoletter(tm.substring(0, 3)));
                uni2 = uni2.concat(hextoletter(tm.substring(3, 6)));
                uni2 = uni2.concat(hextoletter(tm.substring(6, 9)));
            } else if (digitno == 2) {
                uni2 = uni2.concat(hextoletter(tm.substring(0, 2)));
            //uni2=uni2.concat(filename[ln].substring(0, 2));
            } else if (digitno == 1) {
                //uni2=uni2.concat(hextoletter(filename[ln].substring(0, 1))); 
                uni2 = uni2.concat(tm.substring(0, 1));
            } else {
                //System.out.println("\nError in file name i=\n" + ln);
                uni2 = uni2.concat(tm.substring(0, tm.length() - 4));
            }

        } else if (filename[ln].indexOf("7d") != -1) {
            tm = filename[ln].replaceFirst("7d", "07d");

            digitno = tm.length() - 4;
            if (digitno == 3) {
                uni2 = uni2.concat(hextoletter(tm.substring(0, 3)));
            } else if (digitno == 6) {
                uni2 = uni2.concat(hextoletter(tm.substring(0, 3)));
                uni2 = uni2.concat(hextoletter(tm.substring(3, 6)));
            } else if (digitno == 9) {
                uni2 = uni2.concat(hextoletter(tm.substring(0, 3)));
                uni2 = uni2.concat(hextoletter(tm.substring(3, 6)));
                uni2 = uni2.concat(hextoletter(tm.substring(6, 9)));
            } else if (digitno == 2) {
                uni2 = uni2.concat(hextoletter(tm.substring(0, 2)));
            //uni2=uni2.concat(filename[ln].substring(0, 2));
            } else if (digitno == 1) {
                //uni2=uni2.concat(hextoletter(filename[ln].substring(0, 1))); 
                uni2 = uni2.concat(tm.substring(0, 1));
            } else {
                //System.out.println("\nError in file name i=\n" + ln);
                uni2 = uni2.concat(tm.substring(0, tm.length() - 4));
            }

        } else {
            digitno = filename[ln].length() - 4;

            if (digitno == 3) {
                uni2 = uni2.concat(hextoletter(filename[ln].substring(0, 3)));
            } else if (digitno == 6) {
                uni2 = uni2.concat(hextoletter(filename[ln].substring(0, 3)));
                uni2 = uni2.concat(hextoletter(filename[ln].substring(3, 6)));
            } else if (digitno == 9) {
                uni2 = uni2.concat(hextoletter(filename[ln].substring(0, 3)));
                uni2 = uni2.concat(hextoletter(filename[ln].substring(3, 6)));
                uni2 = uni2.concat(hextoletter(filename[ln].substring(6, 9)));
            } else if (digitno == 2) {
                uni2 = uni2.concat(hextoletter("0" + filename[ln].substring(0, 2)));
            //uni2=uni2.concat(filename[ln].substring(0, 2));
            } else if (digitno == 1) {
                //uni2=uni2.concat(hextoletter(filename[ln].substring(0, 1))); 
                uni2 = uni2.concat(filename[ln].substring(0, 1));
            } else {
                //System.out.println("\nError in file name i=\n" + ln);
                uni2 = uni2.concat(filename[ln].substring(0, filename[ln].length() - 4));
            }

        }
        return uni2;
    }

    String hextoletter(String dig) {
        String ans = "";
        int intans;
        //intans=(Float.valueOf(dig)).intValue();
        intans = callint(dig, 16);
        return ans + (char) intans;
    }

    int callint(String arr, int base) {
        int i, x, y, sum = 0, val;

        x = arr.length() - 1;

        for (i = 0; i < arr.length(); i++) {
            val = (int) Math.pow(base, i);

            if (arr.charAt(x) > 64 && arr.charAt(x) < 71) {
                y = arr.charAt(x) - 65 + 10;
            } else if (arr.charAt(x) > 96 && arr.charAt(x) < 103) {
                y = arr.charAt(x) - 97 + 10;
            } else {
                y = arr.charAt(x) - 48;
            }

            sum = sum + val * y;
            x--;
        }
        return sum;
    }
    
    
    public String SearchHashTable(String key){
        String value="$";
        if(hsh.containsKey(key)){
            value=(String) hsh.get(key);      
        }
        return value;
    }    
    
    
    
}
