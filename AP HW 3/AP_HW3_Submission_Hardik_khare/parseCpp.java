import java.io.*;
import java.util.*;
public class parseCpp {
    static HashSet<Character> opset = new HashSet<>();
    static HashSet<String> multiopset = new HashSet<>();
    public static void initOperators() {
        opset.add('#');opset.add('%');opset.add('^');opset.add('.');
        opset.add('(');opset.add(')');opset.add('{');opset.add('}');
        opset.add('[');opset.add(']');opset.add(',');opset.add(';');
        opset.add('~');opset.add(' ');opset.add('\n');opset.add('\t');
    }

    public static void initMultiops(){
        multiopset.add("+");multiopset.add("+=");multiopset.add("++");
        multiopset.add("-");multiopset.add("-=");multiopset.add("--");
        multiopset.add("*");multiopset.add("*=");multiopset.add("**");
        multiopset.add("/");multiopset.add("/=");multiopset.add("%");
        multiopset.add("%=");multiopset.add("=");multiopset.add("==");
        multiopset.add("!");multiopset.add("!=");multiopset.add(">");
        multiopset.add(">=");multiopset.add(">>");multiopset.add(">>=");
        multiopset.add("<");multiopset.add("<=");multiopset.add("<<");
        multiopset.add("<<=");multiopset.add("&");multiopset.add("&=");
        multiopset.add("&&");multiopset.add("|");multiopset.add("|=");
        multiopset.add("||");multiopset.add("^");multiopset.add("^=");
        multiopset.add("?");multiopset.add("?:");multiopset.add("->");
        multiopset.add(":");multiopset.add("::");

    }

    public static int multiOperatorsCheck(String part, int i, List<String> tokens) {
        int ind = i+1;
        if(part.length()>=3&&multiopset.contains(part.substring(i,i+3)))
            ind = i+3;
        else if(part.length()>=2&&multiopset.contains(part.substring(i,i+2)))
            ind = i+2;
        tokens.add(part.substring(i,ind));
        return ind;
    }

    public static int findMultiLineEnd(int j, String code){
        while(j<code.length()-1){
            if(code.charAt(j)=='*'&&code.charAt(j+1)=='/') break;
            j++;
        }
        return j;
    }

    public static int checkComment(String code, int ind, List<String> tokens){
        int j=ind;
        if(code.length()>2&&code.charAt(ind)=='/'){
            if(code.charAt(ind+1)=='/'){
                j=code.indexOf("\n",ind+1)+1;
            }else if(code.charAt(ind+1)=='*'){
                j=findMultiLineEnd(j,code)+2;
            }
        }
        return j;
    }

    public static int addPrintStmt(String curr, List<String> tokens, int start) {
        int end =start+1;
        while(end<curr.length()){
            if(curr.charAt(end)=='"'&&curr.charAt(end-1)!='\\') break;
            end++;
        }
        if(end==curr.length()) end--;
        tokens.add(curr.substring(start,end+1));
        return end+1;
    }


    public static StringBuilder pushStringToken(List<String> tokens, String curr){
        if(!curr.equals(" ")&&!curr.equals("\n")&&!curr.equals("\t")&&curr.length()>0)
            tokens.add(curr);
        return new StringBuilder();
    }

    public static List<String> parseCode (String code){
        StringBuilder curr = new StringBuilder();
        List<String> tokens = new ArrayList<>();
        for(int i=0;i<code.length();){
            if(code.charAt(i)=='/'){
                int j=checkComment(code,i,tokens);
                if(j!=i) {i=j;continue;}
            }
            if(code.charAt(i)=='"')
                i = addPrintStmt(code,tokens,i);
            else if(multiopset.contains(code.substring(i,i+1))){
                curr = pushStringToken(tokens,curr.toString());
                i = multiOperatorsCheck(code,i,tokens);
            }else if(opset.contains(code.charAt(i))){
                curr = pushStringToken(tokens,curr.toString());
                pushStringToken(tokens,code.substring(i,i+1));
                i++;
            }else
                curr.append(code.charAt(i++));
        }
        return tokens;
    }

    public static String getFilePath(String[] args, Scanner sc) {
        String path;
        if(args.length==0){
            System.out.println("Enter File location");
            path = sc.nextLine();
        }else path = args[0];
        return path;
    }

    public static String openFile(String[] args){
        try{
            String path = getFilePath(args, new Scanner(System.in));
            BufferedReader br = new BufferedReader(new FileReader(path));
            String line=""; StringBuilder code = new StringBuilder();
            while (( line= br.readLine()) != null)
            {code.append(line);code.append("\n");}
            return code.toString();
        } catch (Exception e) { System.out.println("Error in opening file");}
        return "";
    }

    public static void printTokens(List<String> tokens){
        for(String t:tokens)
            if(!t.equals("\n"))
                System.out.println(t);
    }

    public static void main(String[] args) {
        String code = openFile(args);
        initMultiops();initOperators();
        List<String> tokens = parseCode(code);
        printTokens(tokens);
    }
}
