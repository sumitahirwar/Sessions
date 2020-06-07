package com.lti.smooks.example.Model;

import java.util.Date;

public class Order {
		
		
	
	    private Date creationDate;
	    private Long number;
	    private Status status;
	    
	    
		public Date getCreationDate() {
			return creationDate;
		}
		public void setCreationDate(Date creationDate) {
			this.creationDate = creationDate;
		}
		public Long getNumber() {
			return number;
		}
		public void setNumber(Long number) {
			this.number = number;
		}
		public Status getStatus() {
			return status;
		}
		public void setStatus(Status status) {
			this.status = status;
		}
		@Override
		public String toString() {
			return "Order [creationDate=" + creationDate + ", number=" + number + ", status=" + status + "]";
		}
	    
	    
}
