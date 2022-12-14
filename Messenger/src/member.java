import java.io.DataOutputStream;

public class member {
	
	private String name;
	private String nameid;
	private DataOutputStream writer;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNameid() {
		return nameid;
	}
	public void setNameid(String nameid) {
		this.nameid = nameid;
	}
	public DataOutputStream getWriter() {
		return writer;
	}
	public void setWriter(DataOutputStream writer) {
		this.writer = writer;
	}
}