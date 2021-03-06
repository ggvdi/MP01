package datastructures.graph;

import datastructures.Queue;

public class UndirectedWeightedMatrixGraph extends WeightedGraph {
	private int[][] weightMatrix = new int[0][0];
	private String[] vertexNames = new String[0];
	private static final int NO_EDGE = Integer.MIN_VALUE;
	
	private int getIndex(String vertexName) {
		for (int i = 0; i < vertexNames.length; ++i) {
			if (vertexNames[i].equals(vertexName)) return i;
		}
		return -1;
	}
	
	private void resizeMatrices(int newSize) {
		String[] n_vertexNames = new String[newSize];
		int[][] n_weightMatrix = new int[newSize][newSize];
	
		int size = Math.min(vertexNames.length, newSize);
		for (int i = 0; i < size; ++i) {
			n_vertexNames[i] = vertexNames[i];
			for (int j = 0; j < size; ++j) {
				n_weightMatrix[i][j] = weightMatrix[i][j];
			}
		}
		
		weightMatrix = n_weightMatrix;
		vertexNames = n_vertexNames;
	}
	
	private void removeItem(int index) {
		int size = vertexNames.length;
		
		for (int i = index; i + 1 < size; ++i) {
			vertexNames[i] = vertexNames[i + 1];
			weightMatrix[i] = weightMatrix[i + 1];
		}
		
		for (int i = 0; i < size; ++i) {
			for (int j = index; j + 1 < size; ++j) {
				weightMatrix[i][j] = weightMatrix[i][j + 1];
			}
		}
		
		resizeMatrices(size - 1);
	}
	
	public String[] getVertices() {
		return vertexNames;
	}
	
	public String[] getAdjacentVertices(String a) {
		int ix = getIndex(a);
		int size = 0;
		for (int i = 0; i < vertexNames.length; ++i) {
			if (weightMatrix[ix][i] != NO_EDGE) size++;
		}
		
		String[] adjacents = new String[size];
		
		int a = 0;
		for (int i = 0; i < vertexNames.length; ++i) {
			if (weightMatrix[ix][i] != NO_EDGE) adjacents[a++] = vertexNames[i];
		}
	}
	
	public int getVertexCount() {
		return vertexNames.length;
	}
	
	public int getEdgeCount() {
		return 0;
	}
	
	public boolean isConnected(String a, String b);
	public boolean isAdjacent(String a, String b);
	
	public void addVertex(String a) throws Exception {
		if (getIndex(a) == -1) throw new Exception("ERROR: THERE IS ALREADY A VERTEX NAMED \"" + a + "\"; VERTICES CANNOT HAVE THE SAME NAME");
		int size = vertexNames.length;
		
		resizeMatrices(size + 1);
		
		vertexNames[size] = a;
	}
	
	public void removeVertex(String a) throws Exception {
		int ix = getIndex(a);
		
		if (ix == -1) throw new Exception("ERROR: VERTEX \"" +a+ "\" NOT FOUND");
		
		removeItem(ix);
	}
	
	public void addEdge(String a, String b) {
		addEdge(a,b,0);
	}
	
	public void addEdge(String a, String b, int weight) {
		int ix1 = getIndex(a);
		int ix2 = getIndex(b);
		
		weightMatrix[ix1][ix2] = weight;
		weightMatrix[ix2][ix1] = weight;
	}
	
	public void removeEdge(String a, String b){
		int ix1 = getIndex(a);
		int ix2 = getIndex(b);
		
		weightMatrix[ix1][ix2] = NO_EDGE;
		weightMatrix[ix2][ix1] = NO_EDGE;
	}
	
	public String[] depthFirstTraversal(String startingVertex) {
		int ix = getIndex(startingVertex);
		
		ArrayList<String> closedSet = new ArrayList<String>();
		closedSet.add(startingVertex);
		depthFirstTraversal(ix, closedSet);
		
		String[] ret = new String[closedSet.size()];
		
		for (int i = 0; i < ret.length; ++i) {
			ret[i] = closedSet.get(i);
		}
		
		return ret;
	}
	
	private void depthFirstTraversal(int IX, ArrayList<String> closedSet) {
		for (int i = 0; i < vertexNames.length; ++i) {
			if (weightMatrix[IX][i] != NO_EDGE) {
				closedSet.add(vertexNames[i]);
				depthFirstTraversal(i, closedSet);
			}
		}
	}
	
	public String[] breadthFirstTraversal(String startingVertex) {
		Queue<String> q = new Queue<String>();
		ArrayList<String> closedSet = new ArrayList<String>();
		q.enqueue(startingVertex);
		
		String c;
		while ( (c = q.dequeue()) != null) {
			closedSet.add(c);
			int ix = getIndex(c);
			for (int i = 0; i < vertexNames.length; ++i) {
				if (weightMatrix[ix][i] != NO_EDGE) {
					if (!closedSet.contains(vertexNames[i]) {
						q.enqueue(vertexNames[i]);
					}
				}
			}
		}
		
		String[] ret = new String[closedSet.size()];
		
		for (int i = 0; i < ret.length; ++i) {
			ret[i] = closedSet.get(i);
		}
		
		return ret;
	}
}