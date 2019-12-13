package spanning.tree.fx;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.ArrayList;


/**
 *
 * @author Amr
 */
public class SpanningAlgorithm {
     ArrayList< ArrayList<ArrayList< Integer>>> resultSet;
    private int numberOfNodes;
     // static variables
    private ArrayList<pair> edgeList;
    private ArrayList<pair> currentChosenEdges;

    public void backtrack(int index) {
        // base cases
        if (currentChosenEdges.size() + 1 == numberOfNodes) {
            DSU dsu = new DSU(numberOfNodes);

            // zero indexing
            for (int i = 0; i < currentChosenEdges.size(); i++) {
                dsu.mrg(currentChosenEdges.get(i).first, currentChosenEdges.get(i).second);
            }

            if (dsu.size(0) == numberOfNodes) {
                ArrayList<ArrayList<Integer>> treeAdjacencyList = new ArrayList<>(numberOfNodes);
                for (int i = 0; i < numberOfNodes; i++) {
                    ArrayList<Integer> a1 = new ArrayList<>();
                    for (int j = 0; j < numberOfNodes; j++) {
                        a1.add(-1);
                    }
                    treeAdjacencyList.add(a1);
                }
                for (int i = 0; i < currentChosenEdges.size(); i++) {
                    treeAdjacencyList.get(currentChosenEdges.get(i).first).add(currentChosenEdges.get(i).second);
                    treeAdjacencyList.get(currentChosenEdges.get(i).second).add(currentChosenEdges.get(i).first);
                }
                resultSet.add(treeAdjacencyList);
            }
            return;
        }
        if (index >= edgeList.size()) {
            return;
        }

        // Don't use
        backtrack(index + 1);

        // use
        currentChosenEdges.add(edgeList.get(index));
        backtrack(index + 1);
        currentChosenEdges.remove(currentChosenEdges.size() - 1);
    }

    public  ArrayList< ArrayList<ArrayList< Integer>>>  Solve (ArrayList<edge>edges){
        edgeList = new ArrayList<>();
        resultSet = new ArrayList<>();
        currentChosenEdges = new ArrayList<>();
        int first, second;
        for (int i = 0; i < edges.size(); i++) {
            first = edges.get(i).getFrom().getMyNumber();
            second = edges.get(i).getTo().getMyNumber();       
            edgeList.add(make_pair(first, second)); 
            numberOfNodes = Math.max(first, second); 
        }
        // becasue nodes counted from zero 
         numberOfNodes++;   
        //Call backtrack
        backtrack(0);
        // print trees
        for (int i = 0; i < resultSet.size(); i++) {
            System.out.println("tree " + i);
            for (int j = 0; j < resultSet.get(i).size(); j++) {
                System.out.println("node " + j);
                for (int k = 0; k < resultSet.get(i).get(j).size(); k++) {
                    if (resultSet.get(i).get(j).get(k) != -1) {
                        System.out.println(resultSet.get(i).get(j).get(k) + " ");
                    }
                }
            }

        }
        
        return resultSet ;
    }

    
    private  pair make_pair(int first, int second) {
        pair pair = new pair();
        pair.first = first;
        pair.second = second;
        return pair;
    }

}

class DSU {

    int p[], sz[];

    DSU(int n) {
        p = new int[n];
        sz = new int[n];
        for (int i = 0; i < n; i++) {
            p[i] = i;
            sz[i] = 1;
        }
    }

    int par(int x) {
        if ( p[x] == x) {
            return x;
        }
        return p[x] = par(p[x]);
    }

    void mrg(int px, int py) {
        px = par(px);
        py = par(py);
        if (px == py) {
            return;
        }
        if (sz[px] > sz[py]) {
            sz[px] += sz[py];
            p[py] = px;
        } else {
            p[px] = py;
            sz[py] += sz[px];
        }
    }

    int size(int x) {
        return sz[par(x)];
    }
};

class pair {

    int first;
    int second;
}
