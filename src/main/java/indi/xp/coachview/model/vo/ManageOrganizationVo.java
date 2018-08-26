package indi.xp.coachview.model.vo;

import java.io.Serializable;

public class ManageOrganizationVo implements Serializable {

    private static final long serialVersionUID = 2625856161723220770L;

    public static final String TYPE_CLUB = "club";
    public static final String TYPE_SCHOOL = "school";
    public static final String TYPE_TEAM = "team";

    private String type;
    private String id;
    private String name;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
