import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * This class is used to generate output.csv
 * Deffered: Now output.csv is generated through replicationKit
 */
public class ParsingResultFiles {

	public static String windowsize = null;
	public static String configuration = null;

	public static int totalFiles = 0;

	public static void main(String[] args) throws IOException {

		File dir = new File(System.getProperty("user.home") + "/Desktop/CMPUT663/663Dataset");
		// writeToCsv("technique ,configuration, windowSize,trVersions,testVersions,
		// training ,testing,tpRate,fpRate ,precision ,recall,fscore, mcc , roc, prc");

		writeToCsv("technique,configuration,windowSize,tpRate,fpRate,precision,recall,fscore,mcc,roc,prc,time");

			loadAPKsFromDir(dir);

	}

	public static void loadAPKsFromDir(File file) throws IOException {

		File[] children = file.listFiles();
		for (File child : children) {
			String name = child.getName();
			totalFiles++;
			/*
			 * if (name.contains(".apk")) { totalAPKs++;
			 * //Config.apkPathMap.put(child.getName().split(".apk")[0], file.getPath());
			 * //System.out.println(child.getName().split(".apk")[0] + "::::" +
			 * file.getPath()); }
			 */

			if (child.isDirectory()) {

				if (name.contains("Configurations") || name.contains("Dataset") || name.contains(".git")
						|| name.contains("RData") ) {
					continue;
				}
				if (name.contains("Configuration")) {
					configuration = name.split("Configuration")[1];
				} else if (name.contains("ResultK=")) {
					windowsize = name.split("ResultK=")[1];
				}

				loadAPKsFromDir(child);
			} else {
				if (name.contains("output")|| name.contains("README")) {
					continue;
				}
				readTextFile(child.getPath(), name);
			}
		}
	}

	private static void readTextFile(String path, String fileName) throws IOException {

		
		
		try{int time = Integer.parseInt(fileName.split(".txt")[0]);
		
//		if(configuration.equals("1") || configuration.equals("3")) {
//			int k = Integer.parseInt(windowsize);
//			time = time + (k -1);
//		}
		
		String technique = null;
		if (path.contains("Amasaki")) {
			technique = "Amasaki";
		} else if (path.contains("Ma2012")) {
			technique = "Ma2012";
		} else if (path.contains("Watanabe")) {
			technique = "Watanabe";
		} else if (path.contains("Camruz")) {
			technique = "Camruz";
		} else if (path.contains("Nam2015")) {
			technique = "Nam2015";
		}

		if(technique == null) {
			System.out.println(path);
		}
		
		File file = new File(path);

		BufferedReader br = new BufferedReader(new FileReader(file));
		
		String st;
		String tpRate = null;
		String fpRate = null;
		String precision = null;
		String recall = null;
		String fscore = null;
		String mcc = null;
		String roc = null;
		String prc = null;

		// 23456789
		while ((st = br.readLine()) != null) {
			if (st.contains("Weighted Avg.")) {
				System.out.println(st);
				String[] tokens = st.split("\\s *");
				tpRate = tokens[2];
				fpRate = tokens[3];
				precision = tokens[4];
				recall = tokens[5];
				fscore = tokens[6];
				mcc = tokens[7];
				roc = tokens[8];
				prc = tokens[9];
			}
		}
		
		String line = technique + "," + configuration + "," + windowsize + "," + tpRate + "," + fpRate + "," + precision
				+ "," + recall + "," + fscore + "," + mcc + "," + roc + "," + prc+ "," + time;

		writeToCsv(line);
		}
		catch(java.lang.NumberFormatException e) {
			System.out.println(e);
		}

	}

	/*
	 * private static void readTextFile(String path, String fileName) throws
	 * IOException {
	 * 
	 * String technique = null; if (path.contains("Amasaki")) { technique =
	 * "Amasaki"; } else if (path.contains("Ma2012")) { technique = "Ma2012"; } else
	 * if (path.contains("Watnabe")) { technique = "Watnabe"; } else if
	 * (path.contains("Camruz")) { technique = "Camruz"; } else if
	 * (path.contains("Nam2015")) { technique = "Nam2015"; }
	 * 
	 * String parsingName = fileName;// "X-File24 to 25.csv-Z-File26 to 27.csv.txt";
	 * parsingName = parsingName.trim(); // System.out.println(path);
	 * System.out.println(parsingName); parsingName =
	 * parsingName.split(".csv.txt")[0]; // X-File24 to 25.csv-Z-File26 to 27
	 * 
	 * String set[] ; if(parsingName.contains(".csv-")) { set =
	 * parsingName.split(".csv-"); // X-File24 to 25, Z-File26 to 27 }else { set =
	 * parsingName.split(".csv..."); //[g-File1to37, File38to42] }
	 * 
	 * 
	 * String training = set[0].split("-File")[1]; // 24 to 25 if
	 * (training.contains("to")) training = training.split("to")[0] + "_" +
	 * training.split("to")[1];
	 * 
	 * String testing = set[1].split("-File")[1]; // 26 to 27
	 * 
	 * if (testing.contains("to")) testing = testing.split("to")[0] + "_" +
	 * testing.split("to")[1];
	 * 
	 * System.out.println(training); System.out.println(testing);
	 * 
	 * File file = new File(path);
	 * 
	 * BufferedReader br = new BufferedReader(new FileReader(file));
	 * 
	 * String st;
	 * 
	 * Double trainingSize; if(training.contains("_")) { trainingSize = (new
	 * Double(training.split("_")[1]) - new Double(training.split("_")[0]))+1; }else
	 * { trainingSize = (double) 1; }
	 * 
	 * Double testSize; if(testing.contains("_")) { testSize = (new
	 * Double(testing.split("_")[1]) - new Double(testing.split("_")[0]))+1; }else {
	 * testSize = (double) 1; }
	 * 
	 * String tpRate = null; String fpRate = null; String precision = null; String
	 * recall = null; String fscore = null; String mcc = null; String roc = null;
	 * String prc = null;
	 * 
	 * // 23456789 while ((st = br.readLine()) != null) { if
	 * (st.contains("Weighted Avg.")) { System.out.println(st); String[] tokens =
	 * st.split("\\s *"); tpRate = tokens[2]; fpRate = tokens[3]; precision =
	 * tokens[4]; recall = tokens[5]; fscore = tokens[6]; mcc = tokens[7]; roc =
	 * tokens[8]; prc = tokens[9]; } }
	 * 
	 * String line = technique + "," + configuration + "," + windowsize + "," +
	 * trainingSize + "," + testSize + "," + training + "," + testing + "," + tpRate
	 * + "," + fpRate + "," + precision + "," + recall + "," + fscore + "," + mcc +
	 * "," + roc + "," + prc;
	 * 
	 * writeToCsv(line);
	 * 
	 * }
	 */

	private static void writeToCsv(String line) {
		FileWriter fw;
		try {
			fw = new FileWriter(System.getProperty("user.home") + "/Desktop/CMPUT663/663Dataset/output.csv", true);
			StringBuilder sb = new StringBuilder();
			sb.append("\n");
			sb.append(line);
			fw.write(sb.toString());
			fw.close();
		} catch (IOException e) {
			System.out.println("Issue with writing csv");
			e.printStackTrace();
		}
	}

}
