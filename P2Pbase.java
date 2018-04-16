import java.io.*;
import java.io.Console.*;
import java.nio.file.*;
import java.util.*;

/** 
 * 
 */
public class P2Pbase {

    
    public P2Pbase() {
        Scanner scan = new Scanner(System.in);
        String opt = scan.nextLine();
        switch(opt){
            case "-music":
                //Run special music P2P program
                try{MusicBootloader.executeScript();}
                catch(Exception e){e.printStackTrace();}
                new MusicBootloader();
                break;
            case "-text":
                //Do an IRC kind of txtmsg-P2P thing 
                //Like Encrypted chat
                Vector<String>txt = new Vector<>();
                txt.add(new String(Login.query.get(0)));
                new TextBootloader(txt);
            case "-file":
                //More like a torrenting program
                new FileBootloader();
            default:
                break;
        }
        
    }

    public static void main(String[]args){
        if(args.length!=2){System.out.println("Incorrect Usage. Try $:java PS2Pbase -u Username");}
        else{
            String opt  = args[0];
            String user = args[1];
            Vector<char[]>credentials = new Vector<>();
            if(opt.compareTo("-u")==0){
                //Check if that username is taken, 
                //if it is then see if this is that user, 
                //if not create a new account 
                System.out.println("Hello "+args[1]+". Please enter your password.");
                System.out.println("Password:"); 
                credentials.add(user.toCharArray());
                Console cons;
                char[] passwd; 
                try{passwd =  System.console().readPassword();credentials.add(passwd);}
                catch(Exception e){e.printStackTrace();}
                /** Validate these credentials securely */
                Login log = new Login(credentials); 
                if(log.LEGIT){new P2Pbase();}
                else{System.out.println("Login Attempt Failed. Please Try running P2Pbase again.");}
            }
            
        }
        
    }

    private static class Login implements Runnable {
        
        public static  Vector<char[]> query = new Vector<>();
        private boolean isEmpty = false; 
        private static boolean LEGIT = false; 
        public static Map<String,String> database = new HashMap<>();
        
        static boolean newly = false;
        
        public Login(Vector<char[]> auth) {
            this.query = auth;
            run();
           
        }


        public void run() {
            Path p = Paths.get(System.getProperty("user.dir"),"usrbase.txt");
            BufferedReader br = null;
            Vector<String>contents = new Vector<>(); 
            String ln;
            try{
                 br = new BufferedReader(new FileReader(p.toFile()));
                 while((ln = br.readLine()) != null){contents.add(ln.split("\n")[0]);} 
            }
            
            catch(FileNotFoundException e){e.printStackTrace();System.exit(1);}
            catch(IOException e){e.printStackTrace();System.exit(1);}
            Login.LEGIT = queryUserDatabase(contents);
            /** If user returns to this point they've either logged in
            or created a new account with their own credentials **/
            if(Login.LEGIT){Login.userGreeting();}

            // Now let user decide what they want to do based
            // on the menu options provided 
           
        }
        
        /** Search the database and verify that the credentials
        given match up with the username associated with them */
        public static boolean queryUserDatabase(Vector<String>db){
            boolean legit = false;
            
            Vector<String>usrnames = new Vector<>();
            Vector<String>passwds = new Vector<>();
            Map<String,String> datab = new HashMap<>();
            for(String s : db){
                
                String username=null; String pass=null;
                try{username=s.split(" ")[0];pass=s.split(" ")[1];database.put(pass,username);}
                catch(ArrayIndexOutOfBoundsException e){e.printStackTrace();}
                if(new String(query.get(0)).compareTo(s.split(" ")[0])==0 && new String(query.get(1)).compareTo(s.split(" ")[1])==0){legit=true;break;}
                if(new String(query.get(0)).compareTo(username)==0 && new String(query.get(1)).compareTo(pass)!=0){
                    System.out.println("Failed Login Attempt");
                    }
                if(new String(query.get(0)).compareTo(username)!=0 && new String(query.get(1)).compareTo(pass)!=0){createNewUser();}
                try{
                if(datab.get(new String(query.get(1))).compareTo(new String(query.get(0)))==0){legit=true;}
                else{System.out.println(datab.get(new String(query.get(1))));}}
                catch(NullPointerException e){recordLoginAttempt(query);}
                if(legit){LEGIT=true;break;}
                
            }
            /**TODO: DEBUG User logins for accounts other than admin */
            return legit;
        }
        
        private static void recordLoginAttempt(Vector<char[]>badauth){
            /** Log these bad attempts, and which accounts/passwords 
            they are connected to, to a txt file for monitoring! */
        }
        
