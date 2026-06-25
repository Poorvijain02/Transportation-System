import java.util.*; 
import java.awt.Point; 
public class Graph { 
    private Map<String, List<Edge>> adjacencyList = new HashMap<>(); 
    private Map<String, Point> cityPositions = new HashMap<>(); 
    private Random random = new Random();
static class Edge {
    String destination;
    int weight;
    Edge(String dest, int w) {
        destination = dest;
        weight = w;
    }
}

public boolean addCity(String name) {
    if (adjacencyList.containsKey(name)) return false;
    adjacencyList.put(name, new ArrayList<>());
    cityPositions.put(name, new Point(100 + random.nextInt(600), 100 + random.nextInt(400)));
    return true;
}

public boolean addRoad(String from, String to, int dist) {
    adjacencyList.get(from).add(new Edge(to, dist));
    adjacencyList.get(to).add(new Edge(from, dist));
    return true;
}

public Set<String> getCities() {
    return adjacencyList.keySet();
}

public List<Edge> getRoads(String city) {
    return adjacencyList.get(city);
}

public Point getCityPosition(String city) {
    return cityPositions.get(city);
}

public void setCityPosition(String city, Point p) {
    cityPositions.put(city, p);
}

// DIJKSTRA
public Map<String, Integer> dijkstra(String source) {
    Map<String, Integer> dist = new HashMap<>();
    Set<String> visited = new HashSet<>();

    for (String city : adjacencyList.keySet())
        dist.put(city, Integer.MAX_VALUE);

    dist.put(source, 0);

    PriorityQueue<Pair> pq = new PriorityQueue<>((a, b) -> a.dist - b.dist);
    pq.offer(new Pair(source, 0));

    while (!pq.isEmpty()) {
        Pair curr = pq.poll();
        if (visited.contains(curr.city)) continue;
        visited.add(curr.city);

        for (Edge e : adjacencyList.get(curr.city)) {
            int newDist = dist.get(curr.city) + e.weight;
            if (newDist < dist.get(e.destination)) {
                dist.put(e.destination, newDist);
                pq.offer(new Pair(e.destination, newDist));
            }
        }
    }
    return dist;
}

static class Pair {
    String city;
    int dist;
    Pair(String c, int d) {
        city = c;
        dist = d;
    }
}

// PRIM MST
public MSTResult primMST(String start) {
    MSTResult result = new MSTResult();
    Set<String> visited = new HashSet<>();
    PriorityQueue<MSTEdge> pq = new PriorityQueue<>((a, b) -> a.weight - b.weight);

    visited.add(start);
    addEdges(start, visited, pq);

    while (!pq.isEmpty()) {
        MSTEdge edge = pq.poll();
        if (visited.contains(edge.to)) continue;

        visited.add(edge.to);
        result.edges.add(edge);
        result.totalCost += edge.weight;

        addEdges(edge.to, visited, pq);
    }
    return result;
}

private void addEdges(String city, Set<String> visited, PriorityQueue<MSTEdge> pq) {
    for (Edge e : adjacencyList.get(city)) {
        if (!visited.contains(e.destination)) {
            pq.offer(new MSTEdge(city, e.destination, e.weight));
        }
    }
}

public static class MSTResult {
    public List<MSTEdge> edges = new ArrayList<>();
    public int totalCost = 0;
}

public static class MSTEdge {
    String from, to;
    int weight;
    MSTEdge(String f, String t, int w) {
        from = f;
        to = t;
        weight = w;
    }
}
} 