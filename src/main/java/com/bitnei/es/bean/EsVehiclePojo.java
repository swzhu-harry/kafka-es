package com.bitnei.es.bean;

import java.util.Date;

/**
 * ES车辆表实体类
 * <p>
 * Created by IntelliJ IDEA.
 * User: zhaogd
 * Date: 2017/9/5
 */
public class EsVehiclePojo {

    private String id;//id
    private String uuid;//uuid
    private String ruleId;//规约ID
    private String operatingId;//运营单位ID
    private String placeID;//存放地点
    private String manufacturersId;//制造厂商ID
    private String vehModelId;//车辆类型ID
    private String terminalId;//终端厂商id
    private String vehTypeId;//车辆类别ID
    private String terminalModelId;//终端类型ID
    private String driveModeId;//驱动方式iD
    private String salesAreaId;//销售区域id
    private String firmOwnersid;//企业负责人ID
    private String linkmanId; //联系人id

    private String vin;//vin
    private String ruleName;//规约名称
    private String lic;//车牌
    private String ruleNo;//规约编号
    private String operatingUnit;//运营单位名称
    private String placeName;//存放地点
    private String manufacturersName;//制造厂商名称
    private String vehTypeName;//车辆类别名称
    private String vehModelName;//车辆类型名称
    private String terminalFirm;//终端厂商名称
    private String terminalModelName;//终端类型名称
    private String driveModeName;//驱动方式名称
    private String salesAreaName;//销售区域名称
    private String firmOwnersName;//企业负责人
    private String firmOwnersPhone;//企业负责人电话
    private String linkmanName; //联系人名称
    private String linkmanPhone; //联系人电话
    private String simCard;//SIM卡
    private String iccid;//终端ICCID

    private String[] operatingPath;//运营单位路径
    private String[] manufacturersPath;//制造厂商路径
    private String[] terminalPath;//终端厂商路径
    private String[] jurisdiction; //权限管理
    private String[] vehTypePath;//车辆类别path
    private String[] salesAreaPath;//销售区域路径

    private Date factoryDate;//生产日期
    private Date firstReg;//首次注册日期
    private Date entryDate;//录入日期
    private Date officialOperationData;//正式运营时间

    private Integer vehStatus;//车辆在线状态（0：从未上过线，1：在线 2：离线）默认为0
    private Integer alarmStatus;//车辆报警状态（1：报警 2：未报警）默认2
    private Integer onlined;//车辆注册状态(0:未注册 1:注册)默认0  （是否向平台发送过报文 ）

    private Double officialOperatingMil;//正式运营里程

