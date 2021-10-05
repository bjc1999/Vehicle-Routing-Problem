## Searching Algorithm
### Max Execution Time

The maximum execution time for every search by default is 1 mins.  
If any of the search is searching longer than 1 mins, it will stopped and return current best tour immediately.  

### DFS

The naming is a bit confusing, it should be brute force.  
I use permutation to list out all possible combinations and look for the lowest cost combination.  
This should result in the same as using DFS traversal to travel whole search space.

### Greedy search

Nothing special, it work like normal greedy search which always select best move of all possible next move from a current state

### NRPA

Monte Carlo Tree Search with Nested Rollout Policy Adaptation
This searching is implemented based on several research papers that give pseudocode I had read through.
The pseudocode provided is somehow quite ambigious at some parts, so those parts I implement it just by trying and based on my understanding, thus may have mistakes where I didn't notice.

## Folder Structure
```
.  
├── resources    
|   ├── nx-cy.txt 
|   └── ...  
├── mcts  
|   ├── pseudo-code.txt  
|   └── README.md
├── dfs-bfs    
|   ├── DFS-BFS.md
|   └── README.md
├── src
├── DS Assignment_Delivery Route.pdf 
└── README.md
```
| Folder/File                         | Descriptions                                                                                      |
|:-----------------------------------:|---------------------------------------------------------------------------------------------------|
|`resources`                          |Resource folder that contains all test sets you can play with.                                     |
|`mcts`                               |Folder contains pseudocode for MCTS algorithm and a documentation for MCTS brief explanation.      |
|`dfs-bfs`                            |Folder contains question and dfs or bfs based related clarification.                               |
|`src`                                |Folder contains source codes.                                                                      |
|`DS Assignment_Delivery Route.pdf`   |Original question pdf.                                                                             |
|`README.md`                          |The document you are currently reading.                                                            |


