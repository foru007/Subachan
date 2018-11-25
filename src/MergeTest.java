
import java.util.*;
import java.io.*;

public class MergeTest {

    private int count, attempt, total;
    private char store, store1;
    private static HashMap<String, Integer> hm = new HashMap<String, Integer>();
    private static HashMap<String, String> dt = new HashMap<String, String>();

    protected boolean errorTest(String org) {
        int i;
        char[] xh = new char[50];
        xh = org.toCharArray();
        for (i = 0; i < org.length(); i++) {
            if(AA(xh[i])==111)return false;
            else if (i == 0 && (AA(xh[i]) == 3 || AA(xh[i]) == 4)) {
                return false;
            } else if (AA(xh[i]) < 1) {
                return false;
            } else if (AA(xh[i]) > 4) {
                return false;
            } else if (AA(xh[i]) == 2 && i + 1 < org.length() && !(AA(xh[i + 1]) == 1 || AA(xh[i + 1]) == 2)) {
                return false;
            } else if (AA(xh[i]) == 4 && i + 1 == org.length()) {
                return false;
            } else if (AA(xh[i]) == 3 && xh[i] == '১') {
                return false;
            }
        }
        return true;
    }
//This method insert some exceptional word in hashmap those didn't follow grammertical rules

    protected void dataInsertion() throws Exception {
        String str, temp, test, temp1;
        char[] xx = new char[50];
        int i = 0;
        boolean flag1, flag2;
        ClassLoader CLDR = this.getClass().getClassLoader();
        InputStream inputStream = CLDR.getResourceAsStream("database.txt");
        InputStreamReader isr = new InputStreamReader(inputStream, "UTF-8");
        BufferedReader br = new BufferedReader(isr);
        //File f = new File("database.txt");
        // FileInputStream fr = new FileInputStream(f);
        // BufferedReader br = new BufferedReader(new InputStreamReader(fr, "UTF-8"));
        while ((str = br.readLine()) != null) {
            temp = "";
            temp1 = "";
            flag1 = true;
            flag2 = false;
            test = str.trim();
            xx = test.toCharArray();
            for (i = 0; i < xx.length; i++) {
                if (xx[i] == ' ') {
                    flag2 = true;
                    flag1 = false;
                    continue;
                } else if (flag1 && xx[i] != ' ') {
                    temp = temp + xx[i];
                } else if (flag2 && xx[i] != ' ') {
                    temp1 = temp1 + xx[i];
                }
            }
            dt.put(temp.trim(), temp1.trim());
        }
    }
    // we will use this metod to retrieve vord from hashmap

    protected String getTest(String str) {
        String temp = "" + str;
        if (dt.get(temp) == null) {
            return null;
        } else {
            return dt.get(temp);
        }
    }
    // This method initialize and make group to all the vowel and consonant.
    //Such as all the consonant has the value 1 and vowel has the value 2 all kar has value 3 except all oters are 0

    protected void initialize() {
        String a;
        int i, b = 0;
        char d;
        for (i = 2433; i <= 2527; i++) {
            b = 0;
            d = (char) i;
            if (i == 2509) {
                b = 4;
            } else if (i == 2510 || i == 2524 || i == 2525 || i == 2527) {
                b = 1;
            } else if ((i >= 2433 && i <= 2435) || (i >= 2453 && i <= 2489)) {
                if (i == 2473 || i == 2481 || (i >= 2483 && i <= 2485)) {
                    b = 0;
                } else {
                    b = 1;
                }
            } else if ((i >= 2437 && i <= 2443) || (i >= 2447 && i <= 2448) || (i >= 2451 && i <= 2452)) {
                b = 2;
            } else if ((i >= 2494 && i <= 2499) || (i >= 2503 && i <= 2504) || ((i >= 2507 && i <= 2508))) {
                b = 3;
            }
            a = "" + d;

            if (b == 0) {
                continue;
            } else {
                hm.put(a, new Integer(b));
            }
        }
        hm.put("$", new Integer(1));
        hm.put("১", new Integer(3));/*
        hm.put("০",new Integer(0));
        hm.put("৪",new Integer(0));
        hm.put("৫",new Integer(0));
        hm.put("৬",new Integer(0));
        hm.put("৭",new Integer(0));
        hm.put("৮",new Integer(0));
        hm.put("৯",new Integer(0));

        hm.put("৩",new Integer(5));
        hm.put("২",new Integer(5));*/
        Set<Map.Entry<String, Integer>> set = hm.entrySet();
    }
// this method return the corresponding value of each vowel and consonants.

    private int AA(int i) {
        char b = (char) i;
        String S = "" + b;
        if (hm.get(S) == null) {
            return 111;
        }
        return hm.get(S);
    }
// this is the method for processing 'এ' which determines whether 'এ' will be pronounced as 'এ' or 'অ্যা'

