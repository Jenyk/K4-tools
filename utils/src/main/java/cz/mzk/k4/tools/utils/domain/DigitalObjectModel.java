/*
 * Metadata Editor
 * @author Jiri Kremser
 * 
 * 
 * 
 * Metadata Editor - Rich internet application for editing metadata.
 * Copyright (C) 2011  Jiri Kremser (kremser@mzk.cz)
 * Moravian Library in Brno
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *
 * 
 */

package cz.mzk.k4.tools.utils.domain;

import java.io.Serializable;

// TODO: Auto-generated Javadoc

/**
 * The Enum KrameriusModel.
 */
public enum DigitalObjectModel
        implements Serializable {

    /** The MONOGRAPH. */
    MONOGRAPH("monograph", "", TopLevelObjectModel.MONOGRAPH),

    /** The SHEET MUSIC. */
    SHEETMUSIC("sheetmusic", "", TopLevelObjectModel.MONOGRAPH), // TODO: je to správně?

    /**
     * The MONOGRAPHUNIT.
     */
    //MONOGRAPHUNIT("monographunit", Constants.MONOGRAPH_UNIT_ICON),
    MONOGRAPHUNIT("monographunit", ""),

    /** The SOUND RECORDING. */
    SOUNDRECORDING("soundrecording", "", TopLevelObjectModel.MONOGRAPH),
    SOUND_UNIT("soundunit", ""),
    //IMAGE_UNIT("imageunit", Constants.MONOGRAPH_UNIT_ICON),
    TRACK("track", ""),

    /**
     * The PERIODICAL.
     */
    PERIODICAL("periodical", "", TopLevelObjectModel.PERIODICAL),
    NDKPERIODICAL("ndkperiodical", "", TopLevelObjectModel.NDKPERIODICAL), // fsv proarc

    /**
     * The PERIODICALVOLUME.
     */
    PERIODICALVOLUME("periodicalvolume", ""),
    NDKPERIODICALVOLUME("ndkperiodicalvolume", ""), // fsv proarc

    /**
     * The PERIODICALITEM.
     */
    PERIODICALITEM("periodicalitem", ""),
    NDKPERIODICALISSUE("ndkperiodicalissue", ""),

    /**
     * The PAGE.
     */
    PAGE("page", ""),
    NDKPAGE("ndkpage", ""),

    /** The INTERNALPART. */
    INTERNALPART("internalpart", ""), /*
                                                                 * ,
                                                                 * THESIS("thesis"
                                                                 * , "")
                                                                 */

    /** The ARTICLE. */
    ARTICLE("article", ""),

    /** The MAP */
    MAP("map", "", TopLevelObjectModel.MONOGRAPH),

    /** The MANUSCRIPT */
    MANUSCRIPT("manuscript", "", TopLevelObjectModel.MONOGRAPH),

    /** The ARCHIVE */
    ARCHIVE("archive", "", TopLevelObjectModel.MONOGRAPH),

    /** The SUPPLEMENT */
    SUPPLEMENT("supplement", ""),

    /***/
    //    SUPPLEMENT("supplement", Constants.MONOGRAPH_UNIT_ICON), 

    /**
     * TODO: add the whole RELS-EXT support
     */
    GRAPHIC("graphic", "", null),

    /**
     * Instantiates a new kramerius model.
     * 
     * @param value
     *        the value
     * @param icon
     *        the icon
     */
    ;

    public static enum TopLevelObjectModel {
        MONOGRAPH, PERIODICAL, NDKPERIODICAL;
    }

    private DigitalObjectModel(String value, String icon) {
        this.value = value;
        this.icon = icon;
    }

    private DigitalObjectModel(String value, String icon, TopLevelObjectModel topLevelType) {
        this.value = value;
        this.icon = icon;
        this.topLevelType = topLevelType;
    }

    /** The value. */
    private final String value;

    /** The icon. */
    private final String icon;

    private TopLevelObjectModel topLevelType = null;

    /**
     * Gets the value.
     * 
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * Gets the icon.
     * 
     * @return the icon
     */
    public String getIcon() {
        return icon;
    }

    /**
     * @return the topLevelType
     */
    public TopLevelObjectModel getTopLevelType() {
        return topLevelType;
    }

    /**
     * To string.
     * 
     * @param km
     *        the km
     * @return the string
     */
    public static String toString(DigitalObjectModel km) {
        return km.getValue();
    }

    /**
     * Parses the string.
     * 
     * @param s
     *        the s
     * @return the model
     */
    public static DigitalObjectModel parseString(String s) {
        DigitalObjectModel[] values = DigitalObjectModel.values();
        for (DigitalObjectModel model : values) {
            if (model.getValue().equalsIgnoreCase(s)) return model;
        }
        throw new RuntimeException("Unsupported type: " + s);
    }

    public static DigitalObjectModel getModel(int ordinal) {
        for (DigitalObjectModel model : values()) {
            if (ordinal == model.ordinal()) return model;
        }
        return null;
    }

}
