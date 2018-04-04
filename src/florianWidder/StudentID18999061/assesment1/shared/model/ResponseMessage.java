package florianWidder.StudentID18999061.assesment1.shared.model;

public class ResponseMessage extends RequestMessage {

    /**
     *
     */
    private final static long serialVersionUID = 1L;

    private Object Response;

    public Object getResponse() {
	return Response;
    }

    public void setResponse(Object response) {
	Response = response;
    }

}