    protected String OAA(String org) {
        String res = "";
        char[] orig = new char[50];
        orig = org.toCharArray();
        int b = org.length();
        String test = "" + '১';
        for (int i = 0; i < b; i++) {
            if (i <= 1) {
                // বেঙ = ব্যাঙ ; লেংড়া = ল্যাঙড়া ; নেংটি = নেঙটি
                if (orig[i] == 'ে' && i == 1 && i + 1 < b && (orig[i + 1] == 'ং' || orig[i + 1] == 'ঙ')) {
                    if ((i + 3 < b && orig[i + 3] == 'া') || (i + 2 == b) || (i + 2 < b && orig[i + 2] == 'া')) {
                        res = res + test;
                    } //  else if(i+5<b && orig[i+2]=='্'&& orig[i+3]=='গ'&& orig[i+5]=='া') res = res + test;
                    else {
                        res = res + orig[i];
                    }
                } //খেদা = খেদ্যা ; তেলা = ত্যালা ; গেলা = গেলা ; কে; সে; মেরু = মেরু; তেল = তেল; বেত = বেত ;এল; জেল;
                // এক = অ্যাক ; কেন = ক্যানো ; এখান ; একুশ ; হেরি ; একা = অ্যাকা;
                else if (i + 1 < b && AA(orig[i + 1]) == 1 && ((orig[i] == 'এ' && i == 0) || (orig[i] == 'ে' && i == 1)) && orig[i + 1] != 'য়' && orig[i + 1] != 'শ' && orig[i + 1] != 'হ') {
                    if (orig[i] == 'এ') {
                        if ((i + 2 == b && orig[i + 1] != 'ল' && orig[i + 1] != 'র') || (i + 2 < b && ((AA(orig[i + 2]) == 1 || orig[i + 2] == 'া' || orig[i + 2] == 'ো')))) {
                            res = "" + 'অ' + test;
                        } else {
                            res = "" + orig[i];
                        }
                    } else {

                        if (i + 3 == b && (orig[i + 2] == 'া' || orig[i + 2] == 'ো')) {
                            res = "" + orig[0] + test;
                        } else if (i + 2 < b && orig[i + 1] != 'র' && (orig[i + 2] == 'া' || orig[i + 2] == 'ো' || AA(orig[i + 2]) == 1)) {
                            System.out.println(i);
                            if (AA(orig[i + 2]) == 1) {
                                res = "" + orig[0] + test + orig[i + 1] + 'ো';
                                i++;
                            } else {
                                res = "" + orig[0] + test;
                            }
                            System.out.println("hello");
                        } else if (i + 2 == b && orig[i + 1] != 'ল' && orig[i + 1] != 'র') {
                            res = "" + orig[0] + test;
                        } else {
                            res = res + orig[i];
                        }
                    }
                } else {
                    res = res + orig[i];
                }
            } else {
                res = res + orig[i];
            }
        }
        System.out.println(res);
        return res;
    }

