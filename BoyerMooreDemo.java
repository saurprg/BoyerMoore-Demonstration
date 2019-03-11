package boyermooredemo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.Pair;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;

/**
 * Author : saur_prg 
 * Description : This Program demonstrates the implementation of boyer moore
 *               algorithm for patter searching in text file
 * 
 */
public class BoyerMooreDemo {
    
    JFrame frame;
    File f;
    String[] heads;
    String[][] vals;
    String data,pat;
    static long tim;
    long noc;
    JTabbedPane tbp=new JTabbedPane();
    JLabel lb1,lbb,fname;
    JPanel frame1,p0,p1,p2,p3,frame3,frame2;
    JTextField pattern;
    JTextArea pattern3,data3;
    static int NO_OF_CHARS = 300; 
    ArrayList<Pair<Integer,Integer>> plist=new ArrayList<Pair<Integer,Integer>>();
    boolean correct=false,stop=false;
    JTextArea lb2;
    JScrollPane scrl,scrl3;
    JFileChooser browseFile;
    JButton choose,bt1,animate;
    Highlighter highlighter,hl3,hl30;
    Font font,font3;
    int ndx=0;
    Font font2=new Font("Serif",Font.ITALIC,15);        
    Thread timer;
    JTable tabl;
    JPanel pptbl=new JPanel();
    JLabel comp;
    
    BoyerMooreDemo(){
        frame=new JFrame("Boyer Moore Demo");
        f=null;
        animate=new JButton("Animation");
        font=new Font("Serif",Font.BOLD |Font.ITALIC, 40);
        font3=new Font("Serif",Font.BOLD , 25);
        data="";
        tbp=new JTabbedPane();
        lb1=new JLabel("Enter Pattern        :");
        frame1=new JPanel(new GridLayout(0,1));
        lbb=new JLabel("<html><font color='white'>Boyer Moore Algorithm</font></html>");
        p0=new JPanel();
        pattern=new JTextField("",20);
        pattern3=new JTextArea(2,20);
        pattern3.setFont(font3);
        pattern3.setEditable(false);
        data3=new JTextArea(20,65);
        data3.setFont(font2);
        scrl3=new JScrollPane(data3);
        data3.setEditable(false);
        p1=new JPanel(new FlowLayout(FlowLayout.LEADING,20,20));
        correct=false;
        lb2=new JTextArea(18,65);
        scrl=new JScrollPane(lb2);
        fname=new JLabel("e.g.File.txt");
        browseFile=new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        choose =new JButton("   Choose File    ");   
        p2=new JPanel(new FlowLayout(FlowLayout.LEADING,15,20));
        bt1=new JButton("Search");
        p3=new JPanel();
        frame2=new JPanel(new BorderLayout(0, 1));
        highlighter = lb2.getHighlighter();
        hl3 = pattern3.getHighlighter();
        hl30= data3.getHighlighter();
        frame3=new JPanel(new BorderLayout(0, 1));
        //Thread which runs animation 
        timer=new Thread(() -> {
            try {
                search(data.toCharArray(),pat.toCharArray(),true);
            } catch (BadLocationException | InterruptedException ex) {
                Logger.getLogger(BoyerMooreDemo.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        comp=new JLabel("No. of Comp: 0");
    }
    
    private void constuctFrame(){
            
            frame.setBounds(400, 100, 0, 0);
            frame.setSize(700, 600);
            
            
            //Boyer Moore Heading
            lbb.setFont(font);
            p0.setBackground(Color.gray);//Gray);
            p0.add(lbb);
           
            //getPattern
            Font ffont=new Font("Serif",Font.ITALIC,20);
            pattern.setFont(ffont);
            lb1.setFont(ffont);
            p1.add(lb1);p1.add(pattern);
            
            //File Browser
            lb2.setEditable(false);
            lb2.setFont(font2);
            scrl.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            scrl3.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            fname.setFont(ffont);
            choose.setFont(ffont);
            p2.add(choose);
            JLabel lbbb=new JLabel(" :  ");
            p2.add(lbbb);
            p2.add(fname);
            bt1.setFont(ffont);
            bt1.addActionListener((ActionEvent ae) -> {
                try {
                    performSearch();
                } catch (Exception ex) {
                    Logger.getLogger(BoyerMooreDemo.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            choose.addActionListener((ActionEvent ae) -> {
                choose();
            });
            animate.setFont(ffont);
            animate.addActionListener((ActionEvent ae) -> {
                try {
                    stop=false;
                    animate();
                } catch (Exception ex) {
                    Logger.getLogger(BoyerMooreDemo.class.getName()).log(Level.SEVERE, null, ex);
                } 
            });
            p3.add(animate,BorderLayout.WEST);
            p3.add(bt1,BorderLayout.EAST);
            
            //add panels to frame
            frame1.add(p0);
            p1.setBackground(Color.green);
            frame1.add(p1);
            p2.setBackground(Color.green);
            frame1.add(p2);
            p3.setBackground(Color.green);
            frame1.add(p3);
            
            
            JPanel pp01=new JPanel();
            pp01.add(scrl);
            frame2.add(pptbl,BorderLayout.NORTH);
            frame2.add(pp01,BorderLayout.CENTER);
            JButton next = new JButton("Next>>");next.setSize(1, 1);
            JButton backButton=new JButton("Back");
            JButton backButton1=new JButton("Back");
            JPanel ppp=new JPanel(new FlowLayout(FlowLayout.RIGHT));
            ppp.add(next);
            JButton all=new JButton("Find All");
            ppp.add(all,FlowLayout.CENTER);
            ppp.add(backButton1,FlowLayout.LEFT);
            all.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    findAll();
                }
            });
            backButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    stop=true;  
                    tbp.setSelectedIndex(0);
                }
            });
            backButton1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                      tbp.setSelectedIndex(0);
                }
            });
            frame2.add(ppp,BorderLayout.SOUTH);
            
