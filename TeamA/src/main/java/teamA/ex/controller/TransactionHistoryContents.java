package teamA.ex.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import teamA.ex.model.entity.CourseEntity;

public class TransactionHistoryContents {
	public String date; 
	public int amount; 
	public List<CourseEntity> courses = new ArrayList<CourseEntity>();
	
	public TransactionHistoryContents(String date, int amount) {
		this.date = date;
		this.amount = amount;
	}
}
