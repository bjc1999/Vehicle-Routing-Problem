public class Greedy {
    // graph info
    private int N;
    private int[][] graph;
    // constraints
    private int capacity;
    // time
    private long start;
    private long end;

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
        start = System.nanoTime();
        while (true) {
            int currentState = solution.getSolution().getLast().getLast();
            double shortestDistance = Double.POSITIVE_INFINITY;
            int shortestNextStop = currentState;
            for (int i=1; i<N; i++) {
                if (!checked[i]) {
                    double distance = Utils.pythagorousDistance(graph[currentState][0], graph[currentState][1], graph[i][0], graph[i][1]);
                    if (distance < shortestDistance) {
                        shortestDistance = distance;
                        shortestNextStop = i;
                    }
                }
            }
            if (shortestNextStop == currentState) {
                // all vehicles must return to depot
                solution.addStop(0);
                solution.setCost(solution.getCost() + Utils.pythagorousDistance(this.graph[currentState][0], this.graph[currentState][1], this.graph[0][0], this.graph[0][1]));
                solution.appendCost(solution.getCost());
                // if all stop visited then end the tour searching
                if (Utils.isAllNodeVisited(visited))
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

            end = System.nanoTime();
            long duration = end-start;
            Utils.printProgress(duration);
        }
        return solution;
    }
}
