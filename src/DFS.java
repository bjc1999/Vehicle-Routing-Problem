public class DFS {
    // graph info
    private int N;
    private int[][] graph;
    private Tour bestTour;
    // constraints
    private int capacity;
    // time
    private long start;
    private long end;
    private boolean time_exceeded = false;

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
        start = System.nanoTime();
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
                solution.setCost(solution.getCost() + Utils.pythagorousDistance(this.graph[a[i-1]][0], this.graph[a[i-1]][1], this.graph[0][0], this.graph[0][1]));
                solution.appendCost(solution.getCost());
                // else add new vehicle to handle remaining demands
                solution.addNewVehicle();
            }
            double distance = 0;
            if (solution.getSolution().getLast().getLast() == 0)
                distance = Utils.pythagorousDistance(this.graph[a[i]][0], this.graph[a[i]][1], this.graph[0][0], this.graph[0][1]);
            else
                distance = Utils.pythagorousDistance(this.graph[a[i-1]][0], this.graph[a[i-1]][1], this.graph[a[i]][0], this.graph[a[i]][1]);
            solution.addStop(a[i]);
            solution.setCost(solution.getCost() + distance);
            solution.addCapacity(this.graph[a[i]][2]);
        }
        int lastStop = solution.getSolution().getLast().getLast();
        // all vehicles must return to depot
        solution.addStop(0);
        solution.setCost(solution.getCost() + Utils.pythagorousDistance(this.graph[lastStop][0], this.graph[lastStop][1], this.graph[0][0], this.graph[0][1]));
        solution.appendCost(solution.getCost());
        if (solution.getCost() < bestTour.getCost())
            bestTour = solution;
    }

    public void heapPermutation(int a[], int size, int n)
    {
        // if size becomes 1 then prints the obtained
        // permutation
        if (size == 1) {
            calcSolution(a, n);
            end = System.nanoTime();
            long duration = end-start;
            Utils.printProgress(duration);
            if(duration > App.EXECUTION_TIME)
                time_exceeded=true;
        }
 
        for (int i = 0; i < size; i++) {
            if(time_exceeded) return;

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

}
