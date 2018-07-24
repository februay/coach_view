package indi.xp.coachview.model;

import java.io.Serializable;

public class SysRole implements Serializable {

    private static final long serialVersionUID = -8115010645802447038L;

    private String roleId;
    private String name;
    private String code;
    private String status;
    private String description;
    private Integer orderNumber;
    private Boolean deleteStatus; // 是否删除

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Boolean getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(Boolean deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    @Override
    public String toString() {
        return "SysRole [roleId=" + roleId + ", name=" + name + ", code=" + code + ", status=" + status
            + ", description=" + description + ", orderNumber=" + orderNumber + ", deleteStatus=" + deleteStatus + "]";
    }

}
