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
}
