package apps;

import structures.*;
import java.util.ArrayList;

public class MST {
	
	/**
	 * Initializes the algorithm by building single-vertex partial trees
	 * 
	 * @param graph Graph for which the MST is to be found
	 * @return The initial partial tree list
	 */
public static PartialTreeList initialize(Graph graph) {
		
		PartialTreeList partialList = new PartialTreeList(); 		
		for (int i = 0; i<graph.vertices.length; i++)
		{ 
			Vertex ver = graph.vertices[i];
			PartialTree parTree = new PartialTree(ver); 		
			Vertex.Neighbor verNeighbor = graph.vertices[i].neighbors;		
			MinHeap<PartialTree.Arc> P = parTree.getArcs();		
		while (verNeighbor != null)
		{		
			PartialTree.Arc D = new PartialTree.Arc(graph.vertices[i], verNeighbor.vertex , verNeighbor.weight);			
			P.insert(D);
			if (verNeighbor.next == null )
			{
				break;
			}
			verNeighbor = verNeighbor.next;		
			if (verNeighbor.vertex == graph.vertices[i])
			{
				verNeighbor = verNeighbor.next;
			}		
		}					
		partialList.append(parTree); 
		}	
			return partialList;				
	}

	/**
	 * Executes the algorithm on a graph, starting with the initial partial tree list
	 * 
	 * @param ptlist Initial partial tree list
	 * @return Array list of all arcs that are in the MST - sequence of arcs is irrelevant
	 */
public static ArrayList<PartialTree.Arc> execute(Graph graph, PartialTreeList ptlist) {
	
		ArrayList<PartialTree.Arc> output = new ArrayList<PartialTree.Arc>();
		int i = ptlist.size();
		while(i>1)	
		{
		PartialTree parTree = ptlist.remove();	
		if(parTree==null)
		{
			break;
		}
		MinHeap<PartialTree.Arc> mHeap = parTree.getArcs();		
		PartialTree.Arc maxP= mHeap.deleteMin();
		Vertex v2 = maxP.v2;
		Vertex v1 = parTree.getRoot();				
		if(v1==v2 || v1==v2.parent)
		{
			maxP = mHeap.deleteMin();
			v2= maxP.v2.parent;
		}
		output.add(maxP);	
		PartialTree parTree1 = ptlist.removeTreeContaining(v2);
		if(parTree1==null)
		{
			continue;
		}
		parTree1.getRoot().parent = parTree.getRoot();
		parTree1.merge(parTree);
		ptlist.append(parTree1);		
		i--;
		}
		return output;
		}
}
