package com.revature.services;

public class Calculation {
	public static double refunds(double tc, int grade, int eventType){     
	     if(eventType==1){
	        double check = tc * .8 + grade;
	        return check;
	    }
	    else if(eventType==2){
	        double check = tc * .6 + grade;
	        return check;
	    }
	    else if(eventType==3){
	        double check = tc * .75 + grade;
	        return check;
	    }
	    else if(eventType==4){
	        double check = tc + grade;
	    }
	    else if(eventType==5){
	        double check = tc * .9 + grade;
	        return check;
	    }
	    else if(eventType==6){
	        double check = tc * .3 + grade;
	        return check;
	    } 
	     return 0;
	}
}
