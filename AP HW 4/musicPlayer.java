import java.util.*;
public class musicPlayer {
    static class SimplePlayList{
        String song_title;
        String artist;
        SimplePlayList nextSong;
        SimplePlayList(String song_title, String artist){
            this.song_title = song_title;
            this.artist = artist;
        }
        SimplePlayList(String song_title, String artist, SimplePlayList nextSong){
            this.song_title = song_title;
            this.artist = artist;
            this.nextSong = nextSong;
        }
    }

    public static String[] extractSongInfo(String userInput) {
        String[] songInfo = userInput.split("\\[");
        return new String[] {songInfo[0],songInfo[1].substring(0,songInfo[1].length()-1)};
    }

    public static SimplePlayList pushSong(SimplePlayList playList, SimplePlayList currSong, String userInput) {
        String[] songInfo = extractSongInfo(userInput);
        return new SimplePlayList(songInfo[0],songInfo[1],playList);
    }

    public static SimplePlayList queueSong(SimplePlayList playList, SimplePlayList currSong, String userInput) {
        String[] songInfo = extractSongInfo(userInput);
        SimplePlayList newSong = new SimplePlayList(songInfo[0],songInfo[1],playList), iter=playList;
        if(playList==null) return newSong;
        while(iter.nextSong!=playList) //old null
            iter = iter.nextSong;
        iter.nextSong = newSong;
        return newSong;
    }

    public static void getCurrentSong(SimplePlayList playList, SimplePlayList currSong) {
        while (playList.nextSong!=currSong)
            playList = playList.nextSong;
        System.out.println("The current song is: "+currSong.song_title+" ["+currSong.artist+"]");
        System.out.println("    The previous song is: "+playList.song_title+" ["+playList.artist+"]");
        System.out.println("    The next song is: "+currSong.nextSong.song_title+" ["+currSong.nextSong.artist+"]");
    }

    public static SimplePlayList deleteCurrentSong(SimplePlayList playList,SimplePlayList lastNode, SimplePlayList currSong, boolean isLast){
        if(currSong==currSong.nextSong){
            return null;
        }else {
            currSong.artist = currSong.nextSong.artist;
            currSong.song_title = currSong.nextSong.song_title;
            currSong.nextSong = currSong.nextSong.nextSong; //handle next
            if(isLast) playList = playList.nextSong;
        }
        return playList;
    }

    public static SimplePlayList makePrevAsCurrentSong(SimplePlayList playList,SimplePlayList currSong){
        while(playList.nextSong!=currSong)
            playList = playList.nextSong;
        return playList;
    }

    public static SimplePlayList makeNextasCurrentSong(SimplePlayList currSong){
        return currSong.nextSong;
    }

    public static void printPlaylist(SimplePlayList playList, SimplePlayList iter, int i){
        System.out.println("The playlist is:");
        do{
            System.out.println(i+". "+iter.song_title+" ["+iter.artist+"]");
            iter = iter.nextSong; i++;
        }while (iter!=playList);
    }

    public static void findSong(SimplePlayList playList, String userInput) {
        String[] songInfo = extractSongInfo(userInput);
        SimplePlayList prev = new SimplePlayList("",""), iter = playList;
        while(!(iter.song_title.equals(songInfo[0])&&iter.song_title.equals(songInfo[1]))){
            prev = iter;
            iter = iter.nextSong;
            if(iter == playList) {
                System.out.println("Song not found"); return;
            }
        }
        System.out.println("The current song is:"+playList.song_title+" ["+playList.artist+"]");
        System.out.println("The previous song is:"+prev.song_title+" ["+prev.artist+"]");
        System.out.println("The next song is:"+playList.nextSong.song_title+" ["+playList.nextSong.artist+"]");
    }

    public static String[][] extractBothSongs(String userInfo){
        int splitInd = userInfo.indexOf(']');
        String[] firstSong = extractSongInfo(userInfo.substring(0,splitInd+1));
        String[] secondSong = extractSongInfo(userInfo.substring(splitInd+2));
        return new String[][]{firstSong,secondSong};
    }

    public static void addAfter(SimplePlayList playList, String userInfo){
        String[][] songs = extractBothSongs(userInfo);
        SimplePlayList prev = new SimplePlayList("","");
        while(!(playList.song_title.equals(songs[0][0])&&playList.artist.equals(songs[0][1]))){
            prev = playList;
            playList = playList.nextSong;
        }
        SimplePlayList newSong = new SimplePlayList(songs[1][0],songs[1][1],playList.nextSong);
        playList.nextSong = newSong;
    }

