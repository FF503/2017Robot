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

import org.usfirst.frc.team503.robot.Robot;

public class ProfileGenerator {
	
	private static String FILE_PATH = "/home/lvuser/motionProfiles/";
	private static String FILE_NAME = "";
	private String[] splitProfiles;
	private boolean reverse;
	public double lastEncPosRight;
	public double lastEncPosLeft;
	private double[][] leftProfile;
	private double[][] rightProfile;

	public  ProfileGenerator(String fileName, boolean reverse){
		this.FILE_NAME = fileName;
		this.FILE_PATH += this.FILE_NAME;
		this.reverse = reverse;
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
	
	private double[][] manipulateProfile(double[][] profile, boolean reverse){
		for (int i = 0; i < profile.length; i++){
			if (reverse){
				if (i != profile.length - 1){
					profile[i][2] = (profile[i+1][2] - profile[i][2]) * 1000; // future time - curtime then seconds to milliseconds
				}
				profile[i][0] = -toEncoderPos(profile[i][0]);
				profile[i][1] = -profile[i][1];
			}
			else{
				if (i != profile.length - 1){
					profile[i][2] = (profile[i+1][2] - profile[i][2]) * 1000; // future time - curTime then seconds to milliseconds
				}
				profile[i][0] = toEncoderPos(profile[i][0]);
			}
			
		
		}
		return profile;
	}
	
	private double toEncoderPos(double feet){
		return Robot.bot.DRIVE_COUNTS_PER_REV * ( feet/(Math.PI * Robot.bot.WHEEL_DIAMETER / 12));
	}
	
	public double[][] getLeftProfile(){
		try {
			String fileContents = getFileContentsAsString(FILE_PATH);
			System.out.println(fileContents);
			splitProfiles = splitSides(fileContents);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		leftProfile = manipulateProfile(splitSideIntoProfile(splitProfiles[0]), reverse);
	
		return leftProfile;
	}
	
	public double[][] getRightProfile(){
		try {
			splitProfiles = splitSides(getFileContentsAsString(FILE_PATH));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		rightProfile = manipulateProfile(splitSideIntoProfile(splitProfiles[1]), reverse);
		return rightProfile;
	}
}
