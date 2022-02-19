import java.util.*;

public class Codec {
    public String chklen(String s) {
        if(s.length()<10)
            return "00"+Integer.toString(s.length());
        else if(s.length()<100)
            return "0"+Integer.toString(s.length());
        return Integer.toString(s.length());
    }

	public String encode(List<String> strs) {
        StringBuilder enc=new StringBuilder();
        for(String s:strs){
            enc.append(chklen(s));
            enc.append(s);
        }
        return enc.toString();
    }

    public List<String> decode(String s) {
        List<String> res = new ArrayList<>();
        int i=0;
        while(i<s.length()){
            int lt=Integer.valueOf(s.substring(i,i+3));
            res.add(s.substring(i+3,i+3+lt));
            i=i+3+lt;
        }
        return res;
    }

	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter input string:");
		List<String> ans = new ArrayList<>();
		while(true){
			String stsr = sc.nextLine();
			if(stsr.equals("-1")) break;
			ans.add(stsr);
		}
		Codec codec = new Codec();
		String enc = codec.encode(ans);
		System.out.println("Encoded string: "+enc);
		List<String> dec = codec.decode(enc);
		System.out.println("Decoded string: "+dec.toString());
	}
}
