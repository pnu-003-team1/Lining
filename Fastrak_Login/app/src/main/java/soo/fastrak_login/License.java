package soo.fastrak_login;
public class License {

    private boolean full;
    private String email;
    private String bname;
    private String addr;
    private String tel;
    private double bLatitude;
    private double bLongitude;
    private double position;

    public License(boolean full, String email, String bname, String addr, String tel, double bLatitude, double bLongitude){
        this.full = full;
        this.email = email;
        this.bname = bname;
        this.addr = addr;
        this.tel = tel;
        this.bLatitude = bLatitude;
        this.bLongitude = bLongitude;
    }

    public boolean isFull() {
        return full;
    }

    public void setFull(boolean full) {
        this.full = full;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBname() {
        return bname;
    }

    public void setBname(String bname) {
        this.bname = bname;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public double getbLatitude() {
        return bLatitude;
    }

    public void setbLatitude(double bLatitude) {
        this.bLatitude = bLatitude;
    }

    public double getbLongitude() {
        return bLongitude;
    }

    public void setbLongitude(double bLongitude) {
        this.bLongitude = bLongitude;
    }

    public void setPositon(double latitude, double longitude){
        double x = latitude - bLatitude;
        double y = longitude - bLongitude;
        x = x>0?x:x*-1;
        y = y>0?y:y*-1;
        position = x + y;
    }

    public double getPositon(){ return position;}
}