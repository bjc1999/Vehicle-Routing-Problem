public class Utils {
    public static double pythagorousDistance(int x1, int y1, int x2, int y2) {
        return Math.sqrt(Math.pow((x1-x2), 2) + Math.pow((y1-y2), 2));
    }

    public static boolean isAllNodeVisited(boolean[] visited) {
        for (boolean isVisited: visited)
            if (!isVisited)
                return false;
        return true;
    }

    public static void printProgress(long duration) {
        long secs = duration/1000000000;
        String progress = "|";
        for(int i=1; i<=60; i++) {
            if(i<=secs)
                progress += "=";
            else
                progress += " ";
        }
        progress += "| "+secs+"s";
        if(secs == 60)
            progress += " (Searching is forced to stop!)";
        System.out.print("Time elapsed: "+progress+"\r");
    }
}
