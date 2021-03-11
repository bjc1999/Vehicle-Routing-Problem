public class DFS {
    // graph info
    private int N;
    private int[][] graph;
    private Tour bestTour;
    // constraints
    private int capacity;

    public DFS(int N, int[][] graph) {
        this.N = N;
        this.graph = graph;
        bestTour = new Tour(Double.POSITIVE_INFINITY);
    }

    public void setCapacityConstraint(int capacity) {
        this.capacity = capacity;
    }

    public Tour heapSearch() {
        int[] stops = new int[N-1];
        for (int i=1; i<N; i++)
            stops[i-1] = i;
        heapPermutation(stops, N-1, N-1);
        return bestTour;
    }

    public void printArr(int a[], int n)
    {
        for (int i = 0; i < n; i++)
            System.out.print(a[i] + " ");
        System.out.println();
    }

    public void calcSolution(int a[], int n) {
        Tour solution = new Tour(0);
        for (int i = 0; i < n; i++) {
            if (solution.getCurrentVehicleCapacity() + this.graph[a[i]][2] > capacity) {
                // all vehicles must return to depot
                solution.addStop(0);
                solution.setCost(solution.getCost() + pythagorousDistance(this.graph[a[i-1]][0], this.graph[a[i-1]][1], this.graph[0][0], this.graph[0][1]));
                solution.appendCost(solution.getCost());
                // else add new vehicle to handle remaining demands
                solution.addNewVehicle();
            }
            double distance = 0;
            if (solution.getSolution().getLast().getLast() == 0)
                distance = pythagorousDistance(this.graph[a[i]][0], this.graph[a[i]][1], this.graph[0][0], this.graph[0][1]);
            else
                distance = pythagorousDistance(this.graph[a[i-1]][0], this.graph[a[i-1]][1], this.graph[a[i]][0], this.graph[a[i]][1]);
            solution.addStop(a[i]);
            solution.setCost(solution.getCost() + distance);
            solution.addCapacity(this.graph[a[i]][2]);
        }
        int lastStop = solution.getSolution().getLast().getLast();
        // all vehicles must return to depot
        solution.addStop(0);
        solution.setCost(solution.getCost() + pythagorousDistance(this.graph[lastStop][0], this.graph[lastStop][1], this.graph[0][0], this.graph[0][1]));
        solution.appendCost(solution.getCost());
        if (solution.getCost() < bestTour.getCost())
            bestTour = solution;
    }

    public void heapPermutation(int a[], int size, int n)
    {
        // if size becomes 1 then prints the obtained
        // permutation
        if (size == 1)
            calcSolution(a, n);
 
        for (int i = 0; i < size; i++) {
            heapPermutation(a, size - 1, n);
 
            // if size is odd, swap 0th i.e (first) and
            // (size-1)th i.e (last) element
            if (size % 2 == 1) {
                int temp = a[0];
                a[0] = a[size - 1];
                a[size - 1] = temp;
            }
 
            // If size is even, swap ith 
            // and (size-1)th i.e last element
            else {
                int temp = a[i];
                a[i] = a[size - 1];
                a[size - 1] = temp;
            }
        }
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
