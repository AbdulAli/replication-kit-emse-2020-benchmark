import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class LoadingTrainingSet {

	static int totalDir = 0;
	static int totalProjects = 0;
	static MonthData mData = new MonthData();
	static ArrayList<MonthData> mDataList = new ArrayList<MonthData>(0);

	public static void main(String[] args) throws IOException  {
		String home = "";
		String mainDirectoryPath = "";
		
		File dir = new File(System.getProperty("user.home") + "/Desktop/CMPUT663/JurekzoBalanced" );
		loadFilePathsInMemory(dir);

		sort(mDataList);
		mDataList.remove(mDataList.size() - 1);

		/*
		 * for (MonthData md : mDataList) { System.out.println("Directory: " +
		 * md.getMonthName()); System.out.println("============"); for (Project p :
		 * md.getProjects()) { System.out.println("Project: " + p.getName()); }
		 * System.out.println("============"); }
		 */

		System.out.println("Total Directories: " + totalDir);
		System.out.println("Total Projects: " + totalProjects);

		generateConfigs();
		printConfigs();
	}

	private static void printConfigs() {

		try {

			ConfigurationGeneration.printEEConfig();
			//ConfigurationGeneration.printPEConfig();
			//ConfigurationGeneration.printEPConfig();
			//ConfigurationGeneration.printPPConfig();
			//ConfigurationGeneration.printStats();
		} catch (IOException e) {
			System.out.println("Issues in filing");
			e.printStackTrace();
		}
	}
	
	private static void generateConfigs() {
		//ConfigurationGeneration.generateEPConfig(mDataList);
		ConfigurationGeneration.generateEEConfig(mDataList);
		//ConfigurationGeneration.generatePEConfig(mDataList);
		//ConfigurationGeneration.generatePPConfig(mDataList);
	}

	public static void loadFilePathsInMemory(File file) throws IOException {

		File[] children = file.listFiles();
		for (File child : children) {
			String name = child.getName();
			String path = child.getPath();

			if (child.isDirectory()) {
				mData.setMonthName(name);
				totalDir++;
				loadFilePathsInMemory(child);
			} else {
				if(!name.contains(".csv")) continue;
				totalProjects++;
				Project p = new Project();
				p.setName(name);
				p.setPath(path);
				mData.getProjects().add(p);
			}
		}
		mDataList.add(mData);
		mData = new MonthData();
	}
	

	private static void sort(ArrayList<MonthData> mDataList) {

		for (int i = 0; i < mDataList.size(); i++) {
			for (int j = i + 1; j < mDataList.size() - 1; j++) {
				if (mDataList.get(i).getMonthName().compareTo(mDataList.get(j).getMonthName()) > 0) {
					MonthData temp = mDataList.get(i);
					mDataList.set(i, mDataList.get(j));
					mDataList.set(j, temp);
				}
			}
		}
	}
}