package com.bertazoli.charity.shared.beans.enums;

import java.util.ArrayList;
import java.util.List;

import com.bertazoli.charity.client.application.oracle.IsOracleData;


public enum CharityCategory implements IsOracleData {
    CODE30("Anglican Parishes"),
    CODE33("Baha'is Religious Groups"),
    CODE31("Baptist Congregations"),
    CODE35("Buddhist Religious Groups"),
    CODE53("Community - Charitable Corporations"),
    CODE55("Community - Charitable Trusts (Other than Service Clubs and Fraternal)"),
    CODE59("Community Organizations, (not else classified)"),
    CODE46("Convents and Monasteries"),
    CODE83("Corporation Funding Registered Canadian Amateur Athletic Association"),
    CODE22("Cultural Activities and Promotion of the Arts"),
    CODE02("Disaster Funds"),
    CODE23("Education - Charitable Corporations"),
    CODE25("Education - Charitable Trusts"),
    CODE29("Education Organizations, (not else classified)"),
    CODE75("Employees' Charity Trusts"),
    CODE13("Health - Charitable Corporations"),
    CODE15("Health - Charitable Trusts"),
    CODE19("Health Organizations, (not else classified)"),
    CODE48("Hindu Religions Groups"),
    CODE10("Hospitals"),
    CODE60("Islamic Religious Groups"),
    CODE61("Jehovah' Witnesses Congregations"),
    CODE50("Libraries, Museums and Other Repositories"),
    CODE32("Lutheran Congregations"),
    CODE34("Mennonite Congregations"),
    CODE51("Military Units"),
    CODE99("Miscellaneous Charitable Organizations, (not else classified)"),
    CODE47("Missionary Organizations and Propagation of Gospel"),
    CODE01("Organizations Providing Care Other than Treatment"),
    CODE39("Other Denominations' Congregations or Parishes, (not else classified)"),
    CODE36("Pentecostal Assemblies (Pentecostal Assemblies) of Canada only"),
    CODE37("Presbyterian Congregations"),
    CODE52("Preservation of Sites, Beauty and Historical"),
    CODE54("Protection of Animals"),
    CODE56("Recreation, Playgrounds and Vacation Camps"),
    CODE81("Registered National Arts Services Organization (RNASO)"),
    CODE43("Religion - Charitable Organizations"),
    CODE45("Religion - Charitable Trusts"),
    CODE49("Religious Organizations, (not else classified)"),
    CODE38("Roman Catholic Parishes and Chapels"),
    CODE40("Salvation Army Temples"),
    CODE63("Service Clubs and Fraternal Societies' Charitable Corporations"),
    CODE65("Service Clubs and Fraternal Societies' Projects"),
    CODE11("Services Other Than Hospitals"),
    CODE41("Seventh Day Adventist Congregations"),
    CODE62("Sikh Religious Groups"),
    CODE21("Support of Schools and Education"),
    CODE42("Synagogues"),
    CODE20("Teaching Institutions or Institutions of Learning"),
    CODE57("Temperance Associations"),
    CODE85("Trust Funding Registered Canadian Amateur Athletic Association"),
    CODE44("United Church Congregations"),
    CODE03("Welfare - Charitable Corporations"),
    CODE05("Welfare - Charitable Trusts"),
    CODE09("Welfare Organizations (not else classified)");
    
    private String description;
    CharityCategory(String description) {
        this.description = description;
    }
    public static CharityCategory getFromString(String string) {
        for (CharityCategory value : values()) {
            if (value.name().equalsIgnoreCase("CODE"+(string.length() == 1 ? "0"+string : string))) {
                return value;
            }
        }
        return null;
    }
    public String getDescription() {
        return description;
    }
    @Override
    public String id() {
        return this.name();
    }
    @Override
    public String description() {
        return description;
    }
    public static List<IsOracleData> getValues() {
        List<IsOracleData> data = new ArrayList<IsOracleData>();
        for (CharityCategory value : values()) {
            data.add(value);
        }
        return data;
    }
}
