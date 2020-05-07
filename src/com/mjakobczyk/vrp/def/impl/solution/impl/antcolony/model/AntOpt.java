package com.mjakobczyk.vrp.def.impl.solution.impl.antcolony.model;

/**
 * Optimized version of {@link com.mjakobczyk.vrp.def.impl.solution.impl.antcolony.model.Ant}.
 */
public class AntOpt {

    public int trailSize;
    public int trail[];
    public boolean visited[];

    public AntOpt(int tourSize) {
        this.trailSize = tourSize;
        this.trail = new int[tourSize];
        this.visited = new boolean[tourSize];
    }

    public void visitCity(int currentIndex, int city) {
        trail[currentIndex + 1] = city;
        visited[city] = true;
    }

    public boolean visited(int i) {
        return visited[i];
    }

    public double trailLength(double graph[][]) {
        double length = graph[trail[trailSize - 1]][trail[0]];
        for (int i = 0; i < trailSize - 1; i++) {
            length += graph[trail[i]][trail[i + 1]];
        }
        return length;
    }

    public void clear() {
        for (int i = 0; i < trailSize; i++)
            visited[i] = false;
    }
}
