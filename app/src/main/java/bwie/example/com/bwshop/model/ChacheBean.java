package bwie.example.com.bwshop.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class ChacheBean {
    @Id(autoincrement = true)
    private Long id;
    private String data;
    @Generated(hash = 958147060)
    public ChacheBean(Long id, String data) {
        this.id = id;
        this.data = data;
    }
    @Generated(hash = 880469185)
    public ChacheBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getData() {
        return this.data;
    }
    public void setData(String data) {
        this.data = data;
    }

}
