import java.awt.*;

public class Message {

    private Point nextPos;

    public Message(Point nextPos) {
        this.nextPos = nextPos;
    }

    public Point getNextPos() {
        return nextPos;
    }
}