    /**
     * vin，车牌号排序专用
     */
    private String vinSort;
    private String licSort;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getRuleId() {
        return ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

    public String getOperatingId() {
        return operatingId;
    }

    public void setOperatingId(String operatingId) {
        this.operatingId = operatingId;
    }

    public String getPlaceID() {
        return placeID;
    }

    public void setPlaceID(String placeID) {
        this.placeID = placeID;
    }

    public String getManufacturersId() {
        return manufacturersId;
    }

    public void setManufacturersId(String manufacturersId) {
        this.manufacturersId = manufacturersId;
    }

    public String getVehModelId() {
        return vehModelId;
    }

    public void setVehModelId(String vehModelId) {
        this.vehModelId = vehModelId;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public String getVehTypeId() {
        return vehTypeId;
    }

    public void setVehTypeId(String vehTypeId) {
        this.vehTypeId = vehTypeId;
    }

    public String getTerminalModelId() {
        return terminalModelId;
    }

    public void setTerminalModelId(String terminalModelId) {
        this.terminalModelId = terminalModelId;
    }

    public String getDriveModeId() {
        return driveModeId;
    }

    public void setDriveModeId(String driveModeId) {
        this.driveModeId = driveModeId;
    }

    public String getSalesAreaId() {
        return salesAreaId;
    }

    public void setSalesAreaId(String salesAreaId) {
        this.salesAreaId = salesAreaId;
    }

    public String getFirmOwnersid() {
        return firmOwnersid;
    }

    public void setFirmOwnersid(String firmOwnersid) {
        this.firmOwnersid = firmOwnersid;
    }

    public String getLinkmanId() {
        return linkmanId;
    }

    public void setLinkmanId(String linkmanId) {
        this.linkmanId = linkmanId;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public String getLic() {
        return lic;
    }

    public void setLic(String lic) {
        this.lic = lic;
    }

    public String getRuleNo() {
        return ruleNo;
    }

    public void setRuleNo(String ruleNo) {
        this.ruleNo = ruleNo;
    }

    public String getOperatingUnit() {
        return operatingUnit;
    }

    public void setOperatingUnit(String operatingUnit) {
        this.operatingUnit = operatingUnit;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getManufacturersName() {
        return manufacturersName;
    }

    public void setManufacturersName(String manufacturersName) {
        this.manufacturersName = manufacturersName;
    }

    public String getVehTypeName() {
        return vehTypeName;
    }

    public void setVehTypeName(String vehTypeName) {
        this.vehTypeName = vehTypeName;
    }

    public String getVehModelName() {
        return vehModelName;
    }

    public void setVehModelName(String vehModelName) {
        this.vehModelName = vehModelName;
    }

    public String getTerminalFirm() {
        return terminalFirm;
    }

    public void setTerminalFirm(String terminalFirm) {
        this.terminalFirm = terminalFirm;
    }

    public String getTerminalModelName() {
        return terminalModelName;
    }

    public void setTerminalModelName(String terminalModelName) {
        this.terminalModelName = terminalModelName;
    }

    public String getDriveModeName() {
        return driveModeName;
    }

    public void setDriveModeName(String driveModeName) {
        this.driveModeName = driveModeName;
    }

    public String getSalesAreaName() {
        return salesAreaName;
    }

    public void setSalesAreaName(String salesAreaName) {
        this.salesAreaName = salesAreaName;
    }

    public String getFirmOwnersName() {
        return firmOwnersName;
    }

    public void setFirmOwnersName(String firmOwnersName) {
        this.firmOwnersName = firmOwnersName;
    }

    public String getFirmOwnersPhone() {
        return firmOwnersPhone;
    }

    public void setFirmOwnersPhone(String firmOwnersPhone) {
        this.firmOwnersPhone = firmOwnersPhone;
    }

    public String getLinkmanName() {
        return linkmanName;
    }

    public void setLinkmanName(String linkmanName) {
        this.linkmanName = linkmanName;
    }

    public String getLinkmanPhone() {
        return linkmanPhone;
    }

    public void setLinkmanPhone(String linkmanPhone) {
        this.linkmanPhone = linkmanPhone;
    }

    public String getSimCard() {
        return simCard;
    }

    public void setSimCard(String simCard) {
        this.simCard = simCard;
    }

    public String getIccid() {
        return iccid;
    }

    public void setIccid(String iccid) {
        this.iccid = iccid;
    }

    public String[] getOperatingPath() {
        return operatingPath;
    }

    public void setOperatingPath(String[] operatingPath) {
        this.operatingPath = operatingPath;
    }

    public String[] getManufacturersPath() {
        return manufacturersPath;
    }

    public void setManufacturersPath(String[] manufacturersPath) {
        this.manufacturersPath = manufacturersPath;
    }

    public String[] getTerminalPath() {
        return terminalPath;
    }

    public void setTerminalPath(String[] terminalPath) {
        this.terminalPath = terminalPath;
    }

    public String[] getJurisdiction() {
        return jurisdiction;
    }

    public void setJurisdiction(String[] jurisdiction) {
        this.jurisdiction = jurisdiction;
    }

    public String[] getVehTypePath() {
        return vehTypePath;
    }

    public void setVehTypePath(String[] vehTypePath) {
        this.vehTypePath = vehTypePath;
    }

    public String[] getSalesAreaPath() {
        return salesAreaPath;
    }

    public void setSalesAreaPath(String[] salesAreaPath) {
        this.salesAreaPath = salesAreaPath;
    }

    public Date getFactoryDate() {
        return factoryDate;
    }

    public void setFactoryDate(Date factoryDate) {
        this.factoryDate = factoryDate;
    }

    public Date getFirstReg() {
        return firstReg;
    }

    public void setFirstReg(Date firstReg) {
        this.firstReg = firstReg;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    public Date getOfficialOperationData() {
        return officialOperationData;
    }

    public void setOfficialOperationData(Date officialOperationData) {
        this.officialOperationData = officialOperationData;
    }

    public Integer getVehStatus() {
        return vehStatus;
    }

    public void setVehStatus(Integer vehStatus) {
        this.vehStatus = vehStatus;
    }

    public Integer getAlarmStatus() {
        return alarmStatus;
    }

    public void setAlarmStatus(Integer alarmStatus) {
        this.alarmStatus = alarmStatus;
    }

    public Integer getOnlined() {
        return onlined;
    }

    public void setOnlined(Integer onlined) {
        this.onlined = onlined;
    }

    public Double getOfficialOperatingMil() {
        return officialOperatingMil;
    }

    public void setOfficialOperatingMil(Double officialOperatingMil) {
        this.officialOperatingMil = officialOperatingMil;
    }

    public String getVinSort() {
        return vinSort;
    }

    public void setVinSort(String vinSort) {
        this.vinSort = vinSort;
    }

    public String getLicSort() {
        return licSort;
    }

    public void setLicSort(String licSort) {
        this.licSort = licSort;
    }
}


