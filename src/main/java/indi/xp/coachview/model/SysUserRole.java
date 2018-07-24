package indi.xp.coachview.model;

import java.io.Serializable;

public class SysUserRole implements Serializable {

    private static final long serialVersionUID = -8115010645802447038L;

    private String uid;
    private String roleId;
    private Boolean deleteStatus; // 是否删除

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public Boolean getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(Boolean deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    @Override
    public String toString() {
        return "SysUserRole [uid=" + uid + ", roleId=" + roleId + ", deleteStatus=" + deleteStatus + "]";
    }

}