    private void bornoCount(String str) {
        int i;
        count = 0;
        char[] orig = new char[50];
        orig = str.toCharArray();
        for (i = 0; i < orig.length; i++) {
            if (AA(orig[i]) == 1 || AA(orig[i]) == 2) {
                if (count == 1) {
                    store1 = orig[i];
                }
                store = orig[i];
                count++;
            }
        }
        for (i = 0; i < orig.length; i++) {
            if (orig[i] == store) {
                attempt++;
            }
        }
        attempt--;
        total = count;
    }
// this  method process 'অ' which determines whether 'অ' will be pronounced as 'অ' or 'ও'
    protected String AMD(String org) {
        bornoCount(org);
        String res = "";
        char[] orig = new char[50];
        orig = org.toCharArray();
        int b = org.length();
        for (int i = 0; i < b; i++) {
            if (i == 0) {  // অদ্য অ
                if (orig[i] == 'অ') { // অভিধান = ওভিধান ; অরুনা = ওরুনা ; অতি = ওতি ; অসি;
                    if (i + 2 < b && (orig[i + 2] == 'ি' || orig[i + 2] == 'ু' || orig[i + 2] == 'ী' || orig[i + 2] == 'ূ')) {
                        res = "" + 'ও';
                    }//অদ্য = ওদদো
                    else if (i + 3 < b && orig[i + 2] == '্' && orig[i + 3] == 'য') {
                        res = "" + 'ও';
                    }// অক্ষ = ওকখো
                    else if (i + 3 < org.length() && orig[i + 1] == 'ক' && orig[i + 2] == '্' && orig[i + 3] == 'ষ') {
                        if (i + 4 < org.length() && orig[i + 4] == '্') {
                            res = res + org.substring(i, i + 5);
                            i = i + 4;
                        } else {
                            res = 'ও' + org.substring(i + 1, i + 4);
                            i = i + 3;
                        }
                    }// অজ্ঞ = ওগগো ;
                    else if (i + 3 < org.length() && orig[i + 1] == 'জ' && orig[i + 2] == '্' && orig[i + 3] == 'ঞ') {
                        res = 'ও' + org.substring(i + 1, i + 4);
                        i = i + 3;
                    } else {
                        res = res + orig[i];
                    }
                } else if (AA(orig[i]) == 1) {
                    if (i + 2 < b && (orig[i + 2] == 'ি' || orig[i + 2] == 'ু' || orig[i + 2] == 'ী' || orig[i + 2] == 'ূ')) {
                        res = "" + orig[i] + 'ো';
                    } else if (i + 3 < b && orig[i + 2] == '্' && orig[i + 3] == 'য') {
                        res = "" + orig[i] + 'ো';
                    } else if (i + 3 < org.length() && orig[i + 1] == 'ক' && orig[i + 2] == '্' && orig[i + 3] == 'ষ') {
                        // লক্ষ্মণ = লকখোন
                        if (i + 4 < org.length() && orig[i + 4] == '্') {
                            res = res + org.substring(i, i + 5);
                            i = i + 4;
                        }// লক্ষণ = লোকখোন
                        else {
                            res = "" + orig[i] + 'ো';
                            res = res + org.substring(i + 1, i + 4);
                            i = i + 3;
                        }
                    }// যজ্ঞ = জগগো ;
                    else if (i + 3 < org.length() && orig[i + 1] == 'জ' && orig[i + 2] == '্' && orig[i + 3] == 'ঞ') {

                        res = "" + orig[i] + 'ো';
                        res = res + org.substring(i + 1, i + 4);
                        i = i + 3;

                    }// মসৃণ = মোসূন ; বক্তৃতা = বোকতৃতা ;
                    else if ((i + 2 < org.length() && orig[i + 2] == 'ৃ') || (i + 4 < org.length() && orig[i + 2] == '্' && orig[i + 4] == 'ৃ')) {
                        res = "" + orig[i] + 'ো';
                        if (orig[i + 2] == 'ৃ') {
                            res = res + org.substring(i + 1, i + 3);
                            i = i + 2;
                        } else if (orig[i + 4] == 'ৃ') {
                            res = res + org.substring(i + 1, i + 5);
                            i = i + 4;
                        }
                    }//  মউ = মোউ ; বউ = বোউ ;
                    else if (i + 1 < b && orig[i + 1] == 'উ') {
                        res = "" + orig[i] + 'ো';
                    } else if (i + 2 < b && orig[i + 1] == '্' && AA(orig[i + 2]) == 1) {
                        res = "" + org.substring(i, i + 3);
                        i = i + 2;
                    } // বন্ধু = বোনধু ;
                    else if (i + 4 < b && orig[i + 2] == '্' && AA(orig[i + 1]) == 1 && AA(orig[i + 3]) == 1 && (orig[i + 4] == 'ি' || orig[i + 4] == 'ী' || orig[i + 4] == 'ূ' || orig[i + 4] == 'ু')) {
                        res = "" + orig[i] + 'ো';
                    } else {
                        res = res + orig[i];
                    }
                } else {
                    res = res + orig[i];
                }
            }// মধ্য অ
            else if (AA(orig[i]) == 1 && ((attempt != 0 && orig[i] == store) || orig[i] != store) && orig[i] != 'ঙ' && orig[i] != 'ং') {
                if (orig[i] == store) {
                    attempt--;
                }
                // কাকলি = কাকোলি ; অতনু = ওতনু ; তরনি = তরোনি  ;
                if (i + 2 < b && (orig[i + 2] == 'ি' || orig[i + 2] == 'ু' || orig[i + 2] == 'ী' || orig[i + 2] == 'ূ')) {
                    res = res + orig[i] + 'ো';
                }// আলস্য = আলোস্য ; অকথ্য = অকোথ্য
                else if (i + 3 < b && orig[i + 2] == '্' && orig[i + 3] == 'য') {
                    res = res + orig[i] + 'ো';
                }// যবক্ষার = জবোখার ;
                else if (i + 3 < org.length() && orig[i + 1] == 'ক' && orig[i + 2] == '্' && orig[i + 3] == 'ষ') {
                    if (i + 4 < org.length() && orig[i + 4] == '্') {
                        res = res + org.substring(i, i + 5);
                        i = i + 4;
                    } else {
                        res = res + orig[i] + 'ো';
                        res = res + org.substring(i + 1, i + 4);
                        i = i + 3;
                    }
                }// সর্পজজ্ঞ =  সরপোজগগো ;
                else if (i + 3 < org.length() && orig[i + 1] == 'জ' && orig[i + 2] == '্' && orig[i + 3] == 'ঞ') {
                    res = res + orig[i] + 'ো';
                    res = res + org.substring(i + 1, i + 4);
                    i = i + 3;
                }// অমসৃণ = অমোসৃণ ;
                else if (i + 2 < org.length() && (orig[i + 2] == 'ৃ' || (i + 4 < org.length() && orig[i + 2] == '্' && orig[i + 4] == 'ৃ'))) {
                    res = res + orig[i] + 'ো';
                    if (orig[i + 2] == 'ৃ') {
                        res = res + org.substring(i + 1, i + 3);
                        i = i + 2;
                    } else {
                        res = res + org.substring(i + 1, i + 5);
                        i = i + 4;
                    }
                } else if (i + 2 < b && orig[i + 1] == '্' && AA(orig[i + 2]) == 1) {
                    res = res + org.substring(i, i + 3);
                    i = i + 2;
                }// অসম্পূর্ন = অশমপুরনো ;
                else if (i + 4 < b && orig[i + 2] == '্' && orig[0] != 'অ' && AA(orig[i + 1]) == 1 && AA(orig[i + 3]) == 1 && (orig[i + 4] == 'ি' || orig[i + 4] == 'ী' || orig[i + 4] == 'ূ' || orig[i + 4] == 'ু')) {
                    res = res + orig[i] + 'ো';
                } // && orig[i] == store1
                // মতন = মতোন ; সাগর = সাগোর ; কোমল =কোমোল ; ওজন = ওজোন ; অমল ; অজর;
                else if (this.total >= 3 && i + 1 < b && i >= 1 && orig[i] == this.store1 && (orig[i - 1] == 'অ' || orig[i - 1] == 'আ' || orig[i - 1] == 'া' || orig[i - 1] == 'ে'
                        || orig[i - 1] == 'ো' || orig[i - 1] == 'এ' || orig[i - 1] == 'ও' || AA(orig[i - 1]) == 1) && AA(orig[i + 1]) == 1) {
                    System.out.println("oh");
                    if (orig[i] == 'ঙ' || orig[i] == 'ং') {
                        res = res + orig[i];
                    } //else if(i+2<b && A.AA(orig[i+2])==3) res = res +orig[i];
                    else if (orig[0] == 'অ' || orig[0] == 'স') {
                        if (AA(orig[1]) == 1) {
                            res = res + orig[i];
                        } else {
                            res = res + orig[i] + 'ো';
                        }
                    } else {
                        res = res + orig[i] + 'ো';
                    }
                } else {
                    res = res + orig[i];
                }
            }// অন্ত্য অ
            else if (AA(orig[i]) == 1 && orig[i] == store && orig[i] != 'ঙ' && orig[i] != 'ং' && i + 2 >= b) {
                if (i >= 3 && orig[i - 3] == '্' && AA(orig[i - 1]) == 3) {
                    res = res + orig[i];
                }// পালান = পালানো ; দেখান = দেখানো ; পাঠান = পাঠানো;
                /*   else if (i >= 1 && orig[i - 1] == 'া' && orig[i] == 'ন') {
                res = res + orig[i] + 'ো';
                }*/// মত = মতো ; পালিত = পালিতো ; পরিক্ষিত = পরিখিতো ;
                else if (i >= 1 && orig[i] == 'ত' && (AA(orig[i - 1]) == 1 || orig[i - 1] == 'ই'
                        || orig[i - 1] == 'ি' || orig[i - 1] == 'ৃ' || orig[i - 1] == 'া' || orig[i - 1] == 'ী')) {
                    if ((i + 1 < b && AA(orig[i + 1]) != 3) || i + 1 == b) {
                        res = res + orig[i] + 'ো';
                    } else {
                        res = res + orig[i];
                    }
                } // প্রিয় = প্রিয়ো ; অনুমেয় = অনুমেয়ো ; বিধেয় = বিধেয়ো ;
                else if (i >= 1 && orig[i] == 'য়' && (orig[i - 1] == 'ি' || orig[i - 1] == 'ে' || orig[i - 1] == 'ী') && i + 1 == b) {
                    res = res + orig[i] + 'ো';
                }// বিবাহ = বিবাহো ; কলহ = কলহো; গাঢ় = গাঢ়ো ;
                else if (i > 1 && (orig[i] == 'হ' || orig[i] == 'ঢ়') && i+1==b) {
                    
                    res = res + orig[i] + 'ো';
                }//  তৈল = তোইলো; যৌথ = যোউথো ; অংশ = অংশো ; দুঃখ = দুখখো ; নৃপ = নৃপো ;
                else if (i >= 2 && (orig[i - 1] == 'ৈ' || orig[i - 1] == 'ং' || orig[i - 1] == 'ঃ' || orig[i - 1] == 'ৃ' || orig[i - 1] == 'ৌ')) {
                    if (i + 1 == b) {
                        res = res + orig[i] + 'ো';
                    } else {
                        res = res + orig[i];
                    }
                } else {
                    res = res + orig[i];
                }
            } else {
                res = res + orig[i];
            }
        }
        //   attempt = 0;
        return res;
    }
    private int b;
// this method is for processing all the 5 fola;

