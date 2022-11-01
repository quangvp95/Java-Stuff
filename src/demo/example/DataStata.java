package demo.example;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;

public class DataStata {
	public static int JOB_INDEX = 8;
	public static int GENDER_INDEX = 7;
	public static int EDU_INDEX = 6;
	
	public static void main(String[] args) {
		File f = new File("2012.txt");

		BufferedReader br;
		ArrayList<Provice> arrProvice = new ArrayList<>();
		ArrayList<Job> arrJob = new ArrayList<>();
		String st = "";
		try {
			br = new BufferedReader(new FileReader(f));
			while ((st = br.readLine()) != null) {
				String[] arr = st.split("_");
				int proviceId = Integer.parseInt(arr[0]);
				Provice currentProvice = null;
				for (Provice provice : arrProvice)
					if (provice.id == proviceId) {
						currentProvice = provice;
						break;
					}
				if (currentProvice == null) {
					currentProvice = new Provice(proviceId);
					arrProvice.add(currentProvice);
				}
//				if (st.startsWith("101_9_33_14")) {
//					System.out.println("tinh " + currentProvice.id + " " + st);
//				}
				if (arr.length <= JOB_INDEX || arr[JOB_INDEX] == null || arr[JOB_INDEX].length() == 0) {
//					System.out.println("null: " + st);
					continue;
				}
				int jobId = Integer.parseInt(arr[JOB_INDEX]);
				if (jobId < 10 || jobId > 43) {
					continue;
				}
				Job currentJob = null;
				for (Job job : currentProvice.arr)
					if (job.id == jobId) {
						currentJob = job;
						break;
					}
				if (currentJob == null) {
					currentJob = new Job(jobId);
					currentProvice.arr.add(currentJob);
				}
				currentJob.number++;
				currentProvice.number++;
				if (Integer.parseInt(arr[GENDER_INDEX]) == 2) {
					currentJob.female++;
					currentProvice.female++;
				}
				if (arr.length > EDU_INDEX && arr[EDU_INDEX] != null && arr[EDU_INDEX].length() > 0 && Integer.parseInt(arr[EDU_INDEX]) >= 7) {
//					System.out.println("null: " + st);
					currentJob.college++;
					currentProvice.college++;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("ERR " + st);
			e.printStackTrace();
		}

		Collections.sort(arrProvice);
		for (Provice provice : arrProvice) {
			Collections.sort(provice.arr);
			for (Job i : provice.arr) {
				Job temp = null;
				for (Job job : arrJob)
					if (job.id == i.id) {
//						if (job.id == 15) {
//							System.out.println("tinh " + provice.id + " " + i.college);
//						}
						job.number += i.number;
						job.female += i.female;
						job.college += i.college;
						temp = job;
						break;
					}
				if (temp == null) {
					temp = new Job(i);
					arrJob.add(temp);
				}
			}
		}

		Collections.sort(arrJob);

		
		System.out.println("EDU=============================");
		StringBuilder builder;
		builder = new StringBuilder("\t");
		for (Job job : arrJob) {
			builder.append(job.id).append("\t");
		}
		System.out.println(builder);
		for (Provice provice : arrProvice) {
			builder = new StringBuilder(provice.id + "").append(":\t");
			for (Job job : arrJob) {
				Job temp = null;
				for (Job i : provice.arr)
					if (job.id == i.id) {
						temp = i;
						break;
					}
				builder.append(temp == null ? 0 : temp.getEdu()).append("\t");
			}
			builder.append("-> ").append(provice.getEdu());
			System.out.println(builder);
		}
	
		builder = new StringBuilder("\t");
		for (Job job : arrJob) {
			builder.append(job.getEdu()).append("\t");
		}
		System.out.println(builder);

		System.out.println("\n\nFEMALE=============================");
		builder = new StringBuilder("\t");
		for (Job job : arrJob) {
			builder.append(job.id).append("\t");
		}
		System.out.println(builder);
		for (Provice provice : arrProvice) {
			builder = new StringBuilder(provice.id + "").append(":\t");
			for (Job job : arrJob) {
				Job temp = null;
				for (Job i : provice.arr)
					if (job.id == i.id) {
						temp = i;
						break;
					}
				builder.append(temp == null ? 0 : temp.getFemale()).append("\t");
			}
			builder.append("-> ").append(provice.getFemale());
			System.out.println(builder);
		}
	
		builder = new StringBuilder("\t");
		for (Job job : arrJob) {
			builder.append(job.getFemale()).append("\t");
		}
		System.out.println(builder);
}

}

class Job implements Comparable<Job> {
	int id;
	int number = 0;
	int female = 0;
	int college = 0;

	public Job(int id) {
		super();
		this.id = id;
	}

	public Job(Job j) {
		super();
		id = j.id;
		female = j.female;
		college = j.college;
		number = j.number;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Job " + id + ": " + college;
	}

	@Override
	public int compareTo(Job o) {
		// TODO Auto-generated method stub
		return id - o.id;
	}
	
	public String getEdu() {
		return String.format("%.2f", college * 100f / number);
//		return college;
	}
	
	public String getFemale() {
		return String.format("%.2f", female * 100f / number);
//		return college;
	}
}

class Provice implements Comparable<Provice> {
	int id;
	ArrayList<Job> arr = new ArrayList<>();
	int number = 0;
	int female = 0;
	int college = 0;

	Provice(int idProvice) {
		this.id = idProvice;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Provice " + id + ": " + arr.size();
	}

	@Override
	public int compareTo(Provice o) {
		// TODO Auto-generated method stub
		return id - o.id;
	}
	
	public String getEdu() {
		return String.format("%.2f", college * 100f / number);
//		return college;
	}
	
	public String getFemale() {
		return String.format("%.2f", female * 100f / number);
//		return college;
	}
}
