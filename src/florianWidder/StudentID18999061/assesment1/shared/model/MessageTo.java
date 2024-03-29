package florianWidder.StudentID18999061.assesment1.shared.model;

public class MessageTo extends Message {

    /**
     *
     */
    private final static long serialVersionUID = -1182471325700008016L;
    private String rec;

    public MessageTo(String rec) {
	this.rec = rec;
    }

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
