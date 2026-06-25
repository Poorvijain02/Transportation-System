import java.awt.*;
import javax.swing.*; 
public class NetworkPanel extends JPanel { 
    private Graph graph;
public NetworkPanel(Graph graph) {
    this.graph = graph;
    setBackground(Color.WHITE);
}

protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    for (String city : graph.getCities()) {
        Point p1 = graph.getCityPosition(city);

        for (Graph.Edge e : graph.getRoads(city)) {
            Point p2 = graph.getCityPosition(e.destination);
            g.drawLine(p1.x, p1.y, p2.x, p2.y);
            g.drawString(e.weight + " km", (p1.x + p2.x)/2, (p1.y + p2.y)/2);
        }
    }

    for (String city : graph.getCities()) {
        Point p = graph.getCityPosition(city);
        g.setColor(Color.BLUE);
        g.fillOval(p.x - 20, p.y - 20, 40, 40);

        g.setColor(Color.WHITE);
        g.drawString(city, p.x - 5, p.y + 5);
    }
}
}