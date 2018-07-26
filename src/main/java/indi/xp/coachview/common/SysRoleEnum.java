package indi.xp.coachview.common;

public enum SysRoleEnum {

    ADMIN("10001", "sys_role_admin", "管理员"),
    CLUB("10002", "sys_role_club", "俱乐部"),
    SCHOOL("10003", "sys_role_school", "学校"),
    TEAM("10004", "sys_role_team", "球队");

    private SysRoleEnum(String id, String code, String name) {
        this.id = id;
        this.code = code;
        this.name = name;
    }

    private String id;
    private String code;

    private String name;

    public String getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

}
