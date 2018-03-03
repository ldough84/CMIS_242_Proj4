/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmis242_project4;

/**
 *
 * @author Admin
 */
public class Student {
    private String name;
    private String major;
    private double credits;
    private double qualityPoints;
    
    
    public Student(String name, String major, double credits, double qualityPoints) {
        this.name = name;
        this.major = major;
        this.credits = credits;
        this.qualityPoints = qualityPoints;
    }
    
    public void courseComplete(double credits, double qualityPoints) {
        this.credits += credits;
        this.qualityPoints += qualityPoints;
    }
    
    @Override
    public String toString() {
        if (credits > 0) {
            return "Name: " + name + "\nMajor: " + major + "\nGPA: " + Double.toString(qualityPoints/credits);
        }
        else {
            return "Name: " + name + "\nMajor: " + major + "\nGPA: 4.0";
        }
    }
    
    public String getName() {
        return name;
    }
    
    public String getMajor() {
        return major;
    }
    
    public String getGPA() {
        return Double.toString(qualityPoints/credits);
    }
    public void addCredit(double credits) {
        this.credits += credits;
    }
    public void addQualityPoints(double qualityPoints) {
        this.qualityPoints += qualityPoints;
    }
    
}
