package gr.uoa.ec.shopeeng.models;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.io.Serializable;
import java.util.Hashtable;

public class Login implements Serializable, KvmSerializable {
    private String username;
    private String password;

    public Login() {
    }

    public Login(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public Object getProperty(int index) {
        switch(index)
        {
            case 0:
                return username;
            case 1:
                return password;
            default:
                return null;
        }
    }

    @Override
    public int getPropertyCount() {
        return 2;
    }

    @Override
    public void setProperty(int index, Object value) {
        switch(index)
        {
            case 0:
                username = value.toString();
                break;
            case 1:
                password = value.toString();
                break;
            default:
                break;
        }
    }

    @Override
    public void getPropertyInfo(int index, Hashtable properties, PropertyInfo info) {
        switch(index)
        {
            case 0:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "username";
                break;
            case 1:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "password";
                break;
            default:
                break;
        }
    }


    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
