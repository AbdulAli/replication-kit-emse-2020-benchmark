import java.util.ArrayList;

public class Configuration {

	int windowSize;
	ArrayList<Project> tr = new ArrayList<Project>();
	ArrayList<Project> test = new ArrayList<Project>();

	public ArrayList<Project> getTr() {
		return tr;
	}

	public void setTr(ArrayList<Project> tr) {
		this.tr = tr;
	}

	public ArrayList<Project> getTest() {
		return test;
	}

	public void setTest(ArrayList<Project> test) {
		this.test = test;
	}

	public int getWindowSize() {
		return windowSize;
	}

	public void setWindowSize(int windowSize) {
		this.windowSize = windowSize;
	}

}
