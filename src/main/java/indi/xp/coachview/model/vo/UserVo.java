package indi.xp.coachview.model.vo;

import java.io.Serializable;
import java.util.List;

import indi.xp.coachview.model.User;

public class UserVo extends User implements Serializable {

    private static final long serialVersionUID = 7318789932910212804L;

    private List<String> roles;

    private List<ManageOrganizationVo> manageOrganizationList;

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public List<ManageOrganizationVo> getManageOrganizationList() {
        return manageOrganizationList;
    }

    public void setManageOrganizationList(List<ManageOrganizationVo> manageOrganizationList) {
        this.manageOrganizationList = manageOrganizationList;
    }

}
