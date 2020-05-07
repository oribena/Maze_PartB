package algorithms.search;

import java.io.Serializable;

public abstract class AState implements Serializable {
    protected String state;
    private int priority;
    private AState cameFrom;
    private boolean Visited;

    public AState() {
        this.state = "";
        this.priority = 0;
        this.cameFrom = null;
        Visited = false;
    }

    public int getPriority() {
        return priority;
    }

    public AState getCameFrom() {
        return cameFrom;
    }

    public boolean isVisited() {
        return Visited;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setCameFrom(AState cameFrom) {
        this.cameFrom = cameFrom;
    }

    public void setVisited(boolean visited) {
        Visited = visited;
    }

    @Override
    public int hashCode() {
        return state != null ? state.hashCode() : 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (this==obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        AState state1 = (AState)obj;
        return  state != null ? state.equals(state1.state) : state1.state == null;
    }

    @Override
    public String toString() {
        return this.state;
    }
}