    protected String Fola(String str) {
        String res;
        res = "";
        int a = str.length(), i;
        char xx[] = new char[50];
        xx = str.toCharArray();
        boolean flag = false;
        for (i = 0; i < a; i++) {
            if (xx[i] == '্') {

                if (xx[i + 1] == 'য') {
                    if (i >= 1 && xx[i - 1] == 'র') {
                        res = res + xx[i + 1];
                        if (i + 2 == a || (i + 2 < a && AA(xx[i + 2]) == 1)) {
                            res = res + 'ো';
                        }
                        i++;
                    } else {
                        res = Zafala(res, str, i);
                        i = b;
                    }
                } else if (xx[i + 1] == 'র') {
                    res = Rafala(res, str, i);
                    i = b;
                } else if (xx[i + 1] == 'ল') {
                    res = Lafala(res, str, i);
                    i = b;
                } else if (xx[i + 1] == 'ম') {
                    res = Mafala(res, str, i);
                    i = b;
                } else if (xx[i + 1] == 'ব') {
                    if (i >= 1 && xx[i - 1] == 'র') {
                        res = res + xx[i + 1];
                        if (i + 2 == a || (i + 2 < a && AA(xx[i + 2]) == 1)) {
                            res = res + 'ো';
                        }
                        i++;
                    } else {
                        res = Bafala(res, str, i);
                        i = b;
                    }

                } else if (i >= 1 && AA(xx[i - 1]) == 1 && AA(xx[i + 1]) == 1) {
                    flag = true;
                    res = res + xx[i];
                }
            } else {
                res = res + xx[i];
            }
        }
        if (flag) {
            res = Cluster(res);
        }
        return res;
    }
// য ফলা

    private String Zafala(String res, String str, int i) {
        char xx[] = new char[50];
        xx = str.toCharArray();
        // ব্যথিত = বেথিতো ; ত্যজিয় = তেজিয়ো ; ব্যতিক্রম = বেতিকক্রোম ;
        if (i == 1 && xx[i - 1] != 'হ') {
            if (i + 3 < str.length() && (xx[i + 3] == 'ি' || xx[i + 3] == 'ী')) {
                res = res + 'ে';
                b = i + 1;
            } else {
                if (i + 2 < str.length() && AA(xx[i + 2]) == 3) {
                    res = res + '১';
                    b = i + 2;
                } else {
                    res = res + '১';
                    b = i + 1;
                }
            }
        }//  বাহ্য = বাজঝো ; গ্রাহ্য = গ্রাজঝো ; দাহ্য = দাজঝো ;
        else if (i >= 2 && xx[i - 1] == 'হ') {
            res = res.substring(0, i - 1) + 'জ' + 'ঝ';
            res = res + 'ো';
            b = i + 1;
        }// কণ্ঠ্য = কনঠো; স্বাস্থ্য = শাসথো ; মর্ত্য = মরতো; অর্ঘ্য = অরঘো ; ধন্য = ধননো ; কল্যাণ = কোললান
        else if (i >= 3 && AA(xx[i - 1]) == 1 && AA(xx[i - 3]) == 1 && AA(xx[i - 2]) == 4) {
            // System.out.println("Hello, this is me.");
            if (i + 2 < str.length() && AA(xx[i + 2]) == 3) {
                b = i + 1;
            } else {
                res = res + 'ো';
                b = i + 1;
            }

        } else if (i >= 2 && xx[i - 1] != 'হ') {

            if (i + 2 == str.length()) {
                res = res + xx[i - 1] + 'ো';
                b = i + 1;
            } else if (i + 2 < str.length() && (AA(xx[i + 2]) != 3 || AA(xx[i + 2]) == 1)) {
                res = res + xx[i - 1] + 'ো';
                b = i + 1;
            } else if (i + 2 < str.length() && xx[i + 2] == 'া') {
                res = res + xx[i - 1] + xx[i + 2];
                b = i + 2;
            } else {
                if (i + 2 < str.length() && AA(xx[i + 2]) == 3 && xx[i + 2] != 'া') {
                    res = res + xx[i - 1];
                    b = i + 1;
                } else {
                    res = res + xx[i - 1] + '১';
                    b = i + 2;
                }
            }
        } else {
            //    System.out.println("hello");
            res = res + '১';
            b = i + 1;
        }
        return res;
    }

