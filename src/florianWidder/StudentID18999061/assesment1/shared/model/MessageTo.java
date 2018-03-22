package florianWidder.StudentID18999061.assesment1.shared.model;

public class MessageTo extends Message {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1182471325700008016L;
	private String rec;

	public String getRec() {
		return rec;
	}

	public void setRec(String rec) {
		this.rec = rec;
	}

	@Override
	public String toString() {
		return "MessageTo [rec=" + rec + "]";
	}
}
