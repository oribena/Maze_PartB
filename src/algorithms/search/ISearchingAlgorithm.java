package algorithms.search;

public interface ISearchingAlgorithm {

    Solution solve(ISearchable Is);
    int getNumberOfNodesEvaluated();
    String getName();

}