    private String Rafala(String res, String str, int i) {
        char xx[] = new char[50];
        xx = str.toCharArray();
        String temp = "" + '্' + 'র';
        // প্রকাশ = প্রোকাশ ;  গ্রহ = গ্রোহো;  প্রজ্ঞা = প্রোগগ্যা ;
        if (i == 1 && xx[i - 1] != 'হ') {
            if (i + 2 < str.length()) {
                if (AA(xx[i + 2]) != 3) {
                    res = res + temp + 'ো';
                    b = i + 1;
                } else {
                    res = res + temp;
                    b = i + 1;
                }
            } else {
                res = res + temp + 'ো';
                b = i + 1;
            }

        }// হ্রদ = রদ ; হ্রাস = রাশ;
        else if (i >= 1 && xx[i - 1] == 'হ') {
            int c = res.length();
            if (i + 2 == str.length() || (i + 3 < str.length() && AA(xx[i + 2]) == 1 && AA(xx[i + 3]) == 3)) {
                res = res.substring(0, c - 1) + xx[i + 1] + 'ো';
                b = i + 1;
            } else {
                res = res.substring(0, c - 1) + xx[i + 1];
                b = i + 1;
            }
        }// বিদ্রোহ = বিদদ্রোহ ; ; যাত্রী = জাতত্রি; ছাত্র = ছাতত্রো ;
        else if (i >= 2) {

            if (i + 2 == str.length()) {
                if (i > 3 && xx[i - 2] == '্' && AA(xx[i - 1]) == 1 && AA(xx[i - 3]) == 1) {
                    res = res.substring(0, i - 2) + xx[i - 1] + temp + 'ো';
                    b = i + 1;
                } else {
                    res = res + xx[i - 1] + temp + 'ো';
                    b = i + 1;
                }
            }// কেন্দ্র = কেনদ্রো ; পুরন্ধ্রী = পুরনধ্রি  ;
            else {
                if (i > 3 && xx[i - 2] == '্' && AA(xx[i - 1]) == 1 && AA(xx[i - 3]) == 1) {
                    if (i + 2 < str.length() && AA(xx[i + 2]) == 1) {
                        res = res.substring(0, i - 2) + xx[i - 1] + temp + 'ো';
                        b = i + 1;
                    } else {
                        res = res.substring(0, i - 2) + xx[i - 1] + temp;
                        b = i + 1;
                    }
                    //  System.out.println("I am here.");
                } else {
                    res = res + xx[i - 1] + temp;
                    b = i + 1;
                }
            }
        }
        return res;
    }

    private String Lafala(String res, String str, int i) {
        char xx[] = new char[50];
        String temp = "" + '্' + 'ল';
        xx = str.toCharArray();
        // নির্লোভ = নিরলোভ নির্লজ্জ = নিরলজজো
        if (i >= 1 && xx[i - 1] == 'র') {
            res = res + xx[i + 1];
            b = i + 1;
        } // ম্লান = ম্লান ; প্লুত = প্লুত ;
        else if (i == 1 && xx[i - 1] != 'হ') {
            if (i + 2 < str.length() && AA(xx[i + 2]) == 1) {
                res = res + temp + 'ো';
                b = i + 1;
            } else {
                res = res + temp;
                b = i + 1;
            }
        }//স্লথ = স্লোথ ; অশ্লীল = অসস্লিল;  অক্লেশে = অকক্লেশে; অম্লান = অমম্লান;
        else if (i >= 2 && xx[i - 1] != 'হ') {
            res = res + xx[i - 1] + temp;
            b = i + 1;
        }// আহ্লাদ = আললাদ ; হ্লাদিনি = লাদিনি ; প্রহ্লাদ = প্রোললাদ;
        else {

            int c = res.length();
            if (i == 1) {
                res = res.substring(0, c - 1) + 'ল';
                b = i + 1;
            } else {
                if (i + 2 == str.length() || (i + 3 < str.length() && AA(xx[i + 2]) == 1 && AA(xx[i + 3]) == 3)) {
                    res = res.substring(0, c - 1) + xx[i + 1] + xx[i + 1] + 'ো';
                    b = i + 1;
                    System.out.println("I'm here.");
                } else {
                    res = res.substring(0, c - 1) + xx[i + 1] + xx[i + 1];
                    b = i + 1;
                }
            }
        }
        return res;
    }

    private String Mafala(String res, String str, int i) {
        char xx[] = new char[50];
        char x[] = new char[50];
        xx = str.toCharArray();
        // স্মরন = শরন ; স্মৃতি = শৃতি ; শ্মস্রু = শসস্রু  ;
        if (i == 1 && xx[i - 1] != 'হ') {
            b = i + 1;
        }//  ব্রহ্ম = ব্রমহো; ব্রাহ্মণ = ব্রামহোন ;
        else if (i >= 1 && xx[i - 1] == 'হ') {
            if (i + 2 == str.length() || (i + 2 < str.length() && AA(xx[i + 2]) == 1)) {
                res = res.substring(0, i - 1) + xx[i + 1] + xx[i - 1] + 'ো';
                b = i + 1;
            } else {
                res = res.substring(0, i - 1) + xx[i + 1] + xx[i - 1];
                b = i + 1;
            }
        } // ছদ্ম = ছদদো ; পদ্ম = পদদো ; মহাত্ম = মহাততো ; অকশ্মিক =
        else if (i >= 3 && (AA(xx[i - 1]) == 1 && AA(xx[i - 3]) == 1 && xx[i - 2] == '্')) {
            if (i + 2 == str.length()) {
                res = res + 'ো';
            }
            b = i + 1;
        } else if (i >= 2) {
            // বাগ্মি = বাগমি ; বঙ্ময় = বঙময় ; কুট্মল = কুটমল ; হিরন্ময় = হিরনময় ; উন্মাদ = উনমাদ ; শমমান; গুল্ম = গুলমো ;
            if (xx[i - 1] == 'গ' || xx[i - 1] == 'ঙ' || xx[i - 1] == 'ট'
                    || xx[i - 1] == 'ন' || xx[i - 1] == 'র' || xx[i - 1] == 'ণ' || xx[i - 1] == 'ম' || xx[i - 1] == 'ল') {
                if (i + 2 < str.length()) {
                    if (i + 3 == str.length() && AA(xx[i + 2]) == 1) {
                        res = res + 'ম';
                        b = i + 1;
                    } else if (i + 3 < str.length() && AA(xx[i + 2]) == 1 && AA(xx[i + 3]) == 3) {
                        res = res + 'ম' + 'ো';
                        b = i + 1;
                    } else {
                        b = i;
                    }
                } else if (i + 2 == str.length()) {
                    res = res + 'ম' + 'ো';
                    b = i + 1;
                } else {
                    b = i;
                }
            }//রশ্মি = রোশশি ; আকস্মিক = আকোশশিক ;
            else if (i + 2 == str.length()) {
                res = res + xx[i - 1] + 'ো';
                b = i + 1;
            } else {
                res = res + xx[i - 1];
                b = i + 1;
            }
        }
        return res;
    }

