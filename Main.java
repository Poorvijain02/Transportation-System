import java.awt.*;
import java.util.Map;
import javax.swing.*; 
public class Main extends JFrame {
private Graph graph;
private NetworkPanel panel;
private JTextArea output;
private JComboBox<String> selector;

public Main() {
    graph = new Graph();

    setTitle("Transportation Network");
    setSize(1200, 700);
    setLayout(new BorderLayout());

    panel = new NetworkPanel(graph);
    add(panel, BorderLayout.CENTER);

    add(controlPanel(), BorderLayout.WEST);
    add(outputPanel(), BorderLayout.EAST);

    sample();

    setVisible(true);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
}

private JPanel controlPanel() {
    JPanel p = new JPanel();
    p.setPreferredSize(new Dimension(200, 600));

    selector = new JComboBox<>();
    p.add(selector);

    JButton d = new JButton("Run Dijkstra");
    d.addActionListener(e -> {
        Map<String, Integer> res = graph.dijkstra((String) selector.getSelectedItem());
        output.setText("Dijkstra:\n");
        for (String c : res.keySet())
            output.append(c + " : " + res.get(c) + "\n");
    });

    JButton m = new JButton("Run MST");
    m.addActionListener(e -> {
        Graph.MSTResult mst = graph.primMST((String) selector.getSelectedItem());
        output.setText("MST:\n");
        for (Graph.MSTEdge e1 : mst.edges)
            output.append(e1.from + "-" + e1.to + ":" + e1.weight + "\n");
        output.append("Total = " + mst.totalCost);
    });

    p.add(d);
    p.add(m);
    return p;
}

private JPanel outputPanel() {
    JPanel p = new JPanel(new BorderLayout());
    p.setPreferredSize(new Dimension(300, 600));

    output = new JTextArea();
    p.add(new JScrollPane(output));

    return p;
}

private void sample() {
    graph.addCity("A");
    graph.addCity("B");
    graph.addCity("C");
    graph.addCity("D");

    graph.addRoad("A", "B", 4);
    graph.addRoad("A", "C", 2);
    graph.addRoad("B", "D", 5);
    graph.addRoad("C", "D", 8);

    for (String c : graph.getCities())
        selector.addItem(c);
}

public static void main(String[] args) {
    new Main();
}
}