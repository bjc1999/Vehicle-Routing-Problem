## Getting Started

Welcome to the VS Code Java world. Here is a guideline to help you get started to write Java code in Visual Studio Code.

## Folder Structure

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies
- `resources`: the folder to maintain resources

## Dependency Management

The `JAVA DEPENDENCIES` view allows you to manage your dependencies. More details can be found [here](https://github.com/microsoft/vscode-java-pack/blob/master/release-notes/v0.9.0.md#work-with-jar-files-directly).

## Searching Algorithm
### DFS

The naming is a bit confusing, it should be brute force.
I use permutation to list out all possible combinations and look for the lowest cost combination
This should result in the same as using DFS traversal to travel whole search space
It only can work with small N, for test case with N = 30+ might need to comment out the DFS search in main method

### Greedy search

Nothing special, it work like normal greedy search which always select best move of all possible next move from a current state

### NRPA

Nested Rollout Policy Adaptation
A variant of MCTS.
