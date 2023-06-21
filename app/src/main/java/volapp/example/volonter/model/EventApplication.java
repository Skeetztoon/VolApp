package volapp.example.volonter.model;

public class EventApplication {

    private String name_event, mem_id, place, date, org, mem_name, mem_lastname, key;

    public String getKey() {return key;}

    public void setKey(String key) {this.key = key; }
//
    public String getMem_name(String mem_name) {return this.mem_name;}
    //public void setPlace(String place) {this.place = place; }
    public void setMem_name(String mem_name) {this.mem_name = mem_name; }
//
    public String getMem_lastname(String mem_lastname) {return this.mem_lastname;}

    public void setMem_lastname(String mem_lastname) {this.mem_lastname = mem_lastname; }

    public String getOrg(String org) {return this.org;}

    public void setOrg(String org) {this.org = org; }

    public String getDate() {return date;}

    public void setDate(String date) {this.date = date; }

    public String getPlace() {return place;}

    public void setPlace(String place) {this.place = place; }

    public String getName_event() { return name_event; }

    public void setName_event(String name_event) {
        this.name_event = name_event;
    }


    public String getMem_id() {
        return mem_id;
    }

    public void setMem_id(String mem_id) {
        this.mem_id = mem_id;
    }
    // EventApplition(

    public EventApplication(String name_event, String place, String org, String date, String mem_name, String mem_lastname, String key) {
        this.name_event = name_event;
        this.mem_id = mem_id;
        this.place = place;
        this.date = date;
        this.org = org;
        this.mem_name = mem_name;
        this.mem_lastname = mem_lastname;
        this.key = key;
    }


    public EventApplication(){}

}
