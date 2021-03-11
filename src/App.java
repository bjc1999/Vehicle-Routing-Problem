import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        //editFileFormat();
        int[] dim_cap = new int[2];
        int[][] graph = readInstance("n5-k3.txt", dim_cap);
        int N = dim_cap[0];
        int capacity = dim_cap[1];

        double[][] globalPolicy = new double[N][N];
        for (int i=0; i<N; i++)
            for (int j=0; j<N; j++)
                globalPolicy[i][j] = 0;
        
        DFS dfs = new DFS(N, graph);
        dfs.setCapacityConstraint(capacity);
        System.out.println("Basic Simulation");
        System.out.println(dfs.heapSearch());

        Greedy greedy = new Greedy(N, graph);
        greedy.setCapacityConstraint(capacity);
        System.out.println("Greedy Simulation");
        System.out.println(greedy.search());
        
        NRPA nrpa = new NRPA(N, graph, globalPolicy);
        nrpa.setParamters(3, 100, 1);
        nrpa.setCapacityConstraint(capacity);
        System.out.println("MCTS Simulation");
        System.out.println(nrpa.search());
        
    }

    /*
     * Function to read VRP instances (test cases) from text file
     */
    public static int[][] readInstance(String filename, int[] dim_cap) {
        int[][] graph = new int[1][1];
        try {   
            FileInputStream fis = new FileInputStream("resources/"+filename);       
            Scanner sc=new Scanner(fis);
            int i = 0;
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                if (i==0) {
                    String[] words = line.split(" ");
                    dim_cap[0] = Integer.valueOf(words[0]);
                    dim_cap[1] = Integer.valueOf(words[1]);
                    graph = new int[dim_cap[0]][3];
                }
                else {
                    String[] words = line.split(" ");
                    graph[i-1][0] = Integer.valueOf(words[0]);
                    graph[i-1][1] = Integer.valueOf(words[1]);
                    graph[i-1][2] = Integer.valueOf(words[2]);
                }
                i++;
            }
            sc.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return graph;
    }

    /*
     * Function to change input file format to <x-coor><y-coor><demand>
     */
    public static void editFileFormat() {
        try {
            String[] pathnames;

            File f = new File("resources");

            pathnames = f.list();

            // For each pathname in the pathnames array
            for (String pathname : pathnames) {
                //the file to be opened for reading  
                FileInputStream fis=new FileInputStream("resources/"+pathname);       
                Scanner sc=new Scanner(fis);    //file to be scanned  
                //returns true if there is another line to read
                int lineCounter = 1;
                int dim = 0;
                int capacity = 0;
                int[][] graph = new int[1][1];
                while(sc.hasNextLine()) {  
                    String line = sc.nextLine().trim();
                    if (lineCounter==4) {
                        String[] words = line.split(" ");
                        dim = Integer.valueOf(words[words.length-1]);
                        graph = new int[dim][3];
                    }
                    if (lineCounter==6) {
                        String[] words = line.split(" ");
                        capacity = Integer.valueOf(words[words.length-1]);
                    }
                    if (lineCounter > 7 && lineCounter <= 7+dim) {
                        String[] words = line.split(" ");
                        graph[lineCounter-8][0] = Integer.valueOf(words[1]);
                        graph[lineCounter-8][1] = Integer.valueOf(words[2]);
                    }
                    if (lineCounter > 8+dim && lineCounter <= 8+dim+dim) {
                        String[] words = line.split(" ");
                        graph[lineCounter-9-dim][2] = Integer.valueOf(words[1]);
                    }
                    lineCounter++;
                }
                sc.close();     //closes the scanner

                FileWriter myWriter = new FileWriter("resources/"+pathname.substring(2));
                myWriter.write(dim+" "+capacity+"\n");
                for (int i=0; i<dim; i++){
                    myWriter.write(graph[i][0]+" "+graph[i][1]+" "+graph[i][2]+"\n");
                }
                myWriter.close();
            }
            
        }
        catch(IOException e) {  
            e.printStackTrace();  
        }
    }
}
