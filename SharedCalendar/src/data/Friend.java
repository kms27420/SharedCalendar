package data;

public class Friend {
	private String id, name;
	
	public Friend() {}
	
	public Friend(String id, String name) {
		setFriend(id, name);
	}
	
	public Friend(Friend friend) {
		setFriend(friend.id, friend.name);
	}
	
	public void setFriend(String id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public String getID() {
		return id;
	}
	
	public String getName() {
		return name;
	}
}
