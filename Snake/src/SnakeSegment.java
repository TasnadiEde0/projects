public class SnakeSegment {

    private int position_x;
    private int position_y;
    private int velocity_x;
    private int velocity_y;

    public SnakeSegment(int position_x, int position_y, int velocity_x, int velocity_y) {
        this.position_x = position_x;
        this.position_y = position_y;
        this.velocity_x = velocity_x;
        this.velocity_y = velocity_y;
    }

    public int getVelocity_x() {
        return velocity_x;
    }
    public int getVelocity_y() {
        return velocity_y;
    }
    public int getPosition_x() {
        return position_x;
    }
    public int getPosition_y() {
        return position_y;
    }

    public void setVelocity_x(int velocity_x) {
        this.velocity_x = velocity_x;
    }
    public void setVelocity_y(int velocity_y) {
        this.velocity_y = velocity_y;
    }
    public void setPosition_x(int position_x) {
        this.position_x = position_x;
    }
    public void setPosition_y(int position_y) {
        this.position_y = position_y;
    }

}
