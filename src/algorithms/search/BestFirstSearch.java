package algorithms.search;

import java.util.Comparator;
import java.util.PriorityQueue;

public class BestFirstSearch extends BreadthFirstSearch {

    private class AStateComparator implements Comparator<AState>{

        public int compare(AState s1,AState s2)
        {
            int cost1 = s1.getPriority();
            int cost2 = s2.getPriority();

            if (s1 == s2 )
                return 0;
            else if (cost1 > cost2)
                return 1;
            else
                return -1;
        }
    }

    public BestFirstSearch() {
        this.queue = new PriorityQueue<AState>( new AStateComparator());
        this.visitedNodes = 0;
    }

    @Override
    public String getName() {
        return "Best First Search";
    }

}
