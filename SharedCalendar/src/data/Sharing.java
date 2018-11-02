package data;

public class Sharing {
	private int scheduleID;
	private String ownerID;
	private String guestID;
	
	public Sharing() {}
	
	public Sharing(int scheduleID, String guestID) {
		setScheduleID(scheduleID);
		setGuestID(guestID);
	}
	
	public Sharing(int scheduleID, String ownerID, String guestID) {
		setScheduleID(scheduleID);
		setOwnerID(ownerID);
		setGuestID(guestID);
	}
	
	public Sharing(Sharing sharing) {
		setSharing(sharing);
	}
	
	public void setScheduleID(int scheduleID) {
		this.scheduleID = scheduleID;
	}
	
	public void setOwnerID(String ownerID) {
		this.ownerID = ownerID;
	}
	
	public void setGuestID(String guestID) {
		this.guestID = guestID;
	}
	
	public void setSharing(Sharing sharing) {
		setScheduleID(sharing.scheduleID);
		setGuestID(sharing.guestID);
	}
	
	public int getScheduleID(){
		return scheduleID;
	}
	
	public String getOwnerID() {
		return ownerID;
	}
	
	public String getGuestID() {
		return guestID;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Sharing))	return false;
		return scheduleID==((Sharing)obj).scheduleID && guestID.equals(((Sharing)obj).guestID);
	}
}
