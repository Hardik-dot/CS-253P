import java.util.*;
class APLab3{
    static HashMap<Character,Character> bracketMap = new HashMap<>();

    public  static void listSizeCheck(int depth, List<List<String>> wordsByLevel) {
        while(depth>=wordsByLevel.size())
            wordsByLevel.add(new ArrayList<>());
    }

    public static void addStringToDepthList(int depth, StringBuilder curr, List<List<String>> wordsByLevel){
        listSizeCheck(depth, wordsByLevel);
        if(curr.length()>0)
            wordsByLevel.get(depth).add(curr.toString());
    }

    public static int openingBrace(char open, int depth, Stack<Character> brkt, StringBuilder curr, List<List<String>> wordsByLevel){
        addStringToDepthList(depth, curr, wordsByLevel);
        brkt.add(open);
        return depth+1;
    }

    public static int closingBrace(char close, int depth, Stack<Character> brkt, StringBuilder curr, List<List<String>> wordsByLevel){
        if(!brkt.isEmpty()) {
            char open = brkt.pop();
            if (open == bracketMap.get(close)) {
                addStringToDepthList(depth, curr, wordsByLevel);
                return depth - 1;
            }
        }
        return -1;
    }

    public static void displayOutput(List<List<String>> wordsByLevel, int validStatus){
        if(validStatus==-1){
            System.out.println("mismatched groups!");
            return;
        }
        for(int i=0;i<wordsByLevel.size();i++){
            if(wordsByLevel.get(i).size()>0){
                System.out.println();
                for(int j=0;j<i*2;j++) // to print desired whitespaces
                    System.out.print(" ");
                for(String word:wordsByLevel.get(i)) // print strings at each depth from left to right
                    System.out.print(word+" ");
            }
        }
        System.out.println();
    }

    public static int paranthesisTextDecode(String text, List<List<String>> wordsByLevel) {
        int depth=0,index=0;
        Stack<Character> brkt = new Stack<>();
        StringBuilder currStr = new StringBuilder();
        for(;index<text.length();index+=1){
            char curr_ch = text.charAt(index);
            if(bracketMap.containsKey(curr_ch)){ // check for closing bracket
                depth = closingBrace(curr_ch,depth,brkt,currStr,wordsByLevel);
                if(depth==-1) return -1;
                currStr = new StringBuilder();
            }
            else if(bracketMap.containsValue(curr_ch)) { // check for opening bracket
                depth = openingBrace(curr_ch,depth,brkt,currStr,wordsByLevel);
                currStr = new StringBuilder();
            }
            else currStr.append(curr_ch); //normal character
        }
        if(!brkt.isEmpty()) return -1;
        if(currStr.length()!=0) wordsByLevel.get(0).add(currStr.toString());
        return 0;
    }

    public static String takeInput(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter input: ");
        return sc.nextLine();
    }

    public static void main(String[] args){
        String input = takeInput();
        bracketMap.put('}','{');
        bracketMap.put(')','(');
        bracketMap.put(']','[');
        List<List<String>> wordsByLevel = new ArrayList<>();
        listSizeCheck(0,wordsByLevel);
        int validStatus = paranthesisTextDecode(input, wordsByLevel);
        displayOutput(wordsByLevel, validStatus);
    }
}