    public static void addBefore(SimplePlayList playList, SimplePlayList lastSong, String userInfo){
        String[][] songs = extractBothSongs(userInfo);
        SimplePlayList prev = lastSong;
        while(!(playList.song_title.equals(songs[0][0])&&playList.artist.equals(songs[0][1]))){
            prev = playList;
            playList = playList.nextSong;
        }
        SimplePlayList newSong = new SimplePlayList(songs[1][0],songs[1][1],playList);
        prev.nextSong = newSong;
    }

    public static SimplePlayList changeCurrentSongTo(SimplePlayList playList, String userInput) {
        String[] songInfo = extractSongInfo(userInput);
        while(playList!=null&&!(playList.song_title.equals(songInfo[0])&&playList.artist.equals(songInfo[1]))){
            playList = playList.nextSong;
        }
        return playList;
    }

    public static SimplePlayList setRandom(SimplePlayList playList){
        int noOfElement = 1;
        Random rnd = new Random();
        SimplePlayList ans = playList, iter = playList;
        do {
            if(rnd.nextInt(noOfElement)==0) ans = iter;
            noOfElement += 1;
            iter = iter.nextSong;
        } while (iter != playList);
        return ans;
    }

    public static String[] getChoice(Scanner sc){
        System.out.println("Please enter choice");
        String userInput = sc.nextLine();
        String choices[] = userInput.split(" "), songInfo = choices[0];
        if(!choices[0].equals(userInput)) songInfo = userInput.substring(choices[0].length()+1);
        return new String[]{choices[0].toLowerCase(),songInfo};
    }

    public static void selectionMenu(Scanner sc, SimplePlayList playList, SimplePlayList currSong, SimplePlayList lastSong){
        boolean isEmpty = true;
        while(true){
            String[] userInput = getChoice(sc);
            if(userInput[0].equals("quit")) break;
            else if(userInput[0].equals("push")) {
                playList = pushSong(playList,currSong,userInput[1]);
                if(isEmpty){
                    currSong = playList; playList.nextSong = playList;
                    lastSong = playList;
                    isEmpty = false;
                }else lastSong.nextSong = playList;
            }
            else if(userInput[0].equals("queue")) {
                SimplePlayList newSong = queueSong(playList,currSong,userInput[1]);
                if(isEmpty){
                    playList = newSong; currSong = playList;
                    playList.nextSong = playList; lastSong = playList;
                    isEmpty = false;
                }else{
                    lastSong.nextSong = newSong;
                    lastSong = newSong;
                }
            }
            else if(userInput[0].equals("current")&&!isEmpty) getCurrentSong(playList,currSong);
            else if(userInput[0].equals("delete")&&!isEmpty) {
                boolean isLast = lastSong == currSong;
                SimplePlayList ret = deleteCurrentSong(playList, lastSong, currSong, isLast);
                if(ret==null) {
                    isEmpty = true;playList =null;currSong=null;lastSong=null;
                }
                else playList = ret;
                int a =0;
            }
            else if(userInput[0].equals("next")&&!isEmpty) currSong = makeNextasCurrentSong(currSong);
            else if(userInput[0].equals("prev")&&!isEmpty) currSong = makePrevAsCurrentSong(playList,currSong);
            else if(userInput[0].equals("restart")) currSong = playList;
            else if(userInput[0].equals("print")&&!isEmpty) printPlaylist(playList,playList,1);
            else if(userInput[0].equals("find")&&!isEmpty) findSong(playList, userInput[1]);
            else if(userInput[0].equals("changeto")) currSong = changeCurrentSongTo(playList, userInput[1]);
            else if(userInput[0].equals("addbefore")&&!isEmpty) addBefore(playList, lastSong, userInput[1]);
            else if(userInput[0].equals("addafter")&&!isEmpty) addAfter(playList, userInput[1]);
            else if(userInput[0].equals("random")&&!isEmpty) currSong = setRandom(playList); 
        }
    }

    public static void main(String[] args) {
        SimplePlayList playList = null, currSong = null, lastSong = null;
        selectionMenu(new Scanner(System.in), playList, currSong, lastSong);
    }
}
