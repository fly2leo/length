import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class Length {
	private String ifilename;
	private String ofilename;
	private String names[] = new String[6];
	private String expressions[] = new String[10];
	private double results[] = new double[10];
	private Map<String, Double> unit = new HashMap<String, Double>();
	
	public Length(String ifname, String ofname){
		this.ifilename = ifname;
		this.ofilename = ofname;
	}
	
	public void readFile(){
		File inFile = new File(this.ifilename);
		String temp;
		String temps[];
		if (!inFile.exists()){
//			System.out.println(ifilename + " does not exist!");
			return;
		}else{
			try {
				BufferedReader br = new BufferedReader(new FileReader(inFile));
				for (int i = 0; i < 6; i++){
					temp = br.readLine();
					temps = temp.split(" ");
					this.names[i] = temps[1];
					this.unit.put(temps[1], Double.parseDouble(temps[3]));
//					System.out.println("1 " + this.names[i] + " = " + unit.get(this.names[i]) + " m");
				}
				temp = br.readLine();
//				System.out.println();
				for (int i = 0; i < 10; i++){
					this.expressions[i] = br.readLine();
				}	
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void writeFile(){
		File outFile = new File(this.ofilename);
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(outFile));
			bw.write("11301087@bjtu.edu.cn\r\n\r\n");
			for (int i = 0; i < 10; i++){
//				System.out.println("Result" + i + ": " + results[i] + " m");
				bw.write(this.results[i] + " m\r\n");
			}
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void process(){
		for (int i = 0; i < 10; i++){
			results[i] = compute(i);
		} 
	}
	
	private double round(double num){
		return ((double)((int)(num * 100 + 0.5)))/ 100;
	}
	
	private double compute(int index){
		double sum = 0;
		double op = 1;
		double v = 0;
		String n;
		String temps[] = this.expressions[index].split(" ");
		
		for (int i = 0; i < temps.length; i++){
			v = Double.parseDouble(temps[i++]);
			n = temps[i++];
			for (int j = 0; j < 6; j++){
				if(n.contains(this.names[j]))
					n = this.names[j];
			}
			if (n.equals("feet"))
				n = "foot";
//			System.out.println(v + n);
			sum += v * op * unit.get(n);
			if (i < temps.length){
//				System.out.println(temps[i]);
				if ("-" == temps[i])
					op = -1;
				else if ("+" == temps[i])
					op = 1;
			}
		}
//		System.out.println();
		return round(sum);
	}
	
	public static void main(String[] args){
		Length length = new Length("input.txt", "output.txt");
		length.readFile();
		length.process();
		length.writeFile();
	}
}
