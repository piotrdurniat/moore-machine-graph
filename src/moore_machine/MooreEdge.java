package moore_machine;

public class MooreEdge extends Edge {

    private String input;

    MooreEdge(MooreNode startNode, MooreNode endNode, String input) {
        super(startNode, endNode);
        this.input = input;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    @Override
	public String toString() {
		return startNode + "- " + input + " ->"  + endNode;
	}
}
