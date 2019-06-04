package soo.fastrak_login;
public class License {

    private boolean full;
    private String email;
    private String bname;
    private String addr;
    private String tel;
    private double latitude;
    private double longitude;

    public License(boolean full, String email, String bname, String addr, String tel, double latitude, double longitude){
        this.full = full;
        this.email = email;
        this.bname = bname;
        this.addr = addr;
        this.tel = tel;
        this.latitude = latitude;
        this.longitude = longitude;
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

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}