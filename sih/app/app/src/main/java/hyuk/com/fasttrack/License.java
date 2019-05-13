package hyuk.com.fasttrack;

public class License {

//    private boolean full;
    private String email;
    private String bname;
    private String addr;
    private String tel;

    public License(String email, String bname, String addr, String tel){
//        this.full = full;
        this.email = email;
        this.bname = bname;
        this.addr = addr;
        this.tel = tel;
    }

//    public boolean isFull() {
//        return full;
//    }
//
//    public void setFull(boolean full) {
//        this.full = full;
//    }

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
}