            JPanel pp1=new JPanel(new GridLayout(0, 1));
            pp1.add(pattern3);
            JPanel pp11=new JPanel();
            pp11.add(comp);
            pp1.add(pp11);
            comp.setFont(font);
            frame3.add(pp1,BorderLayout.NORTH);
            JPanel pp=new JPanel();
            pp.add(scrl3);
            JButton stp=new JButton("Abort Animation");
            stp.setFont(ffont);
            stp.addActionListener((ActionEvent ae) -> {
                stop=true;
                try {
                    timer.join();
                } catch (InterruptedException ex) {
                    Logger.getLogger(BoyerMooreDemo.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            frame3.add(pp,BorderLayout.CENTER);
            JPanel pp1p=new JPanel();
            pp1p.add(stp);
            frame3.add(pp1p,BorderLayout.SOUTH);
            
            //tabpane
            tbp.add("Input tab",frame1);
            tbp.add("Output tab",frame2);
            tbp.setEnabledAt(1, false);
            tbp.add("Animation",frame3);
            
            //add tabs to frame
            frame.add(tbp);
            frame.setVisible(true);
            frame.setResizable(false);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            
            next.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    try {
                        next();
                    } catch (BadLocationException ex) {
                        Logger.getLogger(BoyerMooreDemo.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
    }  
    
    public static void main(String[] args) {
        new BoyerMooreDemo().constuctFrame();
    }
    
    private void next() throws BadLocationException{
        Pair<Integer,Integer> p = plist.get(ndx);
        highlighter.removeAllHighlights();
        Highlighter.HighlightPainter painter = new DefaultHighlighter.DefaultHighlightPainter(Color.green);
        highlighter.addHighlight(p.getKey(), p.getValue(), painter);ndx++;
        if(ndx>=plist.size())ndx=0;
    }
    
    static int max (int a, int b) { return (a > b)? a: b; } 
    
    private void badCharHeuristic( char []str, int size,int badchar[]) 
    { 
      int i; 
      for (i = 0; i < NO_OF_CHARS; i++) 
           badchar[i] = -1; 
      vals=new String[1][size];
      for (i = 0; i < size; i++){ 
           badchar[(int) str[i]] = i;
      }
      for(i=0;i<size;i++){
          vals[0][i]=""+badchar[(int)str[i]];
      }
    }
    
    String search( char txt[],  char pat[],boolean animate) throws BadLocationException, InterruptedException 
    { 
      if(animate)tbp.setSelectedIndex(2);
      tim=System.nanoTime();
      int m = pat.length; 
      int n = txt.length; 
      String ret="";
      int badchar[] = new int[NO_OF_CHARS]; 
      Highlighter.HighlightPainter painter1 = new DefaultHighlighter.DefaultHighlightPainter(Color.green);
      Highlighter.HighlightPainter painter2 = new DefaultHighlighter.DefaultHighlightPainter(Color.cyan);                  
      Highlighter.HighlightPainter painter3 = new DefaultHighlighter.DefaultHighlightPainter(Color.red);                  
      badCharHeuristic(pat, m, badchar); 
      int s = 0;  // s is shift of the pattern with  
                  // respect to text 
      Object oo,oo0;            
      while(s <= (n - m)) 
      { 
          if(stop)return ret;
          int j = m-1; 
          if(animate)sleep(20000);
          System.out.println("11");
          while(j >= 0 && pat[j] == txt[s+j]) 
          { if(stop)return ret;
            noc++;
            if(animate && j+1<=pat.length && s+j+1<=txt.length){
                oo=hl3.addHighlight(j, j+1, painter1);
                oo0=hl30.addHighlight(s+j,s+j+1,painter1);
                comp.setText("No. of Comp : "+noc+"");
                sleep(20000);
                //timer.wait();
                hl3.removeHighlight(oo);
                hl30.removeHighlight(oo0);
                
            }
            j--;
          }
          noc++;
          if(stop)return ret;
          if(animate && j >= 0 && j+1<=pat.length && s+j+1<=txt.length){
                oo=hl3.addHighlight(j, j+1, painter3);
                oo0=hl30.addHighlight(s+j,s+j+1,painter3);
                sleep(20000);
                comp.setText("No. of Comp : "+noc+"");
                //timer.start();
                hl3.removeHighlight(oo);
                hl30.removeHighlight(oo0);             
          }
          //System.out.println(s);
          if (j < 0) 
          { 
              int bb=pat.length+s;
              if(!animate)plist.add(new Pair<>(s,bb));
              if(animate){hl30.addHighlight(s, bb, painter2);sleep(20000);}
              s += (s+m < n)? m-badchar[txt[s+m]] : 1; 
              //System.out.println(s);
          } 
          else{
              s += max(1, j - badchar[(int)txt[s+j]]);
            //  System.out.println(s);
          }
          if(animate)sleep(20000);
    }
      tim=System.nanoTime()-tim;
      return ret;
    }
    
    private static String getFileExtension(File file) {
        String fileName = file.getName();
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
        return fileName.substring(fileName.lastIndexOf(".")+1);
        else return "";
    }
    
    private void animate() throws BadLocationException, InterruptedException{
        pat=pattern.getText();
        data3.setText(data);
        pattern3.setText(pat);
        if("".equals(data) || pat.split(" ").length==0)
            JOptionPane.showMessageDialog(tbp, "Please choose file and enter pattern to search");
        else{
               timer.start();
        }                  
    }
    
    private void performSearch() throws BadLocationException, InterruptedException{
                    pat=pattern.getText();
                    stop=false;
                   heads=new String[pat.length()];
                   char cc[]=pat.toCharArray();
                   for(int i=0;i<pat.length();i++){
                       heads[i]=""+cc[i]+"";
                       System.out.println(heads[i]);
                   }
                   if("".equals(data) || pat.split(" ").length==0)
                        JOptionPane.showMessageDialog(tbp, "Please choose file and enter pattern to search");
                    else{
                        noc=0;
                        plist.clear();
                        ndx=0;
                        search(data.toCharArray(),pat.toCharArray(),false);
                        DefaultTableModel model = new DefaultTableModel();
                        model.setColumnIdentifiers(heads);
                        tabl=new JTable(model);
                        model.insertRow(0,vals[0]);
                        tabl.setRowHeight(1,2);
                        //tabl.setSize(2,20);
                        JScrollPane sp=new JScrollPane(tabl);
                        Dimension d = tabl.getPreferredSize();
                        sp.setPreferredSize(
                        new Dimension(d.width,tabl.getRowHeight()*3+1));
                        JPanel pn=new JPanel(new FlowLayout());
                        pn.add(sp);
                        pptbl.add(pn,BorderLayout.SOUTH);
                        lb2.setText(data+"\nTime Required : "+tim/1000000.0+"ms\n"+"No. Of Comparisions : "+noc);
                        tbp.setSelectedIndex(1);
                    }
    }
    
    private void findAll(){
        ndx=0;
        Highlighter.HighlightPainter painter = new DefaultHighlighter.DefaultHighlightPainter(Color.ORANGE);
        highlighter.removeAllHighlights();
        plist.stream().map((p) -> {
                            System.out.println(p.getKey()+" -> "+p.getValue());
                            return p;
                        }).forEachOrdered((p) -> {
                            try {
                                highlighter.addHighlight(p.getKey(), p.getValue(), painter);
                            } catch (BadLocationException ex) {
                                Logger.getLogger(BoyerMooreDemo.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        });
    }
    
    private void choose(){
                boolean chose=false;
                if(correct)chose=true;
                correct=false;
                while(!correct){
                    int r=browseFile.showOpenDialog(frame);
                    if(r==JFileChooser.APPROVE_OPTION){
                        f=new File(browseFile.getSelectedFile().getAbsolutePath());
                        if(getFileExtension(f).equals("txt")) {
                            correct=true;
                        } else {
                        }
                        fname.setText(f.getName());
                    }
                    else {
                        if(chose)break;
                    }
                    if(!correct)
                        JOptionPane.showMessageDialog(frame, "Choose text File only");
                }
                try {
                    FileReader fr=new FileReader(f);
                    int i;
                    while((i=fr.read())!=-1){
                        data+=(char)i;
                    }
                }catch (IOException ex) {
                    Logger.getLogger(BoyerMooreDemo.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println(data);
    }
    
    private void sleep(long i){
       while(i--!=0){
           for(long j=0;j<100000;j++){
               j++;
               j--;
           }
       }
    }
}
