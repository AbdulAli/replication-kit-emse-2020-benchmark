import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class ConfigurationGeneration {

    static String approach = "Baseline";

    // static String approach = "Log";
    static boolean logTechniqueEnabled = false; // True if applying log on data; else false

    static String pathConfig1 = System.getProperty("user.home") + "/Desktop/CMPUT663/663Dataset/Configurations/"
            + approach + "/Configuration1_ResultsCC/";
    static String pathConfig2 = System.getProperty("user.home") + "/Desktop/CMPUT663/663Dataset/Configurations/"
            + approach + "/Configuration2_ResultsIC/";
    static String pathConfig3 = System.getProperty("user.home") + "/Desktop/CMPUT663/663Dataset/Configurations/"
            + approach + "/Configuration3_ResultsCI/";
    static String pathConfig4 = System.getProperty("user.home") + "/Desktop/CMPUT663/663Dataset/Configurations/"
            + approach + "/Configuration4_ResultsII/";

    static ArrayList<Configuration> EE = new ArrayList<Configuration>(); // Configuration-1
    static ArrayList<Configuration> PE = new ArrayList<Configuration>(); // Configuration-2
    static ArrayList<Configuration> EP = new ArrayList<Configuration>(); // Configuration-3
    static ArrayList<Configuration> PP = new ArrayList<Configuration>(); // Configuration-4


    public static void printStats() throws IOException {

        FileWriter fw = new FileWriter(System.getProperty("user.home") + "/Desktop/CMPUT663/663Dataset/Configurations/stats.csv", true);
        fw.write(stats.toString());
        fw.close();
    }

    public static void printEEConfig() throws IOException {
        int count = 1;
        int k = 0;
        for (Configuration c : EE) {

            File checkDir = new File(pathConfig1 + "DataK=" + c.getWindowSize());
            if (!checkDir.isDirectory()) {
                new File(pathConfig1 + "DataK=" + c.getWindowSize()).mkdirs();
            }

            if (k != c.getWindowSize()) {
                k = c.getWindowSize();
                count = 1;
            }

            String newPath = pathConfig1 + "DataK=" + c.getWindowSize() + "/";
            String trPath = newPath + count + "tr.csv";
            FileWriter trFw = new FileWriter(trPath, true);

            StringBuilder sb = new StringBuilder(
                    "proj,ver,class,wmc,dit,noc,cbo,rfc,lcom,ca,ce,npm,lcom3,loc,dam,moa,mfa,cam,ic,cbm,amc,max_cc,avg_cc,bug\n");
            for (Project p : c.getTr()) {
                writeToBuffer(sb, p.getPath(), logTechniqueEnabled);
            }
            trFw.write(sb.toString());
            trFw.close();
            stats.append("1,"+c.getWindowSize() + "," + count + ",tr," + totalInstances + "," + defectiveInstance + "\n");
            totalInstances = 0;
            defectiveInstance = 0;
            System.out.println("\n>>>>Test set:<<<<\n");

            HashMap<String, ArrayList<Integer>> projectOccurence = new HashMap<String, ArrayList<Integer>>();

            int i=0;
            for (Project p : c.getTest()) {
                if(projectOccurence.get(p.getName())==null) {
                    ArrayList<Integer> temp = new ArrayList<Integer>();
                    temp.add(i);
                    projectOccurence.put(p.getName(), temp);
                }else {
                    projectOccurence.get(p.getName()).add(i);
                }
                i++;
            }

            Iterator hmIterator = projectOccurence.entrySet().iterator();

            int mapEntryIndex = 0;
            while (hmIterator.hasNext()) {
                mapEntryIndex++;
                Map.Entry mapElement = (Map.Entry)hmIterator.next();
                ArrayList<Integer> tempList = ((ArrayList<Integer>)mapElement.getValue());

                sb = new StringBuilder(
                        "proj,ver,class,wmc,dit,noc,cbo,rfc,lcom,ca,ce,npm,lcom3,loc,dam,moa,mfa,cam,ic,cbm,amc,max_cc,avg_cc,bug\n");

                for(int indexOfProject : tempList){
                    ArrayList<Project> testProjects = c.getTest();
                    writeToBuffer(sb, testProjects.get(indexOfProject).getPath(), logTechniqueEnabled);
                }

                String testPath = newPath + count + "typetest_project" + mapElement.getKey();
                FileWriter testFw = new FileWriter(testPath, true);

                testFw.write(sb.toString());
                testFw.close();
                stats.append("1,"+c.getWindowSize() + "," + count + ",test," + totalInstances + "," + defectiveInstance + "\n");
                totalInstances = 0;
                defectiveInstance = 0;
                System.out.println("===========");

            }



            System.out.println("===========");
            System.out.println("Configuration no: " + ++count);
            System.out.println("Window size: " + c.getWindowSize());
            System.out.println("\n>>>>Training set:<<<<\n");
        }
    }

    public static void printPPConfig() throws IOException {

        int count = 1;
        int k = 0;
        for (Configuration c : PP) {

            File checkDir = new File(pathConfig4 + "DataK=" + c.getWindowSize());
            if (!checkDir.isDirectory()) {
                new File(pathConfig4 + "DataK=" + c.getWindowSize()).mkdirs();
            }

            if (k != c.getWindowSize()) {
                k = c.getWindowSize();
                count = 1;
            }

            String newPath = pathConfig4 + "DataK=" + c.getWindowSize() + "/";
            String trPath = newPath + count + "tr.csv";


            FileWriter trFw = new FileWriter(trPath, true);

            StringBuilder sb = new StringBuilder(
                    "proj,ver,class,wmc,dit,noc,cbo,rfc,lcom,ca,ce,npm,lcom3,loc,dam,moa,mfa,cam,ic,cbm,amc,max_cc,avg_cc,bug\n");
            for (Project p : c.getTr()) {
                writeToBuffer(sb, p.getPath(), logTechniqueEnabled);
            }
            trFw.write(sb.toString());
            trFw.close();
            stats.append("4,"+c.getWindowSize() + "," + count + ",tr," + totalInstances + "," + defectiveInstance + "\n");
            totalInstances = 0;
            defectiveInstance = 0;
            System.out.println("\n>>>>Test set:<<<<\n");

            HashMap<String, ArrayList<Integer>> projectOccurence = new HashMap<String, ArrayList<Integer>>();

            int i=0;
            for (Project p : c.getTest()) {
                if(projectOccurence.get(p.getName())==null) {
                    ArrayList<Integer> temp = new ArrayList<Integer>();
                    temp.add(i);
                    projectOccurence.put(p.getName(), temp);
                }else {
                    projectOccurence.get(p.getName()).add(i);
                }
                i++;
            }

            Iterator hmIterator = projectOccurence.entrySet().iterator();

            int mapEntryIndex = 0;
            while (hmIterator.hasNext()) {
                mapEntryIndex++;
                Map.Entry mapElement = (Map.Entry)hmIterator.next();
                ArrayList<Integer> tempList = ((ArrayList<Integer>)mapElement.getValue());

                sb = new StringBuilder(
                        "proj,ver,class,wmc,dit,noc,cbo,rfc,lcom,ca,ce,npm,lcom3,loc,dam,moa,mfa,cam,ic,cbm,amc,max_cc,avg_cc,bug\n");

                for(int indexOfProject : tempList){
                    ArrayList<Project> testProjects = c.getTest();
                    writeToBuffer(sb, testProjects.get(indexOfProject).getPath(), logTechniqueEnabled);
                }

                String testPath = newPath + count + "typetest_project" + mapElement.getKey();
                FileWriter testFw = new FileWriter(testPath, true);

                testFw.write(sb.toString());
                testFw.close();
                stats.append("4,"+c.getWindowSize() + "," + count + ",test," + totalInstances + "," + defectiveInstance + "\n");
                totalInstances = 0;
                defectiveInstance = 0;
                System.out.println("===========");

            }



            System.out.println("===========");
            System.out.println("Configuration no: " + ++count);
            System.out.println("Window size: " + c.getWindowSize());
            System.out.println("\n>>>>Training set:<<<<\n");
        }
    }

    public static void printEPConfig() throws IOException {
        int count = 1;
        int k = 0;
        for (Configuration c : EP) {

            File checkDir = new File(pathConfig3 + "DataK=" + c.getWindowSize());
            if (!checkDir.isDirectory()) {
                new File(pathConfig3 + "DataK=" + c.getWindowSize()).mkdirs();
            }

            if (k != c.getWindowSize()) {
                k = c.getWindowSize();
                count = 1;
            }

            String newPath = pathConfig3 + "DataK=" + c.getWindowSize() + "/";
            String trPath = newPath + count + "tr.csv";
            FileWriter trFw = new FileWriter(trPath, true);

            StringBuilder sb = new StringBuilder(
                    "proj,ver,class,wmc,dit,noc,cbo,rfc,lcom,ca,ce,npm,lcom3,loc,dam,moa,mfa,cam,ic,cbm,amc,max_cc,avg_cc,bug\n");
            for (Project p : c.getTr()) {
                writeToBuffer(sb, p.getPath(), logTechniqueEnabled);
            }
            trFw.write(sb.toString());
            trFw.close();
            stats.append("3,"+c.getWindowSize() + "," + count + ",tr," + totalInstances + "," + defectiveInstance + "\n");
            totalInstances = 0;
            defectiveInstance = 0;

            System.out.println("\n>>>>Test set:<<<<\n");

            HashMap<String, ArrayList<Integer>> projectOccurence = new HashMap<String, ArrayList<Integer>>();

            int i=0;
            for (Project p : c.getTest()) {
                if(projectOccurence.get(p.getName())==null) {
                    ArrayList<Integer> temp = new ArrayList<Integer>();
                    temp.add(i);
                    projectOccurence.put(p.getName(), temp);
                }else {
                    projectOccurence.get(p.getName()).add(i);
                }
                i++;
            }

            Iterator hmIterator = projectOccurence.entrySet().iterator();

            int mapEntryIndex = 0;
            while (hmIterator.hasNext()) {
                mapEntryIndex++;
                Map.Entry mapElement = (Map.Entry)hmIterator.next();
                ArrayList<Integer> tempList = ((ArrayList<Integer>)mapElement.getValue());

                sb = new StringBuilder(
                        "proj,ver,class,wmc,dit,noc,cbo,rfc,lcom,ca,ce,npm,lcom3,loc,dam,moa,mfa,cam,ic,cbm,amc,max_cc,avg_cc,bug\n");

                for(int indexOfProject : tempList){
                    ArrayList<Project> testProjects = c.getTest();
                    writeToBuffer(sb, testProjects.get(indexOfProject).getPath(), logTechniqueEnabled);
                }

                String testPath = newPath + count + "typetest_project" + mapElement.getKey();
                FileWriter testFw = new FileWriter(testPath, true);

                testFw.write(sb.toString());
                testFw.close();
                stats.append("3,"+c.getWindowSize() + "," + count + ",test," + totalInstances + "," + defectiveInstance + "\n");
                totalInstances = 0;
                defectiveInstance = 0;
                System.out.println("===========");

            }



            System.out.println("===========");
            System.out.println("Configuration no: " + ++count);
            System.out.println("Window size: " + c.getWindowSize());
            System.out.println("\n>>>>Training set:<<<<\n");
        }
    }

    public static int totalInstances = 0;
    public static int defectiveInstance = 0;
    public static StringBuilder stats = new StringBuilder("configuration,window,split,type,size,defects\n");

    public static void printPEConfig() throws IOException {
        int count = 1;
        int k = 0;
        for (Configuration c : PE) {

            File checkDir = new File(pathConfig2 + "DataK=" + c.getWindowSize());
            if (!checkDir.isDirectory()) {
                new File(pathConfig2 + "DataK=" + c.getWindowSize()).mkdirs();
            }

            if (k != c.getWindowSize()) {
                k = c.getWindowSize();
                count = 1;
            }

            String newPath = pathConfig2 + "DataK=" + c.getWindowSize() + "/";
            String trPath = newPath + count + "tr.csv";
            FileWriter trFw = new FileWriter(trPath, true);

            StringBuilder sb = new StringBuilder(
                    "proj,ver,class,wmc,dit,noc,cbo,rfc,lcom,ca,ce,npm,lcom3,loc,dam,moa,mfa,cam,ic,cbm,amc,max_cc,avg_cc,bug\n");

            for (Project p : c.getTr()) {
                writeToBuffer(sb, p.getPath(), logTechniqueEnabled);
            }
            trFw.write(sb.toString());
            trFw.close();
            stats.append("2,"+c.getWindowSize() + "," + count + ",tr," + totalInstances + "," + defectiveInstance + "\n");
            totalInstances = 0;
            defectiveInstance = 0;

            System.out.println("\n>>>>Test set:<<<<\n");

            HashMap<String, ArrayList<Integer>> projectOccurence = new HashMap<String, ArrayList<Integer>>();

            int i=0;
            for (Project p : c.getTest()) {
                if(projectOccurence.get(p.getName())==null) {
                    ArrayList<Integer> temp = new ArrayList<Integer>();
                    temp.add(i);
                    projectOccurence.put(p.getName(), temp);
                }else {
                    projectOccurence.get(p.getName()).add(i);
                }
                i++;
            }

            Iterator hmIterator = projectOccurence.entrySet().iterator();

            int mapEntryIndex = 0;
            while (hmIterator.hasNext()) {
                mapEntryIndex++;
                Map.Entry mapElement = (Map.Entry)hmIterator.next();
                ArrayList<Integer> tempList = ((ArrayList<Integer>)mapElement.getValue());

                sb = new StringBuilder(
                        "proj,ver,class,wmc,dit,noc,cbo,rfc,lcom,ca,ce,npm,lcom3,loc,dam,moa,mfa,cam,ic,cbm,amc,max_cc,avg_cc,bug\n");

                for(int indexOfProject : tempList){
                    ArrayList<Project> testProjects = c.getTest();
                    writeToBuffer(sb, testProjects.get(indexOfProject).getPath(), logTechniqueEnabled);
                }

                String testPath = newPath + count + "typetest_project" + mapElement.getKey();
                FileWriter testFw = new FileWriter(testPath, true);

                testFw.write(sb.toString());
                testFw.close();
                stats.append("2,"+c.getWindowSize() + "," + count + ",test," + totalInstances + "," + defectiveInstance + "\n");
                totalInstances = 0;
                defectiveInstance = 0;
                System.out.println("===========");

            }

            System.out.println("===========");
            System.out.println("Configuration no: " + ++count);
            System.out.println("Window size: " + c.getWindowSize());
            System.out.println("\n>>>>Training set:<<<<\n");
        }
    }


    private static void writeToBuffer(StringBuilder sb, String path, boolean log) {

        File file = new File(path);

        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(file));
            String st;
            br.readLine();
            while ((st = br.readLine()) != null) {
                String[] tokens = st.split(","); // returns 21 tokens i.e. 0-20
                String finalString = "";
                for (int i = 0; i < 23; i++) {
                    finalString = finalString + tokens[i] + ",";
                }

                Integer bugs = Integer.parseInt(tokens[23]);
                if (bugs != 0) {
                    defectiveInstance++;
                }
                totalInstances++;
                finalString = finalString + tokens[23];
                sb.append(finalString + "\n");
            }
        } catch (FileNotFoundException e) {
            System.out.println("Tring to read csv and write in string buffer");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Tring to read csv and write in string buffer");
            e.printStackTrace();
        }
    }

    public static void generatePPConfig(ArrayList<MonthData> mDataList) {


        for (int split = 1; split <= mDataList.size(); split++) {
            ArrayList<Project> tr = new ArrayList<Project>();
            ArrayList<Project> test = new ArrayList<Project>();
            // Generating Training data from past
            for (int i = split - 1; i < mDataList.size() && i > -1; i--) {
                tr.addAll(mDataList.get(i).getProjects());
            }

            // Generating Test data from future

            for (int j = split+1; j < mDataList.size(); j++) {
                ArrayList<Project> temp = getDistinctProjectsForTestSet(tr, mDataList.get(j).getProjects());

                if (temp != null) {
                    test.addAll(temp);
                }
            }
            if (test.size()>0) {
                Configuration c = new Configuration();
                c.setWindowSize(0);
                c.setTest(test);
                c.setTr(tr);
                PP.add(c);

            }
        }

        return;

    }

    public static void generateEPConfig(ArrayList<MonthData> mDataList) {

        for(int window = 1; window<mDataList.size(); window++){
            for(int split=1;split<mDataList.size();split++){
                ArrayList<Project> tr = new ArrayList<Project>();
                ArrayList<Project> test = new ArrayList<Project>();
                int count = 0;
                for(int i=split-1; i>-1 && count<window; i--){
                    tr.addAll(mDataList.get(i).getProjects());
                    count++;
                }

                for(int j=split+1; j<mDataList.size(); j++){
                    ArrayList<Project> temp = getDistinctProjectsForTestSet(tr, mDataList.get(j).getProjects());

                    if (temp != null) {
                        test.addAll(temp);
                    }
                }

                if (test.size()>0) {
                    Configuration c = new Configuration();
                    c.setWindowSize(window);
                    c.setTest(test);
                    c.setTr(tr);
                    EP.add(c);
                }

            }
        }
    }

    public static void generatePEConfig(ArrayList<MonthData> mDataList) {

        for (int window = 1; window < mDataList.size(); window++) {
            for (int split = 1; split <= mDataList.size(); split++) {
                ArrayList<Project> tr = new ArrayList<Project>();
                ArrayList<Project> test = new ArrayList<Project>();
                // Generating Training data from past
                for (int i = split - 1; i < mDataList.size() && i > -1; i--) {
                    tr.addAll(mDataList.get(i).getProjects());
                }

                // Generating Test data from future

                int count = 0;
                for (int j = split+1; j < mDataList.size() && count < window; j++) {
                    ArrayList<Project> temp = getDistinctProjectsForTestSet(tr, mDataList.get(j).getProjects());

                    if (temp != null) {
                        test.addAll(temp);
                    }
                    count++;
                }
                if (test.size()>0) {
                    Configuration c = new Configuration();
                    c.setWindowSize(window);
                    c.setTest(test);
                    c.setTr(tr);
                    PE.add(c);
                }

            }
        }

//		for (int window = 1; window <= mDataList.size() - 1; window++) {
//			for (int iteration = 0; iteration < mDataList.size(); iteration++) {
//				ArrayList<Project> tr = new ArrayList<Project>();
//				ArrayList<Project> test = new ArrayList<Project>();
//
//				if ((window + iteration) > mDataList.size()) {
//					continue;
//				}
//				int i;
//				for (i = 0; i < iteration; i++) {
//					tr.addAll(mDataList.get(i).getProjects());
//				}
//
//				for (int slide = i; slide < mDataList.size(); slide++) {
//					int count = 0;
//					for (int j = slide; j < mDataList.size() && count < window; j++) {
//						ArrayList<Project> temp = getDistinctProjectsForTestSet(tr, mDataList.get(j).getProjects());
//
//						if (temp != null) {
//							test.addAll(temp);
//							count++;
//						}
//					}
//					if (count == window) {
//						Configuration c = new Configuration();
//						c.setWindowSize(window);
//						c.setTest(test);
//						c.setTr(tr);
//						PE.add(c);
//					}
//				}
//			}
//
//		}

        return;

    }

    public static void generateEEConfig(ArrayList<MonthData> mDataList) {


        for (int window = 1; window <= mDataList.size(); window++) {
            for (int split = 1; split <= mDataList.size(); split++) {
                ArrayList<Project> tr = new ArrayList<Project>();
                ArrayList<Project> test = new ArrayList<Project>();
                // Generating Training data from past
                for (int i = split - 1, j = 0; i < mDataList.size() && i > -1 && j < window; i--, j++) {
                    tr.addAll(mDataList.get(i).getProjects());
                }

                // Generating Test data from future
                int count = 0;
                for (int j = split+1; j < mDataList.size() && count < window; j++) {
                    ArrayList<Project> temp = getDistinctProjectsForTestSet(tr, mDataList.get(j).getProjects());

                    if (temp != null) {
                        test.addAll(temp);
                    }
                    count++;
                }
                if (test.size()>0) {
                    Configuration c = new Configuration();
                    c.setWindowSize(window);
                    c.setTest(test);
                    c.setTr(tr);
                    EE.add(c);
                }

            }
        }

//		for (int window = 1; window <= mDataList.size() / 2; window++) {
//			for (int iteration = 1 - window; iteration < mDataList.size(); iteration++) {
//
//				if ((window + iteration) > mDataList.size()) {
//					continue;
//				}
//
//				int i;
//				int offset = window;
//				for (i = iteration; i <= offset + iteration; i++) {
//					if (i < 0) {
//						offset = offset--;
//						continue;
//					}
//					tr.addAll(mDataList.get(i).getProjects());
//				}
//
//				int slide = i;
//				int count = 0;
//				for (int j = slide; j < mDataList.size() && count < window; j++) {
//					ArrayList<Project> temp = getDistinctProjectsForTestSet(tr, mDataList.get(j).getProjects());
//
//					if (temp != null) {
//						test.addAll(temp);
//						count++;
//					}
//				}
//				if (count == window) {
//					Configuration c = new Configuration();
//					c.setWindowSize(window);
//					c.setTest(test);
//					c.setTr(tr);
//					EE.add(c);
//				}
//			}
//		}

        return;
    }

    public static ArrayList<Project> getDistinctProjectsForTestSet(ArrayList<Project> tr, ArrayList<Project> testing) {

        ArrayList<Project> listOfDistinctProjects = new ArrayList<Project>(0);

        int index = -1;
        for (int i = 0; i < testing.size(); i++) {

            for (int j = 0; j < tr.size(); j++) {
                if (!testing.get(i).getName().equals(tr.get(j).getName())) {
                    index = i;
                } else {
                    index = -1;
                    break;
                }
            }

            if (index != -1) {
                listOfDistinctProjects.add(testing.get(i));
            }

        }

        if (listOfDistinctProjects.size() < 1) {
            return null;
        } else {
            return listOfDistinctProjects;
        }

    }

}