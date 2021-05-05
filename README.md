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
To be honest, I couldn't search of any source codes (in this domain) about this so I implement this searching based on several research papers that give pseudocode I had read through.
The pseudocode provided is somehow quite ambigious at some parts, so those parts I implement it just by trying and based on my understanding, thus may have mistakes where I didn't notice.


## Getting Started

Welcome to the VS Code Java world. Here is a guideline to help you get started to write Java code in Visual Studio Code.

## Folder Structure

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources
- `resources`: the folder to maintain resources

## Dependency Management

The `JAVA DEPENDENCIES` view allows you to manage your dependencies. More details can be found [here](https://github.com/microsoft/vscode-java-pack/blob/master/release-notes/v0.9.0.md#work-with-jar-files-directly).

