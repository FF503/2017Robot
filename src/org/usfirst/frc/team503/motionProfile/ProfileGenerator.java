package org.usfirst.frc.team503.motionProfile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Collectors;

public class ProfileGenerator {
	
	private static String FILE_PATH = "/Users/ankith/";
	private static String FILE_NAME = "";
	private String[] splitProfiles;
	private double[][] leftProfile;
	private double[][] rightProfile;

	public  ProfileGenerator(String fileName){
		this.FILE_NAME = fileName;
		this.FILE_PATH += this.FILE_NAME;
	}

	private String getFileContentsAsString(String filename) throws IOException {
		    String content = null;
		    File file = new File(filename); //for ex foo.txt
		    BufferedReader reader = null;
		    try {
		        reader = new BufferedReader(new FileReader(file));
		        char[] chars = new char[(int) file.length()];
		        reader.read(chars);
		        content = new String(chars);
		        reader.close();
		    } catch (IOException e) {
		        e.printStackTrace();
		    } finally {
		        if(reader !=null){
		        	reader.close();
		        }
		    }
		    return content;

	}
	
	private String[] splitSides(String split) {
		return split.split("-");
	}
	
	private double[][] splitSideIntoProfile(String split){
		 String[] firstOrder = split.split(",");
		 double[][] secondOrder = new double[firstOrder.length][3];
		 for (int i = 0; i < firstOrder.length; i++){
			 secondOrder[i][0] = Double.parseDouble(firstOrder[i].split(" ")[0]); 
			 secondOrder[i][1] = Double.parseDouble(firstOrder[i].split(" ")[1]); 
			 secondOrder[i][2] = Double.parseDouble(firstOrder[i].split(" ")[2]); 
		 }
		 return secondOrder;
	}
	
	private double[][] manipulateProfile(double[][] profile){
		for (int i = 0; i < profile.length; i++){
			if (i != profile.length - 1){
				profile[i][2] = profile[i+1][2] - profile[i][2];
			}
			profile[i][0] = toEncoderPos(profile[i][0]);
		}
		return profile;
	}
	
	private double toEncoderPos(double feet){
		return 512 * ( feet/(Math.PI * 0.25));
	}
	
	public double[][] getLeftProfile(){
		try {
			splitProfiles = splitSides(getFileContentsAsString(FILE_PATH));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		leftProfile = manipulateProfile(splitSideIntoProfile(splitProfiles[0]));
		return leftProfile;
	}
	
	public double[][] getRightProfile(){
		try {
			splitProfiles = splitSides(getFileContentsAsString(FILE_PATH));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		rightProfile = manipulateProfile(splitSideIntoProfile(splitProfiles[1]));
		return rightProfile;
	}
}
