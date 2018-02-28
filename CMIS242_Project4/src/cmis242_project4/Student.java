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
    private int credits;
    private Double qualityPoints;
    
    public void Student(String name, String major, int credits, Double qualityPoints) {
        this.name = name;
        this.major = major;
        this.credits = credits;
        this.qualityPoints = qualityPoints;
    }
    
    public void courseComplete(int credits, Double qualityPoints) {
        this.credits += credits;
        this.qualityPoints += qualityPoints;
    }
    
    @Override
    public String toString() {
        if (credits > 0) {
            return "Name: " + name + "Major: " + major + "GPA: " + Double.toString(qualityPoints/credits);
        }
        else {
            return "Name: " + name + "Major: " + major + "GPA: 4.0";
        }
    }
}
