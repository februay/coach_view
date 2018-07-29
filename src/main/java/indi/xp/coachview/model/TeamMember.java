package indi.xp.coachview.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

import indi.xp.common.utils.CollectionUtils;
import indi.xp.common.utils.ObjectUtils;
import indi.xp.common.utils.StringUtils;

public class TeamMember implements Serializable {

    private static final Logger logger = LoggerFactory.getLogger(TeamMember.class);

    private static final long serialVersionUID = -4173171901498089727L;

    public static final List<String> defaultHeaderList = new ArrayList<String>(
        Arrays.asList("球衣号", "姓名", "身份证号", "年龄", "身高", "体重", "第一位置", "第二位置"));

    public static final Map<String, String> nameToPropertyMapping = new HashMap<>();
    static {
        nameToPropertyMapping.put("球衣号", "number");
        nameToPropertyMapping.put("姓名", "name");
        nameToPropertyMapping.put("身份证号", "idNumber");
        nameToPropertyMapping.put("年龄", "age");
        nameToPropertyMapping.put("身高", "height");
        nameToPropertyMapping.put("体重", "weight");
        nameToPropertyMapping.put("第一位置", "firstPosition");
        nameToPropertyMapping.put("第二位置", "secondPosition");
    }

    private String memberId;
    private String number;
    private String name;
    private String idNumber;
    private String age;
    private String height;
    private String weight;
    private String photo;
    private String firstPosition;
    private String secondPosition;
    private String teamId;
    private String teamName;
    private String createTime;
    private Boolean deleteStatus;

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getFirstPosition() {
        return firstPosition;
    }

    public void setFirstPosition(String firstPosition) {
        this.firstPosition = firstPosition;
    }

    public String getSecondPosition() {
        return secondPosition;
    }

    public void setSecondPosition(String secondPosition) {
        this.secondPosition = secondPosition;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Boolean getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(Boolean deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    @Override
    public String toString() {
        return "TeamMember [memberId=" + memberId + ", number=" + number + ", name=" + name + ", idNumber=" + idNumber
            + ", age=" + age + ", height=" + height + ", weight=" + weight + ", photo=" + photo + ", firstPosition="
            + firstPosition + ", secondPosition=" + secondPosition + ", teamId=" + teamId + ", teamName=" + teamName
            + ", createTime=" + createTime + ", deleteStatus=" + deleteStatus + "]";
    }

    public static List<List<String>> parseToRowList(List<TeamMember> memberList) {
        List<List<String>> rowList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(memberList)) {
            for (TeamMember member : memberList) {
                try {
                    List<String> row = new ArrayList<String>();
                    Map<String, Object> objMap = ObjectUtils.objectToMap(member);
                    for (String header : defaultHeaderList) {
                        String property = nameToPropertyMapping.get(header);
                        if (objMap.get(property) != null && StringUtils.isNotBlank((String) objMap.get(property))) {
                            row.add((String) objMap.get(property));
                        } else {
                            row.add("");
                        }
                    }
                    rowList.add(row);
                } catch (Exception e) {
                    logger.error("parse member to map error, member=" + JSON.toJSONString(member), e);
                }
            }
        }
        return rowList;
    }

    public static List<TeamMember> parseToObjectList(List<List<String>> rowList, String teamId, String teamName) {
        List<TeamMember> memberList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(rowList) && rowList.size() > 1) {
            // 第一行为表头信息
            List<String> headerList = rowList.get(0);
            for (int i = 1; i < rowList.size(); i++) {
                List<String> row = rowList.get(i);
                try {
                    Map<String, Object> objMap = new HashMap<String, Object>();
                    for (int j = 0; j < headerList.size(); j++) {
                        String header = headerList.get(j);
                        String property = nameToPropertyMapping.get(header);
                        if (StringUtils.isNotBlank(property)) {
                            objMap.put(property, StringUtils.isNotBlank(row.get(j)) ? row.get(j) : null);
                        }
                    }
                    TeamMember member = ObjectUtils.mapToObject(objMap, TeamMember.class);
                    member.setTeamId(teamId);
                    member.setTeamName(teamName);
                    memberList.add(member);
                } catch (Exception e) {
                    logger.error("parse row to member object error, row=" + JSON.toJSONString(row), e);
                }
            }
        }
        return memberList;
    }

}
