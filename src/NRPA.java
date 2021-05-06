import java.util.LinkedList;
import java.util.Random;

public class NRPA {
    // graph info
    private int N;
    private int[][] graph;
    // policy
    private double[][] globalPolicy;
    private double[][][] policy;
    // configuration parameters
    private int level;
    private int iterations;
    private double ALPHA;
    // constraints
    private int capacity;
    // time
    private long start;
    private long end;

    public NRPA(int N, int[][] graph, double[][] globalPolicy) {
        this.N = N;
        this.graph = graph;
        this.globalPolicy = globalPolicy;
    }

    public void setParamters(int level, int iterations, double ALPHA) {
        this.level = level;
        this.iterations = iterations;
        this.ALPHA = ALPHA;
        this.policy = new double[level+1][N][N];
    }

    public void setCapacityConstraint(int capacity) {
        this.capacity = capacity;
    }

    public Tour search() {
        start = System.nanoTime();
        // for low N, iterations no need to be too large
        if(this.N <= 10) iterations = 70;
        return search(level, iterations);
    }

    public Tour search(int level, int iterations) {
        Tour best_solution = new Tour(Double.POSITIVE_INFINITY);
        if (level == 0) {
            return rollout();
        }
        else {
            policy[level] = globalPolicy.clone();
            for (int i=0; i<=iterations; i++) {
                Tour new_solution = search(level-1, iterations);
                //System.out.println(new_solution);
                if (new_solution.getCost() < best_solution.getCost()) {
                    best_solution = new_solution;
                    adapt(best_solution.getSolution(), level);
                }
                
                // return current best tour if time exceeded
                end = System.nanoTime();
                long duration = end-start;
                Utils.printProgress(duration);
                if(duration > App.EXECUTION_TIME)
                    return best_solution;
            }
            // update policy
            globalPolicy = policy[level].clone();
        }
        return best_solution;
    }

    public void adapt(LinkedList<LinkedList<Integer>> solution, int level) {
        boolean[] visited = new boolean[N];
        visited[0] = true;
        for (int i=1; i<visited.length; i++) {
            visited[i] = false;
        }
        for (LinkedList<Integer> route: solution) {
            int currentState = 0;
            for (int i=1; i<route.size(); i++) {
                policy[level][currentState][route.get(i)] += ALPHA;
                double z =0;
                for (int j=0; j<N; j++)
                    if (!visited[j])
                        z += Math.exp(globalPolicy[currentState][j]);
                for (int j=0; j<N; j++)
                    if (!visited[j])
                        policy[level][currentState][j] -= ALPHA * (Math.exp(globalPolicy[currentState][j])/z);
                currentState = route.get(i);
                visited[currentState] = true;
            }
        }
    }

    public Tour rollout() {
        Tour solution = new Tour(0);
        boolean[] visited = new boolean[N];
        visited[0] = true;
        for (int i=1; i<visited.length; i++) {
            visited[i] = false;
        }
        boolean[] checked = visited.clone();
        while (true) {
            int currentState = solution.getSolution().getLast().getLast();
            LinkedList<Integer> successors = find_successors_of(checked);
            if (successors.size() == 0) {
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
            int nextState = select_next_move(currentState, successors);
            boolean isViolated = false;
            // check if add nextState into current vehicle violate any constraints
            if (solution.getCurrentVehicleCapacity() + this.graph[nextState][2] > capacity) {
                isViolated = true;
                // solution.setCost(solution.getCost() + violationCost);
            }
            // if no violation then add into route of current vehicle in solution
            if (!isViolated) {
                solution.addStop(nextState);
                solution.setCost(solution.getCost() + Utils.pythagorousDistance(this.graph[currentState][0], this.graph[currentState][1], this.graph[nextState][0], this.graph[nextState][1]));
                solution.addCapacity(this.graph[nextState][2]);
                visited[nextState] = true;
                checked = visited.clone();
            }
            checked[nextState] = true;

            if (isViolated) {
                LinkedList<Integer> possibleSuccessors = find_successors_of(checked);
                for (Integer successor: possibleSuccessors)
                    if (solution.getCurrentVehicleCapacity() + this.graph[successor][2] > capacity)
                        checked[successor] = true;
            }
        }
        // calculate penalty and add into cost if applicable

        return solution;
    }

    public LinkedList<Integer> find_successors_of(boolean[] checked) {
        LinkedList<Integer> successors = new LinkedList<>();
        for (int i=0; i<checked.length; i++)
            if (!checked[i])
                successors.add(i);
        return successors;
    }

    public int select_next_move(int currentState, LinkedList<Integer> successors) {
        double[] probability = new double[successors.size()];
        double sum = 0;
        for (int i=0; i<successors.size(); i++) {
            probability[i] = Math.exp(globalPolicy[currentState][successors.get(i)]);
            sum += probability[i];
        }
        double mrand = new Random().nextDouble() * sum;
        int i = 0;
        sum = probability[0];
        while (sum < mrand)
            sum += probability[++i];
        return successors.get(i);
    }
}
