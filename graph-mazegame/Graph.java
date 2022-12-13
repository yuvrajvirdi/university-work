import java.util.Iterator;
import java.util.ArrayList;

public class Graph {
    // num of vertices
    private int n;
    // array of vertices
    private Node[] vertices;
    // adjacency list
    private ArrayList<ArrayList<Edge>> adjList;
 
    /**
     * constructor
     * @param n
     */
    public Graph(int n) {
        this.n = n;
        vertices = new Node[this.n];
        adjList = new ArrayList<ArrayList<Edge>>();
        for (int i = 0; i < this.n; i++) {
            vertices[i] = new Node(i);
            adjList.add(new ArrayList<Edge>());
        }
    }

    /**
     * gets node
     * @param id
     * @return node with id
     * @throws GraphException
     */
    public Node getNode(int id) throws GraphException {
        for (int i = 0; i < n; i++) {
            if (vertices[i].getId() == id) {
                return vertices[i];
            }
        }
        throw new GraphException("Node not found");
    }

    /**
     * check if node exists
     * @param u
     * @return boolean
     */
    private boolean exists(Node u) {
        for (int i = 0; i < n; i++) {
            if (vertices[i].getId() == u.getId()) {
                return true;
            }
        }
        return false;
    }

    /**
     * gets index of node
     * @param u
     * @return index of node
     */
    private int getIndex(Node u) {
        for (int i = 0; i < n; i++) {
            if (vertices[i].getId() == u.getId()) {
                return i;
            }
        }
        return -1;
    }

    /**
     * checks if two edges are the same
     * @param e1
     * @param e2
     * @return boolean 
     */
    private boolean sameEdges(Edge e1, Edge e2) {
        if (e1.getType().compareTo(e2.getType()) == 0 &&  
        ((e1.firstNode().getId() == e2.firstNode().getId() && e1.secondNode().getId() == e2.secondNode().getId()) || 
        (e1.firstNode().getId() == e2.secondNode().getId() && e1.secondNode().getId() == e2.firstNode().getId())))
            return true;
        else return false;
    }

    /**
     * adds edge between u and v
     * @param u
     * @param v
     * @param edgeType
     * @throws GraphException
     */
    public void addEdge(Node u, Node v, String edgeType) throws GraphException {
        if (!exists(u) || !exists(v)) throw new GraphException("Node DNE");
        else {
            int uIndex = getIndex(u), vIndex = getIndex(v);
            Edge newEdge = new Edge(u, v, edgeType);
            // if edge exists
            // only need to search one node's edges in the adjacency list
            for (int i = 0; i < adjList.get(uIndex).size(); i++) {
                if (sameEdges(newEdge, adjList.get(uIndex).get(i))) 
                    throw new GraphException("Edge exists");
            }
            adjList.get(uIndex).add(newEdge);
            adjList.get(vIndex).add(newEdge);
        }
    }

    /**
     * makes an iterator of incident edges to u
     * @param u
     * @return iterator 
     */
    public Iterator incidentEdges(Node u) throws GraphException{
        if (!exists(u)) throw new GraphException("Node DNE");
        else {
            int uIndex = getIndex(u);
            if (adjList.get(uIndex).size() == 0) return null;
            else {
                ArrayList<Edge> edges = new ArrayList<Edge>();
                for (int i = 0; i < adjList.get(uIndex).size(); i++)
                    edges.add(adjList.get(uIndex).get(i));
                return edges.iterator();
            }
        }
    }

    /**
     * gets edge between u and v, if it exists
     * @param u
     * @param v
     * @return
     * @throws GraphException
     */
    public Edge getEdge(Node u, Node v) throws GraphException {
        if (!exists(u) || !exists(v)) throw new GraphException("Node DNE");
        else {
            int uIndex = getIndex(u);
            for (int i = 0; i < adjList.get(uIndex).size(); i++) {
                Edge edge = adjList.get(uIndex).get(i);
                if ((edge.firstNode().getId() == u.getId() && edge.secondNode().getId() == v.getId()) || 
                (edge.firstNode().getId() == v.getId() && edge.secondNode().getId() == u.getId())) {
                    return edge;
                }
            }
            throw new GraphException("Edge DNE");
        }
    }

    /**
     * checks if u and v are adjacent
     * @param u
     * @param v
     * @return boolean
     */
    public Boolean areAdjacent(Node u, Node v) throws GraphException{
        if (!exists(u) || !exists(v)) throw new GraphException("Node DNE");
        else {
            try {
                Edge edge = getEdge(u, v);
                return true;
            } catch (GraphException err) {
                return false;
            }
        }
    }
}
