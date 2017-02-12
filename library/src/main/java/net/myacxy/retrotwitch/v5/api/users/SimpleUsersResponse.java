package net.myacxy.retrotwitch.v5.api.users;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SimpleUsersResponse {

    @SerializedName("_total")
    private Integer total;
    @SerializedName("users")
    private List<SimpleUser> users;

    public Integer getTotal() {
        return total;
    }

    public List<SimpleUser> getUsers() {
        return users;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SimpleUsersResponse{");
        sb.append("total=").append(total);
        sb.append(", users=").append(users);
        sb.append('}');
        return sb.toString();
    }
}