        /** Validate a legitimate user **/
        public static void userGreeting(){
            System.out.println("******************************");
            System.out.println("* <<-(-{WELCOME TO P2P}-)->> *");
            System.out.println("*============================*");
            System.out.println("*Enter -music for P2P-music  *");
            System.out.println("*Enter -txt to message P2P   *");
            System.out.println("*Enter -file for filesharing *");
            System.out.println("*============================*");
            System.out.println("*All connections are made and*");
            System.out.println("*broken immediately. Anything*");
            System.out.println("*sent or recieved is uniquely*");
            System.out.println("*encrypted as well. P2P.java *");
            System.out.println("*is not designed to look at  *");
            System.out.println("*message content, leaving the*");
            System.out.println("*user responsible for how to *");
            System.out.println("*use this program (P2P.java).*");
            System.out.println("*============================*");
            System.out.println("*Breaking the following rules*");
            System.out.println("*will result in being banned.*");
            System.out.println("*[1]sending malware/malicious*");
            System.out.println("*software of anykind. Be nice*");
            System.out.println("*[2]Respect the general legal*");
            System.out.println("*rights of users, as they may*");
            System.out.println("*be defined where you live or*");
            System.out.println("*where your peer lives.      *");
            System.out.println("* Rules will be amended on an*");
            System.out.println("* as needed basis only.      *");
            System.out.println("*============================*");
            System.out.println("*|#|<:     HAVE FUN!    :>|#|*");
            System.out.println("*============================*");
            System.out.println("******************************");
            
            
        }
        
        /** Create a new User/Passsword entry in the login database */
        private static void createNewUser(){
            System.out.println("Incorrect password for Username "+
                    new String(query.get(0))+":"+new String(query.get(1))+"\n::DEBUG::");
             for(Map.Entry<String,String>entry:database.entrySet()){
                 System.out.println(entry.getKey()+":"+entry.getValue());
             }
            
            System.out.println("Would you like to create a new account?[Y/N]");
            Scanner sc = new Scanner(System.in);
            String ans = sc.nextLine();
            if(ans.compareTo("Y")!=0&&ans.compareTo("N")!=0){
                System.out.println("You didn't answer yes or no. Let's try again.");
                createNewUser();
            }
            if(ans.compareTo("Y")==0){registerNewUser();}
            if(ans.compareTo("N")==0){System.exit(0);}
            sc.close();
        }
        
        private static void registerNewUser(){
            newly = true;
            System.out.println("Adding "+new String(query.get(0))+" to the User Database");
            String payload = new String(query.get(0))+" "+new String(query.get(1));
            BufferedWriter bw = null;
            try{
            File f = Paths.get(System.getProperty("user.dir"),"usrbase.txt").toFile();
            bw = new BufferedWriter(new FileWriter(f,true));
            bw.write(payload);
        }catch(IOException e){e.printStackTrace();}
            /**TODO: New user data isn't being logged, but work on that later */
        }

    }
    
/** * * * * * **** ** ** * * ** * </:/MUSIC_BOOTLOADER_CLASS/:/> * * * * **** ** ** * * **/
    private static class MusicBootloader implements Runnable{
        
        public MusicBootloader(){
            run();
        }
        public void run(){
            Scanner sc = new Scanner(System.in);
            String input = sc.nextLine();
            
            
            
            
            sc.close();
        }
        
        public static void executeScript() throws Exception{
        try{
            Path mloader = Paths.get(System.getProperty("user.dir"),"music.sh");
            String target = new String(mloader.toString());
            Runtime rt = Runtime.getRuntime();
            Process proc = rt.exec(target);
            proc.waitFor();
            StringBuffer output = new StringBuffer();
            BufferedReader br = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line = "";
            while((line = br.readLine()) != null){
                output.append(line+"\n");
            }
            System.out.println("### "+output);
        }catch(Throwable t){t.printStackTrace();}   
    }
    
        
        
    }
/** * * * * * **** ** ** * * ** * </EndOf:/MUSIC_BOOTLOADER_CLASS/:/> * * * * **** ** ** * * **/

/** * * * * * **** ** ** * * ** * </:/TEXT_BOOTLOADER_CLASS/:/> * * * * **** ** ** * * * */
private static class TextBootloader implements Runnable{
    
    private static  Vector<String> messagePayloads = new Vector<>();
    
    public TextBootloader(Vector<String>flocs){
        this.messagePayloads = flocs;
        run();
    }
    public void run(){
       try{executeScript();}
       catch(Exception e){e.printStackTrace();}
    }
    
    public static void executeScript() throws Exception{
        String fnames = "";
        for(String fname : messagePayloads){fnames+=fname+" ";}
        try{
            Path mloader = Paths.get(System.getProperty("user.dir"),"messenger.sh");
            String target = new String(mloader.toString())+" "+fnames;
           
            Runtime rt = Runtime.getRuntime();
            Process proc = rt.exec(target);
            proc.waitFor();
            StringBuffer output = new StringBuffer();
            BufferedReader br = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line = "";
            while((line = br.readLine()) != null){
                output.append(line+"\n");
            }
            System.out.println(output);
        }catch(Throwable t){t.printStackTrace();}   
    }
    
}
/** * * * * * **** ** ** * * ** * </EndOf:/TEXT_BOOTLOADER_CLASS/:/> * * * * **** ** ** * * * */

/** * * * * * **** ** ** * * ** * </:/FILE_BOOTLOADER_CLASS/:/> * * * * **** ** ** * * * */
private static class FileBootloader implements Runnable{
    public FileBootloader(){
        
    }
    public void run(){
        
    }
}
/** * * * * * **** ** ** * * ** * </EndOf:/FILE_BOOTLOADER_CLASS/:/> * * * * **** ** ** * * * */
    
}
