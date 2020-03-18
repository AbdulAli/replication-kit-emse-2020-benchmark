import java.util.ArrayList;

public class MonthData {

	String monthName;
	ArrayList<Project> projects = new ArrayList<Project>(0);

	public String getMonthName() {
		return monthName;
	}

	public void setMonthName(String monthName) {
		this.monthName = monthName;
	}

	public ArrayList<Project> getProjects() {
		return projects;
	}

	public void setProjects(ArrayList<Project> projects) {
		this.projects = projects;
	}
}
