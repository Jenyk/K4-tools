package cz.mzk.k4.tools.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by holmanj on 20.3.15.
 * pro načtení a serializaci údajů o ročníku
 */
public class Volume implements Serializable {
    String xmlFileName;
    String year;
    String pid;
    List<Page> pages; // title, issue

    public Volume(String year, String pid) {
        this.year = year;
        this.pid = pid;
        this.pages = new ArrayList<>();
    }

    public Volume() {
        this(null,null);
    }

    public String getXmlFileName() {
        return xmlFileName;
    }

    public void setXmlFileName(String xmlFileName) {
        this.xmlFileName = xmlFileName;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public List<Page> getPages() {
        return pages;
    }

    public void setPages(List<Issue> issues) {
        this.pages = pages;
    }
}
