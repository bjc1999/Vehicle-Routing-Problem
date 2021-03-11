public class Greedy {
    // graph info
    private int N;
    private int[][] graph;
    // constraints
    private int capacity;

    public Greedy(int N, int[][] graph) {
        this.N = N;
        this.graph = graph;
    }

    public void setCapacityConstraint(int capacity) {
        this.capacity = capacity;
    }

    public Tour search() {
        Tour solution = new Tour(0);
        boolean[] visited = new boolean[N];
        visited[0] = true;
        for (int i=1; i<visited.length; i++) {
            visited[i] = false;
        }
        boolean[] checked = visited.clone();
        while (true) {
            int currentState = solution.getSolution().getLast().getLast();
            double shortestDistance = Double.POSITIVE_INFINITY;
            int shortestNextStop = currentState;
            for (int i=1; i<N; i++) {
                if (!checked[i]) {
                    double distance = pythagorousDistance(graph[currentState][0], graph[currentState][1], graph[i][0], graph[i][1]);
                    if (distance < shortestDistance) {
                        shortestDistance = distance;
                        shortestNextStop = i;
                    }
                }
            }
            if (shortestNextStop == currentState) {
                // all vehicles must return to depot
                solution.addStop(0);
                solution.setCost(solution.getCost() + pythagorousDistance(this.graph[currentState][0], this.graph[currentState][1], this.graph[0][0], this.graph[0][1]));
                solution.appendCost(solution.getCost());
                // if all stop visited then end the tour searching
                if (isAllNodeVisited(visited))
                    break;
                // else add new vehicle to handle remaining demands
                solution.addNewVehicle();
                checked = visited.clone();
                continue;
            }
            boolean isViolated = false;
            if (solution.getCurrentVehicleCapacity() + this.graph[shortestNextStop][2] > capacity) {
                isViolated = true;
            }
            if (!isViolated) {
                solution.addStop(shortestNextStop);
                solution.setCost(solution.getCost() + shortestDistance);
                solution.addCapacity(this.graph[shortestNextStop][2]);
                visited[shortestNextStop] = true;
                checked = visited.clone();
            }
            checked[shortestNextStop] = true;

            if (isViolated) {
                for (int i=1; i<N; i++) 
                    if (!checked[i]) 
                        if (solution.getCurrentVehicleCapacity() + this.graph[i][2] > capacity)
                            checked[i] = true;
            }
        }
        return solution;
    }

    public double pythagorousDistance(int x1, int y1, int x2, int y2) {
        return Math.sqrt(Math.pow((x1-x2), 2) + Math.pow((y1-y2), 2));
    }

    public boolean isAllNodeVisited(boolean[] visited) {
        for (boolean isVisited: visited)
            if (!isVisited)
                return false;
        return true;
    }
}
