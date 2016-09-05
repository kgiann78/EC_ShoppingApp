package gr.uoa.ec.shopeeng.models;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.io.Serializable;
import java.util.Hashtable;

public class User implements Serializable, KvmSerializable {
    private String username;
    private String password;
    private String name;
    private String lastname;

    public User() {
    }

    public User(String username, String password, String name, String lastname) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.lastname = lastname;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Override
    public Object getProperty(int index) {
        switch(index)
        {
            case 0:
                return username;
            case 1:
                return password;
            case 2:
                return name;
            case 3:
                return lastname;
            default:
                return null;
        }
    }

    @Override
    public int getPropertyCount() {
        return 4;
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
            case 2:
                name = value.toString();
                break;
            case 3:
                lastname = value.toString();
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
            case 2:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "name";
                break;
            case 3:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "lastname";
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
                ", name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                '}';
    }
}
