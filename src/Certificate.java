public class Certificate {
    private String id;
    private String type;
    private String requesterName;
    private String status;

    public Certificate(String id, String type, String requesterName) {
        this.id = id;
        this.type = type;
        this.requesterName = requesterName;
        this.status = "Выдано";
    }

    public String getId() { return id; }
    public String getType() { return type; }
    public String getRequesterName() { return requesterName; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