    private String Bafala(String res, String str, int i) {
        char xx[] = new char[50];
        xx = str.toCharArray();
        // স্বদেশ = শদেশ ; জ্বালা = জালা ; শ্বাস = শাশ;
        if (i == 1 && xx[i - 1] != 'হ') {
            b = i + 1;
        }//আহ্বান =  আওভান ; বিহ্বল = বিউভল ; জিহ্বা = জিউভা ;
        else if (i >= 1 && xx[i - 1] == 'হ') {
            if (i > 2 && xx[i - 2] == 'ি') {
                if (i + 2 == str.length() || (i + 3 < str.length() && AA(xx[i + 2]) == 1 && AA(xx[i + 3]) == 3)) {
                    res = res.substring(0, i - 1) + 'উ' + 'ভ' + 'ো';
                    b = i + 1;
                } else {
                    res = res.substring(0, i - 1) + 'উ' + 'ভ';
                    b = i + 1;
                }
            } else {
                if (i + 2 == str.length() || (i + 3 < str.length() && AA(xx[i + 2]) == 1 && AA(xx[i + 3]) == 3)) {
                    res = res.substring(0, i - 1) + 'ও' + 'ভ' + 'ো';
                    b = i + 1;
                } else {
                    res = res.substring(0, i - 1) + 'ও' + 'ভ';
                    b = i + 1;
                }
            }

        }// দ্বিত্ব = দিততো ;
        else if (i >= 2) {
            if (i + 3 < str.length() && xx[i + 2] == '্' && xx[i + 3] == 'য') {
                res = res + 'ব' + '১';
                b = i + 3;
            }//উদ্বেগ = উদবেগ ; উদাস্তূ = উদাসতু;
            else if (xx[i - 2] == 'উ' && xx[i - 1] == 'দ') {
                if (i + 2 == str.length()) {
                    res = res + xx[i + 1] + 'ো';
                    b = i + 1;
                } else {
                    b = i;
                }
            }// দিগ্বিদিক = দিগবিদিক ; বাব্বা = বাববা ; বিম্ব = বিমবো ;
            else if (xx[i - 1] == 'গ' || xx[i - 1] == 'ম' || xx[i - 1] == 'ব') {
                if (i + 2 == str.length()) {
                    res = res + xx[i + 1] + 'ো';
                    b = i + 1;
                } else {
                    b = i;
                }
            } //উচ্ছ্বাস = উচছাশ ; আমসত্ত্ব = আমোশততো ;
            else if (i >= 3 && xx[i - 2] == '্' && AA(xx[i - 1]) == 1 && AA(xx[i - 3]) == 1) {
                b = i + 1;
            } else if (i + 2 == str.length()) {
                res = res + xx[i - 1] + 'ো';
                b = i + 1;
            } else {
                res = res + xx[i - 1];
                b = i + 1;
            }
        }
        return res;
    }
protected String Cluster(String str) {
        String res;
        res = "";
        int a = str.length(), i;
        char xx[] = new char[50];
        xx = str.toCharArray();
        for (i = 0; i < a; i++) {
            if (i == 0) {
                res = res + xx[0];
                continue;
            } else if (xx[i] == '্' && i + 1 < str.length()) {
                if((xx[i-1]=='র' && (xx[i+1]=='ট'|| xx[i+1]=='স')) || (xx[i-1]=='ন' && (xx[i+1]=='ড'|| xx[i+1]=='স' || xx[i+1]=='ট')) ||
                   ((xx[i-1]=='স' || xx[i-1]=='ল')&&(xx[i+1]=='ট'))||(xx[i-1]=='ক' && (xx[i+1]=='স' || xx[i+1]=='ট'))){
                 if(xx[i+1]=='স'){
                    res = res + xx[i]+'$';
                 }
                 else res = res + xx[i]+xx[i+1];
                 i++;
                 continue;
                }
                // বৃক্ক = বৃককো; নিক্কন = নিককন ;
                else if ((xx[i - 1] == 'ক') && (xx[i + 1] == 'ক')) {
                } else if (xx[i + 1] == 'য' || xx[i + 1] == 'র' || xx[i + 1] == 'ল') {
                    res = res + xx[i];
                } // ভিক্ষুক = ভিকখুক ; রক্ষা = রোকখা ;
                else if ((xx[i - 1] == 'ক') && (xx[i + 1] == 'ষ')) {
                    // System.out.println("oh");
                    if (i == 1) {
                        res = "" + 'খ';
                    } else if (i + 2 == str.length() || (i + 2 < str.length() && AA(xx[i + 2]) == 1)) {

                        res = res + 'খ' + 'ো';
                    } else {
                        res = res + 'খ';
                    }
                    i = i + 1;
                } // আস্থা = আসথা ;
                // This block's need modification for 'স'
                else if ((xx[i - 1] == 'স' || xx[i - 1] == 'শ') && (xx[i + 1] == 'ত' || xx[i + 1] == 'থ' || xx[i + 1] == 'ন' || xx[i + 1] == 'ল')) {
                    if (i + 2 == str.length() || (i + 2 < str.length() && AA(xx[i + 2]) == 1)) {
                        if (i >= 2) {
                            res = res.substring(0, i - 1) + '$' + xx[i + 1] + 'ো';
                        } else if (xx[i + 1] == 'ত' || xx[i + 1] == 'থ') {
                            res = "" + 'এ' + '$' + xx[i + 1] + 'ো';
                        } else {
                            res = "" + '$' + xx[i + 1] + 'ো';
                        }

                    } else {
                        if (i >= 2) {
                            res = res.substring(0, i - 1) + '$' + xx[i + 1];
                        } else if (xx[i + 1] == 'ত' || xx[i + 1] == 'থ') {
                            res = "" + 'এ' + '$' + xx[i + 1];
                        } else {
                            res = "" + '$' + xx[i + 1];
                        }
                    }
                    i = i + 1;
                } else if (xx[i - 1] == 'স' && i == 1) {
                    res = "" + 'এ' + 'শ';
                } //জ্ঞান = গ্যান ;  বিজ্ঞান = বিগগ্যান ;
                else if ((xx[i - 1] == 'জ') && (xx[i + 1] == 'ঞ')) {
                    if (i == 1) {
                        if (i + 2 < str.length() && AA(xx[i + 2]) == 1) {
                            res = "" + 'গ' + 'ো';
                        } else if (i + 2 < str.length() && xx[i + 2] == 'া') {
                            res = "" + 'গ' + '্' + 'য';
                        } else {
                            res = "" + 'গ';
                        }
                        i = i + 1;
                    } else {
                        if (i + 2 == str.length() || (i + 3 < str.length() && AA(xx[i + 2]) == 1 && AA(xx[i + 3]) == 3)) {
                            res = res.substring(0, i - 1) + 'গ' + 'গ' + 'ো';
                        } else if (i + 2 < str.length() && xx[i + 2] == 'া') {
                            res = res.substring(0, i - 1) + 'গ' + 'গ' + '্' + 'য';
                        } else {
                            res = res.substring(0, i - 1) + 'গ' + 'গ';
                        }
                        i = i + 1;
                    }
                }//  অঞ্জন = অনজোন ; গঞ্জনা = গনজোনা ;ঝঞ্জা = ঝনজা;
                else if ((xx[i - 1] == 'ঞ') && (xx[i + 1] == 'জ' || xx[i + 1] == 'চ' || xx[i + 1] == 'ছ' || xx[i + 1] == 'ঝ')) {
                    if (i + 2 == str.length() || (i + 2 < str.length() && AA(xx[i + 2]) == 1)) {
                        res = res.substring(0, i - 1) + 'ন' + xx[i + 1] + 'ো';
                        i = i + 1;
                    } else {
                        res = res.substring(0, i - 1) + 'ন';
                    }
                }//অপরাহ্ন = অপরানহো ; মধ্যাহ্ন = মোধধানহো ;
                else if ((xx[i - 1] == 'হ') && (xx[i + 1] == 'ণ' || xx[i + 1] == 'ন')) {
                    if (i + 2 == str.length() || (i + 2 < str.length() && AA(xx[i + 2]) == 1)) {
                        res = res.substring(0, i - 1) + 'ন' + 'হ' + 'ো';
                    } else {
                        res = res.substring(0, i - 1) + 'ন' + 'হ';
                    }
                    i = i + 1;

                } else if ((xx[i - 1] == 'ত') && (xx[i + 1] == 'ত' || xx[i + 1] == 'ন' || xx[i + 1] == 'থ')) {
                    if (i + 2 == str.length() || (i + 3 < str.length() && AA(xx[i + 2]) == 1 && AA(xx[i + 3]) == 3)) {
                        res = res + xx[i + 1] + 'ো';
                    } else {
                        res = res + xx[i + 1];
                    }
                    i = i + 1;
                } else if ((xx[i - 1] == 'দ' || xx[i - 1] == 'গ' || xx[i - 1] == 'ন' || xx[i - 1] == 'ব') && (xx[i + 1] == 'ধ')) {
                    if (i + 2 == str.length() || (i + 2 < str.length() && AA(xx[i + 2]) == 1)) {
                        res = res + xx[i + 1] + 'ো';
                    } else {
                        res = res + xx[i + 1];
                    }
                    i = i + 1;
                } else if ((xx[i - 1] == 'গ') && (xx[i + 1] == 'ন')) {
                    if (i + 2 == str.length() || (i + 2 < str.length() && AA(xx[i + 2]) == 1)) {
                        res = res + xx[i + 1] + 'ো';
                    } else {
                        res = res + xx[i + 1];
                    }
                    i = i + 1;
                } else if ((xx[i - 1] == 'ণ') && (xx[i + 1] == 'ড')) {
                    if (i + 2 == str.length() || (i + 2 < str.length() && AA(xx[i + 2]) == 1)) {
                        res = res + xx[i + 1] + 'ো';
                    } else {
                        res = res + xx[i + 1];
                    }
                    i = i + 1;
                } else if ((xx[i - 1] == 'ন') && (xx[i + 1] == 'থ')) {
                    if (i + 2 == str.length() || (i + 3 < str.length() && AA(xx[i + 2]) == 1 && AA(xx[i + 3]) == 3)) {
                        res = res + xx[i + 1] + 'ো';
                    } else {
                        res = res + xx[i + 1];
                    }
                    i = i + 1;
                } else if ((xx[i - 1] == 'স' || xx[i - 1] == 'ষ') && (xx[i + 1] == 'থ' || xx[i + 1] == 'ক')) {
                    if (i + 2 == str.length() || (i + 2 < str.length() && AA(xx[i + 2]) == 1)) {
                        res = res + xx[i + 1] + 'ো';
                    } else {
                        res = res + xx[i + 1];
                    }
                    i = i + 1;
                } else if ((xx[i - 1] == 'ম' || xx[i - 1] == 'ণ' || xx[i - 1] == 'ন') && (xx[i + 1] == 'ন')) {
                    if (i + 2 == str.length() || (i + 2 < str.length() && AA(xx[i + 2]) == 1)) {
                        res = res + xx[i + 1] + 'ো';
                    } else {
                        res = res + xx[i + 1];
                    }
                    i = i + 1;
                } else {
                    if (i + 2 == str.length() || (i + 2 < str.length() && AA(xx[i + 2]) == 1)) {
                        res = res + xx[i + 1] + 'ো';
                    } else {
                        res = res + xx[i + 1];
                    }
                    i = i + 1;
                }
            } else if (i > 1 && i + 1 == str.length() && xx[i - 1] == '্') {
                res = res + xx[i] + 'ো';
            } else {
                res = res + xx[i];
            }
        }
        return res;
    }
protected String Process(String str) {
        char xh[] = new char[50];
        xh = str.toCharArray();
        int a, i;
        String result = "";
        a = str.length();
        for (i = 0; i < a; i++) {
            if (xh[i] == 'ী') {
                result = result + 'ি';
            } /*else if (xh[i] == '১') {
            result = result + '্' + 'য' + 'া';
            }*/ else if (xh[i] == 'ৌ') {
                result = result + 'ো' + 'উ';
            } else if (xh[i] == 'ৈ') {
                result = result + 'ো' + 'ই';
            } else if (xh[i] == 'ঐ') {
                result = result + 'ও' + 'ই';
            } else if (xh[i] == 'ঔ') {
                result = result + 'ও' + 'উ';
            } else if (xh[i] == 'ূ') {
                result = result + 'ু';
            } else if (xh[i] == 'ঈ') {
                result = result + 'ই';
            } else if (xh[i] == 'ী') {
                result = result + 'ি';
            } else if (xh[i] == 'ঊ') {
                result = result + 'উ';
            } else if (xh[i] == 'ঋ') {
                result = result + 'র' + 'ি';
            } else if (xh[i] == 'ং') {
                result = result + 'ঙ';
            } else if (xh[i] == 'ণ') {
                result = result + 'ন';
            } else if (xh[i] == 'ষ') {
                result = result + 'শ';
            } else if (xh[i] == '$') {
                result = result + 'স';
            } else if (xh[i] == 'স' || xh[i] == 'শ') {
                if ((i + 2 < a && xh[i + 1] == '্' && (xh[i + 2] == 'র' || xh[i + 2] == 'ল')) || (i + 3 < a && xh[i + 2] == '্' && (xh[i + 3] == 'র' || xh[i + 3] == 'ল'))) {
                    result = result + 'স';
                } else {
                    result = result + 'শ';
                }
            } else if (xh[i] == 'ৃ') {
                result = result + '্' + 'র' + 'ি';
            } else if (xh[i] == 'ঢ়') {
                result = result + 'ড়';
            } else if (xh[i] == 'ৎ') {
                result = result + 'ত';
            } else if (xh[i] == 'ঃ') {
                if (i + 1 == a) {
                    if (i <= 2) {
                        result = result + 'হ';
                    } else {
                        result = result + 'ো';
                    }
                } else if (i + 1 < a && AA(xh[i + 1]) == 1) {
                    if (xh[i + 1] == 'ং') {
                        result = result + 'ঙ';
                    } else if (xh[i + 1] == 'ণ') {
                        result = result + 'ন';
                    } else if (xh[i + 1] == 'ষ') {
                        result = result + 'শ';
                    } else if (xh[i + 1] == '$') {
                        result = result + 'স';
                    } else if (xh[i + 1] == 'স' || xh[i + 1] == 'শ') {
                        if ((i + 3 < a && xh[i + 2] == '্' && (xh[i + 3] == 'র' || xh[i + 3] == 'ল')) || (i + 4 < a && xh[i + 3] == '্' && (xh[i + 4] == 'র' || xh[i + 4] == 'ল'))) {
                            result = result + 'স';
                        } else {
                            result = result + 'শ';
                        }
                    } else if (xh[i + 1] == 'ঢ়') {
                        result = result + 'র';
                    } else if (xh[i + 1] == 'ড়') {
                        result = result + 'র';
                    } else if (xh[i + 1] == 'ৎ') {
                        result = result + 'ত';
                    } else {
                        result = result + xh[i + 1];
                    }
                }
            } else if (xh[i] == 'য') {
                if (i + 1 < str.length() && (i >= 1 && xh[i - 1] == '্') && xh[i + 1] == 'া') {
                    result = result.substring(0, i - 1) + '১';
                    i = i + 1;
                    continue;
                }
                result = result + 'জ';
            } else if (xh[i] == 'হ' && i + 1 < str.length() && xh[i + 1] == 'ৃ') {
                result = result + 'র' + 'ি';
                i = i + 1;
            } else if (xh[i] == 'ঁ') {
                continue;
            } else {
                result = result + xh[i];
            }
        }
        return result;
    }
protected String breakPoint(String str) {
        char[] xx = new char[50];
        String res = "";
        xx = str.toCharArray();
        int i, j;
        int b = str.length();
        int deck[] = new int[50];
        char dick[] = new char[50];
        j = 0;
        int c = 0;
        // System.out.println(str);
        for (i = 0; i < str.length(); i++) {
            if (AA(xx[i]) == 2) {
                deck[j++] = 1;
                dick[j - 1] = xx[i];
            } else if (AA(xx[i]) == 1) {

                if (i + 1 < b && (AA(xx[i + 1]) == 1 || AA(xx[i + 1]) == 2)) {
                    deck[j++] = 0;
                    dick[j - 1] = xx[i];
                } else if (i + 1 < b && (xx[i + 1] == '্')) {
                    deck[j++] = 1;
                    dick[j - 1] = xx[i];
                    i += 2;
                } else if (i + 1 == b) {
                    deck[j++] = 0;
                    dick[j - 1] = xx[i];
                } else {
                    deck[j++] = 1;
                    dick[j - 1] = xx[i];
                }
            }

        }
        c = j;
        j--;
        for (i = b - 1; i >= 0; i--) {
            if (xx[i] == dick[j]) {
                if (deck[j] == 0) {
                    if (j == 0) {
                        deck[j] = 1;
                        break;
                    } else if (i == b - 1) {
                        //        deck[j]=1;
                        deck[j - 1] = 1;
                    } else {
                        deck[j - 1] = 1;
                    }

                }

                j--;
            }
        }
        int a = 0;
        for (i = 0; i < b; i++) {
            if (xx[i] == dick[a]) {
                if (deck[a] == 0) {
                    res = res + xx[i] + '-';
                } else {
                    res = res + xx[i];
                }
                a++;
            } else {
                res = res + xx[i];
            }
        }
        //  System.out.println(res);
        return res;
    }
}